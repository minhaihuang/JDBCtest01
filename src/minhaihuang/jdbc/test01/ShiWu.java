package minhaihuang.jdbc.test01;

import java.sql.Connection;
import java.sql.SQLException;

import minhaihuang.jdbc.Util.CloseUtil;
import minhaihuang.jdbc.Util.JdbcUtils;

/**
 * 事务基础：一致性，当所有的事务都完成时才改变数据库，否则，将数据库恢复到原来的状态
 * 防止数据库中的数据的异常改变
 * @author 黄帅哥
 *
 */
public class ShiWu {

   
    public static void main(String[] args) {
	  Connection conn=null;
	  //先往T_counts中插入几条数据
	  /*
	try {
	  conn=JdbcUtils.createConnection();
	  
	  //准备sql语句
	  String sql="insert into T_Counts(number) values(1000)";
	  JdbcUtils.executeUpdate(conn, sql);
	} catch (SQLException e) {
	   System.out.println("操作数据库失败"+e.getMessage());
	}finally{
	    CloseUtil.close(conn);
	}
	*/
	  try {
	    conn=JdbcUtils.createConnection();
	    
	    //定义事务，保证数据库数据的正常变化
	    //设置值为false，使语句不会主动提交，只有全部语句都完成后才会提交
	    conn.setAutoCommit(false);
	    
	    String sql="update T_counts set number=number-1000 where id=1";
	    String sql2="update T_counts set number=number+1000 where id=2";
	    
	    //执行第一句语句，减1000
	    JdbcUtils.executeUpdate(conn, sql);
	    //当此内部出错时，程序由于执行不到加1000而导致总金额丢失1000
	    //定义一个出错的东西。
	    //Integer i=null;
	    //int j=i;
	    
	    //执行第二局语句，加1000
	    JdbcUtils.executeUpdate(conn, sql2);
	    
	    //当所有语句都执行通过后，提交语句，改变数据库的数据
	    conn.setAutoCommit(true);;
	} catch (SQLException e) {
	   System.out.println("操作数据库失败"+e.getMessage());
	   //若执行有异常，回滚程序，恢复数据库的数据
	   try {
	    conn.rollback();
	} catch (SQLException e1) {
	    System.out.println("回滚失败"+e.getMessage());
	}
	}finally{
	    CloseUtil.close(conn);
	}
    }
}
