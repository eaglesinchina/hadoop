package com.controller;

import com.bean.User;
import com.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController2 {

    //属性
    @Resource
    UserServiceImpl service;

        @RequestMapping("/index2")
        public String index(){
            System.out.println("hello world");
            return "jsp2/a";
        }

    @RequestMapping("/insert2")
    public String insert(){

        System.out.println("insert 表");
        User user = new User(23, "a", "girl");

        int res = service.insert(user);
        System.out.println("res=="+res);
        return "jsp2/a";
    }

    @RequestMapping("/select2")
    public String selectOne(ModelMap map, int id){
        System.out.println("select one.....");

        User user = service.selectById(id);

        map.put("user",user);

        System.out.println(user);
        return "jsp2/user";
    }
    @RequestMapping("/selectAll2")
    public String selectAll(ModelMap map){
        System.out.println("select all2.....");

        List<User> userList = service.selectAll();
        map.put("userList",userList);

        System.out.println(userList);
        return "userList2" ;
    }

    /**
     * 更新
     */
    @RequestMapping("/update2")
    public String update2(ModelMap map, int id){
        System.out.println("update2...");

        User user = service.selectById(id);
        map.put("user",user);
        return "forms/updateForm";
    }
    /**
     * 更新
     */
    @RequestMapping("/doUpdate2")
    public String doupdate2(User user){
        System.out.println("处理更新..   update2..."+user);

        service.update(user);
        return "redirect:/home/selectAll2";
    }

    /**
     * add添加
     */
    @RequestMapping("/doAdd2")
    public String doAdd2(User user){
        System.out.println("处理add2..  ..."+user);

        service.insert(user);
        return "redirect:/home/selectAll2";
    }
    /**
     * add添加
     */
    @RequestMapping("/add2")
    public String add2(User user){
        System.out.println("add2..  ..."+user);

        return "forms/addForm";
    }

    /**
     * 删除
     */
    @RequestMapping("/delete2")
    public String delete2(int id){
        System.out.println("delete2...");
        service.delete(id);
        return "redirect:/home/selectAll2";
    }
}
