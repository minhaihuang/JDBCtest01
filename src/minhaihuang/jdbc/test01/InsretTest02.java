package minhaihuang.jdbc.test01;
/**
 * 重新打一遍连接与插入数据的代码，增强记忆力
 * 
 * 注意：引用的包都是为与java.sql包下
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import minhaihuang.jdbc.Util.CloseUtil;

public class InsretTest02 {

    public static void main(String[] args) {
	
	Connection con=null;
	PreparedStatement pre=null;
	try {
	  //1,利用反射机制加载JDBC驱动包
	    Class.forName("com.mysql.jdbc.Driver");
	    
	//2，用DriverManager下的getConnection(url,str,str)方法获取与数据库的一个连接
	 //返回的是一个Connection对象，此处扩张作用域
	    con=DriverManager.getConnection("jdbc:mysql://localhost/studay1?seUnicode=true&characterEncoding=UTF8", "root","root");
	
	    //利用con中的prepareStament(String sql)来包装准备往表中插入的数据，
	    //返回的是PrepareStatement接口的一个对象，又扩张作用域
	    pre=con.prepareStatement("insert into T_persons(id,name,age,Gender) values(3,'何植源',22,1)");
	
	    //调用pre中的PrepareStament来执行语句，往表中插入数据
	    //返回的是一个整数，意思是影响了表中几行数据
	    int i=pre.executeUpdate();
	    System.out.println(i);
	    
	} catch (ClassNotFoundException e) {
	    System.out.println("jdbc驱动包找不到"+e.getMessage());
	} catch (SQLException e) {
	   System.out.println("连接数据库失败"+e.getMessage());
	}finally{//最后，记得关闭资源，否则数据库会逐渐的失去响应
	   CloseUtil.close(pre,con);
	}
	
    }
}
