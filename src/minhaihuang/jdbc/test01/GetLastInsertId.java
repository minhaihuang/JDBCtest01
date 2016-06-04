package minhaihuang.jdbc.test01;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import minhaihuang.jdbc.Util.JdbcUtils;

/**
 * 如何获取最后行插入的数据
 * @author 黄帅哥
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
	    
	    //获取最后插入的一行数据
	    rs=JdbcUtils.executeQuery(conn, "select last_insert_id() ma from T_counts");
	
	    //rs当前指向为空，需将其指向下一行
	    rs.next();
	    
	    long id=rs.getLong("ma");
	    System.out.println(id);
	} catch (SQLException e) {  
	    System.out.println("操作数据库失败"+e.getMessage());
	}finally{
	    JdbcUtils.closeAll(rs);
	}
    }

}
