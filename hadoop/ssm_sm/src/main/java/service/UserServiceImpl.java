package service;

import a.User;
import dao.UserDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserServiceImpl {

    @Autowired
    UserDaoImpl dao;

    public int insert(User user){
       return dao.insert(user);
    }
}
