package minhaihuang.jdbc.test01;
/**
 * �����������MySQL������
 * �Լ���������ݿ�ı��в�������
 * 
 * ע�⣺���õİ�����Ϊ��java.sql����
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
	    //���÷�����ƣ�����JDBC����
	    Class.forName("com.mysql.jdbc.Driver");
	    
	    //����DriverManager��getConnection(url,str,str);��������ȡ��������һ������
	  con=DriverManager.getConnection("jdbc:mysql://localhost/studay1?seUnicode=true&characterEncoding=UTF8", "root","root");
	  	
	  //�������ӳɹ��Ķ���con�е�prepareStatement����׼�������ݿ�������ݵ����
	  pre=con.prepareStatement("insert into T_Persons(id,name,age,Gender) values(1,'�ƺ���',20,1)");
	  
	  //���÷�����ִ����䣬��������
	  int i=pre.executeUpdate();//�Ż�һ����������֪Ӱ���༸��
	  System.out.println(i);
	} catch (ClassNotFoundException e) {
	   System.out.println("����JDBCʧ��"+e.getMessage());
	} catch (SQLException e) {
	    System.out.println("�������ݿ�ʧ��"+e.getMessage());
	}
    }
}
