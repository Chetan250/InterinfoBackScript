package actionNational;

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
import beanNational.ClientMonthBeanNc;
import beanNational.ClientQuarterBeanNc;
import beanNational.ClientSemBeanNc;
import beanNational.ClientYearBean;
import beanNational.MasterBeanNc;
import beanNational.StateDestMonthBeanNc;
import beanNational.StateDestQuarterBeanNc;
import beanNational.StateDestSemBeanNc;
import beanNational.StateDestYearBeanNc;
import beanNational.SubReportMapNc;
import beanNational.SupplierMonthBeanNc;
import beanNational.SupplierQuarterBeanNc;
import beanNational.SupplierSemBeanNc;
import beanNational.SupplierYearBeanNc;
import connection.MyConnection;
import daoNational.FinalDaoNc;


public class ReportDataNc {

		Connection conn;
		Statement stmt;
		Properties prop;
		MasterBeanNc masterBeanNc=new MasterBeanNc();

		public ReportDataNc(Properties props) {
			try {
				prop = props;
				conn = new MyConnection().getConnection(prop);
				stmt = conn.createStatement();
				
			} catch (Exception e) {
				System.out.println("Error while getting connection in National ReportDataNc");
				e.printStackTrace();

			}
		}
		
		
		// =========================Client By Dollar in Month================
		public void getClientMonthDollar() {
			ArrayList<ClientMonthBeanNc> beans = new ArrayList<ClientMonthBeanNc>();
			String clientQuery = " Select a.razon,ejercicio AS year,"
					+ "case when periodo  = '1' then '1-Jan' "
					+ "when periodo = '2' then '2-Feb'"
					+ " when periodo = '3' then '3-Mar'"
					+ "when periodo = '4' then '4-Apr '"
					+ " when periodo = '5' then '5-May '"
					+ " when periodo = '6' then '6-Jun '"
					+ "when periodo = '7' then '7-Jul '"
					+ "when periodo = '8' then '8-Aug '"
					+ "when periodo = '9' then '9-Sep '"
					+ "when periodo = '10' then '91-Oct '"
					+ " when periodo = '11' then '92-Nov '"
					+ "when periodo = '12' then '93-Dec '"
					+ "end as month, a.valor_final "
					+ "from temp_report_nc a,(select razon,valor_final from temp_report_nc "
					+ "group by 1 order by 2 desc limit 20) b"
					+ " where a.razon=b.razon group by 1,2,3";

			try {
				
				ResultSet rs = stmt.executeQuery(clientQuery);
				while (rs.next()) {
					
					ClientMonthBeanNc clientMonthBeanNc = new ClientMonthBeanNc();
					clientMonthBeanNc.setClient(rs.getString(1));
					clientMonthBeanNc.setYear(rs.getString(2));
					clientMonthBeanNc.setMonth(rs.getString(3));
					clientMonthBeanNc.setValue(rs.getDouble(4));
					
					//System.out.println(rs.getString(1));

					beans.add(clientMonthBeanNc);
					
					
				}
				masterBeanNc.setClientMonthBean(beans);
		
			} catch (Exception ex) {
				System.out.println("Error while getClientMonthDollar " + ex);
			}
		}
		
		// =========================Client By Dollar in Quarter================
		
		public void getClientByQuarterDollar() {
			ArrayList<ClientQuarterBeanNc> beans = new ArrayList<ClientQuarterBeanNc>();
			String clientQuery = "Select a.razon,ejercicio as year, "
					+ " case when periodo <= 3 then 1 "
					+ " when periodo between 4 and 6 then 2 "
					+ " when periodo between 7 and 9 then 3 "
					+ " when periodo between 10 and 12 then 4 end as quarter,a.valor_final as valor15"
					+ " from temp_report_nc a,(select razon,valor_final from temp_report_nc"
					+ " group by 1 order by 2 desc limit 20) b"
					+ " where a.razon=b.razon group by 1,2,3";

			try {
				ResultSet rs = stmt.executeQuery(clientQuery);
				while (rs.next()) {
					ClientQuarterBeanNc clientQuarterBeanNc = new ClientQuarterBeanNc();
					clientQuarterBeanNc.setClient(rs.getString(1));
					clientQuarterBeanNc.setYear(rs.getString(2));
					clientQuarterBeanNc.setMonth(rs.getString(3));
					clientQuarterBeanNc.setValue(rs.getDouble(4));

					beans.add(clientQuarterBeanNc);
				}
				masterBeanNc.setClientQuarterBean(beans);
			} catch (Exception ex) {
				System.out.println("Error while getClientByQuarterDollar " + ex);
			}
		}
		
		// =========================Client By Dollar in Sem================
		
		public void getClientBySemDollar() {
			ArrayList<ClientSemBeanNc> beans = new ArrayList<ClientSemBeanNc>();
			String clientQuery = "Select a.razon,ejercicio as year,"
					+ "case when periodo < '7' then '1'else '2'"
					+ "end as semester,a.valor_final as valor15 "
					+ "from temp_report_nc a,(select razon,valor_final from temp_report_nc "
					+ "group by 1 order by 2 desc limit 20) b  "
					+ "where a.razon=b.razon group by 1,2,3";

			try {
				ResultSet rs = stmt.executeQuery(clientQuery);
				while (rs.next()) {
					ClientSemBeanNc clientSemBeanNc= new ClientSemBeanNc();
					clientSemBeanNc.setClient(rs.getString(1));
					clientSemBeanNc.setYear(rs.getString(2));
					clientSemBeanNc.setMonth(rs.getString(3));
					clientSemBeanNc.setValue(rs.getDouble(4));

					beans.add(clientSemBeanNc);
				}
				
				masterBeanNc.setClientSemBeanNc(beans);
			} catch (Exception ex) {
				System.out.println("Error while getClientBySemDollar " + ex);
			}
		}
		
		// =========================Client By Dollar in Year ================

		public void getClientByYearDollar() {
			ArrayList<ClientYearBean> beans = new ArrayList<ClientYearBean>();
			String clientQuery = " Select a.razon,ejercicio AS year,"
					+ "a.valor_final as valor15 "
					+ "from temp_report_nc a,(select razon,valor_final from temp_report_nc "
					+ "group by 1 order by 2 desc limit 20) b"
					+ " where a.razon=b.razon group by 1,2";

			try {
				ResultSet rs = stmt.executeQuery(clientQuery);
				while (rs.next()) {
					
					ClientYearBean clientYearBean = new ClientYearBean();
					clientYearBean .setClient(rs.getString(1));
					clientYearBean .setYear(rs.getString(2));
					clientYearBean .setValue(rs.getDouble(3));

					beans.add(clientYearBean );
				
					
				}
				masterBeanNc.setClientYearBean(beans);
		
			} catch (Exception ex) {
				System.out.println("Error while getClientByYearDollar " + ex);
			}
		}
		
		// =========================Supplier By Dollar in Month================
		
		public void getSupplierMonthDollar() {
			ArrayList<SupplierMonthBeanNc> beans = new ArrayList<SupplierMonthBeanNc>();
			String supplierQuery = " Select a.sup_razon,ejercicio AS year,"
					+ "case when periodo  = '1' then '1-Jan' "
					+ "when periodo = '2' then '2-Feb'"
					+ " when periodo = '3' then '3-Mar'"
					+ "when periodo = '4' then '4-Apr '"
					+ " when periodo = '5' then '5-May '"
					+ " when periodo = '6' then '6-Jun '"
					+ "when periodo = '7' then '7-Jul '"
					+ "when periodo = '8' then '8-Aug '"
					+ "when periodo = '9' then '9-Sep '"
					+ "when periodo = '10' then '91-Oct '"
					+ " when periodo = '11' then '92-Nov '"
					+ "when periodo = '12' then '93-Dec '"
					+ "end as month,a.valor_final as valor15 "
					+ "from temp_report_nc a,(select sup_razon,valor_final from temp_report_nc "
					+ "group by 1 order by 2 desc limit 20) b"
					+ " where a.sup_razon != '' and  a.sup_razon=b.sup_razon group by 1,2,3";
			//System.out.println("supplierQuery: "+ supplierQuery);
			try {
				//System.out.println("here" );
				ResultSet rs = stmt.executeQuery(supplierQuery);
				while (rs.next()) {
					
					SupplierMonthBeanNc supplierMonthBeanNc = new SupplierMonthBeanNc();
					supplierMonthBeanNc.setSupplierName(rs.getString(1));
					supplierMonthBeanNc.setYear(rs.getString(2));
					supplierMonthBeanNc.setMonth(rs.getString(3));
					supplierMonthBeanNc.setValue(rs.getDouble(4));

					beans.add(supplierMonthBeanNc);
					
					
				}
				//System.out.println("beans size"+ beans.size());
				masterBeanNc.setSupplierMonthBean(beans);
		
			} catch (Exception ex) {
				System.out.println("Error while getSupplierMonthDollar " + ex);
			}
		}
		
		// =========================Supplier By Dollar in Quarter================
		
				public void getSupplierByQuarterDollar() {
					ArrayList<SupplierQuarterBeanNc> beans = new ArrayList<SupplierQuarterBeanNc>();
					String supplierQuery = "Select a.sup_razon,ejercicio as year, "
							+ " case when periodo <= 3 then 1 "
							+ " when periodo between 4 and 6 then 2 "
							+ " when periodo between 7 and 9 then 3 "
							+ " when periodo between 10 and 12 then 4 end as quarter,a.valor_final as valor15"
							+ " from temp_report_nc a,(select sup_razon,valor_final from temp_report_nc"
							+ " group by 1 order by 2 desc limit 20) b"
							+ " where a.sup_razon != '' and  a.sup_razon = b.sup_razon group by 1,2,3";

					try {
						ResultSet rs = stmt.executeQuery(supplierQuery);
						while (rs.next()) {
							SupplierQuarterBeanNc supplierQuarterBeanNc = new SupplierQuarterBeanNc();
							supplierQuarterBeanNc.setSupplierName(rs.getString(1));
							supplierQuarterBeanNc.setYear(rs.getString(2));
							supplierQuarterBeanNc.setMonth(rs.getString(3));
							supplierQuarterBeanNc.setValue(rs.getDouble(4));

							beans.add(supplierQuarterBeanNc);
						}
						masterBeanNc.setSupplierQuarterBean(beans);
					} catch (Exception ex) {
						System.out.println("Error while getSupplierByQuarterDollar " + ex);
					}
				}
				
				// =========================Supplier By Dollar in Sem================
				
				public void getSupplierBySemDollar() {
					ArrayList<SupplierSemBeanNc> beans = new ArrayList<SupplierSemBeanNc>();
					String supplierQuery = "Select a.sup_razon,ejercicio as year,"
							+ "case when periodo < '7' then '1'else '2'"
							+ "end as semester,a.valor_final as valor15 "
							+ "from temp_report_nc a,(select sup_razon,valor_final from temp_report_nc "
							+ "group by 1 order by 2 desc limit 20) b  "
							+ "where a.sup_razon != '' and  a.sup_razon = b.sup_razon group by 1,2,3";

					try {
						ResultSet rs = stmt.executeQuery(supplierQuery);
						while (rs.next()) {
							SupplierSemBeanNc supplierSemBeanNc= new SupplierSemBeanNc();
							supplierSemBeanNc.setSupplierName(rs.getString(1));
							supplierSemBeanNc.setYear(rs.getString(2));
							supplierSemBeanNc.setMonth(rs.getString(3));
							supplierSemBeanNc.setValue(rs.getDouble(4));

							beans.add(supplierSemBeanNc);
						}
						
						masterBeanNc.setSupplierSemBean(beans);
					} catch (Exception ex) {
						System.out.println("Error while getSupplierBySemDollar " + ex);
					}
				}
				
				// =========================Supplier By Dollar in Year ================

				public void getSupplierByYearDollar() {
					ArrayList<SupplierYearBeanNc> beans = new ArrayList<SupplierYearBeanNc>();
					String supplierQuery = " Select a.sup_razon,ejercicio AS year,"
							+ "a.valor_final as valor15 "
							+ "from temp_report_nc a,(select sup_razon,valor_final from temp_report_nc "
							+ "group by 1 order by 2 desc limit 20) b"
							+ " where a.sup_razon != '' and  a.sup_razon=b.sup_razon group by 1,2";

					try {
						ResultSet rs = stmt.executeQuery(supplierQuery);
						while (rs.next()) {
							
							SupplierYearBeanNc supplierYearBeanNc = new SupplierYearBeanNc();
							supplierYearBeanNc .setSupplierName(rs.getString(1));
							supplierYearBeanNc .setYear(rs.getString(2));
							supplierYearBeanNc .setValue(rs.getDouble(3));

							beans.add(supplierYearBeanNc );
							
							
						}
						masterBeanNc.setSupplieryearBeanNc(beans);
				
					} catch (Exception ex) {
						System.out.println("Error while getSupplierByYearDollar " + ex);
					}
				}
				
				// =========================State By Dollar in Month================
				
				public void getStateMonthDollar() {
					ArrayList<StateDestMonthBeanNc> beans = new ArrayList<StateDestMonthBeanNc>();
					String stateDestQuery = " Select a.estado,ejercicio AS year,"
							+ "case when periodo  = '1' then '1-Jan' "
							+ "when periodo = '2' then '2-Feb'"
							+ " when periodo = '3' then '3-Mar'"
							+ "when periodo = '4' then '4-Apr '"
							+ " when periodo = '5' then '5-May '"
							+ " when periodo = '6' then '6-Jun '"
							+ "when periodo = '7' then '7-Jul '"
							+ "when periodo = '8' then '8-Aug '"
							+ "when periodo = '9' then '9-Sep '"
							+ "when periodo = '10' then '91-Oct '"
							+ " when periodo = '11' then '92-Nov '"
							+ "when periodo = '12' then '93-Dec '"
							+ "end as month,a.valor_final as valor15 "
							+ "from temp_report_nc a,(select estado,valor_final from temp_report_nc "
							+ "group by 1 order by 2 desc limit 20) b"
							+ " where a.estado != '' and a.estado=b.estado group by 1,2,3";

					try {
						ResultSet rs = stmt.executeQuery(stateDestQuery);
						while (rs.next()) {
							
						    StateDestMonthBeanNc stateDestMonthBeanNc = new StateDestMonthBeanNc();
						    stateDestMonthBeanNc.setState(rs.getString(1));
						    stateDestMonthBeanNc.setYear(rs.getString(2));
						    stateDestMonthBeanNc.setMonth(rs.getString(3));
						    stateDestMonthBeanNc.setValue(rs.getDouble(4));

							beans.add(stateDestMonthBeanNc);
							
							
						}
						masterBeanNc.setStateDestMonthBean(beans);
				
					} catch (Exception ex) {
						System.out.println("Error while getStateMonthDollar " + ex);
					}
				}
				
				// =========================State By Dollar in Quarter================
				
				public void getStateByQuarterDollar() {
					ArrayList<StateDestQuarterBeanNc> beans = new ArrayList<StateDestQuarterBeanNc>();
					String stateDestQuery = "Select a.estado,ejercicio as year, "
							+ " case when periodo <= 3 then 1 "
							+ " when periodo between 4 and 6 then 2 "
							+ " when periodo between 7 and 9 then 3 "
							+ " when periodo between 10 and 12 then 4 end as quarter,a.valor_final as valor15"
							+ " from temp_report_nc a,(select estado,valor_final from temp_report_nc"
							+ " group by 1 order by 2 desc limit 20) b"
							+ " where a.estado != '' and a.estado=b.estado group by 1,2,3";

					try {
						ResultSet rs = stmt.executeQuery(stateDestQuery);
						while (rs.next()) {
							StateDestQuarterBeanNc stateDestQuarterBeanNc = new StateDestQuarterBeanNc();
							stateDestQuarterBeanNc.setState(rs.getString(1));
							stateDestQuarterBeanNc.setYear(rs.getString(2));
							stateDestQuarterBeanNc.setMonth(rs.getString(3));
							stateDestQuarterBeanNc.setValue(rs.getDouble(4));

							beans.add(stateDestQuarterBeanNc);
						}
						masterBeanNc.setStateDestQuarterBean(beans);
					} catch (Exception ex) {
						System.out.println("Error while getStateByQuarterDollar " + ex);
					}
				}
				
// =========================State By Dollar in Sem================
				
				public void getStateDestBySemDollar() {
					ArrayList<StateDestSemBeanNc> beans = new ArrayList<StateDestSemBeanNc>();
					String stateDestQuery = "Select a.estado,ejercicio as year,"
							+ "case when periodo < '7' then '1'else '2'"
							+ "end as semester,a.valor_final as valor15 "
							+ "from temp_report_nc a,(select estado,valor_final from temp_report_nc "
							+ "group by 1 order by 2 desc limit 20) b  "
							+ "where a.estado != '' and a.estado=b.estado group by 1,2,3";

					try {
						ResultSet rs = stmt.executeQuery(stateDestQuery);
						while (rs.next()) {
							StateDestSemBeanNc stateDestSemBeanNc= new StateDestSemBeanNc();
							stateDestSemBeanNc.setState(rs.getString(1));
							stateDestSemBeanNc.setYear(rs.getString(2));
							stateDestSemBeanNc.setMonth(rs.getString(3));
							stateDestSemBeanNc.setValue(rs.getDouble(4));

							beans.add(stateDestSemBeanNc);
						}
						
						masterBeanNc.setStateDestSemBean(beans);
					} catch (Exception ex) {
						System.out.println("Error while getStateDestBySemDollar " + ex);
					}
				}

				// =========================State By Dollar in Year ================

				public void getSTateDestByYearDollar() {
					ArrayList<StateDestYearBeanNc> beans = new ArrayList<StateDestYearBeanNc>();
					String stateDestQuery = " Select a.estado,ejercicio AS year,"
							+ "a.valor_final as valor15 "
							+ "from temp_report_nc a,(select estado,valor_final from temp_report_nc "
							+ "group by 1 order by 2 desc limit 20) b"
							+ " where a.estado != '' and a.estado=b.estado group by 1,2";

					try {
						ResultSet rs = stmt.executeQuery(stateDestQuery);
						while (rs.next()) {
							
							StateDestYearBeanNc stateDestYearBeanNc = new StateDestYearBeanNc();
							stateDestYearBeanNc .setState(rs.getString(1));
							stateDestYearBeanNc .setYear(rs.getString(2));
							stateDestYearBeanNc .setValue(rs.getDouble(3));

							beans.add(stateDestYearBeanNc );
							
							
						}
						masterBeanNc.setStateDestyearBean(beans);
				
					} catch (Exception ex) {
						System.out.println("Error while getSTateDestByYearDollar " + ex);
					}
				}
				
				
		public ArrayList<SubReportMapNc> getAssignedSubReportsNc(String reportId) {
			ArrayList<SubReportMapNc> subReportBeans = new ArrayList<SubReportMapNc>();
			SubReportMapNc subReportBean;
			try {
				ResultSet rs = stmt
						.executeQuery("select * from report_subreport_mapping_nc "
								+ " where report_id = " + reportId);
				while (rs.next()) {
					subReportBean = new SubReportMapNc();
					subReportBean.setReportId(rs.getInt(1));
					subReportBean.setSubReportId(rs.getInt(2));
					subReportBean.setTimeBy(rs.getString(3));
					
					subReportBeans.add(subReportBean);
				}
			} catch (Exception ex) {
				System.out.println("Error in getAssignedSubReportsNc() " + ex);
			}
			return subReportBeans;
		}

		public void populateSubReportsNc(String reportId) {
			ArrayList<SubReportMapNc> objSubMap;
			System.out.println("Report ID: "+ reportId);
			objSubMap = getAssignedSubReportsNc(reportId);
		
			for (int i = 0; i < objSubMap.size(); i++) {
				if (objSubMap.get(i).getSubReportId() == 1) {
					
					if (objSubMap.get(i).getTimeBy().equals("month"))
						getClientMonthDollar();
					else if (objSubMap.get(i).getTimeBy().equals("quarter"))
						getClientByQuarterDollar();
					else if (objSubMap.get(i).getTimeBy().equals("semester"))
						getClientBySemDollar();
					else if (objSubMap.get(i).getTimeBy().equals("year"))
						getClientByYearDollar();
				}else if (objSubMap.get(i).getSubReportId() == 2) {
						if (objSubMap.get(i).getTimeBy().equals("month"))
						getSupplierMonthDollar();
						else if (objSubMap.get(i).getTimeBy().equals("quarter"))
						getSupplierByQuarterDollar();
						else if (objSubMap.get(i).getTimeBy().equals("semester"))
						getSupplierBySemDollar();
						else if (objSubMap.get(i).getTimeBy().equals("year"))
						getSupplierByYearDollar();
				}else if (objSubMap.get(i).getSubReportId() == 3) {
					if (objSubMap.get(i).getTimeBy().equals("month"))
						getStateMonthDollar();
					else if (objSubMap.get(i).getTimeBy().equals("quarter"))
						getStateByQuarterDollar();
					else if (objSubMap.get(i).getTimeBy().equals("semester"))
						getStateDestBySemDollar();
					else if (objSubMap.get(i).getTimeBy().equals("year"))
						getSTateDestByYearDollar();
				}
					
				

			}
		
		
		
/*
		getClientMonthDollar();
		getClientByQuarterDollar();
		getClientBySemDollar();
		getClientByYearDollar();
		
		getSupplierMonthDollar();
		getSupplierByQuarterDollar();
		getSupplierBySemDollar();
		getSupplierByYearDollar();
		getStateMonthDollar();
		getStateByQuarterDollar();
		getStateDestBySemDollar();
		getSTateDestByYearDollar();*/
		
		}  
		public void getReportDataNc(String reportId) {

			populateSubReportsNc(reportId);
			
			FinalDaoNc objDaoNc = new FinalDaoNc(prop);
			String fileName = objDaoNc.getReportName(reportId);
			fileName = fileName.replace(" ", "");
			String storagePath = prop.getProperty("reportStoragePath");
			
			String subReportPath = prop.getProperty("ReportTemplatePathNc");
			System.out.println("subreport path " + subReportPath);

			String sourceFileName = subReportPath + "/Master_ReportNc.jasper";

			ArrayList<MasterBeanNc> masterBeansNc = new ArrayList<MasterBeanNc>();
			masterBeansNc.add(masterBeanNc);
			//System.out.println("here " +masterBeansNc.size());
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(
					masterBeansNc);

			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("subReportPath", subReportPath+"/");
			try {
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						sourceFileName, parameters, beanColDataSource);

				JasperExportManager.exportReportToPdfFile(jasperPrint,storagePath + "/"+fileName+".pdf");

				System.out.println("National report is  generated ..");
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
				System.out.println("connection close in national report()");
			} catch (Exception e) {

			}

		}

	}



