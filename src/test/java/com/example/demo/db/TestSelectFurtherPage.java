package com.example.demo.db;

import java.sql.*;

/**
 * @author Chenhanqun mail: chenhanqun1@corp.netease.com
 * @date 2018/12/24 17:02
 */
public class TestSelectFurtherPage {

    public static void main(String[] args) {
        TestSelectFurtherPage testSelectFurtherPage = new TestSelectFurtherPage();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Test","root","");
//            long sum = 0;
//            int count = 50;
//            for (int i = 0; i < count; i++) {
//                long totalCount = selectCount(con);
//                long tmp = testSelectFurtherPage.pageOnce(con, totalCount) -
//                        testSelectFurtherPage.pageTwice(con, totalCount);
//                System.out.println("↑↑↑↑ 本次差值为：" + tmp + " ↑↑↑↑\n");
//                sum += tmp;
//            }
//            System.out.println("差值平均为：" + sum / count);
            testSelectFurtherPage.pageMulti(con);

            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    // select * from user limit 10 offset 10;
    private long pageOnce(Connection connection, long totalCount) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "select * from user limit 10 offset " + (totalCount - 10);
        long startTime = System.nanoTime();   //获取开始时间
        ResultSet rs = stmt.executeQuery(sql);
        long result = System.nanoTime() - startTime;

        while(rs.next()) {
            System.out.print(rs.getLong(1) + ",");
        }

        System.out.println("\n程序运行时间： "+result+"ns");
        return result;
    }
    // select id from user limit 10 offset 10; -->> select * from user where id in (1,2,3,4);
    private long pageTwice(Connection connection, long totalCount) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql1 = "select id from user limit 10 offset " + (totalCount - 10);
        long startTime = System.nanoTime();   //获取开始时间
        ResultSet rs = stmt.executeQuery(sql1);
        long result = System.nanoTime() - startTime;

        StringBuilder ids = new StringBuilder("(");
        while(rs.next()) {
            ids.append(rs.getString(1)).append(",");
        }
        String idString = ids.substring(0, ids.length() - 1) + ")";

        String sql2 = "select * from user where id in " + idString;
        startTime = System.nanoTime();
        rs = stmt.executeQuery(sql2);
        result += (System.nanoTime() - startTime);
        while(rs.next()) {
            System.out.print(rs.getLong(1) + ",");
        }

        System.out.println("\n程序运行时间： "+result+"ns");
        return result;
    }

    private long pageMulti(Connection connection) throws SQLException {
        String sql = "select id from user where id > ? limit 10";

        long startTime = System.nanoTime();   //获取开始时间
        int limit = 10;
        long firstId = 0;
        long latestId = 0;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        while (true) {
            preparedStatement.setLong(1, latestId);
            ResultSet rs = preparedStatement.executeQuery();
            rs.getRow();
            int i = 0;
            while(rs.next()) {
                if (i == 0) {
                    firstId = rs.getLong(1);
                }
                if (i == limit - 1) {
                    latestId = rs.getLong(1);
                }
                i++;
            }
            if (i < limit - 1) {
                break;
            }
        }

        preparedStatement.setLong(1, firstId);
        ResultSet rs = preparedStatement.executeQuery();
        rs.getRow();
        while(rs.next()) {
            System.out.print(rs.getLong(1) + ",");
        }

        long result = System.nanoTime() - startTime;
        System.out.println("\n程序运行时间： "+result+"ns");

        return 0;
    }


    // select count(*) from user;
    private static long selectCount(Connection connection) throws SQLException {
        String sql = "select count(*)  from user";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        long count = 0;
        while(rs.next()) {
            count = rs.getInt(1);
            System.out.print("执行：" + sql + "，\t共有：" + count + "行，\t\n");
        }
        return count;
    }
}
