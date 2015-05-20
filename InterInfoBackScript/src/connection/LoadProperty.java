package connection;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class LoadProperty {

	Properties objmProperties;
	
	public Properties getProperty() {
		
		FileInputStream objmFileInputStream = null;
		try {
			File objmFile = new File("interinfo.properties");
			objmFileInputStream = new FileInputStream(objmFile);
			objmProperties = new Properties();
			objmProperties.load(objmFileInputStream);
		} catch (Exception ex) {
			System.out.println("Exception occurred in getProperty method" + ex);
			System.exit(-1);
		} finally {
			try {
				objmFileInputStream.close();
			} catch (Exception ex) {
				ex.printStackTrace();

			}
		}
		return objmProperties;

	}
	
}
