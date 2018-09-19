package com.controller;

import com.bean.User;
import com.service.UserServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController3 {

    //属性
    @Resource
    UserServiceImpl service;
    int pageSize=5;
    int totalPages;
    int endPage;


    /**
     * 删除
     */
    @RequestMapping("/selectAll3")
    public String selectAll3(ModelMap map, int pageNo){
        System.out.println("selectAll3...pageNo="+pageNo);

        //每页的数据
        int start=(pageNo-1)*pageSize;
        RowBounds rowBounds = new RowBounds(start, pageSize);
        List<User> userList=service.selectAllLimit(rowBounds);
        map.put("userList", userList);


        //总页数
        totalPages=service.selectPageCount();
        endPage=(totalPages%pageSize ==0
                ? (totalPages/pageSize)
                : (totalPages/pageSize +1) );
        map.put("endPage",endPage);
        return "userList3";
    }

    @RequestMapping("/showUsers3")
    public String selectAll(ModelMap map, @RequestParam(value = "pageNo",defaultValue = "1") int pageNo ){
        System.out.println("showUsers3....pageNo="+pageNo);

        //每页的数据
        int start=(pageNo-1)*pageSize;
        RowBounds rowBounds = new RowBounds(start, pageSize);
        List<User> userList=service.selectAllLimit(rowBounds);
        map.put("userList",userList);

        //总页数
        totalPages=service.selectPageCount();
        endPage=(totalPages%pageSize ==0
                ? (totalPages/pageSize)
                : (totalPages/pageSize +1) );
        map.put("endPage",endPage);

        System.out.println(userList);
        return "userList3" ;
    }

    //----------------------
    @RequestMapping("/index3")
    public String index(){
        System.out.println("index3.....");
        return "redirect:/home/showUsers3";
    }

    @RequestMapping("/insert3")
    public String insert(){

        System.out.println("insert3 表");
        User user = new User(23, "a", "girl");

        int res = service.insert(user);
        System.out.println("res=="+res);
        return "redirect:/home/showUsers3";
    }


//查询: 一个用户
    @RequestMapping("/select3")
    public String selectOne(ModelMap map, int id){
        System.out.println("select3 one.....");

        User user = service.selectById(id);

        map.put("user",user);

        System.out.println(user);
        return "jsp2/user";
    }
    /**
     * 更新
     */
    @RequestMapping("/update3")
    public String update2(ModelMap map, int id){
        System.out.println("update3...");

        User user = service.selectById(id);
        map.put("user",user);
        return "forms/updateForm3";
    }
    /**
     * 更新
     */
    @RequestMapping("/doUpdate3")
    public String doupdate2(User user){
        System.out.println("处理更新..   update3..."+user);

        service.update(user);
        return "redirect:/home/showUsers3";
    }

    /**
     * add添加
     */
    @RequestMapping("/doAdd3")
    public String doAdd2(User user){
        System.out.println("处理add3..  ..."+user);

        service.insert(user);
        return "redirect:/home/showUsers3";
    }
    /**
     * add添加
     */
    @RequestMapping("/add3")
    public String add2(User user){
        System.out.println("add3..  ..."+user);

        return "forms/addForm3";
    }

    /**
     * 删除
     */
    @RequestMapping("/delete3")
    public String delete2(int id){
        System.out.println("delete3...");
        service.delete(id);
        return "redirect:/home/showUsers3";
    }
}
