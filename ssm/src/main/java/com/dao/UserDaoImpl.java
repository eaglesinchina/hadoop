package com.dao;

import com.bean.User;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("dao")
public class UserDaoImpl extends SqlSessionDaoSupport {

    public int insert(User user){
        int res = getSqlSession().insert("users.insert",user);
        return res;
    }

    public User selectById(int id){
        User user = getSqlSession().selectOne("users.selectById",id);
        return user;
    }

    public List<User> selectAll() {
        return  getSqlSession().selectList("users.selectAll");
    }


    @Resource
    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public void delete(int id) {
        getSqlSession().delete("users.delete",id);
    }

    public void update(User user) {
        getSqlSession().update("users.update",user);
    }

    /**
     * 分页查询
     * @param rowBounds
     * @return
     */
    public List<User> selectAllLimit(RowBounds rowBounds) {
        return getSqlSession().selectList("users.selectAllLimit",rowBounds);
    }

    public int selectPageCount() {
        return getSqlSession().selectOne("users.selectPageCount");
    }
}
