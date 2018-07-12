package com.xqs.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Random;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Config {
    public static void main(String[] args) throws IOException {
        String resource = "com/xqs/mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(inputStream);

        // selectUser2Map(factory);
        // selectUser(factory);
        // update(factory);
        // testFirstLevelCache(factory);
        testSecondLevelCache(factory);
    }

    public static void selectUser2Map(SqlSessionFactory factory) {
        SqlSession session = factory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        Map<String, Object> result = userMapper.selectUser2Map(1);
        System.out.println(result.toString());
        session.commit();
        session.close();
    }

    public static void selectUser(SqlSessionFactory factory) {
        SqlSession session = factory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        User user = userMapper.selectUser(1);
        System.out.println("User is: " + user.toString());
        session.commit();
        session.close();
    }

    public static void update(SqlSessionFactory factory) {
        SqlSession session = factory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        userMapper.updateUserName("zy", 1);
        session.commit();
        session.close();
    }
    
    /**
     * 测试一级缓存：会话级别缓存，生命周期范围限定为SqlSession，在SqlSession中，只要查询结果不变则会复用
     * 默认是开启的，不可以关闭
     * 
     * @param factory
     */
    public static void testFirstLevelCache(SqlSessionFactory factory){
        SqlSession session = factory.openSession(); 
        UserMapper userMapper = session.getMapper(UserMapper.class);
        Random random = new Random();
        // select
        User user = userMapper.selectUser(1);
        System.out.println("================" + user.toString());
        
        // update
        userMapper.updateUserName(String.valueOf(random.nextInt(100)), 1);
        
        // select again, see if first level cache affects! if first select not equals second select, then mean first level cache affects~
        // In a transaction, update will update cache, select can read the result of updating, it's reasonable
        user = userMapper.selectUser(1);
        System.out.println("================" + user.toString());
    }
    
    /**
     * 测试二级缓存：namespace级别缓存，对于namespace下执行的sql语句会进行缓存，后续如果执行相同的sql语句，不会去查数据库
     * 配合使用BareTail工具(这是一个轻巧的查看log文件的工具，很好用)查看MySQL执行的查询语句，会发现只执行了一次查询
     * 
     * @param session
     * @param factory
     */
    public static void testSecondLevelCache(SqlSessionFactory factory) {
        SqlSession session = factory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        
        // select
        userMapper.selectUser(1);
        
        // close first SqlSession
        session.close();
        
        // Open new SqlSession
        SqlSession newSession = factory.openSession();
        
        // do select again
        userMapper = newSession.getMapper(UserMapper.class);
        userMapper.selectUser(1);
        
        // monitor MySQL if it execute two query, if not, that mean mybatis reduce pressure for MySQL
        // log will print "Cache Hit Ratio [com.xqs.mybatis.UserMapper]: 0.5"
    }
}
