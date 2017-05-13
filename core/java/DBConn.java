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

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DBConn {
	public static String TIMESTEN_HOME = System.getenv("TIMESTEN_ROOT");
	private String db_url;
	private String jdbc_driver; 
  	private Connection connection;
  	private String dsn;
  	private String user = "TESTER";
  	private String password = "TESTER";

  	/* Default constructor uses predefined connection settings to globaldb */
  	public DBConn() {
  		this.dsn = "globaldbCS";
  		this.jdbc_driver = "com.timesten.jdbc.TimesTenDriver";
  		this.db_url = "jdbc:timesten:client:dsn=" + this.dsn + ";";
  		try {
        this.connection = DriverManager.getConnection(this.timesten_url,this.user,this.password);
      } catch(Exception e) {
        e.printStackTrace();
      }
  	}

  	/* Constructor with other connection configurations for different purposes*/
  	public DBConn(String dsn) {
  		this.dsn = dsn;
  		this.tt_jdbc_driver = "com.timesten.jdbc.TimesTenDriver";
  		this.timesten_url = "jdbc:timesten:client:" + this.dsn + ";" ;
      //System.out.println(" URL: " + this.timesten_url + "\n\n");
  		try {
        this.connection = DriverManager.getConnection(this.timesten_url,this.user,this.password);
      } catch(Exception e) {
        e.printStackTrace();
      }
  	}

  	/* Constructor with other connection configurations for different purposes*/
  	public DBConn(int element) {
  		String dsn = "globaldb_e" + Integer.toString(element);
  		this.dsn = dsn;
  		this.tt_jdbc_driver = "com.timesten.jdbc.TimesTenDriver";
  		this.timesten_url = "jdbc:timesten:client:dsn=" + this.dsn + ";";
      try {
        this.connection = DriverManager.getConnection(this.timesten_url,this.user,this.password);
      } catch(Exception e) {
        e.printStackTrace();
      }
  		
  	}

    /* 
     * Description: Returns the number of elements in the grid associeted with
     * the connection handle. The number of elements is defined as the number
     * of replica sets(shards) multiplied by the K-safety factor. 
     * 
     * In: This uses the current handler of this class
     * Out: int numEelements
     *
    */
    public int getNumElements() {
      int numElements = -1 ;
      String query = "select * from ttdistributionmap where ptversion = " +
                     "(select max(ptversion) from ttdistributionmap)";

      try {
        numElements = getQueryRowCount(query);
      } catch(Exception e) {
        e.printStackTrace();
      }
      
      return numElements;
    }

    /* 
     * Description: Returns the name of the host associated with the given
     * element number/ element id.
     * 
     * In: This uses the current handler of this class
     * Out: String hostName
     *
    */
    public String getHostName(String element) {
      String hostName = null;
      String query = "select hostname from sys.v$distribution_current where " + 
                     "elementid = "+ element +" and hostname not like '%dummy%'";

      try {
        hostName = getSimpleQueryResult(query);
      } catch(Exception ex) {
        ex.printStackTrace();
      }

      return hostName;
    }

    /* 
     * Description: Returns the DataStore name of the globaldb database.
     * 
     * In: This uses the current handler of this class
     * Out: String datastoreName
     *
    */
    public String getDatastorePath() {
      String datastorePath = null;
      String query = "select first 1 datastore from sys.v$datastore_status";
      Pattern p = Pattern.compile("(.*datastores)");
      Matcher m = null;

      try {
        datastorePath = getSimpleQueryResult(query);
        m = p.matcher(datastorePath);
        //System.out.println(datastorePath);
        while(m.find()) {
          datastorePath = m.group(1);
        }
         
      } catch(Exception ex) {
        ex.printStackTrace();
      }

      return datastorePath;
    }

    /* 
     * Description: Returns the name of the user connected to the database.
     * 
     * In: This uses the current handler of this class
     * Out: String userName
     *
    */
    public String getUserID() {
      String userID = null;
      String query = "select user from dual";

      try { 
        userID = getSimpleQueryResult(query);
      } catch(Exception e) {
        e.printStackTrace();
      }

      return userID;
    }


    /* 
     * Description: Returns the name of the Instance associated with the given
     * element number/ element id
     * 
     * In: This uses the current handler of this class
     * Out: String instanceName
     *
    */
    public Map<String, ArrayList<String>> getHostAndInstanceNames() {
      Map<String, ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
      ArrayList<String> rowResult = new ArrayList<String>();
      int elementid = 1;
      String element = null;
      String hostname = null;
      String instancename = null;
      ResultSetMetaData rsmd = null;
      Statement stmt = null;
      ResultSet rs = null;
      //String query = "select distinct(instancename),hostname from sys.v$distribution_current "+
      //               "order by instancename ";
      String query = "select distinct(instancename), hostname from " + 
                     "sys.v$distribution_current order by instancename";

      try {
        stmt = this.connection.createStatement();
        rs = stmt.executeQuery(query);
        rsmd = rs.getMetaData();

        while( rs.next() ) {
          element = Integer.toString(elementid);
          for(int i = 1; i <= rsmd.getColumnCount(); i++) {
            //System.out.println(rs.getString(i));
            rowResult.add(rs.getString(i));
          }
          map.put(element,rowResult);
          rowResult = new ArrayList<String>();
          elementid++;
        }

      } catch(Exception ex) {
        ex.printStackTrace();
      }

      return map;
    }

    /* 
     * Description: Returns an ArrayList of Strings holding the values corresponding 
     * to the total of columns returned for the given query.
     * 
     * In: String query
     * Out: ArrayList<String>
     *
    */
    public ArrayList<String> getRowQueryResults(String query) {
      ArrayList<String> rowResult = new ArrayList<String>();
      ResultSetMetaData rsmd = null;
      Statement stmt = null;
      ResultSet rs = null;
      
      try {
        stmt = this.connection.createStatement();
        rs = stmt.executeQuery(query);
        rsmd = rs.getMetaData();

        while( rs.next() ) {
          for(int i = 1; i < rsmd.getColumnCount(); i++) {
            rowResult.add(rs.getString(i));
          }
        }
      } catch(Exception ex) {
        ex.printStackTrace();
      }

      return rowResult;
    }

    /* 
     * Description: Returns the name of the Instance associated with the given
     * element number/ element id
     * 
     * In: This uses the current handler of this class
     * Out: String instanceName
     *
    */
    public String getInstanceName(String element) {
      String instanceName = null;
      String query = "select instancename from sys.v$distribution_current where " +
                     "elementid = "+ element +" and hostname not like '%dummy%'";

      try {
        instanceName = getSimpleQueryResult(query);
      } catch(Exception ex) {
        ex.printStackTrace();
      }

      return instanceName;
    }

    /* 
     * Description: Returns the result from executing a query that requires only
     * one value as a result. e.g. a column from a row, etc...
     * 
     * In: String query
     * Out: String colValue
     *
    */
    public String getSimpleQueryResult(String query) {
      Statement stmt = null;
      ResultSetMetaData rsmd = null;
      ResultSet rs = null;
      String result = null;

      try {
        stmt = this.connection.createStatement();
        rs = stmt.executeQuery(query);
        rsmd = rs.getMetaData();

        while( rs.next() ) {
          result = rs.getString(1);
        }
      } catch(Exception ex) {
        ex.printStackTrace();
      }

      return result;
    }

    /* 
     * Description: Returns the number of shards (replicasets) in the grid
     * associated with the current connection handler of this class. 
     * 
     * In: This uses the current handler of this class
     * Out: int numReplicasets
     *
    */
    public int getNumReplicaSets() {
      int numReplicaSets = -1 ;
      String query = "select distinct(repset) as num_shards from ttdistributionmap " + 
                     "where ptversion = (select max(ptversion) from ttdistributionmap)";

      try {
        numReplicaSets = getQueryRowCount(query);
      } catch(Exception e) {
        e.printStackTrace();
      }
      
      return numReplicaSets;
    }

    /* 
     * Description: Returns the K-safety factor for the connected grid. 
     * 
     * In: This uses the current handler of this class
     * Out: int kFactor = ksafety value
     *
    */
    public int getKFactor() {
      int kFactor = -1 ;
      int numElements = -1;
      int numReplicaSets = -1;

      try {
        numElements = getNumElements();
        numReplicaSets = getNumReplicaSets();
        kFactor = numElements / numReplicaSets;
      } catch(Exception e) {
        e.printStackTrace();
      }
      
      return kFactor;
    }

    /* 
     * Description: Returns the count of rows returned by the provided query
     * If an error occurs when attempting to get the result the return value
     * will be -1.
     *
     * In: this uses the current handler of this class,String query to execute
     * Out: int amount of rows returned by query
     *
    */
    public int getQueryRowCount(String query) {
      Statement stmt = null;
      ResultSet rs = null;
      int rowCount = -1; 
      try {
        stmt = this.connection.createStatement();
        rs = stmt.executeQuery(query);
        rowCount = 0;
        while(rs.next()) {
          rowCount++;
        }
      } catch(Exception ex) {
        ex.printStackTrace();
      }
      
      return rowCount;
    }
