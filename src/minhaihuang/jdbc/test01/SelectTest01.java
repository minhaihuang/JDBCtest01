package minhaihuang.jdbc.test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import minhaihuang.jdbc.Util.CloseUtil;

/**
 * ������λ�ȡ���ݿ�ı��е�����
 * 
 * ע�⣺�����ڱ�������ɾ�����߸������ݣ�PrepareStatement�е�executeUpdate()����
 *      �����ڱ��в�ѯ���ݣ���ʹ�õ���ResultSet�е�executeQuery()��������ȡ���ݣ�Ȼ��
 *      ���𲽴�ӡ����
 * @author ��˧��
 *
 */
public class SelectTest01 {

    public static void main(String[] args) {
	//���÷�����Ƽ���jdbc������
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	  System.out.println("����������ʧ��");
	    	}
	
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	try{
	    //��ȡ�����ݿ������
	    con=DriverManager.getConnection("jdbc:mysql://localhost/studay1?seUnicode=true&characterEncoding=UTF8", "root","root");
	
	    //׼���������ݿ�����
	    ps=con.prepareStatement("select * from T_Persons");
	    
	    //ִ�в�ѯ����䣬��ȡ��ѯ�Ľ��
	    rs=ps.executeQuery();
	    while(rs.next()){//�����ӱ��л�ȡ��������
		//�˴����ݵ��������ڱ��ж������������Ҫһ��
		String name=rs.getNString("Name");
		int age=rs.getInt("age");
		boolean gen=rs.getBoolean("Gender");
		System.out.println(name+" "+(gen?"��":"Ů")+" "+age+"��");
	    }
	}catch(SQLException e){
	    System.out.println("�������ݿ�ʧ��"+e.getMessage());
	}finally{
	    CloseUtil.close(rs,ps,con);
	}
    }
}
