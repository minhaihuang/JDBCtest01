package minhaihuang.jdbc.test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import minhaihuang.jdbc.Util.CloseUtil;

/**
 * 测试如何获取数据库的表中的数据
 * 
 * 注意：若是在表中增，删，或者更新数据，PrepareStatement中的executeUpdate()方法
 *      若是在表中查询数据，则使用的是ResultSet中的executeQuery()方法来获取数据，然后
 *      再逐步打印出来
 * @author 黄帅哥
 *
 */
public class SelectTest01 {

    public static void main(String[] args) {
	//利用反射机制加载jdbc驱动包
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	  System.out.println("加载驱动包失败");
	    	}
	
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	try{
	    //获取与数据库的连接
	    con=DriverManager.getConnection("jdbc:mysql://localhost/studay1?seUnicode=true&characterEncoding=UTF8", "root","root");
	
	    //准备操作数据库的语句
	    ps=con.prepareStatement("select * from T_Persons");
	    
	    //执行查询的语句，获取查询的结果
	    rs=ps.executeQuery();
	    while(rs.next()){//遍历从表中获取到的数据
		//此处数据的类型与在表中定义的数据类型要一致
		String name=rs.getNString("Name");
		int age=rs.getInt("age");
		boolean gen=rs.getBoolean("Gender");
		System.out.println(name+" "+(gen?"男":"女")+" "+age+"岁");
	    }
	}catch(SQLException e){
	    System.out.println("操作数据库失败"+e.getMessage());
	}finally{
	    CloseUtil.close(rs,ps,con);
	}
    }
}
