package com.telecom.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;



public class ConnectDatabase {

	
	public static Connection connect(String driver, String userName, String password, String url) throws Exception {
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, userName, password);
		return con;
	}
	
	public static void getDatas(Connection con, String tableName) throws Exception {
		String sql = "select * from " + tableName;
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData columns = rs.getMetaData();
		for(int i = 1; i < columns.getColumnCount(); i++) {
			String columnName = columns.getColumnName(i);
			System.out.println(columnName);
		}
	}
	//根据选择的数据库类型 得到对应url
	public static String getUrl(String dataSource, String ip, String port, String nameOrSID, String serverName) {
		switch(dataSource) {
			case "1":
			    return "jdbc:mysql://" + ip + ":" + port;  
			case "2":
				if(Constants.ONE.equals(nameOrSID)) {
					return "jdbc:oracle:thin:@//" + ip + ":" + port + "/" + serverName;
				}
				return "jdbc:oracle:thin:@" + ip + ":" + port + ":" + serverName;
			default:
				return "";
		}
	}
	//根据选择的数据库类型 得到对应驱动
	public static String getDriver(String dataSource) {
		switch(dataSource) {
			case "1":
				return "com.mysql.jdbc.Driver";
			case "2":
				return "oracle.jdbc.driver.OracleDriver";
			default:
				return "";
		}
	}
	//关闭连接
	public static void close(Connection con, PreparedStatement ps, ResultSet rs) throws Exception {
		if(rs != null) rs.close();  
		if(ps != null) ps.close();  
		if(con != null) con.close();
	}
}
