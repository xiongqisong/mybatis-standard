package com.xqs.mybatis;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;
    private String name;
    private String ip;

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", ip=" + ip + "]";
    }

}
