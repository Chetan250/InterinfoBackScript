package action;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import connection.MyConnection;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelData {

	Connection conn;
	Statement stmt;
	ResultSet rs;
	String reportName;

	public ExcelData(Properties prop) {
		try {
			conn = new MyConnection().getConnection(prop);
		} catch (Exception e) {
			System.out.println("Error while getting connection");
			e.printStackTrace();

		}
	}

	public ExcelData(Properties prop, String reportName) {
		try {
			this.reportName = reportName;
			conn = new MyConnection().getConnection(prop);
		} catch (Exception e) {
			System.out.println("Error while getting connection");
			e.printStackTrace();

		}
	}

	public void getExcelData(Properties prop) {

		String excelquery = "select * from temp_report";
//		String excelPath = prop.getProperty("ExcelTemplatePath");
		String storagePath = prop.getProperty("reportStoragePath");
		String sourceFileName = storagePath + "/"+reportName+".xlsx";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(excelquery);
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet spreadsheet = workbook.createSheet("sheet");
			XSSFRow row = spreadsheet.createRow(1);
			XSSFCell cell;
			cell = row.createCell(1);
			cell.setCellValue("IE");
			cell = row.createCell(2);
			cell.setCellValue("Name");
			cell = row.createCell(3);
			cell.setCellValue("TaxNumber");
			cell = row.createCell(4);
			cell.setCellValue("Border");
			cell = row.createCell(5);
			cell.setCellValue("Date");
			cell = row.createCell(6);
			cell.setCellValue("CANTIDAD");
			cell = row.createCell(7);
			cell.setCellValue("MONTO");
			cell = row.createCell(8);
			cell.setCellValue("rigimen");
			cell = row.createCell(9);
			cell.setCellValue("medio_transporte_esp");
			cell = row.createCell(10);
			cell.setCellValue("ccOrigDest");
			cell = row.createCell(11);
			cell.setCellValue("ccBuyerSeller");
			cell = row.createCell(12);
			cell.setCellValue("regulation");
			cell = row.createCell(13);
			cell.setCellValue("merDest");
			cell = row.createCell(14);
			cell.setCellValue("FRACCION");
			cell = row.createCell(15);
			cell.setCellValue("hscode_description");
			
			
			/*
			 *   (IE, name, taxnumber, border,"
       date, CANTIDAD,MONTO,
       regimen, medio_transporte_esp,
       ccOrigDest,"
       ccBuyerSeller,regulation,
       merDest, FRACCION, 
       hscode_description)" 
			 * cell=row.createCell(3); cell.setCellValue("refresh_date");
			 * cell=row.createCell(4); cell.setCellValue("refresh_month");
			 * cell=row.createCell(5); cell.setCellValue("year");
			 * cell=row.createCell(6); cell.setCellValue("created_date");
			 * cell=row.createCell(7); cell.setCellValue("modified_date");
			 * cell=row.createCell(8); cell.setCellValue("import_export");
			 * cell=row.createCell(9); cell.setCellValue("taxno_company");
			 * cell=row.createCell(10); cell.setCellValue("from_date");
			 * cell=row.createCell(11); cell.setCellValue("to_date");
			 * cell=row.createCell(12); cell.setCellValue("hscode");
			 * cell=row.createCell(13);
			 * cell.setCellValue("value_range_by_doller");
			 * cell=row.createCell(14);
			 * cell.setCellValue("value_range_by_quantity");
			 * cell=row.createCell(15); cell.setCellValue("supplier");
			 * cell=row.createCell(16); cell.setCellValue("border");
			 * cell=row.createCell(17); cell.setCellValue("motion_key");
			 * cell=row.createCell(18); cell.setCellValue("regimes");
			 * cell=row.createCell(19); cell.setCellValue("transportation");
			 * cell=row.createCell(20);
			 * cell.setCellValue("origin_destination_country_code");
			 * cell=row.createCell(21);
			 * cell.setCellValue("buyer_seller_country_code");
			 * cell=row.createCell(22); cell.setCellValue("bounded_warehouse");
			 * cell=row.createCell(23); cell.setCellValue("identifiers");
			 * cell=row.createCell(24);
			 * cell.setCellValue("regulation_restriction");
			 * cell=row.createCell(25); cell.setCellValue("container_type");
			 * cell=row.createCell(26); cell.setCellValue("billing");
			 * cell=row.createCell(27);
			 * cell.setCellValue("merchandise_destination");
			 * cell=row.createCell(28); cell.setCellValue("bar_code");
			 * cell=row.createCell(29);
			 * cell.setCellValue("starategic_fiscal_area");
			 * cell=row.createCell(30); cell.setCellValue("nat_or_internat");
			 * cell=row.createCell(31); cell.setCellValue("hs_code_length");
			 */
			int i = 2;
			while (rs.next()) {				
			row = spreadsheet.createRow(i);
			cell = row.createCell(1);
			  cell.setCellValue(rs.getString(1));
			  cell = row.createCell(2);
			  cell.setCellValue(rs.getString(2));
			  cell=row.createCell(3);
			  cell.setCellValue(rs.getString(3));
			  cell=row.createCell(4);
			  cell.setCellValue(rs.getString(4));
			  cell=row.createCell(5);
			  cell.setCellValue(rs.getString(5));
			  cell=row.createCell(6);
			  cell.setCellValue(rs.getString(6));
			  cell=row.createCell(7);
			  cell.setCellValue(rs.getString(7));
			  cell=row.createCell(8);
			  cell.setCellValue(rs.getString(8));
			  cell=row.createCell(9);
			  cell.setCellValue(rs.getString(9));
			  cell=row.createCell(10);
			  cell.setCellValue(rs.getString(10));
			  cell=row.createCell(11);
			  cell.setCellValue(rs.getString(11));
			  cell=row.createCell(12);
			  cell.setCellValue(rs.getString(12));
			  cell=row.createCell(13);
			  cell.setCellValue(rs.getString(13));
			  cell=row.createCell(14);
			  cell.setCellValue(rs.getString(14));
			  cell=row.createCell(15);
			  cell.setCellValue(rs.getString(15));
			  i++;
				/*  for(int j=0;j<=14;j++){
					  cell = row.createCell(j);
					  cell.setCellValue(rs.getString(j));	*/				  
				  }				  
				 
			
	
			FileOutputStream out = new FileOutputStream(
					new File(sourceFileName));

			workbook.write(out);

			out.close();

			System.out.println("excel file generate successfully");

		} catch (Exception e) {
			System.out.println("In excelclass  getData method");
			e.printStackTrace();
		}
	}

	public void closeAll() {
		try {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
			System.out.println("connection close in excel");
		} catch (Exception e) {

		}

	}

}
