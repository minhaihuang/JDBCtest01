package minhaihuang.jdbc.test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import minhaihuang.jdbc.Util.CloseUtil;

/**
 * 获取数据库中的null
 * @author 黄帅哥
 *
 */
public class GetNull {

    public static void main(String[] args) {
	//利用反射机制加载jdbc驱动包
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
	   System.out.println("加载jdbc驱动包失败");
	}
	
	//获取与数据库的可连接
	 Connection con=null;
	//包装要执行的操作数据库的语句
	 PreparedStatement ps=null;
	 //执行语句，并且获取结果
	 ResultSet rs=null;
	
	try {
	  //获取与数据库的可连接
	    con=DriverManager.getConnection("jdbc:mysql://localhost/studay1?seUnicode=true&characterEncoding=UTF8", "root","root");
	  
	    //包装要执行的操作数据库的语句
	    ps=con.prepareStatement("select * from T_persons");
	    
	    //执行语句，并且获取结果
	    rs=ps.executeQuery();
	    
	    //打印出获取到的结果，如何获取null的值
	    while(rs.next()){
		String name=rs.getString("name");
		Integer age=null;
		String gen=null;
		//获取值为空的值
		Integer i=(Integer) rs.getObject("age");
		if(i!=null){
		    age=i.intValue();
		}
		
		//获取类型为null的boolean值
		Boolean b=(Boolean) rs.getObject("gender");
		
		if(b!=null){
		     gen=(b?"男":"女");
		}
		
		System.out.println("姓名为："+name+" 年龄为："+age+" 性别为："+gen);
	    }
	    
	} catch (SQLException e) {
	    System.out.println("操作数据库失败"+e.getMessage());
	}finally{
	    CloseUtil.close(rs,ps,con);
	}
    }
}
