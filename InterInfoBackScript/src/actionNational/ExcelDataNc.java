package actionNational;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import connection.MyConnection;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




public class ExcelDataNc {

		Connection conn;
		Statement stmt;
		ResultSet rs;
		String reportName;

		

		public ExcelDataNc(Properties prop, String reportName) {
			try {
				this.reportName = reportName.replace(" ", "");
				conn = new MyConnection().getConnection(prop);
			} catch (Exception e) {
				System.out.println("Error while getting connection in National ExcelData()");
				e.printStackTrace();

			}
		}

		public void getExcelDataNc(Properties prop) {

			String excelquery = "select * from temp_report_nc limit 50000";
//			String excelPath = prop.getProperty("ExcelTemplatePath");
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
				   //XSSFFont my_font=workbook.createFont();
			        my_style.setFillForegroundColor(XSSFCellStyle.ALIGN_GENERAL);
			        my_style.setFillPattern(HSSFCellStyle.BORDER_MEDIUM);
			        //my_font.setBoldweight(HSSFFont.DEFAULT_CHARSET);
	                //my_style.setFont(my_font);
	          
			       // my_style.setBorderTop((short) 1); // single line border
			       // my_style.setBorderBottom((short) 1);
			        //my_style.setBorderLeft((short) 1);
			       // my_style.setBorderRight((short) 1);
			        XSSFCellStyle style = workbook.createCellStyle();
			        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			  //======================Insert Image =================================
			      /*  
			        FileInputStream inputStream = new FileInputStream("/home/avinash/git/InterinfoBackScript/InterInfoBackScript/ReportTemplate/logo.png");
			        byte[] bytes = IOUtils.toByteArray(inputStream);
			        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
			        
			        inputStream.close();
			      
			       // System.out.println("above while");
			        CreationHelper helper = workbook.getCreationHelper();
			        Drawing drawing = spreadsheet.createDrawingPatriarch();
			        ClientAnchor anchor = helper.createClientAnchor();
			        //set top-left corner for the image
			        
			        int col1=1,row1=1;  
			        //HSSFClientAnchor object mainly sets the excel cell location where   
			        //the image needs to be inserted  
			         
			        anchor.setAnchorType((short)col1); 
			        anchor.setAnchorType(2);  
			        
			        
			        
			        
			     
			        
			        
			       
			        
			        
			        //Creates a picture
			        Picture pict = drawing.createPicture(anchor, pictureIdx);
			        //Reset the image to the original size
			        pict.resize(0.35);
			
			      
			      // XSSFRow rowhead=   XSSFSheet.createRow((short)0);
		          // HSSFCellStyle style = workbook.createCellStyle();
		            //style.setWrapText(true);
		           // row.setRowStyle(style);
		          //  row.getCell(0).setCellStyle(style);
			      
			      //=========== Date Code =============================
			      
			
			      XSSFDataFormat df = workbook.createDataFormat();
			      CellStyle cs = workbook.createCellStyle();
			      cs.setDataFormat(df.getFormat("d-mmm-yy"));

			       cell = spreadsheet.createRow(0).createCell(0);

			       Calendar c = Calendar.getInstance();
			      c.setTime(c.getTime());
			       cell.setCellValue( c.getTime() );
			       cell.setCellStyle(cs);
			        
			        
			//===================code for increase row height ======================        
					     
					        final XSSFRow row0 = spreadsheet.createRow(0);
					        final XSSFCell cellA1 = row0.createCell(0);
					       row0.setHeight((short)1000);
					      row0.setRowNum(pictureIdx);
*/					       
			        
	           
				cell = row.createCell(0);
				cell.setCellValue("RFC");
	            cell.setCellStyle(my_style);
				cell = row.createCell(1);
				cell.setCellValue("RFCTercero");
				cell.setCellStyle(my_style);
				cell = row.createCell(2);
				cell.setCellValue("Extranjero");
				cell.setCellStyle(my_style);
				cell = row.createCell(3);
				cell.setCellValue("Pais");
				cell.setCellStyle(my_style);
				cell = row.createCell(4);
				cell.setCellValue("Id_Fiscal");
				cell.setCellStyle(my_style);
				cell = row.createCell(5);
				cell.setCellValue("Nacionalidad");
				cell.setCellStyle(my_style);
				cell = row.createCell(6);
				cell.setCellValue("Ejercicio");
				cell.setCellStyle(my_style);
				cell = row.createCell(7);
				cell.setCellValue("Periodo");
				cell.setCellStyle(my_style);
				cell = row.createCell(8);
				cell.setCellValue("Tercero");
				cell.setCellStyle(my_style);
				cell = row.createCell(9);
				cell.setCellValue("Operacion");
				cell.setCellStyle(my_style);
				cell = row.createCell(10);
				cell.setCellValue("Valor15");
				cell.setCellStyle(my_style);
				cell = row.createCell(11);
				cell.setCellValue("Valor10");
				cell.setCellStyle(my_style);
				cell = row.createCell(12);
				cell.setCellValue("Valorbs15");
				cell.setCellStyle(my_style);
				cell = row.createCell(13);
				cell.setCellValue("Valorbs10");
				cell.setCellStyle(my_style);
				cell = row.createCell(14);
				cell.setCellValue("ValorbsExe");
				cell.setCellStyle(my_style);
				cell = row.createCell(15);
				cell.setCellValue("Valor0");
				cell.setCellStyle(my_style);
				cell = row.createCell(16);
				cell.setCellValue("ValorExe");
				cell.setCellStyle(my_style);
				cell = row.createCell(17);
				cell.setCellValue("Estado");
				cell.setCellStyle(my_style);
				cell = row.createCell(18);
				cell.setCellValue("Razon");
				cell.setCellStyle(my_style);
				cell = row.createCell(19);
				cell.setCellValue("SupRazon");
				cell.setCellStyle(my_style);
								
				

				int i = 1;
				while (rs.next()) {		
					
			//	System.out.println("here");	
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
				  cell.setCellValue(rs.getString(17));
				  cell=row.createCell(17);
				  row.getCell(16).setCellStyle(style);
				  cell.setCellValue(rs.getString(18));
				  cell=row.createCell(18);
				  row.getCell(17).setCellStyle(style);
				  cell.setCellValue(rs.getString(19));
				  cell=row.createCell(19);
				  row.getCell(18).setCellStyle(style);
				  cell.setCellValue(rs.getString(20));
				  cell=row.createCell(20);
				  row.getCell(19).setCellStyle(style);
				  i++;
						  
				}				  
					 
				
		
				FileOutputStream out = new FileOutputStream(
						new File(sourceFileName));

				workbook.write(out);

				out.close();

				System.out.println("excel file  in National ExcelData class generate successfully");

			} catch (Exception e) {
				System.out.println("error In national  excelgetDataNc()");
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
				System.out.println("connection close in National excel Class");
			} catch (Exception e) {

			}

		}
}
