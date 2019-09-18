package lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionDB {

	public Connection dbconn;
	private static final Logger LOGGER = Logger.getLogger(ConnectionDB.class.getName());

	public ConnectionDB() {
		//mysql
		try {
			// Class.forName("org.gjt.mm.mysql.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Class.forName("com.mysql.jdbc.Driver");
			
			dbconn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/HateMeter?autoreconnect=true&allowMultiQueries=true&connectTimeout=0&socketTimeout=0&serverTimezone=UTC", "", "");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Database connection error");
		}
		//System.out.println(System.getProperty("catalina.base"));
		String tomcat_home = System.getProperty("catalina.base");
	}
}