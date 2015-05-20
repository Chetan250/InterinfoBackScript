package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import bean.CustomReportBean;



import connection.LoadProperty;
import connection.MyConnection;

public class RetrieveSaveStanderdReport {
	
	Connection conn;
	ResultSet rs;
	Statement stmt;
	Properties objProperties;
	

public RetrieveSaveStanderdReport() {
	
	initalize();
	
	}

public void initalize(){
	
	try {
		objProperties = new LoadProperty().getProperty();
		conn = new MyConnection().getConnection(objProperties);
		
	} catch (Exception e) {
		System.out.println("Error in RetrieveSaveStanderdReport");
	}
}



public CustomReportBean getSaveStanderdReport()
{
	String standerdQuery="select * from save_standard_report_inc a,"
			+ "standard_report_inc_run_now b "
			+ "where a.report_id = b.report_id limit 1;";
	
	CustomReportBean bean = null;
	
	try {
		stmt = conn.createStatement();
		rs = stmt.executeQuery(standerdQuery);

		while (rs.next()) {
			bean =new CustomReportBean();
			bean.setReportId(rs.getString(1));
			bean.setUserId(rs.getString(2));
			bean.setHsCodeLength(rs.getString(3));
			bean.setCreatedDate(rs.getString(4));
			bean.setModifiedDate(rs.getString(5));
			bean.setImportExport(rs.getString(6));
			bean.setTaxNumber(rs.getString(7));
			bean.setFromDate(rs.getString(8));
			bean.setToDate(rs.getString(9));
			bean.setHsCode(rs.getString(10));
			bean.setValueRangeByDollerFrom(rs.getString(11));
			bean.setValueRangeByDollerTo(rs.getString(12));
			bean.setValueRangeByQuantityFrom(rs.getString(13));
			bean.setValueRangeByQuantityTo(rs.getString(14));
			bean.setSupplier(rs.getString(15));
			bean.setBorder(rs.getString(16));
			bean.setMotionKey(rs.getString(17));
			bean.setRegimes(rs.getString(18));
			bean.setTransportation(rs.getString(19));
			bean.setOriginDestinationCountryCode(rs.getString(20));
			bean.setBuyerSellerCountryCode(rs.getString(21));
			bean.setBoundedWarehouses(rs.getString(22));
			bean.setIdentifier(rs.getString(23));
			bean.setRegulationRestriction(rs.getString(24));
			bean.setContainerType(rs.getString(25));
			bean.setBilling(rs.getString(26));
			bean.setMerchandiseDestination(rs.getString(27));
			bean.setBarCode(rs.getString(28));
			bean.setStarategicFiscalArea(rs.getString(29));
			bean.setReportName(rs.getString(30));
			bean.setReportType(rs.getString(31));
			
			
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
				.println("connection close in ReteiveSaveStanderdReport");
	} catch (Exception e) {
e.printStackTrace();
	}

}
public void deleteReportId(String reportId) {
	String query = "delete from standard_report_inc_run_now where report_id = " + reportId;
	try {
		stmt = conn.createStatement();
		stmt.executeUpdate(query);


	} catch (Exception e) {

		e.printStackTrace();
	} finally {
	}
	
}
}


	
	


