package com.xqs.mybatis.interceptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * 自定义插件(拦截器模式) 本拦截器会拦截Executor类上参数为MappedStatement，Object的update方法
 * 
 * @author ycr
 *
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class ExampleInterceptor implements Interceptor {
    private AtomicInteger updateCounter = new AtomicInteger(0);
    private Date today;

    /* 拦截操作 */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println(String.format("[xqs-log-info: %tF updateCounter is %d]", today, updateCounter.incrementAndGet()));
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // initial start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(properties.get("today").toString());
            today = date;
            System.out.println(String.format("[xqs-log-info: %tF]", date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("today", "2018-07-09");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(properties.get("today").toString());
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
