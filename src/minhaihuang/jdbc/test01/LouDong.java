package minhaihuang.jdbc.test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import minhaihuang.jdbc.Util.CloseUtil;

/**
 * 测试一个网站常有的漏洞，顺便复习一下如何运用代码查询表中的数据。
 * 修复漏洞
 * @author 黄帅哥
 *
 */
public class LouDong {

    public static void main(String[] args) {
	//提示用户输入用户名和密码
	Scanner sc=new Scanner(System.in);
	
	System.out.println("please input the UserName:");
	String name=sc.nextLine();
	
	System.out.println("please input the password:");
	String password=sc.nextLine();
	
	//1,利用反射机制加载jdbc驱动包
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
	   System.out.println("加载jdbc驱动包失败");
	}
	
	//2,获取与数据库的一个链接
	Connection con=null;
	
	//3，包装要执行的sql语句
	PreparedStatement ps=null;
	
	//获取执行sql语句后的结果
	ResultSet rs=null;
	
	try{
	    //获取与数据库的一个链接
	    con=DriverManager.getConnection("jdbc:mysql://localhost/studay1?seUnicode=true&characterEncoding=UTF8", "root","root");
	  
	    /*
	    //要执行的sql语句
	    //当使用拼接字符串来输入语句时，则有可能会出现漏洞
	    //例：输入的密码为a' or 'a'='a时，则会拼接成
//select count(*) c from T_Users where UserName='hhm'and password='a' or 'a'='a'
	    //这时，则不需要输入正确的密码仍然都可以登陆，这就是漏洞。
	    String sql="select count(*) c from T_Users where UserName="+"'"+name+"'"
	    +"and "+"password="+"'"+password+"'";
	    
 
	    //包装要执行的sql语句，
	    ps=con.prepareStatement(sql);
	    */
	    //包装要执行的sql语句，进行改进，防止出现漏洞in，当位置参数时，用？代替参数
	    ps=con.prepareStatement("select count(*) c from T_Users where UserName=? and Password=?");
	    
	    //从外部获取参数，建议以后都采用这种写法
	    ps.setString(1, name);
	    ps.setString(2,password);
	    
	    //执行sql语句
	    rs=ps.executeQuery();
	   

	    //特别注意这里：要将rs指向下一行数据，因为rs当前指向的地方为数据的行的上面，什么都没有
	    rs.next();
	    int c=rs.getInt("c");
	    
	    if(c<=0){
		System.out.println("登录失败");
	    }else{
		System.out.println("登录成功");
	    }
	    
	}catch(SQLException e){
	    System.out.println("操作数据库失败"+e.getMessage());
	}finally{
	    CloseUtil.close(rs,ps,con);
	}
	
    }
}
