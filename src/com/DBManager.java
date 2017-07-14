package com;
import java.sql.*;
public class DBManager {
	
	private Connection conn=null;
	private Statement stmt=null;
	private ResultSet rs=null;
	//返回数据库连接对象
	  public Connection ConnDB(){
		  conn=null;
		  try {
			 
				String url="jdbc:mysql://127.0.0.1:3306/dbuser?useUnicode=true&amp;characterEncoding=UTF8";//?useUnicode==true&character=UTF-8
				String user="root";
				String password="";
				 Class.forName("com.mysql.jdbc.Driver").newInstance();
				 
				conn=DriverManager.getConnection(url, user,password);
			  return conn;
		} catch (Exception e) {
			// TODO: handle exception
		return null;
		}
		
		  
	  }
	  /*
	   *返回状态集对象
	   */
	  public Statement CreatStat(){
		  stmt=null;
				  try {
					  if (conn==null) {
						  
						conn=this.ConnDB();
					}
					  stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		
					return stmt;
				} catch (Exception e) {
					// TODO: handle exception
					System.err.println("CreateStat():"+e.getMessage());
					return null;
				}
	  }
	  /*
	   * 获取PreparedStatement的方法
	   */
	  public PreparedStatement prepareStat(String sql,int autoGeneratedKeys){
		  PreparedStatement pstmt=null;
		  try {
			if (conn==null) {
				conn=this.ConnDB();
			}
			pstmt=conn.prepareStatement(sql,autoGeneratedKeys);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		  return pstmt;
		  
	  }
	  /*
	   * 返回记录集
	   */
	  public ResultSet getResult(String sql) {
		  rs=null;
		  try {
			stmt=this.CreatStat();
			rs=stmt.executeQuery(sql);
			return rs;
			
		} catch (SQLException ex) {
			System.err.println("getResult:"+ex.getMessage());
			return null;
		}
	}
	  /*
	   *执行更新、删除语句的方法
	   */
	
	  public int executeSql(String sql){
		  int RowCount;
		  try {
			stmt=this.CreatStat();
			RowCount=stmt.executeUpdate(sql);
			//stmt.execute(sql);
			if (!conn.getAutoCommit()) {
				conn.commit();
			}
			return RowCount;
		  } catch (Exception e) {
			  System.err.println("executeSql:"+ e.toString());
		  }
		return 0;
		  
	  }
	  public int turnSql(String sql,String sql2){
		  int RowCount;
		  try {
			stmt=this.CreatStat();
			RowCount=stmt.executeUpdate(sql);
			RowCount=stmt.executeUpdate(sql2);
			if (!conn.getAutoCommit()) {
				conn.commit();
			}
			return RowCount;
		  } catch (Exception e) {
			  System.err.println("executeSql:"+ e.toString());
		  }
		return 0;
		  
		  
	  }
	  /*
	   *释放资源的方法
	   */
	 public void Release()throws SQLException{
		 if(rs!=null){
				rs.close();
				rs=null;
			}if(stmt!=null){
				stmt.close();
				stmt=null;
				
			}if(conn!=null){
				conn.close();
				conn=null;
			}
	 }
}