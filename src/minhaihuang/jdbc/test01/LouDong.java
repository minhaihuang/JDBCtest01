package minhaihuang.jdbc.test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import minhaihuang.jdbc.Util.CloseUtil;

/**
 * ����һ����վ���е�©����˳�㸴ϰһ��������ô����ѯ���е����ݡ�
 * �޸�©��
 * @author ��˧��
 *
 */
public class LouDong {

    public static void main(String[] args) {
	//��ʾ�û������û���������
	Scanner sc=new Scanner(System.in);
	
	System.out.println("please input the UserName:");
	String name=sc.nextLine();
	
	System.out.println("please input the password:");
	String password=sc.nextLine();
	
	//1,���÷�����Ƽ���jdbc������
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
	   System.out.println("����jdbc������ʧ��");
	}
	
	//2,��ȡ�����ݿ��һ������
	Connection con=null;
	
	//3����װҪִ�е�sql���
	PreparedStatement ps=null;
	
	//��ȡִ��sql����Ľ��
	ResultSet rs=null;
	
	try{
	    //��ȡ�����ݿ��һ������
	    con=DriverManager.getConnection("jdbc:mysql://localhost/studay1?seUnicode=true&characterEncoding=UTF8", "root","root");
	  
	    /*
	    //Ҫִ�е�sql���
	    //��ʹ��ƴ���ַ������������ʱ�����п��ܻ����©��
	    //�������������Ϊa' or 'a'='aʱ�����ƴ�ӳ�
//select count(*) c from T_Users where UserName='hhm'and password='a' or 'a'='a'
	    //��ʱ������Ҫ������ȷ��������Ȼ�����Ե�½�������©����
	    String sql="select count(*) c from T_Users where UserName="+"'"+name+"'"
	    +"and "+"password="+"'"+password+"'";
	    
 
	    //��װҪִ�е�sql��䣬
	    ps=con.prepareStatement(sql);
	    */
	    //��װҪִ�е�sql��䣬���иĽ�����ֹ����©��in����λ�ò���ʱ���ã��������
	    ps=con.prepareStatement("select count(*) c from T_Users where UserName=? and Password=?");
	    
	    //���ⲿ��ȡ�����������Ժ󶼲�������д��
	    ps.setString(1, name);
	    ps.setString(2,password);
	    
	    //ִ��sql���
	    rs=ps.executeQuery();
	   

	    //�ر�ע�����Ҫ��rsָ����һ�����ݣ���Ϊrs��ǰָ��ĵط�Ϊ���ݵ��е����棬ʲô��û��
	    rs.next();
	    int c=rs.getInt("c");
	    
	    if(c<=0){
		System.out.println("��¼ʧ��");
	    }else{
		System.out.println("��¼�ɹ�");
	    }
	    
	}catch(SQLException e){
	    System.out.println("�������ݿ�ʧ��"+e.getMessage());
	}finally{
	    CloseUtil.close(rs,ps,con);
	}
	
    }
}
