package action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import java.util.Properties;

import dao.FinalDao;
import dao.RefreshCustomReportDao;
import bean.CustomReportBean;
import bean.CustomReportBean2;

public class ManageCustomReport {

	Properties prop;

	public static void main(String arg[]) {
		System.out.println("arg[]: "+ arg.length);
		
		if (arg != null  &&  arg.length != 0) {
	   			if (arg[0].equalsIgnoreCase("yes")) {
	   				new ManageCustomReport(0);
				}
		   }else {
					new ManageCustomReport(1);
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
			System.out.println("Exception occurred in getProperty method" + ex);
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

	public ManageCustomReport(int callType) {
		/*-----------Create Object of RefreshCustomReportDao and Call bean class--------------*/
		try {
			prop = getProperty();
			RefreshCustomReportDao rcr = new RefreshCustomReportDao(prop);
			
			
			ArrayList<CustomReportBean> list = null;
			if ( callType == 1) {
				list = rcr.getData();
			}else {
				list = rcr.getSingleData();
			}


			FinalDao finaldao = new FinalDao(prop);
			for (int i = 0; i < list.size(); i++) {
				finaldao.getAllData(list.get(i));
				String reportName = list.get(i).getReportName();
				if (list.get(i).getOutputAs().equals("excel")) {
					ExcelData excData = new ExcelData(prop, reportName);
					excData.getExcelData(prop);
					excData.closeAll();
				} else if (list.get(i).getOutputAs().equals("text")) {
					TextData textData = new TextData(prop, reportName);
					textData.getData(prop);
					textData.closeAll();
				} else if (list.get(i).getOutputAs().equals("report")) {
					ReportData report = new ReportData(prop);
					report.getReportData(list.get(i).getReportId());
					report.closeAll();
				} else if (list.get(i).getOutputAs().equals("inputToOther")) {
					InputToOther input = new InputToOther(prop, reportName);
					CustomReportBean2 crb = new CustomReportBean2();
					input.getHsCoseData();
					input.getTaxNoData();
					crb = input.createTempReport2(list.get(i).getReportId());
					input.getAllData(crb);
					input.genarateExcel();
					input.closeAll();
				}
				
				if (callType == 0 && list.size()>0){
					rcr.deleteReportId(list.get(i).getReportId());
				}
				
			}
			
			rcr.closeAll();

		} catch (Exception e) {

			System.out.println("error in main : " + e);

		}
		
	}

}