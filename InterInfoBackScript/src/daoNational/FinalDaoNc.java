package daoNational;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import beanNational.CustomReportBeanNc;
import connection.MyConnection;

public class FinalDaoNc {

	Connection conn;
	Statement stmt;
	Statement stmt1;
	Statement stmt2;
	Statement stmt3;
	Statement stmt4;
	public FinalDaoNc(Properties prop) {

		try {
			conn = new MyConnection().getConnection(prop);
		} catch (Exception e) {
			System.out.println("Error while getting connection");
			e.printStackTrace();

		}

	}

	public void getAllDataNc(CustomReportBeanNc bean) {
		String selectClause = "";
		String fromClause = "";
		String whereClause = "";
		String finalNationalQuery = "";
		String whereClauseConditional = "";

		selectClause = "SELECT a.rfc, a.rfctercero, a.extranjero,"
				+ "a.pais, a.id_fiscal, a.nacionalidad, "
				+ " a.ejercicio,  a.periodo, "
				+ " (SELECT CASE  WHEN a.tercero = '04' THEN "
				+ "'NATIONAL'  WHEN a.tercero = '05' THEN 'FOREIGN' WHEN a.tercero = '15' "
				+ "THEN 'GLOBAL'  END) as tercero, "
				+ " (SELECT CASE  WHEN a.operacion = '03' THEN "
				+ "'PROFESSIONAL SERVICES'  WHEN a.operacion = '06' THEN 'RENT/LEASE' WHEN "
				+ "a.operacion = '85' THEN 'OTHERS'  END) as operacion, " //10
				+ " a.valor15, a.valor10, a.valorbs15, "
				+ " a.valorbs10, a.valorbs_exe, a.valor0, "
				+ "a.valor_exe, b.razon"; //18

		fromClause = fromClause + " FROM DIOT a left outer join  "
				+ "CATALOGORFC_NC b on a.rfc = b.rfc "
				+ "left outer join Estado c on b.estado = c.Estado "
				+ "left outer join CATALOGORFC_NC d  on a.rfctercero = d.rfc ";
		whereClause = whereClause + " WHERE 1=1 ";

		// ---------- Check RFC ----------
		if (bean.getInformant() != null
				&& bean.getInformant().trim().length() > 0) {
			String rfcs= getWrongRFC(bean.getInformant());
			//System.out.println("rfcs is="+rfcs );
			whereClause = whereClause + " AND a.rfc IN("+getStringForInClause(bean.getInformant())+
					","+getStringForInClause(rfcs)+")";
		}
		// ------------- Mexican Supplier --------------
		if (bean.getMexicanSupplier() != null
				&& bean.getMexicanSupplier().trim().length() > 0) {
			String rfctercero= getWrongRFC(bean.getMexicanSupplier());
			whereClause = whereClause + " AND a.rfctercero IN ("
				+ getStringForInClause(bean.getMexicanSupplier())+","+getStringForInClause(rfctercero)+")";
		}
		// ------------- Foreign Supplier --------------
		if (bean.getForeignSupplier() != null
				&& bean.getForeignSupplier().trim().length() > 0) {
			whereClause = whereClause + " AND a.extranjero IN ("
				+ getStringForInClause(bean.getForeignSupplier()) +")";
		}
		// ------------- Supplier Type --------------
		if (bean.getSupplier_type() != null
				&& bean.getSupplier_type().trim().length() > 0) {
			whereClause = whereClause + " AND a.tercero IN("
				+ getStringForInClause(bean.getSupplier_type()) +")";
		}
		// ------------- Supplier Activity --------------
		if (bean.getSupplierActivity() != null
				&& bean.getSupplierActivity().trim().length() > 0) {
			whereClause = whereClause + " AND a.operacion IN("
				+ getStringForInClause(bean.getSupplierActivity()) +")";
		}
		// ------------- Supplier State --------------
		if (bean.getState() != null && bean.getState().trim().length() > 0) {
			whereClauseConditional = whereClauseConditional
					+ " AND c.EstadoCve IN (" + getStringForInClause(bean.getState()) + ")";
		}

		// ------------- Supplier Country --------------
		if (bean.getCountry() != null && bean.getCountry().trim().length() > 0) {
			whereClause = whereClause + " AND a.pais IN(" +getStringForInClause( bean.getCountry())
					+ ") ";
		}
		// ------------- Supplier date --------------
		if (bean.getFromDate() != null
				&& bean.getFromDate().trim().length() > 0) {
			String[] date = bean.getFromDate().split("-");
			whereClause = whereClause + " AND EJERCICIO = " + "'" + date[0]+"'";
					//+ "' AND PERIODO = '" + date[1] + "' ";
		}

		
		// -------- Query ---------
		String firstQuery = selectClause + ",d.razon as sup_razon,c.Estado, a.valor_final"
				+ fromClause + " "
				+ whereClause
				+ " "
				+ whereClauseConditional;
		String secondQuery = selectClause + ",'' as sup_razon, '' as Estado, a.valor_final"
				+ fromClause + whereClause + "AND a.rfctercero = ''";

		String standardReportQueryForExcel = firstQuery + " UNION "
				+ secondQuery;
		
	//	System.out.println("standardReportQueryForExcel: "+ standardReportQueryForExcel);
		finalNationalQuery = "insert into temp_report_nc (rfc,rfctercero,"
				+ "extranjero ,pais,id_fiscal,nacionalidad,ejercicio,"
				+ "periodo,tercero,operacion,valor15,valor10,valorbs15,"
				+ "valorbs10,valorbs_exe,valor0,valor_exe,razon,sup_razon, estado,valor_final)" // 18
				+ standardReportQueryForExcel;
		String updateRfc = "update temp_report_nc a,final_rfc b,CATALOGORFC_NC c "
				+ "set a.rfc = b.possible_rfc,a.razon = c.razon "
				+ "where a.razon is null "
				+ "and a.rfc = b.rfc "
				+ "and b.possible_rfc = c.rfc;";
		String updaterfctercero = "update temp_report_nc a,final_rfc b,CATALOGORFC_NC c "
				+ "set a.rfctercero = b.possible_rfc, a.sup_razon = c.razon "
				+ "where a.sup_razon is null "
				+ "and a.rfctercero = b.rfc "
				+ "and b.possible_rfc = c.rfc;";
		System.out.println("National Final Query: " + finalNationalQuery);

		try {
			stmt = conn.createStatement();
			String deletequeryNc = " truncate  table temp_report_nc ";
			int delete = stmt.executeUpdate(deletequeryNc);
			System.out.println("record deleted from National  temp_report_nc "
					+ delete);
			stmt1 = conn.createStatement();
			int record = stmt1.executeUpdate(finalNationalQuery);
			System.out.println("record inserted into natinal temp_report_nc "
					+ record);
			stmt2 = conn.createStatement();
			int updatedrfc = stmt2.executeUpdate(updateRfc);
			System.out.println("number of updaterfc = "+updatedrfc);
			stmt3 = conn.createStatement();
			int updatedrfctercero = stmt3.executeUpdate(updaterfctercero);
			System.out.println("number of updatedrfctercero = "+updatedrfctercero);
			

		} catch (Exception e) {

			System.out.println("exception in National finaldaoNc() ");
			e.printStackTrace();
		}

	}

	public String getStringForInClause(String str) {
		String generatedString = "";
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(str
				.split(",")));
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				generatedString = "'"+list.get(i)+"'";
			} else {
				generatedString = generatedString+",'"+list.get(i)+"'";
			}

		}
		return generatedString;
	}
	

  public String getWrongRFC(String rfc)
  {
	  String rfcs="";
	  
	  try {
		 

		  stmt4 = conn.createStatement();
		  String possiblerfc="select rfc from final_rfc where possible_rfc in(" 
		  + getStringForInClause(rfc) +")";
		  ResultSet rs1= null;
	rs1 = stmt4.executeQuery(possiblerfc);
	if(rs1.next()){
		rfcs=rs1.getString("rfc");
	}
	while (rs1.next()) {
			rfcs= rfcs +","+ rs1.getString("rfc");
		
	}
		  
	  } catch (Exception e) {
		e.printStackTrace();
	}
	//System.out.println(rfcs);
	  return rfcs;
  }

	public void closeAll() {
		try {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			if (stmt1 != null)
				stmt1.close();
			if (stmt2 != null)
				stmt2.close();
			if (stmt3 != null)
				stmt3.close();
			if (stmt4 != null)
				stmt4.close();
			System.out.println("connection close in final Dao");
		} catch (Exception e) {
			System.out
					.println("EXCEPTION IN connection close in National final dao");

		}

	}

	public String getReportName(String reportId) {
		String reportName = "";
		ResultSet rs = null;
		try {
			String query = "SELECT report_name FROM custom_report_nc WHERE report_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, reportId);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				reportName = rs.getString("report_name");
				System.out.println("National ReportName: " + reportName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return reportName;

	}
}
