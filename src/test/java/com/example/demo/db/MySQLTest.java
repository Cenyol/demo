package com.example.demo.db;

import com.example.demo.utils.NumberUtils;
import com.example.demo.utils.YxStringUtils;

import java.sql.*;

/**
 * @author Chenhanqun mail: chenhanqun1@corp.netease.com
 * @date 2018/12/24 15:56
 */
public class MySQLTest {

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
            insertUser(con);
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    // 批量生成User表测试数据，插入数据库
    private static void insertUser(Connection connection) throws SQLException {
        String sql = "INSERT INTO user(name,nick,sex,avatar,email,tel,birthday,create_time) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        StringBuilder sb = new StringBuilder("http://nos.127.net/");

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < 5000000; i++) {
            preparedStatement.setString(1, YxStringUtils.randomString(8));
            preparedStatement.setString(2, YxStringUtils.randomString(8));
            preparedStatement.setInt(3, NumberUtils.randomInt(0,2));
            preparedStatement.setString(4,
                    sb.append(YxStringUtils.randomString(32)).append(".jpeg").toString());
            preparedStatement.setString(5, YxStringUtils.randomString(12) + "@hotmail.com");
            preparedStatement.setString(6, "18" + YxStringUtils.randomNumericString(9));
            preparedStatement.setLong(7, NumberUtils.randomInt(0, 154563621) * 10000L);
            preparedStatement.setLong(8, NumberUtils.randomInt(1483200000, 1546272000) * 1000L);
            preparedStatement.execute();

            sb = new StringBuilder("http://nos.127.com/");
        }
    }
}
