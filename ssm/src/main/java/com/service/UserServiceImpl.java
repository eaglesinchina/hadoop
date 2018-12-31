package com.service;

import com.bean.User;
import com.dao.UserDaoImpl;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl {

    @Autowired
    UserDaoImpl dao;

    public int insert(User user){
       return dao.insert(user);
    }

    public User selectById(int id){
        return dao.selectById(id);
    }

    public List<User> selectAll() {
        return dao.selectAll();
    }

    public void delete(int id) {
        dao.delete(id);
    }

    public void update(User user) {
        dao.update(user);
    }

    /**
     * 分页查询
     * @param rowBounds
     */
    public List<User> selectAllLimit(RowBounds rowBounds) {

        return  dao.selectAllLimit(rowBounds);
    }

    public int selectPageCount() {
        return  dao.selectPageCount();
    }
}
