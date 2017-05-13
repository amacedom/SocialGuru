package Connection;
import java.sql.*;  

public class Connection_JDBC {
	
 static Connection con = null;
	
 public static void main(String[] args) 
	{
		crearConexion();
		//populateTable();
	}  
	
	
	public static void crearConexion()
	{
				try{  
					Class.forName("com.mysql.jdbc.Driver");  
					con=DriverManager.getConnection(  
					"jdbc:mysql://45.55.251.74:3306/globaldb","helio","S1ngler345");  
					// se crea la conexion 
					Statement stmt=con.createStatement();  
					// se ejeuta un query para validar que existe la conexion 
					ResultSet rs=stmt.executeQuery("select * from follower");  
					while(rs.next())  
					System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
					//con.close();  
					}
			  catch(Exception e)
			  		{
						System.out.println(e);
					}  
	}
	
	public static void populateTable() {

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


