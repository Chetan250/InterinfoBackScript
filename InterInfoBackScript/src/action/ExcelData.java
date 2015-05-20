package action;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import connection.MyConnection;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
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
			this.reportName = reportName.replace(" ", "");
			conn = new MyConnection().getConnection(prop);
		} catch (Exception e) {
			System.out.println("Error while getting connection");
			e.printStackTrace();

		}
	}

	public void getExcelData(Properties prop) {

		String excelquery = "select * from temp_report limit 50000";
//		String excelPath = prop.getProperty("ExcelTemplatePath");
		String storagePath = prop.getProperty("reportStoragePath");
		System.out.println(reportName+"   "+storagePath);
		String sourceFileName = storagePath +"/"+reportName+".xlsx";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(excelquery);
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet spreadsheet = workbook.createSheet("sheet");
			XSSFRow row = spreadsheet.createRow(0);
			XSSFCell cell;
		       XSSFCellStyle my_style = workbook.createCellStyle();
			  // XSSFFont my_font=workbook.createFont();
			    my_style.setFillForegroundColor(XSSFCellStyle.ALIGN_GENERAL);
		        my_style.setFillPattern(HSSFCellStyle.BORDER_MEDIUM);
		       // my_style.setBorderTop((short) 1); // single line border
		       // my_style.setBorderBottom((short) 1);
		       // my_style.setBorderLeft((short) 1);
		       // my_style.setBorderRight((short) 1);
		        
		       // my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
              //  my_style.setFont(my_font);
		        
		        XSSFCellStyle style = workbook.createCellStyle();
		        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
          
           
			cell = row.createCell(0);
			cell.setCellValue("IE");
            cell.setCellStyle(my_style);
			cell = row.createCell(1);
			cell.setCellValue("FRACCION");
			cell.setCellStyle(my_style);
			cell = row.createCell(2);
			cell.setCellValue("CANTIDAD");
			cell.setCellStyle(my_style);
			cell = row.createCell(3);
			cell.setCellValue("MONTO");
			cell.setCellStyle(my_style);
			cell = row.createCell(4);
			cell.setCellValue("TaxNumber");
			cell.setCellStyle(my_style);
			cell = row.createCell(5);
			cell.setCellValue("Supplier");
			cell.setCellStyle(my_style);
			cell = row.createCell(6);
			cell.setCellValue("Customs");
			cell.setCellStyle(my_style);
			cell = row.createCell(7);
			cell.setCellValue("Date");
			cell.setCellStyle(my_style);
			cell = row.createCell(8);
			cell.setCellValue("Origin");
			cell.setCellStyle(my_style);
			cell = row.createCell(9);
			cell.setCellValue("RUTA");
			cell.setCellStyle(my_style);
			cell = row.createCell(10);
			cell.setCellValue("Regulation");
			cell.setCellStyle(my_style);
			cell = row.createCell(11);
			cell.setCellValue("Destination");
			cell.setCellStyle(my_style);
			cell = row.createCell(12);
			cell.setCellValue("FRACCION Desc");
			 cell.setCellStyle(my_style);
			cell = row.createCell(13);
			cell.setCellValue("Regimen");
			 cell.setCellStyle(my_style);
			cell = row.createCell(14);
			cell.setCellValue("Transporte");
			 cell.setCellStyle(my_style);
			cell = row.createCell(15);
			cell.setCellValue("Mexican Company");
			 cell.setCellStyle(my_style);
			
			

			int i = 1;
			while (rs.next()) {	
				

				
				  row = spreadsheet.createRow(i);	
				  cell = row.createCell(0);
				  cell.setCellValue(rs.getString(1));
				  cell = row.createCell(1);
				  row.getCell(0).setCellStyle(style);
				  cell.setCellValue(rs.getString(2));
				  cell=row.createCell(2);
				  row.getCell(1).setCellStyle(style);
				  cell.setCellValue(rs.getString(3));
				  cell=row.createCell(3);
				  row.getCell(2).setCellStyle(style);
				  cell.setCellValue(rs.getString(4));
				  cell=row.createCell(4);
				  row.getCell(3).setCellStyle(style);
				  cell.setCellValue(rs.getString(5));
				  cell=row.createCell(5);
				  row.getCell(4).setCellStyle(style);
				  cell.setCellValue(rs.getString(6));
				  cell=row.createCell(6);
				  row.getCell(5).setCellStyle(style);
				  cell.setCellValue(rs.getString(7));
				  cell=row.createCell(7);
				  row.getCell(6).setCellStyle(style);
				  cell.setCellValue(rs.getString(8));
				  cell=row.createCell(8);
				  row.getCell(7).setCellStyle(style);
				  cell.setCellValue(rs.getString(9));
				  cell=row.createCell(9);
				  row.getCell(8).setCellStyle(style);
				  cell.setCellValue(rs.getString(10));
				  cell=row.createCell(10);
				  row.getCell(9).setCellStyle(style);
				  cell.setCellValue(rs.getString(11));
				  cell=row.createCell(11);
				  row.getCell(10).setCellStyle(style);
				  cell.setCellValue(rs.getString(12));
				  cell=row.createCell(12);
				  row.getCell(11).setCellStyle(style);
				  cell.setCellValue(rs.getString(13));
				  cell=row.createCell(13);
				  row.getCell(12).setCellStyle(style);
				  cell.setCellValue(rs.getString(14));
				  cell=row.createCell(14);
				  row.getCell(13).setCellStyle(style);
				  cell.setCellValue(rs.getString(15));
				  cell=row.createCell(15);
				  row.getCell(14).setCellStyle(style);
				  cell.setCellValue(rs.getString(16));
				  cell=row.createCell(16);
				  row.getCell(15).setCellStyle(style);
			  i++;
							  
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
