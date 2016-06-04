package minhaihuang.jdbc.test01;

import java.sql.Connection;
import java.sql.SQLException;

import minhaihuang.jdbc.Util.CloseUtil;
import minhaihuang.jdbc.Util.JdbcUtils;

/**
 * ���������һ���ԣ������е��������ʱ�Ÿı����ݿ⣬���򣬽����ݿ�ָ���ԭ����״̬
 * ��ֹ���ݿ��е����ݵ��쳣�ı�
 * @author ��˧��
 *
 */
public class ShiWu {

   
    public static void main(String[] args) {
	  Connection conn=null;
	  //����T_counts�в��뼸������
	  /*
	try {
	  conn=JdbcUtils.createConnection();
	  
	  //׼��sql���
	  String sql="insert into T_Counts(number) values(1000)";
	  JdbcUtils.executeUpdate(conn, sql);
	} catch (SQLException e) {
	   System.out.println("�������ݿ�ʧ��"+e.getMessage());
	}finally{
	    CloseUtil.close(conn);
	}
	*/
	  try {
	    conn=JdbcUtils.createConnection();
	    
	    //�������񣬱�֤���ݿ����ݵ������仯
	    //����ֵΪfalse��ʹ��䲻�������ύ��ֻ��ȫ����䶼��ɺ�Ż��ύ
	    conn.setAutoCommit(false);
	    
	    String sql="update T_counts set number=number-1000 where id=1";
	    String sql2="update T_counts set number=number+1000 where id=2";
	    
	    //ִ�е�һ����䣬��1000
	    JdbcUtils.executeUpdate(conn, sql);
	    //�����ڲ�����ʱ����������ִ�в�����1000�������ܽ�ʧ1000
	    //����һ������Ķ�����
	    //Integer i=null;
	    //int j=i;
	    
	    //ִ�еڶ�����䣬��1000
	    JdbcUtils.executeUpdate(conn, sql2);
	    
	    //��������䶼ִ��ͨ�����ύ��䣬�ı����ݿ������
	    conn.setAutoCommit(true);;
	} catch (SQLException e) {
	   System.out.println("�������ݿ�ʧ��"+e.getMessage());
	   //��ִ�����쳣���ع����򣬻ָ����ݿ������
	   try {
	    conn.rollback();
	} catch (SQLException e1) {
	    System.out.println("�ع�ʧ��"+e.getMessage());
	}
	}finally{
	    CloseUtil.close(conn);
	}
    }
}
