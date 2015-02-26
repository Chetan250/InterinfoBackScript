package action;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import bean.CountryOriginMonthBean;
import bean.CountryOriginQuarterBean;
import bean.CountryOriginSemisterBean;
import bean.CustomPortBean;
import bean.CustomsMonthBean;
import bean.CustomsQuarterBean;
import bean.CustomsSemisterBean;
import bean.ForeignMonthBean;
import bean.ForeignQuarterBean;
import bean.ForeignSemBean;
import bean.HSBean;
import bean.HSBeanQuarter;
import bean.HSBeanSemister;
import bean.HSMonthBean;
import bean.MasterBean;
import bean.MaxicanMonthBean;
import bean.MaxicanQuarterBean;
import bean.MaxicanSemisterBean;
import bean.MexicanCustomPortBean;
import bean.MexicanSupplierBean;
import bean.SubReportMap;
import connection.MyConnection;
import dao.FinalDao;


public class ReportData {

	Connection conn;
	Statement stmt;
	Properties prop;
	MasterBean masterBean=new MasterBean();

	public ReportData(Properties props) {
		try {
			prop = props;
			conn = new MyConnection().getConnection(prop);
			stmt = conn.createStatement();
			
		} catch (Exception e) {
			System.out.println("Error while getting connection");
			e.printStackTrace();

		}
	}

	// =========================HSCODE By Dollar in Month================
	public void getHSByMonthDollar() {
		ArrayList<HSMonthBean> beans = new ArrayList<HSMonthBean>();
		String HSQuery = " Select a.FRACCION,YEAR(date) AS year,"
				+ "case when MONTH(date)  = '1' then '1-Jan' "
				+ "when MONTH(date) = '2' then '2-Feb'"
				+ " when MONTH(date) = '3' then '3-Mar'"
				+ "when MONTH(date) = '4' then '4-Apr '"
				+ " when MONTH(date) = '5' then '5-May '"
				+ " when MONTH(date) = '6' then '6-Jun '"
				+ "when MONTH(date) = '7' then '7-Jul '"
				+ "when MONTH(date) = '8' then '8-Aug '"
				+ "when MONTH(date) = '9' then '9-Sep '"
				+ "when MONTH(date) = '10' then '91-Oct '"
				+ " when MONTH(date) = '11' then '92-Nov '"
				+ "when MONTH(date) = '12' then '93-Dec '"
				+ "end as month,sum(MONTO) as uusd "
				+ "from temp_report a,(select FRACCION,sum(MONTO) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b"
				+ " where a.FRACCION=b.FRACCION group by 1,2,3";

		try {
			ResultSet rs = stmt.executeQuery(HSQuery);
			while (rs.next()) {
				
				HSMonthBean hsMonthBean = new HSMonthBean();
				hsMonthBean.setFRACCION(rs.getString(1));
				hsMonthBean.setYear(rs.getString(2));
				hsMonthBean.setMonth(rs.getString(3));
				hsMonthBean.setMONTO(rs.getInt(4));

				beans.add(hsMonthBean);
				
				
			}
			masterBean.setHsMonthBean(beans);
	
		} catch (Exception ex) {
			System.out.println("Error while getHSByMonthDollar " + ex);
		}
	}

	// =========================HSCODE By Dollar in Quarter================
	public void getHSByQuarterDollar() {
		ArrayList<HSBeanQuarter> beans = new ArrayList<HSBeanQuarter>();
		String HSQuery = "Select a.FRACCION,YEAR(date) as year, "
				+ " quarter(date),sum(MONTO) as MMONTO"
				+ " from temp_report a,(select FRACCION,sum(MONTO) from temp_report"
				+ " group by 1 order by 2 desc limit 10) b"
				+ " where a.FRACCION=b.FRACCION group by 1,2,3";

		try {
			ResultSet rs = stmt.executeQuery(HSQuery);
			while (rs.next()) {
				HSBeanQuarter hsBean = new HSBeanQuarter();
				hsBean.setFRACCION(rs.getString(1));
				hsBean.setYear(rs.getString(2));
				hsBean.setMonth(rs.getString(3));
				hsBean.setMONTO(rs.getInt(4));

				beans.add(hsBean);
			}
			masterBean.setHsBeanQuarter(beans);
		} catch (Exception ex) {
			System.out.println("Error while getHSByQuarterDollar " + ex);
		}
	}

	// =========================HSCODE By Dollar in Sem================

	public void getHSBySemDollar() {
		ArrayList<HSBeanSemister> beans = new ArrayList<HSBeanSemister>();
		String HSQuery = "Select a.FRACCION,YEAR(date) as year,"
				+ "case when MONTH(date) < '7' then '1'else '2'"
				+ "end as semester,sum(MONTO) as MONTO "
				+ "from temp_report a,(select FRACCION,sum(MONTO) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b  "
				+ "where a.FRACCION=b.FRACCION group by 1,2,3";

		try {
			ResultSet rs = stmt.executeQuery(HSQuery);
			while (rs.next()) {
				HSBeanSemister hsBean = new HSBeanSemister();
				hsBean.setFRACCION(rs.getString(1));
				hsBean.setYear(rs.getString(2));
				hsBean.setMonth(rs.getString(3));
				hsBean.setMONTO(rs.getInt(4));

				beans.add(hsBean);
			}
			
			masterBean.setHsBeanSemister(beans);
		} catch (Exception ex) {
			System.out.println("Error while getHSBySemesterDollar " + ex);
		}
	}

	// =========================HSCODE By Qty in Month================

	public void getHSByMonthQty() {
		ArrayList<HSMonthBean> beans = new ArrayList<HSMonthBean>();
		String HSQuery = " Select a.FRACCION,YEAR(date) AS year,"
				+ "case when MONTH(date)  = '1' then '1-Jan' "
				+ "when MONTH(date) = '2' then '2-Feb'"
				+ " when MONTH(date) = '3' then '3-Mar'"
				+ "when MONTH(date) = '4' then '4-Apr '"
				+ " when MONTH(date) = '5' then '5-May '"
				+ " when MONTH(date) = '6' then '6-Jun '"
				+ "when MONTH(date) = '7' then '7-Jul '"
				+ "when MONTH(date) = '8' then '8-Aug '"
				+ "when MONTH(date) = '9' then '9-Sep '"
				+ "when MONTH(date) = '10' then '91-Oct '"
				+ " when MONTH(date) = '11' then '92-Nov '"
				+ "when MONTH(date) = '12' then '93-Dec '"
				+ "end as month,sum(CANTIDAD) as uusd "
				+ "from temp_report a,(select FRACCION,sum(CANTIDAD) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b"
				+ " where a.FRACCION=b.FRACCION group by 1,2,3";

		try {
			ResultSet rs = stmt.executeQuery(HSQuery);
			while (rs.next()) {
				HSMonthBean hsBean = new HSMonthBean();
				hsBean.setFRACCION(rs.getString(1));
				hsBean.setYear(rs.getString(2));
				hsBean.setMonth(rs.getString(3));
				hsBean.setMONTO(rs.getInt(4));

				beans.add(hsBean);
			}
			masterBean.setHsBeanMonthQty(beans);
		} catch (Exception ex) {
			System.out.println("Error while getHSByMonthQty " + ex);
		}
	}

	// =========================HSCODE By Qty in Quarter================

	public void getHSByQuarterQty() {
		ArrayList<HSBeanQuarter> beans = new ArrayList<HSBeanQuarter>();
		String HSQuery = "Select a.FRACCION,YEAR(date) as year, "
				+ " quarter(date),sum(CANTIDAD) as CANTIDAD"
				+ " from temp_report a,(select FRACCION,sum(CANTIDAD) from temp_report"
				+ " group by 1 order by 2 desc limit 10) b"
				+ " where a.FRACCION=b.FRACCION group by 1,2,3";

		try {
			ResultSet rs = stmt.executeQuery(HSQuery);
			while (rs.next()) {
				HSBeanQuarter hsBean = new HSBeanQuarter();
				hsBean.setFRACCION(rs.getString(1));
				hsBean.setYear(rs.getString(2));
				hsBean.setMonth(rs.getString(3));
				hsBean.setMONTO(rs.getInt(4));

				beans.add(hsBean);
			}
			masterBean.setHsBeanQuarterQty(beans);
		} catch (Exception ex) {
			System.out.println("Error while getHSByQuarterQty " + ex);
		}
	}

	// =========================HSCODE By Qty in Sem================

	public void getHSBySemQty() {
		ArrayList<HSBeanSemister> beans = new ArrayList<HSBeanSemister>();
		String HSQuery = "Select a.FRACCION,YEAR(date) as year,"
				+ "case when MONTH(date) < '7' then '1'else '2'"
				+ "end as semester,sum(CANTIDAD) as CANTIDAD "
				+ "from temp_report a,(select FRACCION,sum(CANTIDAD) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b  "
				+ "where a.FRACCION=b.FRACCION group by 1,2,3";

		try {
			ResultSet rs = stmt.executeQuery(HSQuery);
			while (rs.next()) {
				HSBeanSemister hsBean = new HSBeanSemister();
				hsBean.setFRACCION(rs.getString(1));
				hsBean.setYear(rs.getString(2));
				hsBean.setMonth(rs.getString(3));
				hsBean.setMONTO(rs.getInt(4));

				beans.add(hsBean);
			}
			masterBean.setHsBeanSemisterQty(beans);
		} catch (Exception ex) {
			System.out.println("Error while getHSBySemQty " + ex);
		}
	}

	// =========================Maxican By Dollar in Month================

	public void getMaxicanByMonthDollar() {
		ArrayList<MaxicanMonthBean> beans = new ArrayList<MaxicanMonthBean>();
		String MaxicanQuery = " Select a.Taxnumber,YEAR(date) AS year,"
				+ "case when MONTH(date)  = '1' then '1-Jan' "
				+ "when MONTH(date) = '2' then '2-Feb'"
				+ " when MONTH(date) = '3' then '3-Mar'"
				+ "when MONTH(date) = '4' then '4-Apr '"
				+ " when MONTH(date) = '5' then '5-May '"
				+ " when MONTH(date) = '6' then '6-Jun '"
				+ "when MONTH(date) = '7' then '7-Jul '"
				+ "when MONTH(date) = '8' then '8-Aug '"
				+ "when MONTH(date) = '9' then '9-Sep '"
				+ "when MONTH(date) = '10' then '91-Oct '"
				+ " when MONTH(date) = '11' then '92-Nov '"
				+ "when MONTH(date) = '12' then '93-Dec '"
				+ "end as month,sum(MONTO) as MONTO "
				+ "from temp_report a,(select Taxnumber,sum(MONTO) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b"
				+ " where a.Taxnumber=b.Taxnumber group by 1,2,3";
		try {
			ResultSet rs = stmt.executeQuery(MaxicanQuery);
			while (rs.next()) {

				MaxicanMonthBean monthBean = new MaxicanMonthBean();
				monthBean.setTaxNumber(rs.getString(1));
				monthBean.setYear(rs.getString(2));
				monthBean.setMonth(rs.getString(3));
				monthBean.setMONTO(rs.getDouble(4));

				beans.add(monthBean);
			}
			masterBean.setMaxicanMonthBean(beans);

		} catch (Exception ex) {
			System.out.println("Error while getMaxicanByMonthDollar " + ex);
		}
	}

	// =========================Maxican By Dollar in Quarter================

	public void getMaxicanByQuarterDollar() {
		ArrayList<MaxicanQuarterBean> beans = new ArrayList<MaxicanQuarterBean>();
		String MaxicanQuery = "Select a.Taxnumber,YEAR(date) as year, "
				+ " quarter(date),sum(MONTO) as MMONTO"
				+ " from temp_report a,(select Taxnumber,sum(MONTO) from temp_report"
				+ " group by 1 order by 2 desc limit 10) b"
				+ " where a.Taxnumber=b.Taxnumber group by 1,2,3";
		try {
			ResultSet rs = stmt.executeQuery(MaxicanQuery);
			while (rs.next()) {

				MaxicanQuarterBean maxicanQuarterBean = new MaxicanQuarterBean();
				maxicanQuarterBean.setTaxNumber(rs.getString(1));
				maxicanQuarterBean.setYear(rs.getString(2));
				maxicanQuarterBean.setMonth(rs.getString(3));
				maxicanQuarterBean.setMONTO(rs.getDouble(4));

				beans.add(maxicanQuarterBean);
			}
			masterBean.setMaxicanQuarterBean(beans);

		} catch (Exception ex) {
			System.out.println("Error while getMaxicanByQuarterDollar " + ex);
		}
	}

	// =========================Maxican By Dollar in Sem================

	public void getMaxicanBySemDollar() {
		ArrayList<MaxicanSemisterBean> beans = new ArrayList<MaxicanSemisterBean>();
		String MaxicanQuery = "Select a.Taxnumber,YEAR(date) as year,"
				+ "case when MONTH(date) < '7' then '1'else '2'"
				+ "end as semester,sum(MONTO) as MONTO "
				+ "from temp_report a,(select Taxnumber,sum(MONTO) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b  "
				+ "where a.Taxnumber=b.Taxnumber group by 1,2,3";
		try {
			ResultSet rs = stmt.executeQuery(MaxicanQuery);
			while (rs.next()) {

				MaxicanSemisterBean maxicanSemisterBeanBean = new MaxicanSemisterBean();
				maxicanSemisterBeanBean.setTaxNumber(rs.getString(1));
				maxicanSemisterBeanBean.setYear(rs.getString(2));
				maxicanSemisterBeanBean.setMonth(rs.getString(3));
				maxicanSemisterBeanBean.setMONTO(rs.getDouble(4));

				beans.add(maxicanSemisterBeanBean);
			}
			masterBean.setMaxicanSemisterBean(beans);

		} catch (Exception ex) {
			System.out.println("Error while getMaxicanBySemDollar " + ex);
		}
	}

	// =========================Maxican By Qty in Month================

	public void getMaxicanByMonthQty() {
		ArrayList<MaxicanMonthBean> beans = new ArrayList<MaxicanMonthBean>();
		String MaxicanQuery =  " Select a.Taxnumber,YEAR(date) AS year,"
				+ "case when MONTH(date)  = '1' then '1-Jan' "
				+ "when MONTH(date) = '2' then '2-Feb'"
				+ " when MONTH(date) = '3' then '3-Mar'"
				+ "when MONTH(date) = '4' then '4-Apr '"
				+ " when MONTH(date) = '5' then '5-May '"
				+ " when MONTH(date) = '6' then '6-Jun '"
				+ "when MONTH(date) = '7' then '7-Jul '"
				+ "when MONTH(date) = '8' then '8-Aug '"
				+ "when MONTH(date) = '9' then '9-Sep '"
				+ "when MONTH(date) = '10' then '91-Oct '"
				+ " when MONTH(date) = '11' then '92-Nov '"
				+ "when MONTH(date) = '12' then '93-Dec '"
				+ "end as month,sum(CANTIDAD) as CANTIDAD "
				+ "from temp_report a,(select Taxnumber,sum(CANTIDAD) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b"
				+ " where a.Taxnumber=b.Taxnumber group by 1,2,3";
		try {
			ResultSet rs = stmt.executeQuery(MaxicanQuery);
			while (rs.next()) {

				MaxicanMonthBean monthBean = new MaxicanMonthBean();
				monthBean.setTaxNumber(rs.getString(1));
				monthBean.setYear(rs.getString(2));
				monthBean.setMonth(rs.getString(3));
				monthBean.setMONTO(rs.getDouble(4));

				beans.add(monthBean);
			}
			masterBean.setMaxicanMonthBeanQty(beans);

		} catch (Exception ex) {
			System.out.println("Error while getMaxicanByMonthQty " + ex);
		}
	}

	// =========================Maxican By Qty in Quarter================

	public void getMaxicanByQuarterQty() {
		ArrayList<MaxicanQuarterBean> beans = new ArrayList<MaxicanQuarterBean>();
		String MaxicanQuery = "Select a.Taxnumber,YEAR(date) as year, "
				+ " quarter(date),sum(CANTIDAD) as CANTIDAD"
				+ " from temp_report a,(select Taxnumber,sum(CANTIDAD) from temp_report"
				+ " group by 1 order by 2 desc limit 10) b"
				+ " where a.Taxnumber=b.Taxnumber group by 1,2,3";
		try {
			ResultSet rs = stmt.executeQuery(MaxicanQuery);
			while (rs.next()) {

				MaxicanQuarterBean maxicanQuarterBean = new MaxicanQuarterBean();
				maxicanQuarterBean.setTaxNumber(rs.getString(1));
				maxicanQuarterBean.setYear(rs.getString(2));
				maxicanQuarterBean.setMonth(rs.getString(3));
				maxicanQuarterBean.setMONTO(rs.getDouble(4));

				beans.add(maxicanQuarterBean);
			}
			masterBean.setMaxicanQuarterBeanQty(beans);

		} catch (Exception ex) {
			System.out.println("Error while getMaxicanByQuarterQty " + ex);
		}
	}

	// =========================Maxican By Qty in Sem================

	public void getMaxicanBySemQty() {
		ArrayList<MaxicanSemisterBean> beans = new ArrayList<MaxicanSemisterBean>();
		String MaxicanQuery = "Select a.Taxnumber,YEAR(date) as year,"
				+ "case when MONTH(date) < '7' then '1'else '2'"
				+ "end as semester,sum(CANTIDAD) as CANTIDAD "
				+ "from temp_report a,(select Taxnumber,sum(CANTIDAD) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b  "
				+ "where a.Taxnumber=b.Taxnumber group by 1,2,3";
		try {
			ResultSet rs = stmt.executeQuery(MaxicanQuery);
			while (rs.next()) {

				MaxicanSemisterBean maxicanSemisterBeanBean = new MaxicanSemisterBean();
				maxicanSemisterBeanBean.setTaxNumber(rs.getString(1));
				maxicanSemisterBeanBean.setYear(rs.getString(2));
				maxicanSemisterBeanBean.setMonth(rs.getString(3));
				maxicanSemisterBeanBean.setMONTO(rs.getDouble(4));

				beans.add(maxicanSemisterBeanBean);
			}
			masterBean.setMaxicanSemisterBeanQty(beans);

		} catch (Exception ex) {
			System.out.println("Error while getMaxicanBySemQty " + ex);
		}
	}

	// =========================Customs By Dollar in Month================

	public void getCustomsByMonthDollar() {
		ArrayList<CustomsMonthBean> beans = new ArrayList<CustomsMonthBean>();

		String CustomsQuery = " Select a.border,YEAR(date) AS year,"
				+ "case when MONTH(date)  = '1' then '1-Jan' "
				+ "when MONTH(date) = '2' then '2-Feb'"
				+ " when MONTH(date) = '3' then '3-Mar'"
				+ "when MONTH(date) = '4' then '4-Apr '"
				+ " when MONTH(date) = '5' then '5-May '"
				+ " when MONTH(date) = '6' then '6-Jun '"
				+ "when MONTH(date) = '7' then '7-Jul '"
				+ "when MONTH(date) = '8' then '8-Aug '"
				+ "when MONTH(date) = '9' then '9-Sep '"
				+ "when MONTH(date) = '10' then '91-Oct '"
				+ " when MONTH(date) = '11' then '92-Nov '"
				+ "when MONTH(date) = '12' then '93-Dec '"
				+ "end as month,sum(MONTO) as MONTO "
				+ "from temp_report a,(select border,sum(MONTO) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b"
				+ " where a.border=b.border group by 1,2,3";

		try {
			ResultSet rs = stmt.executeQuery(CustomsQuery);
			while (rs.next()) {

				CustomsMonthBean customsMonthBean = new CustomsMonthBean();
				customsMonthBean.setBorder(rs.getString(1));
				customsMonthBean.setYear(rs.getString(2));
				customsMonthBean.setMonth(rs.getString(3));
				customsMonthBean.setMONTO(rs.getInt(4));

				beans.add(customsMonthBean);
			}

			masterBean.setCustomsMonthBean(beans);
		} catch (Exception ex) {
			System.out.println("Error while getCustomsByMonthDollar " + ex);
		}
	}

	// =========================Customs By Dollar in Quarter================

	public void getCustomsByQuarterDollar() {
		ArrayList<CustomsQuarterBean> beans = new ArrayList<CustomsQuarterBean>();
		String CustomsQuery = "Select a.border,YEAR(date) as year, "
				+ " quarter(date),sum(MONTO) as MONTO"
				+ " from temp_report a,(select border,sum(MONTO) from temp_report"
				+ " group by 1 order by 2 desc limit 10) b"
				+ " where a.border=b.border group by 1,2,3";

		try {
			ResultSet rs = stmt.executeQuery(CustomsQuery);
			while (rs.next()) {

				CustomsQuarterBean customsQuarterBean = new CustomsQuarterBean();
				customsQuarterBean.setBorder(rs.getString(1));
				customsQuarterBean.setYear(rs.getString(2));
				customsQuarterBean.setMonth(rs.getString(3));
				customsQuarterBean.setMONTO(rs.getInt(4));

				beans.add(customsQuarterBean);
			}
			masterBean.setCustomsQuarterBean(beans);
		} catch (Exception ex) {
			System.out.println("Error while getCustomsByQuarterDollar " + ex);
		}
	}

	// =========================Customs By Dollar in Sem================

	public void getCustomsBySemDollar() {
		ArrayList<CustomsSemisterBean> beans = new ArrayList<CustomsSemisterBean>();
		String CustomsQuery = "Select a.border,YEAR(date) as year,"
				+ "case when MONTH(date) < '7' then '1'else '2'"
				+ "end as semester,sum(MONTO) as MONTO "
				+ "from temp_report a,(select border,sum(MONTO) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b  "
				+ "where a.border=b.border group by 1,2,3";

		try {
			ResultSet rs = stmt.executeQuery(CustomsQuery);
			while (rs.next()) {

				CustomsSemisterBean customsSemisterBean = new CustomsSemisterBean();
				customsSemisterBean.setBorder(rs.getString(1));
				customsSemisterBean.setYear(rs.getString(2));
				customsSemisterBean.setMonth(rs.getString(3));
				customsSemisterBean.setMONTO(rs.getDouble(4));

				beans.add(customsSemisterBean);
			}
			masterBean.setCustomsSemisterBean(beans);
		} catch (Exception ex) {
			System.out.println("Error while getCustomsBySemDollar " + ex);
		}
	}

	// =========================Customs By Quantity in Month================

	public void getCustomsByMonthQty() {
		ArrayList<CustomsMonthBean> beans = new ArrayList<CustomsMonthBean>();

		String CustomsQuery = " Select a.border,YEAR(date) AS year,"
				+ "case when MONTH(date)  = '1' then '1-Jan' "
				+ "when MONTH(date) = '2' then '2-Feb'"
				+ " when MONTH(date) = '3' then '3-Mar'"
				+ "when MONTH(date) = '4' then '4-Apr '"
				+ " when MONTH(date) = '5' then '5-May '"
				+ " when MONTH(date) = '6' then '6-Jun '"
				+ "when MONTH(date) = '7' then '7-Jul '"
				+ "when MONTH(date) = '8' then '8-Aug '"
				+ "when MONTH(date) = '9' then '9-Sep '"
				+ "when MONTH(date) = '10' then '91-Oct '"
				+ " when MONTH(date) = '11' then '92-Nov '"
				+ "when MONTH(date) = '12' then '93-Dec '"
				+ "end as month,sum(CANTIDAD) as CANTIDAD "
				+ "from temp_report a,(select border,sum(CANTIDAD) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b"
				+ " where a.border=b.border group by 1,2,3";

		try {
			ResultSet rs = stmt.executeQuery(CustomsQuery);
			while (rs.next()) {

				CustomsMonthBean customsMonthBean = new CustomsMonthBean();
				customsMonthBean.setBorder(rs.getString(1));
				customsMonthBean.setYear(rs.getString(2));
				customsMonthBean.setMonth(rs.getString(3));
				customsMonthBean.setMONTO(rs.getInt(4));

				beans.add(customsMonthBean);
			}

			masterBean.setCustomsMonthBeanQty(beans);
		} catch (Exception ex) {
			System.out.println("Error while getCustomsByMonthQty " + ex);
		}
	}

	// =========================Customs By Quantity in Quarter================

	public void getCustomsByQuarterQty() {
		ArrayList<CustomsQuarterBean> beans = new ArrayList<CustomsQuarterBean>();
		String CustomsQuery = "Select a.border,YEAR(date) as year, "
				+ " quarter(date),sum(CANTIDAD) as CANTIDAD"
				+ " from temp_report a,(select border,sum(CANTIDAD) from temp_report"
				+ " group by 1 order by 2 desc limit 10) b"
				+ " where a.border=b.border group by 1,2,3";

		try {
			ResultSet rs = stmt.executeQuery(CustomsQuery);
			while (rs.next()) {

				CustomsQuarterBean customsQuarterBean = new CustomsQuarterBean();
				customsQuarterBean.setBorder(rs.getString(1));
				customsQuarterBean.setYear(rs.getString(2));
				customsQuarterBean.setMonth(rs.getString(3));
				customsQuarterBean.setMONTO(rs.getInt(4));

				beans.add(customsQuarterBean);
			}
			masterBean.setCustomsQuarterBeanQty(beans);
		} catch (Exception ex) {
			System.out.println("Error while getCustomsByQuarterQty " + ex);
		}
	}

	// =========================Customs By Quantity in Sem================

	public void getCustomsBySemQty() {
		ArrayList<CustomsSemisterBean> beans = new ArrayList<CustomsSemisterBean>();
		String CustomsQuery =  "Select a.border,YEAR(date) as year,"
				+ "case when MONTH(date) < '7' then '1'else '2'"
				+ "end as semester,sum(CANTIDAD) as CANTIDAD "
				+ "from temp_report a,(select border,sum(CANTIDAD) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b  "
				+ "where a.border=b.border group by 1,2,3";

		try {
			ResultSet rs = stmt.executeQuery(CustomsQuery);
			while (rs.next()) {

				CustomsSemisterBean customsSemisterBean = new CustomsSemisterBean();
				customsSemisterBean.setBorder(rs.getString(1));
				customsSemisterBean.setYear(rs.getString(2));
				customsSemisterBean.setMonth(rs.getString(3));
				customsSemisterBean.setMONTO(rs.getDouble(4));

				beans.add(customsSemisterBean);
			}
			masterBean.setCustomsSemisterBeanQty(beans);
		} catch (Exception ex) {
			System.out.println("Error while getCustomsBySemQty " + ex);
		}
	}

	// =================Country Origin By Dollar in Month================

	public void getCountryByMonthDollar() {
		ArrayList<CountryOriginMonthBean> beans = new ArrayList<CountryOriginMonthBean>();
		String CountryOriginQuery = " Select a.ccOrigDest,YEAR(date) AS year,"
				+ "case when MONTH(date)  = '1' then '1-Jan' "
				+ "when MONTH(date) = '2' then '2-Feb'"
				+ " when MONTH(date) = '3' then '3-Mar'"
				+ "when MONTH(date) = '4' then '4-Apr '"
				+ " when MONTH(date) = '5' then '5-May '"
				+ " when MONTH(date) = '6' then '6-Jun '"
				+ "when MONTH(date) = '7' then '7-Jul '"
				+ "when MONTH(date) = '8' then '8-Aug '"
				+ "when MONTH(date) = '9' then '9-Sep '"
				+ "when MONTH(date) = '10' then '91-Oct '"
				+ " when MONTH(date) = '11' then '92-Nov '"
				+ "when MONTH(date) = '12' then '93-Dec '"
				+ "end as month,sum(MONTO) as MONTO "
				+ "from temp_report a,(select ccOrigDest,sum(MONTO) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b"
				+ " where a.ccOrigDest=b.ccOrigDest group by 1,2,3";
		try {
			ResultSet rs = stmt.executeQuery(CountryOriginQuery);
			while (rs.next()) {

				CountryOriginMonthBean countryOriginMonthBean = new CountryOriginMonthBean();
				countryOriginMonthBean.setCcOrigDest(rs.getString(1));
				countryOriginMonthBean.setYear(rs.getString(2));
				countryOriginMonthBean.setMonth(rs.getString(3));
				countryOriginMonthBean.setMONTO(rs.getInt(4));

				beans.add(countryOriginMonthBean);
			}
			masterBean.setCountryOriginMonthBean(beans);
		} catch (Exception ex) {
			System.out.println("Error while getCountryOriginMonthDollar " + ex);
		}
	}

	// =========Country Origin By Dollar in Quarter================

	public void getCountryByQuarterDollar() {
		ArrayList<CountryOriginQuarterBean> beans = new ArrayList<CountryOriginQuarterBean>();
		String CountryOriginQuery = "Select a.ccOrigDest,YEAR(date) as year, "
				+ " quarter(date),sum(MONTO) as MONTO"
				+ " from temp_report a,(select ccOrigDest,sum(MONTO) from temp_report"
				+ " group by 1 order by 2 desc limit 10) b"
				+ " where a.ccOrigDest=b.ccOrigDest group by 1,2,3";

		try {
			ResultSet rs = stmt.executeQuery(CountryOriginQuery);
			while (rs.next()) {

				CountryOriginQuarterBean countryOriginQuarterBean = new CountryOriginQuarterBean();
				countryOriginQuarterBean.setCcOrigDest(rs.getString(1));
				countryOriginQuarterBean.setYear(rs.getString(2));
				countryOriginQuarterBean.setMonth(rs.getString(3));
				countryOriginQuarterBean.setMONTO(rs.getInt(4));

				beans.add(countryOriginQuarterBean);
			}
			masterBean.setCountryOriginQuarterBean(beans);
		} catch (Exception ex) {
			System.out.println("Error while getCountryOriginQuarterDollar "
					+ ex);
		}
	}

	// =========================Country Origin By Dollar in Sem================
	public void getCountryBySemDollar() {
		ArrayList<CountryOriginSemisterBean> beans = new ArrayList<CountryOriginSemisterBean>();
		String CountryOriginQuery =  "Select a.ccOrigDest,YEAR(date) as year,"
				+ "case when MONTH(date) < '7' then '1'else '2'"
				+ "end as semester,sum(MONTO) as MONTO "
				+ "from temp_report a,(select ccOrigDest,sum(MONTO) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b  "
				+ "where a.ccOrigDest=b.ccOrigDest group by 1,2,3";
		try {
			ResultSet rs = stmt.executeQuery(CountryOriginQuery);
			while (rs.next()) {

				CountryOriginSemisterBean countryOriginSemisterBean = new CountryOriginSemisterBean();
				countryOriginSemisterBean.setCcOrigDest(rs.getString(1));
				countryOriginSemisterBean.setYear(rs.getString(2));
				countryOriginSemisterBean.setMonth(rs.getString(3));
				countryOriginSemisterBean.setMONTO(rs.getDouble(4));

				beans.add(countryOriginSemisterBean);
			}
			masterBean.setCountryOriginSemisterBean(beans);
		} catch (Exception ex) {
			System.out.println("Error while getCountryOriginSemisterDollar "
					+ ex);
		}
	}

	// ================Country Origin By Quantity in Month================

	public void getCountryByMonthQty() {
		ArrayList<CountryOriginMonthBean> beans = new ArrayList<CountryOriginMonthBean>();
		String CountryOriginQuery = " Select a.ccOrigDest,YEAR(date) AS year,"
				+ "case when MONTH(date)  = '1' then '1-Jan' "
				+ "when MONTH(date) = '2' then '2-Feb'"
				+ " when MONTH(date) = '3' then '3-Mar'"
				+ "when MONTH(date) = '4' then '4-Apr '"
				+ " when MONTH(date) = '5' then '5-May '"
				+ " when MONTH(date) = '6' then '6-Jun '"
				+ "when MONTH(date) = '7' then '7-Jul '"
				+ "when MONTH(date) = '8' then '8-Aug '"
				+ "when MONTH(date) = '9' then '9-Sep '"
				+ "when MONTH(date) = '10' then '91-Oct '"
				+ " when MONTH(date) = '11' then '92-Nov '"
				+ "when MONTH(date) = '12' then '93-Dec '"
				+ "end as month,sum(CANTIDAD) as CANTIDAD "
				+ "from temp_report a,(select ccOrigDest,sum(CANTIDAD) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b"
				+ " where a.ccOrigDest=b.ccOrigDest group by 1,2,3";
		try {
			ResultSet rs = stmt.executeQuery(CountryOriginQuery);
			while (rs.next()) {

				CountryOriginMonthBean countryOriginMonthBean = new CountryOriginMonthBean();
				countryOriginMonthBean.setCcOrigDest(rs.getString(1));
				countryOriginMonthBean.setYear(rs.getString(2));
				countryOriginMonthBean.setMonth(rs.getString(3));
				countryOriginMonthBean.setMONTO(rs.getInt(4));

				beans.add(countryOriginMonthBean);
			}
			masterBean.setCountryOriginMonthBeanQty(beans);
		} catch (Exception ex) {
			System.out.println("Error while getCountryOriginMonthQty " + ex);
		}
	}

	// ==============Country Origin By Quantity in Quarter================

	public void getCountryByQuarterQty() {
		ArrayList<CountryOriginQuarterBean> beans = new ArrayList<CountryOriginQuarterBean>();
		String CountryOriginQuery = "Select a.ccOrigDest,YEAR(date) as year, "
				+ " quarter(date),sum(CANTIDAD) as CANTIDAD"
				+ " from temp_report a,(select ccOrigDest,sum(CANTIDAD) from temp_report"
				+ " group by 1 order by 2 desc limit 10) b"
				+ " where a.ccOrigDest=b.ccOrigDest group by 1,2,3";
		try {
			ResultSet rs = stmt.executeQuery(CountryOriginQuery);
			while (rs.next()) {

				CountryOriginQuarterBean countryOriginQuarterBean = new CountryOriginQuarterBean();
				countryOriginQuarterBean.setCcOrigDest(rs.getString(1));
				countryOriginQuarterBean.setYear(rs.getString(2));
				countryOriginQuarterBean.setMonth(rs.getString(3));
				countryOriginQuarterBean.setMONTO(rs.getInt(4));

				beans.add(countryOriginQuarterBean);
			}
			masterBean.setCountryOriginQuarterBeanQty(beans);
		} catch (Exception ex) {
			System.out.println("Error while getCountryOriginQuarterQty " + ex);
		}
	}

	// ==============Country Origin By Quantity in Sem================
	public void getCountryBySemQty() {
		ArrayList<CountryOriginSemisterBean> beans = new ArrayList<CountryOriginSemisterBean>();
		String CountryOriginQuery =  "Select a.ccOrigDest,YEAR(date) as year,"
				+ "case when MONTH(date) < '7' then '1'else '2'"
				+ "end as semester,sum(CANTIDAD) as CANTIDAD "
				+ "from temp_report a,(select ccOrigDest,sum(CANTIDAD) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b  "
				+ "where a.ccOrigDest=b.ccOrigDest group by 1,2,3";
		try {
			ResultSet rs = stmt.executeQuery(CountryOriginQuery);
			while (rs.next()) {

				CountryOriginSemisterBean countryOriginSemisterBean = new CountryOriginSemisterBean();
				countryOriginSemisterBean.setCcOrigDest(rs.getString(1));
				countryOriginSemisterBean.setYear(rs.getString(2));
				countryOriginSemisterBean.setMonth(rs.getString(3));
				countryOriginSemisterBean.setMONTO(rs.getDouble(4));

				beans.add(countryOriginSemisterBean);
			}
			masterBean.setCountryOriginSemisterBeanQty(beans);
		} catch (Exception ex) {
			System.out.println("Error while getCountryOriginSemisterQty " + ex);
		}
	}
	
	// ==============Foreign Compamy By Dollar in Month================
	public void getForeignByMonthDollar() {
		ArrayList<ForeignMonthBean> beans = new ArrayList<ForeignMonthBean>();
		String foreignQuery =   " Select a.supplier,YEAR(date) AS year,"
				+ "case when MONTH(date)  = '1' then '1-Jan' "
				+ "when MONTH(date) = '2' then '2-Feb'"
				+ " when MONTH(date) = '3' then '3-Mar'"
				+ "when MONTH(date) = '4' then '4-Apr '"
				+ " when MONTH(date) = '5' then '5-May '"
				+ " when MONTH(date) = '6' then '6-Jun '"
				+ "when MONTH(date) = '7' then '7-Jul '"
				+ "when MONTH(date) = '8' then '8-Aug '"
				+ "when MONTH(date) = '9' then '9-Sep '"
				+ "when MONTH(date) = '10' then '91-Oct '"
				+ " when MONTH(date) = '11' then '92-Nov '"
				+ "when MONTH(date) = '12' then '93-Dec '"
				+ "end as month,sum(MONTO) as MONTO "
				+ "from temp_report a,(select supplier,sum(MONTO) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b"
				+ " where a.supplier=b.supplier group by 1,2,3";
		try {
			ResultSet rs = stmt.executeQuery(foreignQuery);
			while (rs.next()) {

				ForeignMonthBean foreignMonthBean = new ForeignMonthBean();
				foreignMonthBean.setForeignCompany(rs.getString(1));
				foreignMonthBean.setYear(rs.getString(2));
				foreignMonthBean.setMonth(rs.getString(3));
				foreignMonthBean.setMONTO(rs.getDouble(4));

				beans.add(foreignMonthBean);
			}
			masterBean.setForeignMonthBean(beans);
		} catch (Exception ex) {
			System.out.println("Error while getForeignByMonthDollar " + ex);
		}
	}

	// ==============Foreign Company By Dollar in Quarter================
	
	public void getForeignByQuarterDollar() {
		ArrayList<ForeignQuarterBean> beans = new ArrayList<ForeignQuarterBean>();
		String foreignQuery =  "Select a.supplier,YEAR(date) as year, "
				+ " quarter(date),sum(MONTO) as MONTO"
				+ " from temp_report a,(select supplier,sum(MONTO) from temp_report"
				+ " group by 1 order by 2 desc limit 10) b"
				+ " where a.supplier=b.supplier group by 1,2,3";		try {
			ResultSet rs = stmt.executeQuery(foreignQuery);
			while (rs.next()) {

				ForeignQuarterBean foreignQuarterBean = new ForeignQuarterBean();
				foreignQuarterBean.setForeignCompany(rs.getString(1));
				foreignQuarterBean.setYear(rs.getString(2));
				foreignQuarterBean.setMonth(rs.getString(3));
				foreignQuarterBean.setMONTO(rs.getDouble(4));

				beans.add(foreignQuarterBean);
			}
			masterBean.setForeignQuarterBean(beans);
		} catch (Exception ex) {
			System.out.println("Error while getForeignByQuarterDollar " + ex);
		}
	}
	
	// ==============Foreign Compamy By Dollar in Sem================
	
	public void getForeignBySemDollar() {
		ArrayList<ForeignSemBean> beans = new ArrayList<ForeignSemBean>();
		String foreignQuery =   "Select a.supplier,YEAR(date) as year,"
				+ "case when MONTH(date) < '7' then '1'else '2'"
				+ "end as semester,sum(MONTO) as MONTO "
				+ "from temp_report a,(select supplier,sum(MONTO) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b  "
				+ "where a.supplier=b.supplier group by 1,2,3";		try {
			ResultSet rs = stmt.executeQuery(foreignQuery);
			while (rs.next()) {

				ForeignSemBean foreignSemBean = new ForeignSemBean();
				foreignSemBean.setForeignCompany(rs.getString(1));
				foreignSemBean.setYear(rs.getString(2));
				foreignSemBean.setMonth(rs.getString(3));
				foreignSemBean.setMONTO(rs.getDouble(4));

				beans.add(foreignSemBean);
			}
			masterBean.setForeignSemBean(beans);
		} catch (Exception ex) {
			System.out.println("Error while getForeignBySemDollar " + ex);
		}
	}

	// ==============Foreign Compamy By Quantity in Month================
	
	public void getForeignByMonthQuantity() {
		ArrayList<ForeignMonthBean> beans = new ArrayList<ForeignMonthBean>();
		String foreignQuery =   " Select a.supplier,YEAR(date) AS year,"
				+ "case when MONTH(date)  = '1' then '1-Jan' "
				+ "when MONTH(date) = '2' then '2-Feb'"
				+ " when MONTH(date) = '3' then '3-Mar'"
				+ "when MONTH(date) = '4' then '4-Apr '"
				+ " when MONTH(date) = '5' then '5-May '"
				+ " when MONTH(date) = '6' then '6-Jun '"
				+ "when MONTH(date) = '7' then '7-Jul '"
				+ "when MONTH(date) = '8' then '8-Aug '"
				+ "when MONTH(date) = '9' then '9-Sep '"
				+ "when MONTH(date) = '10' then '91-Oct '"
				+ " when MONTH(date) = '11' then '92-Nov '"
				+ "when MONTH(date) = '12' then '93-Dec '"
				+ "end as month,sum(CANTIDAD) as CANTIDAD "
				+ "from temp_report a,(select supplier,sum(CANTIDAD) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b"
				+ " where a.supplier=b.supplier group by 1,2,3";
		try {
			ResultSet rs = stmt.executeQuery(foreignQuery);
			while (rs.next()) {

				ForeignMonthBean foreignMonthBeanQty = new ForeignMonthBean();
				foreignMonthBeanQty.setForeignCompany(rs.getString(1));
				foreignMonthBeanQty.setYear(rs.getString(2));
				foreignMonthBeanQty.setMonth(rs.getString(3));
				foreignMonthBeanQty.setMONTO(rs.getDouble(4));

				beans.add(foreignMonthBeanQty);
			}
			masterBean.setForeignMonthBeanQty(beans);
		} catch (Exception ex) {
			System.out.println("Error while getForeignByMonthQty " + ex);
		}
	}

	// ==============Foreign Compamy By Quantity in Quarter================
	
	public void getForeignByQuarterQty() {
		ArrayList<ForeignQuarterBean> beans = new ArrayList<ForeignQuarterBean>();
		String foreignQuery =  "Select a.supplier,YEAR(date) as year, "
				+ " quarter(date),sum(CANTIDAD) as CANTIDAD"
				+ " from temp_report a,(select supplier,sum(CANTIDAD) from temp_report"
				+ " group by 1 order by 2 desc limit 10) b"
				+ " where a.supplier=b.supplier group by 1,2,3";		
		try {
			ResultSet rs = stmt.executeQuery(foreignQuery);
			while (rs.next()) {

				ForeignQuarterBean foreignQuarterBeanQty = new ForeignQuarterBean();
				foreignQuarterBeanQty.setForeignCompany(rs.getString(1));
				foreignQuarterBeanQty.setYear(rs.getString(2));
				foreignQuarterBeanQty.setMonth(rs.getString(3));
				foreignQuarterBeanQty.setMONTO(rs.getDouble(4));

				beans.add(foreignQuarterBeanQty);
			}
			masterBean.setForeignQuarterBeanQty(beans);
		} catch (Exception ex) {
			System.out.println("Error while getForeignByQuarterQty " + ex);
		}
	}
	
	// ==============Foreign Compamy By Quantity in Sem================
		
	public void getForeignBySemQty() {
		ArrayList<ForeignSemBean> beans = new ArrayList<ForeignSemBean>();
		String foreignQuery =   "Select a.supplier,YEAR(date) as year,"
				+ "case when MONTH(date) < '7' then '1'else '2'"
				+ "end as semester,sum(CANTIDAD) as CANTIDAD "
				+ "from temp_report a,(select supplier,sum(CANTIDAD) from temp_report "
				+ "group by 1 order by 2 desc limit 10) b  "
				+ "where a.supplier=b.supplier group by 1,2,3";		
		try {
			ResultSet rs = stmt.executeQuery(foreignQuery);
			while (rs.next()) {

				ForeignSemBean foreignSemBeanQty = new ForeignSemBean();
				foreignSemBeanQty.setForeignCompany(rs.getString(1));
				foreignSemBeanQty.setYear(rs.getString(2));
				foreignSemBeanQty.setMonth(rs.getString(3));
				foreignSemBeanQty.setMONTO(rs.getDouble(4));

				beans.add(foreignSemBeanQty);
			}
			masterBean.setForeignSemBeanQty(beans);
		} catch (Exception ex) {
			System.out.println("Error while getForeignBySemQty " + ex);
		}
	}

	// ==============Mexican Company by Custom_port for Dollar ================
	
public void getMexicanByCustomPortDollar() {
	ArrayList<MexicanCustomPortBean> beans = new ArrayList<MexicanCustomPortBean>();
	String MexicanCustomPortQuery =  "select z.mexican_company,z.border,sum(z.MONTO) as MONTO "
			+ "from temp_report z, "
			+ "(select a.border ,sum(a.MONTO) as MONTO from temp_report a, "
			+ "(select mexican_company ,sum(MONTO) as MONTO from temp_report  "
			+ "group by 1 order by 2 desc limit 10) b "
			+ "where a.mexican_company = b.mexican_company group by 1 order by 2 desc limit 10 ) x, "
			+ "(select mexican_company ,sum(MONTO) as MONTO from temp_report  "
			+ "group by 1 order by 2 desc limit 10) y  "
			+ "where z.mexican_company=y.mexican_company and z.border = x.border "
			+ "group by 1,2";	
	try {
		ResultSet rs = stmt.executeQuery(MexicanCustomPortQuery);
		while (rs.next()) {

			MexicanCustomPortBean mexicanCustomPortBean = new MexicanCustomPortBean();
			mexicanCustomPortBean.setMaxicanCompany(rs.getString(1));
			mexicanCustomPortBean.setBorder(rs.getString(2));
			mexicanCustomPortBean.setMONTO(rs.getDouble(3));
			

			beans.add(mexicanCustomPortBean);
		}
		masterBean.setMexicanCustomPortBean(beans);
	} catch (Exception ex) {
		System.out.println("Error while getMexicanByCustomPortDollar " + ex);
	}
}
	// ==============Mexican Company by Custom_port for Quantity ================
	
public void getMexicanByCustomPortQty() {
	ArrayList<MexicanCustomPortBean> beans = new ArrayList<MexicanCustomPortBean>();
	String MexicanCustomPortQuery =  "select z.mexican_company,z.border,sum(z.CANTIDAD) as CANTIDAD "
			+ "from temp_report z, "
			+ "(select a.border ,sum(a.CANTIDAD) as CANTIDAD from temp_report a, "
			+ "(select mexican_company ,sum(CANTIDAD) as CANTIDAD from temp_report  "
			+ "group by 1 order by 2 desc limit 10) b "
			+ "where a.mexican_company = b.mexican_company group by 1 order by 2 desc limit 10 ) x, "
			+ "(select mexican_company ,sum(CANTIDAD) as CANTIDAD from temp_report  "
			+ "group by 1 order by 2 desc limit 10) y  "
			+ "where z.mexican_company=y.mexican_company and z.border = x.border "
			+ "group by 1,2";	
	try {
		ResultSet rs = stmt.executeQuery(MexicanCustomPortQuery);
		while (rs.next()) {

			MexicanCustomPortBean mexicanCustomPortBean = new MexicanCustomPortBean();
			mexicanCustomPortBean.setMaxicanCompany(rs.getString(1));
			mexicanCustomPortBean.setBorder(rs.getString(2));
			mexicanCustomPortBean.setMONTO(rs.getDouble(3));
			

			beans.add(mexicanCustomPortBean);
		}
		masterBean.setMexicanCustomPortBeanQty(beans);
	} catch (Exception ex) {
		System.out.println("Error while getMexicanByCustomPortQty " + ex);
	}
}

	// ============== Custom_port by Supplier for Dollar ================

	public void getCustomPortDollar() {
		ArrayList<CustomPortBean> beans = new ArrayList<CustomPortBean>();
		String CustomPortQuery =  "select z.border,z.supplier,sum(z.MONTO) as MONTO  "
				+ "from temp_report z,(select a.supplier ,sum(a.MONTO) as MONTO  "
				+ "from temp_report a, (select border ,sum(MONTO) as MONTO  "
				+ "from temp_report group by 1 order by 2 desc limit 10) b  "
				+ "where a.border = b.border "
				+ "group by 1 order by 2 desc limit 10 ) x, "
				+ "(select border ,sum(MONTO) as MONTO from temp_report  "
				+ "group by 1 order by 2 desc limit 10) y where z.border=y.border "
				+ "and z.supplier = x.supplier group by 1,2;";	
	try {
		ResultSet rs = stmt.executeQuery(CustomPortQuery);
		while (rs.next()) {

			CustomPortBean customPortBean = new CustomPortBean();
			customPortBean.setBorder(rs.getString(1));
			customPortBean.setSupplier(rs.getString(2));
			customPortBean.setMONTO(rs.getDouble(3));
			

			beans.add(customPortBean);
		}
		masterBean.setCustomPortBean(beans);
	} catch (Exception ex) {
		System.out.println("Error while getCustomPortDollar " + ex);
	}
}
	
	// ============== Custom_port by Supplier for Quantity ================

	public void getCustomPortQty() {
		ArrayList<CustomPortBean> beans = new ArrayList<CustomPortBean>();
		String CustomPortQuery =  "select z.border,z.supplier,sum(z.CANTIDAD) as CANTIDAD  "
				+ "from temp_report z,(select a.supplier ,sum(a.CANTIDAD) as CANTIDAD  "
				+ "from temp_report a, (select border ,sum(CANTIDAD) as CANTIDAD  "
				+ "from temp_report group by 1 order by 2 desc limit 10) b  "
				+ "where a.border = b.border "
				+ "group by 1 order by 2 desc limit 10 ) x, "
				+ "(select border ,sum(CANTIDAD) as CANTIDAD from temp_report  "
				+ "group by 1 order by 2 desc limit 10) y where z.border=y.border "
				+ "and z.supplier = x.supplier group by 1,2;";	
	try {
		ResultSet rs = stmt.executeQuery(CustomPortQuery);
		while (rs.next()) {

			CustomPortBean customPortBeanQty = new CustomPortBean();
			customPortBeanQty.setBorder(rs.getString(1));
			customPortBeanQty.setSupplier(rs.getString(2));
			customPortBeanQty.setMONTO(rs.getDouble(3));
			

			beans.add(customPortBeanQty);
		}
		masterBean.setCustomPortBeanQty(beans);
	} catch (Exception ex) {
		System.out.println("Error while getCustomPortQty " + ex);
	}
}
	
	// ==============Mexican Company by Supplier for Dollar ================
	
	public void getMexicanSupplierDollar() {
		ArrayList<MexicanSupplierBean> beans = new ArrayList<MexicanSupplierBean>();
		String mexicanSupplierQuery =  "select z.mexican_company,z.supplier,"
				+ "sum(z.MONTO) as MONTO from temp_report z, "
				+ "(select a.supplier ,sum(a.MONTO) as MONTO from temp_report a, "
				+ "(select mexican_company ,sum(MONTO) as MONTO from temp_report "
				+ "group by 1 order by 2 desc limit 10) b  "
				+ "where a.mexican_company = b.mexican_company "
				+ "group by 1 order by 2 desc limit 10 ) x,"
				+ "(select mexican_company ,sum(MONTO) as MONTO "
				+ "from temp_report "
				+ "group by 1 order by 2 desc limit 10) y "
				+ "where z.mexican_company=y.mexican_company "
				+ "and z.supplier = x.supplier group by 1,2;";	
	try {
		ResultSet rs = stmt.executeQuery(mexicanSupplierQuery );
		while (rs.next()) {

			MexicanSupplierBean mexicanSupplierBean = new MexicanSupplierBean();
			mexicanSupplierBean.setMexicanCompany(rs.getString(1));
			mexicanSupplierBean.setSupplier(rs.getString(2));
			mexicanSupplierBean.setMONTO(rs.getDouble(3));
			

			beans.add(mexicanSupplierBean);
		}
		masterBean.setMexicanSupplierBean(beans);
	} catch (Exception ex) {
		System.out.println("Error while getMexicanSupplierDollar " + ex);
	}
}
	
	// ==============Mexican Company by Supplier for Quantity ================
	
	
	public void getMexicanSupplierQty() {
		ArrayList<MexicanSupplierBean> beans = new ArrayList<MexicanSupplierBean>();
		String mexicanSupplierQuery =  "select z.mexican_company,z.supplier,"
				+ "sum(z.CANTIDAD) as CANTIDAD from temp_report z, "
				+ "(select a.supplier ,sum(a.CANTIDAD) as CANTIDAD from temp_report a, "
				+ "(select mexican_company ,sum(CANTIDAD) as CANTIDAD from temp_report "
				+ "group by 1 order by 2 desc limit 10) b  "
				+ "where a.mexican_company = b.mexican_company "
				+ "group by 1 order by 2 desc limit 10 ) x,"
				+ "(select mexican_company ,sum(CANTIDAD) as CANTIDAD "
				+ "from temp_report "
				+ "group by 1 order by 2 desc limit 10) y "
				+ "where z.mexican_company=y.mexican_company "
				+ "and z.supplier = x.supplier group by 1,2;";	
	try {
		ResultSet rs = stmt.executeQuery(mexicanSupplierQuery );
		while (rs.next()) {

			MexicanSupplierBean mexicanSupplierBean = new MexicanSupplierBean();
			mexicanSupplierBean.setMexicanCompany(rs.getString(1));
			mexicanSupplierBean.setSupplier(rs.getString(2));
			mexicanSupplierBean.setMONTO(rs.getDouble(3));
			

			beans.add(mexicanSupplierBean);
		}
		masterBean.setMexicanSupplierBeanQty(beans);
	} catch (Exception ex) {
		System.out.println("Error while getMexicanSupplierQty " + ex);
	}
}
	
	
	
	public ArrayList<SubReportMap> getAssignedSubReports(String reportId) {
		//System.out.println("assignedSubReport()- "+reportId);
		ArrayList<SubReportMap> subReportBeans = new ArrayList<SubReportMap>();
		SubReportMap subReportBean;
		try {
			ResultSet rs = stmt
					.executeQuery("select * from report_subreport_mapping "
							+ " where report_id = " + reportId);
			while (rs.next()) {
				subReportBean = new SubReportMap();
				subReportBean.setReportId(rs.getInt(1));
				subReportBean.setSubReportId(rs.getInt(2));
				subReportBean.setTimeBy(rs.getString(3));
				subReportBean.setValueBy(rs.getString(4));
				subReportBeans.add(subReportBean);
			}
		} catch (Exception ex) {
			System.out.println("Error in getAssignedSubReports " + ex);
		}
		return subReportBeans;
	}

	public void populateSubReports(String reportId) {
		ArrayList<SubReportMap> objSubMap;
		objSubMap = getAssignedSubReports(reportId);
		//System.out.println("SubReportMap  " + objSubMap);
		/*
		for (int i = 0; i < objSubMap.size(); i++) {
			if (objSubMap.get(i).getSubReportId() == 1) {
				if (objSubMap.get(i).getTimeBy().equals("month")
						&& objSubMap.get(i).getValueBy().equals("dollar"))
					getHSByMonthDollar();
				else if (objSubMap.get(i).getTimeBy().equals("quarter")
						&& objSubMap.get(i).getValueBy().equals("dollar"))
					getHSByQuarterDollar();
				else if (objSubMap.get(i).getTimeBy().equals("semester")
						&& objSubMap.get(i).getValueBy().equals("dollar"))
					getHSBySemDollar();
				else if (objSubMap.get(i).getTimeBy().equals("month")
						&& objSubMap.get(i).getValueBy().equals("weight"))
					getHSByMonthQty();
				else if (objSubMap.get(i).getTimeBy().equals("quarter")
						&& objSubMap.get(i).getValueBy().equals("weight"))
					getHSByQuarterQty();
				else if (objSubMap.get(i).getTimeBy().equals("semester")
						&& objSubMap.get(i).getValueBy().equals("weight"))
					getHSBySemQty();
			} else if (objSubMap.get(i).getSubReportId() == 2) {
				if (objSubMap.get(i).getTimeBy().equals("month")
						&& objSubMap.get(i).getValueBy().equals("dollar"))
					getMaxicanByMonthDollar();
				else if (objSubMap.get(i).getTimeBy().equals("quarter")
						&& objSubMap.get(i).getValueBy().equals("dollar"))
					getMaxicanByQuarterDollar();
				else if (objSubMap.get(i).getTimeBy().equals("semester")
						&& objSubMap.get(i).getValueBy().equals("dollar"))
					getMaxicanBySemDollar();
				else if (objSubMap.get(i).getTimeBy().equals("month")
						&& objSubMap.get(i).getValueBy().equals("weight"))
					getMaxicanByMonthQty();
				else if (objSubMap.get(i).getTimeBy().equals("quarter")
						&& objSubMap.get(i).getValueBy().equals("weight"))
					getMaxicanByQuarterQty();
				else if (objSubMap.get(i).getTimeBy().equals("semester")
						&& objSubMap.get(i).getValueBy().equals("weight"))
					getMaxicanBySemQty();
			} else if (objSubMap.get(i).getSubReportId() == 3) {
				if (objSubMap.get(i).getTimeBy().equals("month")
						&& objSubMap.get(i).getValueBy().equals("dollar"))
					getCustomsByMonthDollar();
				else if (objSubMap.get(i).getTimeBy().equals("quarter")
						&& objSubMap.get(i).getValueBy().equals("dollar"))
					getCustomsByQuarterDollar();
				else if (objSubMap.get(i).getTimeBy().equals("semester")
						&& objSubMap.get(i).getValueBy().equals("dollar"))
					getCustomsBySemDollar();
				else if (objSubMap.get(i).getTimeBy().equals("month")
						&& objSubMap.get(i).getValueBy().equals("weight"))
					getCustomsByMonthQty();
				else if (objSubMap.get(i).getTimeBy().equals("quarter")
						&& objSubMap.get(i).getValueBy().equals("weight"))
					getCustomsByQuarterQty();
				else if (objSubMap.get(i).getTimeBy().equals("semester")
						&& objSubMap.get(i).getValueBy().equals("weight"))
					getCustomsBySemQty();
			} else if (objSubMap.get(i).getSubReportId() == 4) {
				if (objSubMap.get(i).getTimeBy().equals("month")
						&& objSubMap.get(i).getValueBy().equals("dollar"))
					getCountryByMonthDollar();
				else if (objSubMap.get(i).getTimeBy().equals("quarter")
						&& objSubMap.get(i).getValueBy().equals("dollar"))
					getCountryByQuarterDollar();
				else if (objSubMap.get(i).getTimeBy().equals("semester")
						&& objSubMap.get(i).getValueBy().equals("dollar"))
					getCountryBySemDollar();
				else if (objSubMap.get(i).getTimeBy().equals("month")
						&& objSubMap.get(i).getValueBy().equals("weight"))
					getCountryByMonthQty();
				else if (objSubMap.get(i).getTimeBy().equals("quarter")
						&& objSubMap.get(i).getValueBy().equals("weight"))
					getCountryByQuarterQty();
				else if (objSubMap.get(i).getTimeBy().equals("semester")
						&& objSubMap.get(i).getValueBy().equals("weight"))
					getCountryBySemQty();
			}

		}*/
		getHSByMonthDollar();
		getHSByQuarterDollar();
		getHSBySemDollar();
		getHSByMonthQty();
		getHSByQuarterQty();
		getHSBySemQty();
		getMaxicanByMonthDollar();
		getMaxicanByQuarterDollar();
		getMaxicanBySemDollar();
		getMaxicanByMonthQty();
		getMaxicanByQuarterQty();
		getMaxicanBySemQty();
		getCustomsByMonthDollar();
		getCustomsByQuarterDollar();
		getCustomsBySemDollar();
		getCustomsByMonthQty();
		getCustomsByQuarterQty();
		getCustomsBySemQty();
		getCountryByMonthDollar();
		getCountryByQuarterDollar();
		getCountryBySemDollar();
		getCountryByMonthQty();
		getCountryByQuarterQty();
		getCountryBySemQty();
		
		getForeignByMonthDollar();
		getForeignByQuarterDollar();
		getForeignBySemDollar();
		getForeignByMonthQuantity();
		getForeignByQuarterQty();
		getForeignBySemQty();
		getMexicanByCustomPortDollar();
		getMexicanByCustomPortQty();
		getCustomPortDollar();
		getCustomPortQty();
		getMexicanSupplierDollar();
		getMexicanSupplierQty();
		
	}

	public void getReportData(String reportId) {

		//MasterBean masterBean = new MasterBean();

		populateSubReports(reportId);
		
		FinalDao objDao = new FinalDao(prop);
		String fileName = objDao.getReportName(reportId);
		fileName = fileName.replace(" ", "");
		String storagePath = prop.getProperty("reportStoragePath");
		
		String subReportPath = prop.getProperty("ReportTemplatePath");
		System.out.println("subreport path " + subReportPath);

		String sourceFileName = subReportPath + "/Master_Report.jasper";

		ArrayList<MasterBean> masterBeans = new ArrayList<MasterBean>();
		masterBeans.add(masterBean);

		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(
				masterBeans);

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("subReportPath", subReportPath);
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					sourceFileName, parameters, beanColDataSource);

			JasperExportManager.exportReportToPdfFile(jasperPrint,storagePath + "/"+fileName+".pdf");

			System.out.println("report is  generated ..");
		} catch (JRException e) {
			e.printStackTrace();
		}

	}

	public void closeAll() {
		try {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			System.out.println("connection close in report");
		} catch (Exception e) {

		}

	}

}
