package com.example.demo.controller;

import com.example.demo.entity.*;

import com.example.demo.response.ResponseCode;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.websocket.server.PathParam;
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
    @PostMapping("user/login")//登录
    public Object signInUp(@RequestBody User user) {
        if (service.exists(user)) {
            User u = service.findByNameAndPassword(user);
            return u!=null?u: new ResponseEntity <> ("密码错误", HttpStatus.FORBIDDEN);
        }
        else {
           // User u=service.insert(user);
            return new ResponseEntity <> ("用户不存在", HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("user/create")//创建
    public Object userCreate(@RequestBody User user) {
        if (service.exists(user)) {
            return  new ResponseEntity <> ("用户名已存在", HttpStatus.FORBIDDEN);
        }
        else {
            User u=service.insert(user);
            return u!=null ?u:new ResponseEntity <> ("创建失败", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("user/update")//用户更新
    public boolean update(@RequestBody User user) {
        return service.update(user) ? true : false;
    }

    @DeleteMapping("user/delete/{id}")//用户删除
    public boolean deleteById(@PathVariable int id) {

        return service.deleteById(id) ? true : false;
    }

    @PostMapping("course/add")//添加课程
    public Object addCourse(@RequestBody Course tt) {
        if (courseService.exists(tt)) {
            return  new ResponseEntity <> ("课程已存在", HttpStatus.FORBIDDEN);
        }
        return courseService.insert(tt)!=null ?new ResponseEntity <> ("创建成功", HttpStatus.OK): new ResponseEntity <> ("创建失败", HttpStatus.FORBIDDEN);
    }
    @GetMapping("course/get")
    public Object getCourse() {
        return courseService.getAllCourse();
    }
    @GetMapping("course/search/{keyword}")
    public Object searchCourse(@PathVariable String keyword) {
        String sql = "SELECT * FROM course where course_name like '%";
        sql += keyword;
        sql += "%' or teacher_name like '%";
        sql += keyword;
        sql += "%'";
        Query query = entityManager.createNativeQuery(sql, Course.class);//指定返回类型
        List<Course> courses = query.getResultList();
        return courses;
    }

    @GetMapping("course/main/{courseId}/{userId}")
    public Object courseMain(@PathVariable int courseId, @PathVariable int userId) {
        if(!courseService.existById(courseId))
        {
            return new ResponseEntity <> ("该课程已删除", HttpStatus.FORBIDDEN);
        }
        Iterable<CourseComment> comments = courseCommentService.findByCourseId(courseId);

        //查询每个comment，根据comment_id和user_id确定用户是否对其点赞
        class returncomment
        {
            public CourseComment thiscomment;
            public boolean islike;
            public boolean dislike;
        }
        Iterable<returncomment> a = new ArrayList<>();;

       for(CourseComment temp :  comments)
       {
           returncomment temp2 = new returncomment();
           temp2.thiscomment = temp;

           int commentId = temp.getCommentId();
           String sql2 = "SELECT * FROM my_like where course_comment_id = ";
           sql2 +=  String.valueOf(commentId);
           sql2 += " and user_id = ";
           sql2 +=  String.valueOf(userId);
           Query query2 = entityManager.createNativeQuery(sql2, myLike.class);//指定返回类型
           List<myLike> aaa = query2.getResultList();

           if(aaa.isEmpty())
               temp2.islike = false;
           else
               temp2.islike = true;

           String sql3 = "SELECT * FROM dislike where course_comment_id = ";
           sql3 +=  String.valueOf(commentId);
           sql3 += " and user_id = ";
           sql3 +=  String.valueOf(userId);
           Query query3 = entityManager.createNativeQuery(sql3, Dislike.class);//指定返回类型
           List<Dislike> aaaa = query2.getResultList();

           if(aaaa.isEmpty())
               temp2.dislike = false;
           else
               temp2.dislike= true;
            ((ArrayList<returncomment>) a).add(temp2);
       }
        return a;
    }

    @DeleteMapping("course/delete/{id}")//课程删除
    public boolean deleteByCourseId(@PathVariable int id) {
        return courseService.deleteById(id) ? true : false;
    }

    @GetMapping("post/get")
    public Object getPost() {
        return postService.getAll();
    }

     @GetMapping("post/my/{userid}")
    public Object getMyPost(@PathVariable int userid) {
        //@RequestParam int id
        String sql = "SELECT * FROM post where user_id =";
        sql += String.valueOf(userid);
        Query query = entityManager.createNativeQuery(sql, Post.class);//指定返回类型
        List<Post> posts = query.getResultList();
        return posts;
    }  
    
    @GetMapping("post/search/{keyword}")
    public Object searchPost(@PathVariable String keyword) {
       String sql = "SELECT distinct post.* FROM post LEFT JOIN comment ON post.post_id = comment.post_id " +
                "where post.user_name like '%";
        sql += keyword;
        sql += "%' or post.content like '%";
        sql += keyword;
        sql += "%' or comment.user_name like '%";
        sql += keyword;
        sql += "%' or comment.content like '%";
        sql += keyword;
        sql += "%'";
        Query query = entityManager.createNativeQuery(sql, Post.class);//指定返回类型
        List<Post> posts = query.getResultList();
        return posts;
    }

    @PostMapping("post/create")
    public Object createPost(@RequestBody Post a) {
        return postService.insert(a);
    }

     @DeleteMapping("post/delete")
    public boolean deletePost(@RequestParam int postid) {
        return postService.deleteById(postid);
    }
    
    @GetMapping("post/main/{postId}/{userId}")
    public Object postMain(@PathVariable int postId, @PathVariable int userId) {
        if(!postService.exist(postId))
        {
            return new ResponseEntity <> ("帖子已删除", HttpStatus.BAD_REQUEST);
        }
     String sql = "SELECT * FROM comment LEFT JOIN post ON post.post_id = comment.post_id " +
                "where post.post_id = ";
        sql += String.valueOf(postId);
        Query query = entityManager.createNativeQuery(sql, Comment.class);//指定返回类型
        List<Comment> comments = query.getResultList();
        //查询每个comment，根据comment_id和user_id确定用户是否对其点赞

        class returncomment
        {
            public Comment thiscomment;
            public boolean islike;
            public boolean dislike;
        }
        class finalreturncomment
        {
            public Post thispost;
            public boolean postislike;
            public boolean postdislike;
            public List<returncomment> recomments = new ArrayList<returncomment>();
        }
        finalreturncomment fretinfo = new finalreturncomment();

        String sql4 = "SELECT * FROM post where post_id = ";
        sql4 += String.valueOf(postId);
        Query query4 = entityManager.createNativeQuery(sql4, Post.class);//指定返回类型
        List<Post> posts = query4.getResultList();

        fretinfo.thispost = posts.get(0);

        String sql5 = "SELECT * FROM my_like where post_id = ";
        sql5 +=  String.valueOf(postId);
        sql5 += " and user_id = ";
        sql5 +=  String.valueOf(userId);
        Query query5 = entityManager.createNativeQuery(sql5, myLike.class);//指定返回类型
        List<myLike> postmyLikes = query5.getResultList();
        if(postmyLikes.isEmpty())
            fretinfo.postislike = false;
        else
            fretinfo.postislike = true;

        String sql6 = "SELECT * FROM dislike where post_id = ";
        sql6 +=  String.valueOf(postId);
        sql6 += " and user_id = ";
        sql6 +=  String.valueOf(userId);
        Query query6 = entityManager.createNativeQuery(sql6, Dislike.class);//指定返回类型
        List<Dislike> postDislikes = query6.getResultList();
        if(postDislikes.isEmpty())
            fretinfo.postdislike = false;
        else
            fretinfo.postdislike = true;
        //List<returncomment> returncomments = new ArrayList<>();
       for(Comment temp :  comments)
       {
           returncomment temp2 = new returncomment();
           temp2.thiscomment = temp;

           int commentId = temp.getCommentId();
           String sql2 = "SELECT * FROM my_like where comment_id = ";
           sql2 +=  String.valueOf(commentId);
           sql2 += " and user_id = ";
           sql2 +=  String.valueOf(userId);
           Query query2 = entityManager.createNativeQuery(sql2, myLike.class);//指定返回类型
           List<myLike> myLikes = query2.getResultList();
           if(myLikes.isEmpty())
               temp2.islike = false;
           else
               temp2.islike = true;

           String sql3 = "SELECT * FROM dislike where comment_id = ";
           sql3 +=  String.valueOf(commentId);
           sql3 += " and user_id = ";
           sql3 +=  String.valueOf(userId);
           Query query3 = entityManager.createNativeQuery(sql3, Dislike.class);//指定返回类型
           List<Dislike> Dislikes = query3.getResultList();
           if(Dislikes.isEmpty())
               temp2.dislike = false;
           else
               temp2.dislike= true;
           fretinfo.recomments.add(temp2);
           //.add(temp2);
       }
        return fretinfo;
    }


    @Transactional
    @PostMapping("comment/add")
    public Object addComment(@RequestBody Comment a){

        if(!postService.exist(a.getPostId()))
        {
            return new ResponseEntity <> ("添加失败，已被删除", HttpStatus.FORBIDDEN);
        }
        New new_t=new New();
        new_t.setNewRead(false);
        new_t.setType(1);
        new_t.setOtherName(a.getUserName());
        new_t.setOtherId(a.getUserId());
        new_t.setContent(a.getContent());
        new_t.setPostId(a.getPostId());
        new_t.setSubContent(postService.getPostContentById(a.getPostId()));
        if (a.getObjectId()==0){
            new_t.setUserId(postService.getUserIDById(a.getPostId()));
        } else{
            new_t.setUserId(commentService.getUserIDById(a.getObjectId()));
            String sql="update comment set comment_count = comment_count + 1 where comment_id = "+String.valueOf(a.getObjectId());
            entityManager.createNativeQuery(sql).executeUpdate();
        }
        new_t.setCourseId(0);

        newService.insert(new_t);
        return commentService.insert(a);
    }

    @Transactional
    @DeleteMapping("comment/delete")
    public Object deleteComment(@RequestParam int commentid) {
        String SQL = "delete from my_like where comment_id = "+String.valueOf(commentid);
        entityManager.createNativeQuery(SQL).executeUpdate();

        String sql1 = "update comment set user_id = 0 where comment_id="+String.valueOf(commentid);
        Query query1=entityManager.createNativeQuery(sql1);
        query1.executeUpdate();
        String sql = "update comment set valid = 0 where comment_id="+String.valueOf(commentid);
        Query query=entityManager.createNativeQuery(sql);
        return query.executeUpdate();
    }
    @GetMapping("comment/my")
    public Object getMyComment(@RequestParam int id) {
        String sql="select * from comment where user_id=";
        sql += String.valueOf(id);
        Query query = entityManager.createNativeQuery(sql, Comment.class);//指定返回类型
        List<Comment> comments = query.getResultList();
        sql = "select * from course_comment where user_id = " + String.valueOf(id);
        query = entityManager.createNativeQuery(sql,CourseComment.class);
        comments.addAll(query.getResultList());
        return comments;
    }
    @Transactional
    @PostMapping("courseComment/add")
    public Object addCComment(@RequestBody CourseComment a){
        if(!courseService.existById(a.getCourseId()))
        {
            return new ResponseEntity <> ("添加失败，已被删除", HttpStatus.FORBIDDEN);
        }
        if (a.getObjectId()!=0){
            New new_t=new New();
            new_t.setNewRead(false);
            new_t.setType(1);
            new_t.setOtherName(a.getUserName());
            new_t.setOtherId(a.getUserId());
            new_t.setContent(a.getContent());
            new_t.setSubContent(courseService.getNameById(a.getCourseId()));
            new_t.setCourseId(a.getCourseId());
            new_t.setUserId(courseCommentService.getUserIDById(a.getObjectId()));
            String sql="update course_comment set comment_count = comment_count + 1 where comment_id = "+String.valueOf(a.getObjectId());
            entityManager.createNativeQuery(sql).executeUpdate();
            new_t.setPostId(0);
            newService.insert(new_t);
        }
        return courseCommentService.insert(a);
    }
    @Transactional
    @DeleteMapping("courseComment/delete")
    public Object deleteCComment(@RequestParam int courseCommentid) {
        String SQL = "delete from my_like where course_comment_id = "+String.valueOf(courseCommentid);
        entityManager.createNativeQuery(SQL).executeUpdate();

        String sql = "update course_comment set valid = 0 where comment_id="+String.valueOf(courseCommentid);
        Query query=entityManager.createNativeQuery(sql);
        return query.executeUpdate();
    }

    @PostMapping("like/add")//点赞
    public Object addLike(@RequestBody myLike a) {//传入一个点赞对象
        if(likeService.exist(a))//判断是否已点赞
        {
            return new ResponseEntity <> ("已点赞" , HttpStatus.FORBIDDEN);
        }
        New new_t=new New();
        new_t.setNewId(0);
        new_t.setNewRead(false);
        new_t.setType(0);
        new_t.setOtherName(service.getNameById(a.getUserId()));
        new_t.setOtherId(a.getUserId());
        new_t.setContent(a.getInfo());
        new_t.setSubContent(null);
        if (a.getPostId()!=0) {//点赞帖子
            new_t.setUserId(postService.getUserIDById(a.getPostId()));
            new_t.setCourseId(0);
            new_t.setPostId(a.getPostId());
        }
        else if(a.getCourseCommentId()!=0)//点赞课程评价
        {
            new_t.setUserId(courseCommentService.getUserIDById(a.getCourseCommentId()));
            new_t.setCourseId(a.getCourseCommentId());
            new_t.setPostId(0);
        }else//点赞评论
        {
            new_t.setUserId(commentService.getUserIDById(a.getCommentId()));
            new_t.setCourseId(0);
            new_t.setPostId(commentService.getPostIDById(a.getCommentId()));
        }
        newService.insert(new_t);
        return likeService.insert(a);
    }
    @GetMapping("like/my/{id}")
    public Object getMyLike(@PathVariable int id) {
      return newService.getByByOtherId(id,0);
    }
    @DeleteMapping("like/delete")
    public Object deleteLike(@RequestBody myLike a) {
        return likeService.delete(a);
    }
    @PostMapping("dislike/add")
    public Object addDislike(@RequestBody Dislike a) {
        if(dislikeService.exist(a))
        {
            return new ResponseEntity <> ("已点踩" , HttpStatus.FORBIDDEN);
        }
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

    @GetMapping("new/get/{id}")
    public Object getNew(@PathVariable int id){
        return newService.getByUserId(id);
    }

    @PostMapping("image/post/upload")
    public String imageUpload1(@RequestParam("file") List<MultipartFile> files,@RequestParam("id")int id) {
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
                String fileExtension = "";
                int lastIndex = originalFilename.lastIndexOf(".");
                if (lastIndex > 0) {
                    fileExtension = originalFilename.substring(lastIndex);
                }

                // 生成新的文件名
                String newFilename = i + fileExtension;
                // 保存文件到本地文件系统
                Path filePath = Paths.get("/root/data/post/"+id+"/", newFilename);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // 处理异常，返回错误提示
                return "Failed to upload file: " + e.getMessage();
            }
        }

        // 返回成功提示
        return "Files successfully uploaded";
    }
    @PostMapping("/image/user/upload")
    public Object imageUpload2(@RequestParam("file") MultipartFile file, @RequestParam("id") int id) {

        try {
            // 获取文件名和扩展名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            int lastIndex = originalFilename.lastIndexOf(".");
            if (lastIndex > 0) {
                fileExtension = originalFilename.substring(lastIndex);
            }
            // 生成新的文件名
            String newFilename =1 + fileExtension;
            // 保存文件到本地文件系统
            Path filePath = Paths.get("/root/data/user/" + id + "/" + newFilename);
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // 处理异常，返回错误提示
            return  new ResponseEntity <> ("上传失败 " + e.getMessage(), HttpStatus.FORBIDDEN);
        }
        // 返回成功提示
        return "上传成功";
    }

    @RequestMapping(value = "image/get", produces ={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public Object getPhotos(@RequestParam("id") int id)  {
        File directory = new File("/root/data/post/" + id);
        if (!directory.isDirectory()) {
            return new ResponseEntity<>("文件不存在", HttpStatus.FORBIDDEN);
        }

        List<byte[]> imageBytesList = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.isFile() &&( file.getName().endsWith(".jpg")||file.getName().endsWith(".png")) ){
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes, 0, inputStream.available());
                    imageBytesList.add(bytes);
                } catch (IOException e) {
                    return new ResponseEntity<>("文件不存在", HttpStatus.FORBIDDEN);
                }
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

    @RequestMapping(value = "/image/get2/{id}/{imgUrl:[a-zA-Z0-9_.]+}", produces ={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public Object getPhoto2(@PathVariable("id") int id, @PathVariable("imgUrl") String imgUrl) {
        File file = new File("/root/data/user/" + id + "/" + imgUrl);
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes, 0, inputStream.available());
                return bytes;
            } catch (IOException e) {
                // 处理读取文件异常
                return new ResponseEntity<>("文件不存在", HttpStatus.FORBIDDEN);
            }
        } else {
            // 文件不存在，返回错误信息
            return new ResponseEntity<>("文件不存在", HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("test")
    public String test() {
        return "test";
    }
    @GetMapping("test/user/login")
    public Object testUserLogin() {
        User a=new User();
        a.setPassword("123456");
        a.setAdmin(true);
        a.setUserId(546);
        a.setGender(true);
        a.setUserName("wang99");

        return signInUp(a);
    }
    @GetMapping("test/new")
    public Object testnew() {

        return newService.getAll();
    }


    @GetMapping("test1")
    public  Object test111(){
        return newService.getAll();
    }

    @GetMapping("test/test")
    public Object test1() {
        New new_t=new New();
        new_t.setNewId(0);
        new_t.setNewRead(false);
        new_t.setType(0);
        new_t.setOtherName(service.getNameById(520));
        new_t.setContent("hhhh");
        new_t.setSubContent(null);
            new_t.setUserId(postService.getUserIDById(1));
            new_t.setCourseId(0);
            new_t.setPostId(1);


        newService.insert(new_t);
        return new_t;
    }
    @GetMapping("test/test1")
    public Object test11() {
        myLike a= new myLike();
        a.setPostId(1);
        a.setLikeId(0);
        a.setUserId(520);
        a.setInfo("太酷了");
        a.setCourseCommentId(0);
        a.setCommentId(0);
        addLike(a);
        return "test add like and new";
        //return commentService.getAll();
    }



    @GetMapping("test2/{id}/{orderId}")//访问test2/123/456
    public Object test2(@PathVariable Long id, @PathVariable Long orderId) {
       //这个是 path参数读取方法
        return  id.toString()+orderId.toString();
    }
    @GetMapping("test3")// 访问test3?id=123可以查看效果
    public Object test3(@RequestParam int id) {
    //这个是query参数
        return deleteById(id);
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
    @GetMapping("/test/image")//测试上传图片
    public String testImage(@RequestParam int id) {
        try {
            Path imagePath = Paths.get("D:/Desktop/2.jpg");
            byte[] imageData = Files.readAllBytes(imagePath);
            MultipartFile file = new MockMultipartFile("1.jpg","1.jpg",MediaType.IMAGE_JPEG_VALUE, imageData);
            imageUpload2(file, id);
            return "Test upload image";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload image: " + e.getMessage();
        }
    }
}
