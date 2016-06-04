package minhaihuang.jdbc.test01;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import minhaihuang.jdbc.Util.JdbcUtils;

/**
 * ��λ�ȡ����в��������
 * @author ��˧��
 *
 */
public class GetLastInsertId {

    public static void main(String[] args) {
	Connection conn=null;
	ResultSet rs=null;
	try {
	    conn=JdbcUtils.createConnection();
	    
	    String sql="insert into T_counts(number) values(?)";
	    
	    JdbcUtils.executeUpdate(conn, sql,123);
	    
	    //��ȡ�������һ������
	    rs=JdbcUtils.executeQuery(conn, "select last_insert_id() ma from T_counts");
	
	    //rs��ǰָ��Ϊ�գ��轫��ָ����һ��
	    rs.next();
	    
	    long id=rs.getLong("ma");
	    System.out.println(id);
	} catch (SQLException e) {  
	    System.out.println("�������ݿ�ʧ��"+e.getMessage());
	}finally{
	    JdbcUtils.closeAll(rs);
	}
    }

}
