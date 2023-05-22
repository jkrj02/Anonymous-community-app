package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Course;

import com.example.demo.response.ResponseBody;
import com.example.demo.response.ResponseCode;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

//主程序
@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Controller {
    private final UserService service;
    private final CourseService courseService;
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
    public ResponseBody update(@RequestBody User user) {
        return new ResponseBody(service.update(user) ? ResponseCode.User_Update_Success : ResponseCode.User_Update_Failed, "");
    }

    @DeleteMapping("user/delete")//用户删除
    public ResponseBody deleteByName(@RequestParam int id) {
        return new ResponseBody(service.deleteById(id) ? ResponseCode.User_Delete_Success : ResponseCode.User_Delete_Failed, "");
    }

    @PostMapping("course/add")//添加课程
    public ResponseBody addCourse(@RequestBody Course tt) {
        if (courseService.exists(tt)) {
            return new ResponseBody(  ResponseCode.Course_Create_Failed,  "");
        }
        return new ResponseBody(courseService.insert(tt)!=null ? ResponseCode.Course_Create_Success : ResponseCode.Course_Create_Failed, "");
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



    @GetMapping("test")
    public String test() {
        return "test";
    }

    @GetMapping("test/test")
    public Object test1() {
        return service.getAllUsers();
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
    @PersistenceContext
    private EntityManager entityManager;
    @GetMapping("test4")//复杂逻辑要用sql,不能自动生成
    public Object test4(){

        String sql = "SELECT * FROM course INNER JOIN user ON course.teacher_name = user.user_name";
        Query query = entityManager.createNativeQuery(sql, Course.class);//指定返回类型
        List<Course> courses = query.getResultList();
        return courses;
    }

}
