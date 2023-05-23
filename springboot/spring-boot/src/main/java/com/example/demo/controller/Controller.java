package com.example.demo.controller;

import com.example.demo.entity.*;

import com.example.demo.response.ResponseMyBody;
import com.example.demo.response.ResponseCode;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

//主程序
@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Controller {
    private final UserService service;
    private final CourseService courseService;
    private final CommentService commentService;
    private final CourseCommentService courseCommentService;
    private final DislikeService dislikeService;
    private final LikeService likeService;
    private final NewService newService;
    private final PostService postService;

    @PersistenceContext
    private EntityManager entityManager;
    @PostMapping("user/login")//注册登录
    public Object signInUp(@RequestBody User user) {
        if (service.exists(user)) {
            User u = service.findByNameAndPassword(user);
            return u!=null?u:ResponseCode.Login_Failed;
        }
        else {
            User u=service.insert(user);
            return u!=null ?u:ResponseCode.User_Create_Failed;
        }
    }
    @PutMapping("user/update")//用户更新
    public ResponseMyBody update(@RequestBody User user) {
        return new ResponseMyBody(service.update(user) ? ResponseCode.User_Update_Success : ResponseCode.User_Update_Failed, "");
    }

    @DeleteMapping("user/delete")//用户删除
    public ResponseMyBody deleteByName(@RequestParam int id) {
        return new ResponseMyBody(service.deleteById(id) ? ResponseCode.User_Delete_Success : ResponseCode.User_Delete_Failed, "");
    }

    @PostMapping("course/add")//添加课程
    public ResponseMyBody addCourse(@RequestBody Course tt) {
        if (courseService.exists(tt)) {
            return new ResponseMyBody(  ResponseCode.Course_Create_Failed,  "");
        }
        return new ResponseMyBody(courseService.insert(tt)!=null ? ResponseCode.Course_Create_Success : ResponseCode.Course_Create_Failed, "");
    }
    @GetMapping("course/get")
    public Object getCourse() {
        return courseService.getAllCourse();
    }
    @GetMapping("course/search")
    public Object searchCourse() {
        //模糊搜索，等待实现
        return courseService.getAllCourse();
    }

    @GetMapping("post/get")
    public Object getPost() {
        return postService.getAll();
    }
    @GetMapping("post/my")
    public Object getMyPost() {
        //@RequestParam int id
        return postService.getAll();
    }

    @GetMapping("post/search")
    public Object searchPost() {
        //模糊搜索，等待实现
        return postService.getAll();
    }

    @PostMapping("post/create")
    public Object createPost(@RequestBody Post a) {
        return postService.insert(a);
    }


    @DeleteMapping("post/delete")
    public Object deletePost() {
        //等待实现 删除有许多关联操作，比如新消息回复，这些该怎么处理
        return postService.getAll();
    }

    @PostMapping("comment/add")
    public Object addComment(){
        return postService.getAll();
    }
    @DeleteMapping("comment/delete")
    public Object deleteComment() {
        //等待实现
        return postService.getAll();
    }
    @GetMapping("comment/my")
    public Object getMyComment() {
        //@RequestParam int id 获取课程评论和帖子评论
        return postService.getAll();
    }
    @PostMapping("courseComment/add")
    public Object addCComment(){
        return postService.getAll();
    }
    @DeleteMapping("courseComment/delete")
    public Object deleteCComment() {
        //等待实现
        return postService.getAll();
    }
    @PostMapping("like/add")
    public Object addLike(@RequestBody myLike a) {
        return likeService.insert(a);
    }
    @GetMapping("like/my")
    public Object getMyLike() {
        //@RequestParam int id
        return postService.getAll();
    }
    @DeleteMapping("like/delete")
    public Object deleteLike(@RequestBody myLike a) {
        return likeService.delete(a);
    }
    @PostMapping("dislike/add")
    public Object addDislike(@RequestBody Dislike a) {
        return dislikeService.insert(a);
    }

    @DeleteMapping("dislike/delete")
    public Object deleteDislike(@RequestBody Dislike a){
        return dislikeService.delete(a);
    }

    @PutMapping("new/change")
    public Object changeNew(@RequestBody New a) {
        return newService.update(a);//return true;
    }

    @GetMapping("new/get")
    public Object getNew(@RequestParam("id") int id){
        return newService.getByUserId(id);
    }

    @PostMapping("image/upload")
    public String handleFileUpload(@RequestParam("file") List<MultipartFile> files,@RequestParam("id") Long id) {
        if (files.isEmpty()) {
            // 文件列表为空，返回错误提示
            return "No files uploaded";
        }
        int i=1;
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                // 文件为空，返回错误提示
                return "File is empty";
            }
            try {
                // 获取文件名和扩展名
                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                // 生成新的文件名
                String newFilename = i + fileExtension;
                // 保存文件到本地文件系统
                Path filePath = Paths.get("/root/data/"+id.toString()+"/", newFilename);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // 处理异常，返回错误提示
                return "Failed to upload file: " + e.getMessage();
            }
        }

        // 返回成功提示
        return "Files successfully uploaded";
    }

    @RequestMapping(value = "image/get", produces ={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getPhotos(@RequestParam("id") Long id) throws IOException {
        File directory = new File("/root/data/" + id);
        if (!directory.isDirectory()) {
            throw new FileNotFoundException(Integer.toString(ResponseCode.No_Image));
        }

        List<byte[]> imageBytesList = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.isFile() &&( file.getName().endsWith(".jpg")||file.getName().endsWith(".png")) ){
                FileInputStream inputStream = new FileInputStream(file);
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes, 0, inputStream.available());
                imageBytesList.add(bytes);
            }
        }

        int totalBytesLength = imageBytesList.stream().mapToInt(bytes -> bytes.length).sum();
        byte[] totalBytes = new byte[totalBytesLength];
        int destPos = 0;
        for (byte[] bytes : imageBytesList) {
            System.arraycopy(bytes, 0, totalBytes, destPos, bytes.length);
            destPos +=bytes.length;
        }

        return totalBytes;
    }


    @GetMapping("test")
    public String test() {
        return "test";
    }

    @GetMapping("test/test")
    public Object test1() {
        myLike a=new myLike();

        a.setUserId(10086);
        a.setCommentId(888);

        return likeService.delete(a);
    }
    @GetMapping("test/test1")
    public Object test11() {
        return commentService.getAll();
    }

    @GetMapping("test2/{id}/{orderId}")//访问test2/123/456
    public Object test2(@PathVariable Long id, @PathVariable Long orderId) {
       //这个是 path参数读取方法
        return  id.toString()+orderId.toString();
    }
    @GetMapping("test3")// 访问test3?id=123可以查看效果
    public Object test3(@RequestParam Long id) {
    //这个是query参数
        return id.toString();
    }

    @GetMapping("test4")//复杂逻辑要用sql,不能自动生成
    public Object test4(){
        String sql ="SELECT * FROM my_like";
        //String sql = "SELECT * FROM course INNER JOIN user ON course.teacher_name = user.user_name";
        Query query = entityManager.createNativeQuery(sql, myLike.class);//指定返回类型
        List<myLike> courses = query.getResultList();
        String a="ss";
        return courses;
    }

}
