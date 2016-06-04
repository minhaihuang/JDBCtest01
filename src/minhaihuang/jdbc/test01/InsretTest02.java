package minhaihuang.jdbc.test01;
/**
 * ���´�һ��������������ݵĴ��룬��ǿ������
 * 
 * ע�⣺���õİ�����Ϊ��java.sql����
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
	  //1,���÷�����Ƽ���JDBC������
	    Class.forName("com.mysql.jdbc.Driver");
	    
	//2����DriverManager�µ�getConnection(url,str,str)������ȡ�����ݿ��һ������
	 //���ص���һ��Connection���󣬴˴�����������
	    con=DriverManager.getConnection("jdbc:mysql://localhost/studay1?seUnicode=true&characterEncoding=UTF8", "root","root");
	
	    //����con�е�prepareStament(String sql)����װ׼�������в�������ݣ�
	    //���ص���PrepareStatement�ӿڵ�һ������������������
	    pre=con.prepareStatement("insert into T_persons(id,name,age,Gender) values(3,'��ֲԴ',22,1)");
	
	    //����pre�е�PrepareStament��ִ����䣬�����в�������
	    //���ص���һ����������˼��Ӱ���˱��м�������
	    int i=pre.executeUpdate();
	    System.out.println(i);
	    
	} catch (ClassNotFoundException e) {
	    System.out.println("jdbc�������Ҳ���"+e.getMessage());
	} catch (SQLException e) {
	   System.out.println("�������ݿ�ʧ��"+e.getMessage());
	}finally{//��󣬼ǵùر���Դ���������ݿ���𽥵�ʧȥ��Ӧ
	   CloseUtil.close(pre,con);
	}
	
    }
}
