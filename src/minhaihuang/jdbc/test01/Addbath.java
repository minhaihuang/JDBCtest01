package minhaihuang.jdbc.test01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import minhaihuang.jdbc.Util.CloseUtil;
import minhaihuang.jdbc.Util.JdbcUtils;

/**
 * 数据库优化：当插入大量的数据时，使用批量提交的方法可大大提高效率
 * 1，必须结合事务来使用
 * 2，每一次准备执行语句时，先把上一次的参数清理掉
 * 3，每一次提交的大小最好设定额度，老师建议：每1000条数据提交一次
 * @author 黄帅哥
 *
 */
public class Addbath {

    public static void main(String[] args) {
	noAddbatch();//用时:40179
	//withAddbatch();//用时：8351
	//从结果看出，批量提交大大提高了插入数据的效率
    }
    
    //首先测试不用批量提交的时间，插入1000条数据
    public static void noAddbatch(){
	Connection conn=null;
	PreparedStatement ps=null;
	try {
	    long start=System.currentTimeMillis();
	    conn=JdbcUtils.createConnection();
	    ps=conn.prepareStatement("insert into T_users(Name,passWord) values(?,?)");
	    for(int i=0;i<10000;i++){
		//清除上一次的参数
		ps.clearParameters();
		
		//重新准备sql语句
		ps.setString(1, "Tom");//注意这里的选取参数为位置，不要用代替1
		ps.setInt(2, i);
		ps.executeUpdate();
	    }
	    long end=System.currentTimeMillis();
	    long time=end-start;
	    System.out.println(time);
	    
	} catch (SQLException e) {
	   System.out.println("操作数据失败"+e.getMessage());
	}finally{
	    CloseUtil.close(ps,conn);
	}
    }
    
    //测试用批量提交插入10000条数据的时间，结合事务来使用
    public static void withAddbatch(){
	Connection conn=null;
	PreparedStatement ps=null;
	try {
	    long start=System.currentTimeMillis();
	    conn=JdbcUtils.createConnection();
	    
	    //结合事务使用
	    conn.setAutoCommit(false);
	    ps=conn.prepareStatement("insert into T_users(Name,passWord) values(?,?)");
	    for(int i=0;i<10000;i++){
		//清楚上一次的参数
		ps.clearParameters();
		
		//重新准备sql语句
		ps.setObject(1, "Tom"+i);
		ps.setObject(2, i);
		//加入数据批量提交
		ps.addBatch();
		
		//每隔1000条数据提交一次
//		if(i%1000==0){
//		    ps.executeUpdate();
//		}
	    }
	    //提交剩余的数据
	    ps.executeBatch();
	    
	    conn.setAutoCommit(true);
	    //计算时间
	    long end=System.currentTimeMillis();
	    long time=end-start;
	    System.out.println(time);
	} catch (SQLException e) {
	    try {
		conn.rollback();
	    } catch (SQLException e1) {
		System.out.println("回滚失败");
	    }
	   System.out.println("操作数据失败"+e.getMessage());
	}finally{
	    CloseUtil.close(ps,conn);
	}
    }
}
