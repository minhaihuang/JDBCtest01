package minhaihuang.jdbc.test01;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import minhaihuang.jdbc.Util.JdbcUtils;

/**
 * 在数据库新建一个表，将2014年7月份手机号码归属地数据库里面的数据插入到表中 要求做到，输入一个手机号，能够查到它的归属地
 * 
 * @author 黄帅哥
 * 
 */
public class CheckPhone {

    public static void main(String[] args) {
	//getPlace();
	insertData();
    }

    // 1，先导入数据，使用之前学习过的IO知识，使用BufferedReader，获取每一行的数据
    // 注意：获取数据时第一行数据为头信息，不需要此行数据，将其去掉
    // 2，获取到数据后，用String类中的分割字符串的方法将分割，返回String数组，获得各个属性的值
    // 3，结合事务与批量插入数据的方法往表中插入数据

    /**
     * 此方法为往数据库中插入数据
     */
    public static void insertData() {
	InputStream is = null;
	InputStreamReader isr = null;
	BufferedReader br = null;
	
	Connection conn=null;
	PreparedStatement ps=null;
	try {
	    long start=System.currentTimeMillis();
	    is = new FileInputStream("G:/tools/2014年7月份手机号码归属地数据库.csv");
	    isr = new InputStreamReader(is);
	    br = new BufferedReader(isr);

	    conn=JdbcUtils.createConnection();
	    conn.setAutoCommit(false);
	    String sql="insert into T_phones"
	    	+ "(MobileNumber,MobileArea,MobileType,AreaCode,PostCode) values(?,?,?,?,?)";
	    ps=conn.prepareStatement(sql);
	   
	    // 读取文件
	    String line = null;
	    int count=0;
	    // 丢弃第一行数据
	    br.readLine();

	    // 逐行获取
	    while ((line = br.readLine()) != null) {
		System.out.println(line);
		// 分割获取到的字符串，获取各个属性的值
		String[] str = line.split(",");
		String s=null;
		for(int i=1;i<str.length;i++){
		
		    
		    s=str[i].replace("\"", "");
		    ps.setString(i, s);
		   // System.out.println(s);
		}
		count++;
		ps.addBatch();
		if(count%1000==0){
		    ps.executeBatch();
		}
	    }
	    ps.executeBatch();
	    conn.commit();
	    long end=System.currentTimeMillis();
	    System.out.println(end-start);
	} catch (FileNotFoundException e) {
	    System.out.println("文件找不到" + e.getMessage());
	} catch (IOException e) {
	    JdbcUtils.rollBack(conn);
	    System.out.println("读取文件失败" + e.getMessage());
	} catch (SQLException e) {
	    JdbcUtils.rollBack(conn);
	   System.out.println("操作数据据失败"+e.getMessage());
	}finally{
	    JdbcUtils.close(br,isr,is,ps,conn);
	}

    }
    
    /**
     * 此方法为查询号码所在地
     */
    public static void getPlace(){
	Connection conn=null;
	ResultSet rs=null;
	try{
	//提示用户输入密码
	    conn=JdbcUtils.createConnection();
	Scanner sc=new Scanner(System.in);
	System.out.println("请输入你的手机号码：");
	String number=sc.nextLine();
	
	//获得号码的前七位
	String number1=number.substring(0, 7);
	
	//准备查询的sql语句、
	String sql="select * from T_phones where MobileNumber="+number1;
	
	//调用JdbcUtils工具类执行sql语句，获取执行的结果
	 rs=JdbcUtils.executeQuery(conn, sql);
	
	 //要将rs指向下一行才是我们想寻找的数据，因为rs当前为空
	 if(!rs.next()){//如果rs指向的下一行数据不存在，则说明数据库中不存在该号码的归属地
	     System.out.println("不存在该号码的归属地");
	 }
	 
	 String place=rs.getString("MobileArea");
	 String type=rs.getString("MobileType");
	 System.out.println("你输入的号码是："+number);
	 System.out.println("你的号码归属地为"+place);
	 System.out.println("所属运营商为："+type);
	}catch(SQLException e){
	    System.out.println("操作数据库失败"+e.getMessage());
	}finally{
	    JdbcUtils.closeAll(rs);
	}
    }
}
