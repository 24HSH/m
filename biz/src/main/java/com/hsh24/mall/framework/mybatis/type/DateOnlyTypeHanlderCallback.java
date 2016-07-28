package com.hsh24.mall.framework.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * 
 * @author JiakunXu
 * 
 */
public class DateOnlyTypeHanlderCallback implements TypeHandler<Object> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ps.setString(i, df.format(parameter));
	}

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		return rs.getObject(columnName);
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getObject(columnIndex);
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getObject(columnIndex);
	}

}
