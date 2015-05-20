package actionCommonNc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import bean.CustomReportBean;
import beanNational.CustomReportBeanNc;
import connection.LoadProperty;
import connection.MyConnection;

public class RetrieveSaveStandardNc {
	
	Connection conn;
	ResultSet rs;
	Statement stmt;
	Properties objProperties;
	
	
	

public RetrieveSaveStandardNc() {
	initalize();
	}

public void initalize(){
	
	try {
		objProperties = new LoadProperty().getProperty();
		conn = new MyConnection().getConnection(objProperties);
		
	} catch (Exception e) {
		System.out.println("Error in RetrieveSaveStandardNc");
	}
}



public CustomReportBeanNc getSaveStanderdReport()
{
	String standardQuery="select * from save_standard_report_nc a,"
			+ "standard_report_nc_run_now b "
			+ "where a.report_id = b.report_id limit 1;";
	
	CustomReportBeanNc bean = null; 
	
	try {
		stmt = conn.createStatement();
		rs = stmt.executeQuery(standardQuery);

		while (rs.next()) {
			bean = new CustomReportBeanNc();
			bean.setReportId(rs.getString(1));
			bean.setUserId(rs.getString(2));
			bean.setInformant(rs.getString(3));
			bean.setMexicanSupplier(rs.getString(4));
			bean.setForeignSupplier(rs.getString(5));
			bean.setCountry(rs.getString(6));
			bean.setDate(rs.getString(7));
			bean.setSupplier_type(rs.getString(8));
			bean.setSupplierActivity(rs.getString(9));
			bean.setState(rs.getString(10));
			bean.setCreatedDate(rs.getString(11));
			bean.setValue_Range_From(rs.getString(12));
			bean.setValue_Range_to(rs.getString(13));
			bean.setToDate(rs.getString(14));
			bean.setReportName(rs.getString(15));
			bean.setModified_Date(rs.getString(16));
			bean.setReportType(rs.getString(17));
						
		}
	    }catch (Exception e) {

			e.printStackTrace();
		}
		return bean;
	}

public void closeAll() {
	try {
		if (conn != null)
			conn.close();
		if (stmt != null)
			stmt.close();
		if (rs != null)
			rs.close();

		System.out
				.println("connection close in CustomReportBeanNc");
	} catch (Exception e) {

	}

}
public void deleteReportId(String reportId) {
	System.out.println("delete id "+reportId);
	String query = "delete from standard_report_nc_run_now where report_id = " + reportId;
	try {
		stmt = conn.createStatement();
		stmt.executeUpdate(query);


	} catch (Exception e) {

		e.printStackTrace();
	} finally {
	}
	
}

}
