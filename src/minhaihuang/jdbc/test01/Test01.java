package minhaihuang.jdbc.test01;

import java.sql.Connection;
import java.sql.SQLException;

import minhaihuang.jdbc.Util.JdbcUtils;

public class Test01 {
public static void main(String[] args) throws SQLException {
    Connection conn=JdbcUtils.createConnection();
    
    String sql="insert into T_tests(name,age) values(null,?)";
    
    
    JdbcUtils.executeUpdate(conn, sql);
}
}
