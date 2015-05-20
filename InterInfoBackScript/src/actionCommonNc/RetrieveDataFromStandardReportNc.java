package actionCommonNc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import bean.CommonInternational;
import beanNational.CommonNational;
import beanNational.CustomReportBeanNc;
import connection.LoadProperty;
import connection.MyConnection;

public class RetrieveDataFromStandardReportNc {
	
	
	Connection conn;
	Statement stmt;
	Statement stmt1;
	Statement stmt2;
	Statement stmt3;
	Statement stmt4;
	Statement stmt5;
	java.sql.ResultSet rs;
	Properties objProperties;
	
	
	
	public RetrieveDataFromStandardReportNc() {
		initalize();
	}

	public void initalize(){
		
		try {
			objProperties = new LoadProperty().getProperty();
			conn = new MyConnection().getConnection(objProperties);
			
		} catch (Exception e) {
			System.out.println("Error in RetrieveDataFromStandardReportInc");
		}

	}
 public void getDataNc(CustomReportBeanNc bean)
	{  
	 	String fromClause= "";
		String selectcluase = "select a.rfctercero,a.ejercicio,a.periodo, "
				+ "a.tercero,a.operacion, a.rfc,b.razon,b.direccion,b.cp,b.municipio,"
				+ "c.Estado,b.actividad, a.id_fiscal,a.extranjero,a.pais,a.nacionalidad,"
				+ "a.valor15,a.valor10,a.valorbs15,a.valorbs10,a.valorbs_exe,a.valor0,"
				+ "a.valor_exe ";
		fromClause = fromClause + " FROM DIOT a left outer join  "
				+ "CATALOGORFC_NC b on a.rfc = b.rfc "
				+ "left outer join Estado c on b.estado = c.Estado ";
	
		
				
		 String whereClause = "WHERE 1=1 ";		

		// ---------- Check RFC ----------
		if (bean.getInformant() != null
				&& bean.getInformant().trim().length() > 0) {
			String rfcs= getWrongRFC(bean.getInformant());
			//System.out.println("rfcs is="+rfcs );
			if (rfcs.equals(""))
				whereClause = whereClause + " AND a.rfc IN("+getStringForInClause(bean.getInformant())+
					")";
			else
				whereClause = whereClause + " AND a.rfc IN("+getStringForInClause(bean.getInformant())+
				","+getStringForInClause(rfcs)+")";
		}
		
		// ------------- Supplier Activity --------------
		if (bean.getSupplierActivity() != null
				&& bean.getSupplierActivity().trim().length() > 0) {
			whereClause = whereClause + " AND a.operacion IN("
					+ getStringForInClause(bean.getSupplierActivity()) + ")";
		}
		// ------------- Supplier State --------------
		if (bean.getState() != null && bean.getState().trim().length() > 0) {
			whereClause = whereClause
					+ " AND c.EstadoCve IN (" + getStringForInClause(bean.getState()) + ")";
		}

		
		// ------------- Supplier date --------------
		if (bean.getFromDate() != null
				&& bean.getFromDate().trim().length() > 0) {
			String[] date = bean.getFromDate().split("-");
			whereClause = whereClause + " AND final_date between '" + bean.getFromDate() +
					" and '"+bean.getToDate() +"'";
					//+ "' AND PERIODO = '" + date[1] + "' ";
		}
		if (!bean.getValue_Range_From().equals("") ) {
			whereClause = whereClause + " AND valor_final between " + bean.getValue_Range_From() +
					" AND " + bean.getValue_Range_to() + " ";
		}

		
		String finalQuery ="insert into temp_report_standard_nc(rfctercero,ejercicio,periodo, "
				+ "tercero,operacion,rfc,razon,direccion,cp,municipio,"
				+ "Estado,actividad,id_fiscal,extranjero,pais,nacionalidad,"
				+ "valor15,valor10,valorbs15,valorbs10,valorbs_exe,valor0,"
				+ "valor_exe ) "+selectcluase + fromClause + whereClause ;
		
		String updateRfc = "update temp_report_standard_nc a,final_rfc b,CATALOGORFC_NC c "
				+ "set a.rfc = b.possible_rfc,a.razon = c.razon "
				+ "where a.razon is null "
				+ "and a.rfc = b.rfc "
				+ "and b.possible_rfc = c.rfc;";
		
		String updaterfctercero = "update temp_report_nc a,final_rfc b "
				+ "set a.rfctercero = b.possible_rfc "
				+ "where a.rfctercero = b.rfc ";
		
		System.out.println("Final query Of Standard National Report = \n" + finalQuery);
		String deleteQuery = "truncate table temp_report_standard_nc ;";
		try {
			stmt1 = conn.createStatement();
			int deleteRecord = stmt1.executeUpdate(deleteQuery);
			System.out.println("record deleted from standard National "+deleteRecord);
			stmt = conn.createStatement();
			int insertRecord = stmt.executeUpdate(finalQuery);
			System.out.println("record inserted into temp_report_standard_nc = "+insertRecord);
			stmt2 = conn.createStatement();
			int updatedrfc = stmt2.executeUpdate(updateRfc);
			System.out.println("number of updaterfc = "+updatedrfc);
			stmt3 = conn.createStatement();
			int updatedrfctercero = stmt3.executeUpdate(updaterfctercero);
			System.out.println("number of updatedrfctercero = "+updatedrfctercero);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String selectRecord = "select * from temp_report_standard_nc ";
		
		ArrayList<CommonNational> list = new ArrayList<CommonNational>();
		CommonNational nc = null;
		try {
			stmt5 = conn.createStatement();
			rs = stmt5.executeQuery(selectRecord);
			
			while (rs.next()) {
				
				nc= new CommonNational();
				nc.setLA_EMPRESA(rs.getString(1));
				nc.setEJERCICIO(rs.getString(2));
				nc.setPERIODO(rs.getString(3));
				nc.setTIPO_TERCERO(rs.getString(4));
				nc.setTIPO_OPERACION(rs.getString(5));
				nc.setTIENE_COMO_PROVEEDOR_A(rs.getString(6));
				nc.setRAZON_SOCIAL(rs.getString(7));
				nc.setDIRECCION(rs.getString(8));
				nc.setCP(rs.getString(9));
				nc.setMUNICIPIO(rs.getString(10));
				nc.setESTADO(rs.getString(11));
				nc.setACTIVIDAD(rs.getString(12));
				nc.setID_FISCAL(rs.getString(13));
				nc.setEXTRANJERO(rs.getString(14));
				nc.setPIAS(rs.getString(15));
				nc.setNACIONALIDAD(rs.getString(16));
				nc.setVALOR_15(rs.getString(17));
				nc.setVALOR_10(rs.getString(18));
				nc.setVALOR_IMP_BYS_15(rs.getString(19));
				nc.setVALOR_IMP_BYS_10(rs.getString(20));
				nc.setVALOR_IMP_BYS_EXENTO(rs.getString(21));
				nc.setVALOR_0(rs.getString(22));
				nc.setVALOR_EXENTO(rs.getString(23));
				list.add(nc);
				
			}
			System.out.println("Path of Jrxml = "+objProperties.getProperty("pathOfStdIncRepTemplate")+"CommonNc.jrxml");
			String fileNameString=bean.getReportName().replace(" ", "")+"_u"+bean.getUserId()+".xls";
			
			System.out.println("Size of list Standard Nc= "+list.size());
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
			Map<String, Object> parameters = new HashMap<String, Object>();			
			//JasperReport jasperReport = JasperCompileManager.compileReport(objProperties.getProperty("pathOfStdIncRepTemplate")+"CommonNc.jasper");
			//JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
			String sourceFileName = objProperties.getProperty("pathOfStdIncRepTemplate")+"CommonNc.jasper";
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					sourceFileName, parameters, beanColDataSource);
			System.out.println("common Standard Nc Report Created...");
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(objProperties.getProperty("ouputFolderForStdNC")+fileNameString));
			
			exporter.exportReport();
				
		} catch (Exception e) {

			System.out.println("exception in Standard NC getDataNc() ");
			e.printStackTrace();
		}

	}

	public String getStringForInClause(String str) {
		String generatedString = "";
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(str
				.split(",")));
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				generatedString = "'" + list.get(i).trim() + "'";
			} else {
				generatedString = generatedString + ", '" + list.get(i).trim() + "'";
			}

		}
		return generatedString;
	}
	

	public void closeAll() {
		try {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			System.out.println("connection close in RetrieveDataFromStandardReportNc");
		} catch (Exception e) {
			System.out
					.println("EXCEPTION IN connection close in National final dao");

		}

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
	
}
