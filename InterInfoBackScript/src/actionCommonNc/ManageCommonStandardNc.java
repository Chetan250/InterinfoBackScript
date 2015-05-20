package actionCommonNc;

import beanNational.CustomReportBeanNc;

public class ManageCommonStandardNc {
	
	public static void main(String arg[])
	{
		RetrieveSaveStandardNc retrieveSaveStandardNc = new RetrieveSaveStandardNc();
		
		CustomReportBeanNc bean = retrieveSaveStandardNc.getSaveStanderdReport();
		if(bean!=null){
			
			RetrieveDataFromStandardReportNc retrieveDataFromStandardReportNc = new RetrieveDataFromStandardReportNc();
			retrieveDataFromStandardReportNc.getDataNc(bean);
			retrieveSaveStandardNc.deleteReportId(bean.getReportId());			
			retrieveDataFromStandardReportNc.closeAll();
			retrieveSaveStandardNc.closeAll();
			
		}
		
			
		
	}

}
