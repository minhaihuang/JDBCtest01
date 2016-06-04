package minhaihuang.jdbc.test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import minhaihuang.jdbc.Util.CloseUtil;

/**
 * ��ȡ���ݿ��е�null
 * @author ��˧��
 *
 */
public class GetNull {

    public static void main(String[] args) {
	//���÷�����Ƽ���jdbc������
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
	   System.out.println("����jdbc������ʧ��");
	}
	
	//��ȡ�����ݿ�Ŀ�����
	 Connection con=null;
	//��װҪִ�еĲ������ݿ�����
	 PreparedStatement ps=null;
	 //ִ����䣬���һ�ȡ���
	 ResultSet rs=null;
	
	try {
	  //��ȡ�����ݿ�Ŀ�����
	    con=DriverManager.getConnection("jdbc:mysql://localhost/studay1?seUnicode=true&characterEncoding=UTF8", "root","root");
	  
	    //��װҪִ�еĲ������ݿ�����
	    ps=con.prepareStatement("select * from T_persons");
	    
	    //ִ����䣬���һ�ȡ���
	    rs=ps.executeQuery();
	    
	    //��ӡ����ȡ���Ľ������λ�ȡnull��ֵ
	    while(rs.next()){
		String name=rs.getString("name");
		Integer age=null;
		String gen=null;
		//��ȡֵΪ�յ�ֵ
		Integer i=(Integer) rs.getObject("age");
		if(i!=null){
		    age=i.intValue();
		}
		
		//��ȡ����Ϊnull��booleanֵ
		Boolean b=(Boolean) rs.getObject("gender");
		
		if(b!=null){
		     gen=(b?"��":"Ů");
		}
		
		System.out.println("����Ϊ��"+name+" ����Ϊ��"+age+" �Ա�Ϊ��"+gen);
	    }
	    
	} catch (SQLException e) {
	    System.out.println("�������ݿ�ʧ��"+e.getMessage());
	}finally{
	    CloseUtil.close(rs,ps,con);
	}
    }
}
