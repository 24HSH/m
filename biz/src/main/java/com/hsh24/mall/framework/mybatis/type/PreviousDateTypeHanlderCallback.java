package com.hsh24.mall.framework.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.hsh24.mall.framework.util.DateUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class PreviousDateTypeHanlderCallback implements TypeHandler<Date> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
		Date date = DateUtil.addDays(parameter, -1);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ps.setString(i, df.format(date));
	}

	@Override
	public Date getResult(ResultSet rs, String columnName) throws SQLException {
		return rs.getDate(columnName);
	}

	@Override
	public Date getResult(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getDate(columnIndex);
	}

	@Override
	public Date getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getDate(columnIndex);
	}

}
