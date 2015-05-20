package actionNational;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import connection.MyConnection;


public class TextDataNc {

			Connection conn;
			Statement stmt;
			ResultSet rs;	
			String reportName;
			
			public TextDataNc(Properties prop, String reportName) {
				try {
					conn = new MyConnection().getConnection(prop);
					this.reportName = reportName.replace(" ", "");
				} catch (Exception e) {
					System.out.println("Error while getting connection in National textData() ");
					e.printStackTrace();

				}
			}

			public void getData(Properties prop) {
				String storagePath = prop.getProperty("reportStoragePath");
				//String textPath = prop.getProperty("TextTemplatePath");
				String sourceFileName = storagePath + "/"+reportName+".txt";
				String select="select * from temp_report_nc limit 50000 ";
					
	         
				try {
					stmt=conn.createStatement();
			      rs=stmt.executeQuery(select);
			      File file = new File(sourceFileName);			
				  FileWriter writer = new FileWriter(file);
				  BufferedWriter writerBuffer = new BufferedWriter(writer);
			 
					while(rs.next())
					{
						  
						    writerBuffer.write(rs.getString(1)+"|");
						    writerBuffer.write(rs.getString(2)+"|");
						    writerBuffer.write(rs.getString(3)+"|");
						    writerBuffer.write(rs.getString(4)+"|");
						    
						    writerBuffer.write(rs.getString(5)+"|");
						    writerBuffer.write(rs.getString(6)+"|");
						    writerBuffer.write(rs.getString(7)+"|");
						    writerBuffer.write(rs.getString(8)+"|");
			              
						    
						    writerBuffer.write(rs.getString(9)+"|");
						    writerBuffer.write(rs.getString(10)+"|");
						    writerBuffer.write(rs.getString(11)+"|");
						    writerBuffer.write(rs.getString(12)+"|");
			              
						    writerBuffer.write(rs.getString(13)+"|");
						    writerBuffer.write(rs.getString(14)+"|");
						    writerBuffer.write(rs.getString(15)+"|");
						    writerBuffer.write(rs.getString(16)+"|");
						    writerBuffer.write(rs.getString(17)+"|");
						    writerBuffer.write(rs.getString(18)+"|");
						    writerBuffer.write(rs.getString(19)+"|");
						    writerBuffer.write(rs.getString(20)+"|");
						    
						    writerBuffer.write(" \n ");
					}	
					System.out.println("txt file genarated of National  ");
					writerBuffer.close(); 
			}catch (Exception e) {
				System.out.println("error In National TextgetData()");
				e.printStackTrace();
			}
			}
			public void closeAll() {
				try {
					if(conn!=null)
						conn.close();
					if(stmt!=null)
						stmt.close();
					if(rs!=null)
						rs.close();
					System.out.println("connection close in National  text class");
				} catch (Exception e) {
				
				}
				
			}
		}

