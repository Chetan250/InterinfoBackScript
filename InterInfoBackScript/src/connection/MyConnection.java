package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;



public class MyConnection {
	Connection connection;

	public Connection getConnection(Properties prop) {
		try {
			//String url="jdbc:mysql://janta.c0hsgbbajbtv.ap-southeast-1.rds.amazonaws.com:3306/interinfo";
			String url=prop.getProperty("connectionUrl");
			Class.forName(prop.getProperty("connectionDriver"));
			connection=DriverManager.getConnection(url, 
				prop.getProperty("connectionUser"), prop.getProperty("connectionPwd"));
			
			
		} catch (Exception e) {
			System.out.println("SQLException...!");
			e.printStackTrace();
		}
		return connection;
	}
}
