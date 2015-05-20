package actionNational;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import daoNational.FinalDaoNc;
import daoNational.RefreshCustomReportDaoNc;
import beanNational.CustomReportBeanNc;

public class ManageCustomReportNational {
	
	Properties prop;
	

		public static void main(String arg[]) {
			//System.out.println("arg[]: "+ arg.length);
		   		if (arg != null  &&  arg.length != 0) {
		   			if (arg[0].equalsIgnoreCase("yes")) {
		   				new ManageCustomReportNational(0);
					}
			   }else {
						new ManageCustomReportNational(1);
			          }
		}
		public Properties getProperty() {
			Properties objmProperties = null;
			FileInputStream objmFileInputStream = null;
			try {
				File objmFile = new File("interinfo.properties");
				objmFileInputStream = new FileInputStream(objmFile);
				objmProperties = new Properties();
				objmProperties.load(objmFileInputStream);
			} catch (Exception ex) {
				System.out.println("Exception occurred in National getProperty method" + ex);
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

		public ManageCustomReportNational(int callType) {
			
	/*-----------Create Object of National RefreshCustomReportDao and Call bean class--------------*/
			try {
				prop = getProperty();
				RefreshCustomReportDaoNc rcrNc = new RefreshCustomReportDaoNc(prop);
				
				
				ArrayList<CustomReportBeanNc> list = null;
				if ( callType == 1) {
					list = rcrNc.getDataNc();
				}else {
					list = rcrNc.getSingleDataNc();
				}


				FinalDaoNc finaldaoNc = new FinalDaoNc(prop);
				for (int i = 0; i < list.size(); i++) {
					finaldaoNc.getAllDataNc(list.get(i));
					String reportName = list.get(i).getReportName();
					if (list.get(i).getOutputAs().equals("excel")) {
						ExcelDataNc excDataNc = new ExcelDataNc(prop, reportName);
						excDataNc.getExcelDataNc(prop);
						excDataNc.closeAll();
					} else if (list.get(i).getOutputAs().equals("text")) {
						TextDataNc textData = new TextDataNc(prop, reportName);
						textData.getData(prop);
						textData.closeAll();
					} else if (list.get(i).getOutputAs().equals("report")) {
						ReportDataNc report = new ReportDataNc(prop);
						report.getReportDataNc(list.get(i).getReportId());
						report.closeAll();
					} 
				}
				if (callType != 1 && list.size()>0){
					rcrNc.deleteReportId(list.get(0).getReportId());
				}
				rcrNc.closeAll();
				
			} catch (Exception e) {

				System.out.println("error in National main : " + e);

			}
		}

	}


