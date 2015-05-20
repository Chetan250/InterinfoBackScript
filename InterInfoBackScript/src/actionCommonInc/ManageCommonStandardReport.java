package actionCommonInc;

import bean.CustomReportBean;
import dao.RetrieveSaveStanderdReport;

public class ManageCommonStandardReport {
	
	public static void main(String arg[])
	{
		RetrieveSaveStanderdReport retrieveSaveStanderdReport = new  RetrieveSaveStanderdReport();
		
		
		CustomReportBean bean = retrieveSaveStanderdReport.getSaveStanderdReport();
		if(bean != null && bean.getReportType() != null){
			if(bean.getReportType().equals("common"))
			{
				RetrieveDataFromStandardReportInc retrieveDataFromStandardReportInc = new RetrieveDataFromStandardReportInc();	
				retrieveDataFromStandardReportInc.getStandardData(bean);
				retrieveDataFromStandardReportInc.closeAll();
			}else if(bean.getReportType().equals("extended"))
			{
				RetrieveDataFromExtendedReport retrieveDataFromExtendedReport = new RetrieveDataFromExtendedReport();
				retrieveDataFromExtendedReport.getExtendedData(bean);
				retrieveDataFromExtendedReport.closeAll();
			}
			retrieveSaveStanderdReport.deleteReportId(bean.getReportId());
			retrieveSaveStanderdReport.closeAll();
		}
	}

}
