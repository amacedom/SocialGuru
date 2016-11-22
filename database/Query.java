package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {

	Statement stmt = null;
	static Connection con = null;
	//stmt = con.createStatement();
	
	public Query() throws ClassNotFoundException, SQLException {
		//Conexion a la dba
		Class.forName("com.mysql.jdbc.Driver");  
		con=DriverManager.getConnection(  
		"jdbc:mysql://45.55.251.74:3306/globaldb","helio","S1ngler345");  
		// se crea la conexion 
		Statement stmt=con.createStatement();  
	}
	
	public  void SelectTable() throws SQLException
	{
		ResultSet rs=stmt.executeQuery("select * from follower");  
		while(rs.next())  
		System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
	}
	
	public  void InsertTable() throws SQLException
	{
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String query = "insert into " + "globaldb" +
					".follower " +
					"values(default,'Sergio Adan','Castilla la vieja','Guadalajara','Jalisco','Mexico','COMPLETADO',default)";
			
			stmt.executeUpdate(query);
			
			ResultSet rs=stmt.executeQuery("select * from follower");
			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			con.close(); 
		} catch (Exception e) {
			//JDBCTutorialUtilities.printSQLException(e);
			e.printStackTrace();
		} finally {
			//con.close();
			if (stmt != null) 
			{
				//stmt.close(); 
			}
		}
	} 

}
