package com.example.demo.db;

import java.sql.*;

/**
 * @author Chenhanqun mail: chenhanqun1@corp.netease.com
 * @date 2018/12/24 17:02
 */
public class TestSelectCount {

    public static void main(String[] args) {
        TestSelectCount testSelectCount = new TestSelectCount();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Test","root","");
            long sum = 0;
            int count = 20;
            for (int i = 0; i < count; i++) {
                long tmp = testSelectCount.selectCount2(con) - testSelectCount.selectCount1(con);
                sum += tmp;
                System.out.println("↑↑↑↑ 本次差值为：" + tmp + " ↑↑↑↑\n");
            }
            System.out.println("差值平均为：" + sum / count);
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    // select count(id) from user;
    private long selectCount1(Connection connection) throws SQLException {
        long startTime = System.nanoTime();   //获取开始时间
        selectCount(connection, "select count(id) from user");
        long result = System.nanoTime() - startTime;
        System.out.println("程序运行时间： "+result+"ns");
        return result;
    }
    // select count(*) from user;
    private long selectCount2(Connection connection) throws SQLException {
        long startTime = System.nanoTime();   //获取开始时间
        selectCount(connection, "select count(*)  from user");
        long result = System.nanoTime() - startTime;
        System.out.println("程序运行时间： "+result+"ns");
        return result;
    }
    private void selectCount(Connection connection, String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            System.out.print("执行：" + sql + "，\t共有：" + rs.getInt(1) + "行，\t");
        }
    }

}
