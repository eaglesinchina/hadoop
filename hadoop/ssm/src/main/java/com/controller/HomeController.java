package com.controller;

import com.bean.User;
import com.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class HomeController {

    //属性
    @Resource
    UserServiceImpl service;

        @RequestMapping("/index")
        public String index(){
            System.out.println("hello world");
            return "jsp2/a";
        }

    @RequestMapping("/insert")
    public String insert(){

        System.out.println("insert 表");
        User user = new User(23, "a", "girl");

        int res = service.insert(user);
        System.out.println("res=="+res);
        return "jsp2/a";
    }

    @RequestMapping("/select")
    public String selectOne(ModelMap map, int id){
        System.out.println("select one.....");

        User user = service.selectById(id);

        map.put("user",user);

        System.out.println(user);
        return "jsp2/user";
    }
    @RequestMapping("/selectAll")
    public String selectAll(ModelMap map){
        System.out.println("select all.....");

        List<User> userList = service.selectAll();

        map.put("userList",userList);

        System.out.println(userList);
        return "userList" ;
    }
}
