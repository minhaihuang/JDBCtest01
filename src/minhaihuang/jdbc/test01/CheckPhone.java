package minhaihuang.jdbc.test01;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import minhaihuang.jdbc.Util.JdbcUtils;

/**
 * �����ݿ��½�һ������2014��7�·��ֻ�������������ݿ���������ݲ��뵽���� Ҫ������������һ���ֻ��ţ��ܹ��鵽���Ĺ�����
 * 
 * @author ��˧��
 * 
 */
public class CheckPhone {

    public static void main(String[] args) {
	//getPlace();
	insertData();
    }

    // 1���ȵ������ݣ�ʹ��֮ǰѧϰ����IO֪ʶ��ʹ��BufferedReader����ȡÿһ�е�����
    // ע�⣺��ȡ����ʱ��һ������Ϊͷ��Ϣ������Ҫ�������ݣ�����ȥ��
    // 2����ȡ�����ݺ���String���еķָ��ַ����ķ������ָ����String���飬��ø������Ե�ֵ
    // 3����������������������ݵķ��������в�������

    /**
     * �˷���Ϊ�����ݿ��в�������
     */
    public static void insertData() {
	InputStream is = null;
	InputStreamReader isr = null;
	BufferedReader br = null;
	
	Connection conn=null;
	PreparedStatement ps=null;
	try {
	    long start=System.currentTimeMillis();
	    is = new FileInputStream("G:/tools/2014��7�·��ֻ�������������ݿ�.csv");
	    isr = new InputStreamReader(is);
	    br = new BufferedReader(isr);

	    conn=JdbcUtils.createConnection();
	    conn.setAutoCommit(false);
	    String sql="insert into T_phones"
	    	+ "(MobileNumber,MobileArea,MobileType,AreaCode,PostCode) values(?,?,?,?,?)";
	    ps=conn.prepareStatement(sql);
	   
	    // ��ȡ�ļ�
	    String line = null;
	    int count=0;
	    // ������һ������
	    br.readLine();

	    // ���л�ȡ
	    while ((line = br.readLine()) != null) {
		System.out.println(line);
		// �ָ��ȡ�����ַ�������ȡ�������Ե�ֵ
		String[] str = line.split(",");
		String s=null;
		for(int i=1;i<str.length;i++){
		
		    
		    s=str[i].replace("\"", "");
		    ps.setString(i, s);
		   // System.out.println(s);
		}
		count++;
		ps.addBatch();
		if(count%1000==0){
		    ps.executeBatch();
		}
	    }
	    ps.executeBatch();
	    conn.commit();
	    long end=System.currentTimeMillis();
	    System.out.println(end-start);
	} catch (FileNotFoundException e) {
	    System.out.println("�ļ��Ҳ���" + e.getMessage());
	} catch (IOException e) {
	    JdbcUtils.rollBack(conn);
	    System.out.println("��ȡ�ļ�ʧ��" + e.getMessage());
	} catch (SQLException e) {
	    JdbcUtils.rollBack(conn);
	   System.out.println("�������ݾ�ʧ��"+e.getMessage());
	}finally{
	    JdbcUtils.close(br,isr,is,ps,conn);
	}

    }
    
    /**
     * �˷���Ϊ��ѯ�������ڵ�
     */
    public static void getPlace(){
	Connection conn=null;
	ResultSet rs=null;
	try{
	//��ʾ�û���������
	    conn=JdbcUtils.createConnection();
	Scanner sc=new Scanner(System.in);
	System.out.println("����������ֻ����룺");
	String number=sc.nextLine();
	
	//��ú����ǰ��λ
	String number1=number.substring(0, 7);
	
	//׼����ѯ��sql��䡢
	String sql="select * from T_phones where MobileNumber="+number1;
	
	//����JdbcUtils������ִ��sql��䣬��ȡִ�еĽ��
	 rs=JdbcUtils.executeQuery(conn, sql);
	
	 //Ҫ��rsָ����һ�в���������Ѱ�ҵ����ݣ���Ϊrs��ǰΪ��
	 if(!rs.next()){//���rsָ�����һ�����ݲ����ڣ���˵�����ݿ��в����ڸú���Ĺ�����
	     System.out.println("�����ڸú���Ĺ�����");
	 }
	 
	 String place=rs.getString("MobileArea");
	 String type=rs.getString("MobileType");
	 System.out.println("������ĺ����ǣ�"+number);
	 System.out.println("��ĺ��������Ϊ"+place);
	 System.out.println("������Ӫ��Ϊ��"+type);
	}catch(SQLException e){
	    System.out.println("�������ݿ�ʧ��"+e.getMessage());
	}finally{
	    JdbcUtils.closeAll(rs);
	}
    }
}
