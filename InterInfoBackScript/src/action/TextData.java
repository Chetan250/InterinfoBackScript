package action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import connection.MyConnection;
public class TextData {

		Connection conn;
		Statement stmt;
		ResultSet rs;	
		String reportName;
		
		public TextData(Properties prop) {
			try {
				conn = new MyConnection().getConnection(prop);
			} catch (Exception e) {
				System.out.println("Error while getting connection");
				e.printStackTrace();

			}
		}

		
		public TextData(Properties prop, String reportName) {
			try {
				conn = new MyConnection().getConnection(prop);
				this.reportName = reportName.replace(" ", "");
			} catch (Exception e) {
				System.out.println("Error while getting connection");
				e.printStackTrace();

			}
		}


		public void getData(Properties prop) {
			String storagePath = prop.getProperty("reportStoragePath");
			//String textPath = prop.getProperty("TextTemplatePath");
			String sourceFileName = storagePath + "/"+reportName+".txt";
			String select="select * from temp_report ";
				
         
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
					    writerBuffer.write(" \n ");
				}	
				System.out.println("txt file genarated");
				writerBuffer.close(); 
		}catch (Exception e) {
			System.out.println("In getData method");
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
				System.out.println("connection close in text class");
			} catch (Exception e) {
			
			}
			
		}
	}



