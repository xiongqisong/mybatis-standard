package com.xqs.mybatis;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    public Map<String, Object> selectUser2Map(int id);

    public User selectUser(int id);

    public void updateUserName(@Param("name") String name, @Param("id") int id);
    
}
