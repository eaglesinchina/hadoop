package dao;

import a.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
@Repository("dao")
public class UserDaoImpl extends SqlSessionDaoSupport {

    public int insert(User user){
        int res = getSqlSession().insert("users.insert",user);
        return res;
    }

    @Resource
    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
}
