package com.xqs.mybatis.type.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.Alias;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

/**
 * 自定义类型转换器
 * @author ycr
 *
 */
@MappedJdbcTypes(value={JdbcType.VARCHAR})
@Alias("myTypeHandler")
public class MyTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
            throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

}
