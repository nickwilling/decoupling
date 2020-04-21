package com.wwl.jdbc;

import java.sql.*;
/*
*程序的耦合
*   耦合：程序间的依赖关系
*       包括：类之间的依赖、方法之间的依赖
*   解耦：降低程序间的依赖关系
*   实际开发中应该做到：编译期不依赖，运行时才依赖
*   解耦的思路：
*       1、使用反射来创建对象而避免使用new关键字
*           new的时候依赖具体的类，如果找不到这个类连编译都通不过
*           使用反射来创建类只是依赖字符串，没有这个类只会在运行时报异常
*       2、通过读取配置文件来获取要创建的对象全限定类名，然后通过反射创建类(如果在反射中写死了类名修改很麻烦)
* */
public class JdbcDemo1 {
    public static void main(String[] args) {
        try {
            // jdbc操作步骤
            // 1、注册驱动
//            mysql6.x之后的版本使用com.mysql.cj.jdbc.Driver加个cj
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            // 2、获取连接
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eesy","root","123456");
            // 3、获取操作数据库的预处理对象
            PreparedStatement ps = connection.prepareStatement("select * from account");
            // 4、执行SQL，得到结果集
            ResultSet rs = ps.executeQuery();
            // 5、遍历结果集
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
            //6、释放资源
            rs.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
