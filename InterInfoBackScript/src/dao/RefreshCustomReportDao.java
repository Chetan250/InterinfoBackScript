package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import bean.CustomReportBean;
import connection.MyConnection;

public class RefreshCustomReportDao {
 Connection conn;
 ResultSet rs;
 Statement stmt;
	
 public	RefreshCustomReportDao(Properties prop)
	{
		try {
			conn = new MyConnection().getConnection(prop);
		} catch (Exception e) {
			System.out.println("Error while getting connection");
			e.printStackTrace();

		}
	}
 
 public ArrayList<CustomReportBean> getData()
 {
	 Calendar calendar = Calendar.getInstance();
	 int day,month,year;
	 day = calendar.get(Calendar.DAY_OF_MONTH);
	 month = calendar.get(Calendar.MONTH)+1;
	// System.out.println(" "+month);
	 year = calendar.get(Calendar.YEAR);
	 String query="select * from custom_report ";
	 
	 query = query + " where (refresh_date = '*' or refresh_date = '"+day+ "') and "+
			 " (refresh_month = '*' or refresh_month = '" + month + "') and " + 
			 " (year = '*' or year = '" + year + "')";
	 
	 ArrayList<CustomReportBean>list=new ArrayList<CustomReportBean>();
	 CustomReportBean bean=null;
	//System.out.println("query="+query);
	 try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(query);
			
			while(rs.next())
			{
				 bean=new CustomReportBean();
				 bean.setReportId(rs.getString(1));
				 bean.setReportName(rs.getString(2));
				 bean.setRefreshDate(rs.getString(3));
				 bean.setRefreshMonth(rs.getString(4));
				 bean.setYear(rs.getString(5));
				 bean.setCreatedDate(rs.getString(6));
				 bean.setModifiedDate(rs.getString(7));
				 bean.setImportExport(rs.getString(8));
				 bean.setTaxNumber(rs.getString(9));
				 bean.setFromDate(rs.getString(10));
				 bean.setToDate(rs.getString(11));
				 bean.setHsCode(rs.getString(12));
				 bean.setValueRangeByDollerFrom(rs.getString(13));
				 bean.setValueRangeByDollerTo(rs.getString(14));
				 bean.setValueRangeByQuantityFrom(rs.getString(15));
				 bean.setValueRangeByQuantityTo(rs.getString(16));
				 bean.setSupplier(rs.getString(17));
				 bean.setBorder(rs.getString(18));
				 bean.setMotionKey(rs.getString(19));
				 bean.setRegimes(rs.getString(20));
				 bean.setTransportation(rs.getString(21));
				 bean.setOriginDestinationCountryCode(rs.getString(22));
				 bean.setBuyerSellerCountryCode(rs.getString(23));
				 bean.setBoundedWarehouses(rs.getString(24));
				 bean.setIdentifier(rs.getString(25));
				 bean.setRegulationRestriction(rs.getString(26));
				 bean.setContainerType(rs.getString(27));
				 bean.setBilling(rs.getString(28));
				 bean.setMerchandiseDestination(rs.getString(29));
				 bean.setBarCode(rs.getString(30));
				 bean.setStarategicFiscalArea(rs.getString(31));
				 bean.setNatOrInternat(rs.getString(32));
				 bean.setHsCodeLength(rs.getString(33));
				 bean.setOutputAs(rs.getString(34));
				 
				 list.add(bean);
			} 
			System.out.println("Size of List ="+list.size());
	 }catch (Exception e) {
		
			e.printStackTrace();
		}
	 return list;
 }
 
	public void closeAll() {
		try {
				if(conn!=null)
					conn.close();
					if(stmt!=null)
						stmt.close();
					if(rs!=null)
						rs.close();

					System.out.println("connection close in refresh  Custom Report Dao");
		} catch (Exception e) {

}

}

	
	

}
