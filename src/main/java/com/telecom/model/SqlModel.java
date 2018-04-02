package com.telecom.model;

import org.apache.ibatis.jdbc.SQL;

//面向对象sql拼接法
public class SqlModel {

	public String findTutorByIdSql(final String conditions) {  
	return new SQL() {{  
		SELECT("tutor_id as tutorId, name, email");  
		FROM("data_info");  
		WHERE(conditions);  
		}}.toString();  
	}  
}
