package minhaihuang.jdbc.test01;
/**
 * 测试如何连接MySQL服务器
 * 以及如何往数据库的表中插入数据
 * 
 * 注意：引用的包都是为与java.sql包下
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ConnectTest01 {

    public static void main(String[] args) {
	
	 Connection con=null;
	 PreparedStatement pre=null;
	try {
	    //利用反射机制，加载JDBC驱动
	    Class.forName("com.mysql.jdbc.Driver");
	    
	    //利用DriverManager的getConnection(url,str,str);方法来获取服务器的一个链接
	  con=DriverManager.getConnection("jdbc:mysql://localhost/studay1?seUnicode=true&characterEncoding=UTF8", "root","root");
	  	
	  //利用连接成功的对象con中的prepareStatement方法准备往数据库插入数据的语句
	  pre=con.prepareStatement("insert into T_Persons(id,name,age,Gender) values(1,'黄海敏',20,1)");
	  
	  //调用方法，执行语句，插入数据
	  int i=pre.executeUpdate();//放回一个整数，告知影响类几行
	  System.out.println(i);
	} catch (ClassNotFoundException e) {
	   System.out.println("加载JDBC失败"+e.getMessage());
	} catch (SQLException e) {
	    System.out.println("链接数据库失败"+e.getMessage());
	}
    }
}
