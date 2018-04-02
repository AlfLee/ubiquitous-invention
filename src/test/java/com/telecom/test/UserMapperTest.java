package com.telecom.test;

import java.sql.Connection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.telecom.utils.ConnectDatabase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {


    @Test
    public void testInsert() throws Exception {
    	String oracleDriver = "oracle.jdbc.driver.OracleDriver";
    	//sid形式 orcl
    	String dbURL2 = "jdbc:oracle:thin:@localhost:1521:orcl";
    	//SERVICE_NAME ABCD服务名
    	String dbURL1 = "jdbc:oracle:thin:@//x.x.x.1:1522/ABCD ";
    	
    	Connection con = ConnectDatabase.connect("com.mysql.jdbc.Driver", "root", "123456", "jdbc:mysql://localhost:3306/examsystem");
    	ConnectDatabase.getDatas(con, "exam where id=14372504892913946");
    	
    }

   
}
