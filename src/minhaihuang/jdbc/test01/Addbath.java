package minhaihuang.jdbc.test01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import minhaihuang.jdbc.Util.CloseUtil;
import minhaihuang.jdbc.Util.JdbcUtils;

/**
 * ���ݿ��Ż������������������ʱ��ʹ�������ύ�ķ����ɴ�����Ч��
 * 1��������������ʹ��
 * 2��ÿһ��׼��ִ�����ʱ���Ȱ���һ�εĲ��������
 * 3��ÿһ���ύ�Ĵ�С����趨��ȣ���ʦ���飺ÿ1000�������ύһ��
 * @author ��˧��
 *
 */
public class Addbath {

    public static void main(String[] args) {
	noAddbatch();//��ʱ:40179
	//withAddbatch();//��ʱ��8351
	//�ӽ�������������ύ�������˲������ݵ�Ч��
    }
    
    //���Ȳ��Բ��������ύ��ʱ�䣬����1000������
    public static void noAddbatch(){
	Connection conn=null;
	PreparedStatement ps=null;
	try {
	    long start=System.currentTimeMillis();
	    conn=JdbcUtils.createConnection();
	    ps=conn.prepareStatement("insert into T_users(Name,passWord) values(?,?)");
	    for(int i=0;i<10000;i++){
		//�����һ�εĲ���
		ps.clearParameters();
		
		//����׼��sql���
		ps.setString(1, "Tom");//ע�������ѡȡ����Ϊλ�ã���Ҫ�ô���1
		ps.setInt(2, i);
		ps.executeUpdate();
	    }
	    long end=System.currentTimeMillis();
	    long time=end-start;
	    System.out.println(time);
	    
	} catch (SQLException e) {
	   System.out.println("��������ʧ��"+e.getMessage());
	}finally{
	    CloseUtil.close(ps,conn);
	}
    }
    
    //�����������ύ����10000�����ݵ�ʱ�䣬���������ʹ��
    public static void withAddbatch(){
	Connection conn=null;
	PreparedStatement ps=null;
	try {
	    long start=System.currentTimeMillis();
	    conn=JdbcUtils.createConnection();
	    
	    //�������ʹ��
	    conn.setAutoCommit(false);
	    ps=conn.prepareStatement("insert into T_users(Name,passWord) values(?,?)");
	    for(int i=0;i<10000;i++){
		//�����һ�εĲ���
		ps.clearParameters();
		
		//����׼��sql���
		ps.setObject(1, "Tom"+i);
		ps.setObject(2, i);
		//�������������ύ
		ps.addBatch();
		
		//ÿ��1000�������ύһ��
//		if(i%1000==0){
//		    ps.executeUpdate();
//		}
	    }
	    //�ύʣ�������
	    ps.executeBatch();
	    
	    conn.setAutoCommit(true);
	    //����ʱ��
	    long end=System.currentTimeMillis();
	    long time=end-start;
	    System.out.println(time);
	} catch (SQLException e) {
	    try {
		conn.rollback();
	    } catch (SQLException e1) {
		System.out.println("�ع�ʧ��");
	    }
	   System.out.println("��������ʧ��"+e.getMessage());
	}finally{
	    CloseUtil.close(ps,conn);
	}
    }
}
