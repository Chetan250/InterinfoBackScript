package daoNational;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import beanNational.CustomReportBeanNc;
import connection.MyConnection;


public class RefreshCustomReportDaoNc {
	
		Connection conn;
		ResultSet rs;
		Statement stmt;

		public RefreshCustomReportDaoNc(Properties prop) {
			try {
				conn = new MyConnection().getConnection(prop);
			} catch (Exception e) {
				System.out.println("Error while getting connection in National ");
				e.printStackTrace();

			}
		}

		public ArrayList<CustomReportBeanNc> getDataNc() {
			Calendar calendar = Calendar.getInstance();
			//int day; 
			int  month, date;
			//day = calendar.get(Calendar.DAY_OF_MONTH);
			month = calendar.get(Calendar.MONTH) + 1;
			// System.out.println(" "+month);
			date = calendar.get(Calendar.DATE);
			String query = "select * from custom_report_nc ";

			query = query + " where (refresh_month = '*' or refresh_month = '"
					+ month + "') and " + " (refresh_date = '*' or refresh_date = '" + date + "')";
			System.out.println("Query :"+ query);
			ArrayList<CustomReportBeanNc> list = new ArrayList<CustomReportBeanNc>();
			CustomReportBeanNc beanNc = null;
			// System.out.println("query="+query);
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					beanNc = new CustomReportBeanNc();
					beanNc.setReportId(rs.getString(1));
					beanNc.setReportName(rs.getString(2));
					beanNc.setReportTitle(rs.getString(3));
					beanNc.setUserId(rs.getString(4));
					beanNc.setRefreshMonth(rs.getString(5));
					beanNc.setRefreshYear(rs.getString(6));
					beanNc.setRefreshDate(rs.getString(7));
					beanNc.setFromDate(rs.getString(8));
					beanNc.setToDate(rs.getString(9));
					beanNc.setInformant(rs.getString(10));
					beanNc.setMexicanSupplier(rs.getString(11));
					beanNc.setForeignSupplier(rs.getString(12));
					beanNc.setCountry(rs.getString(13));
					beanNc.setSupplier_type(rs.getString(14));
					beanNc.setSupplierActivity(rs.getString(15));
					beanNc.setState(rs.getString(16));
					beanNc.setCreatedDate(rs.getString(17));
					beanNc.setOutputAs(rs.getString(18));
					
					list.add(beanNc);
				}
				System.out.println("Size of List in natinal =" + list.size());
			} catch (Exception e) {

				e.printStackTrace();
			}
			return list;
		}

		public void closeAll() {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();

				System.out
						.println("connection close in refresh  Custom Report Dao in National");
			} catch (Exception e) {

			}

		}

		public ArrayList<CustomReportBeanNc> getSingleDataNc() {
			ArrayList<CustomReportBeanNc> objBeans = new ArrayList<CustomReportBeanNc>();
			String query = "select a.* from custom_report_nc a JOIN custom_report_run_now_nc b ON a.report_id = b.report_id limit 1 ";
			CustomReportBeanNc beanNc = null;
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					beanNc = new CustomReportBeanNc();
					beanNc.setReportId(rs.getString(1));
					beanNc.setReportName(rs.getString(2));
					beanNc.setReportTitle(rs.getString(3));
					beanNc.setUserId(rs.getString(4));
					beanNc.setRefreshMonth(rs.getString(5));
					beanNc.setRefreshYear(rs.getString(6));
					beanNc.setRefreshDate(rs.getString(7));
					beanNc.setFromDate(rs.getString(8));
					beanNc.setToDate(rs.getString(9));
					beanNc.setInformant(rs.getString(10));
					beanNc.setMexicanSupplier(rs.getString(11));
					beanNc.setForeignSupplier(rs.getString(12));
					beanNc.setCountry(rs.getString(13));
					beanNc.setSupplier_type(rs.getString(14));
					beanNc.setSupplierActivity(rs.getString(15));
					beanNc.setState(rs.getString(16));
					beanNc.setCreatedDate(rs.getString(17));
					beanNc.setOutputAs(rs.getString(18));
					
					
					objBeans.add(beanNc);
				
				}
				System.out.println("Size of List in getSingleDataNc() in national =" + objBeans.size());
			} catch (Exception e) {

				e.printStackTrace();
			} finally {
		
			}
			return objBeans;
		}

		public void deleteReportId(String reportId) {
			String query = "delete from custom_report_run_now_nc where report_id = " + reportId;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(query);


			} catch (Exception e) {

				e.printStackTrace();
			} finally {
			}
			
		}

	}



