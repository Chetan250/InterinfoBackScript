package action;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bean.CustomReportBean2;
import connection.MyConnection;

public class InputToOther {
	Connection conn;
	Statement stmt;
	Properties prop;
	String reportName;
	ArrayList<String> hscodeList = null;
	ArrayList<String> taxNumberList = null;

	public InputToOther(Properties prop, String reportName) {
		
		try {
			this.prop = prop;
			conn = new MyConnection().getConnection(prop);
			this.reportName = reportName.replace(" ", "");
			stmt = conn.createStatement();

		} catch (Exception e) {
			System.out.println("Error while getting connection");
			e.printStackTrace();

		}

	}

	public CustomReportBean2 createTempReport2(String reportId) {
		CustomReportBean2 objBean = null;
		ResultSet rs = null;
		try {
			String query = "SELECT main_report_id,is_hscode,is_taxno,import_export, "
					+ "from_date, to_date , "
					+ " value_range_by_doller_from, value_range_by_quantity_to ,"
					+ " value_range_by_doller_to ,value_range_by_quantity_from ,"
					+ " supplier, border, motion_key,regimes, transportation, "
					+ " origin_destination_country_code, buyer_seller_country_code, "
					+ " bounded_warehouse,identifiers,regulation_restriction,"
					+ " container_type,billing,merchandise_destination,bar_code, "
					+ " starategic_fiscal_area,report_by  "
					+ " FROM report_report_mapping "
					+ " WHERE main_report_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, reportId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				objBean = new CustomReportBean2();
				objBean.setIs_Taxno(rs.getString("main_report_id"));
				objBean.setMain_Report_Id(rs.getString("is_hscode"));
				objBean.setIs_Hscode(rs.getString("is_taxno"));
				objBean.setImport_export(rs.getString("import_export"));
				objBean.setFrom_Date(rs.getString("from_date"));
				objBean.setTo_Date(rs.getString("to_date"));
				objBean.setValue_Range_By_Dollar_From(rs
						.getString("value_range_by_doller_from"));
				objBean.setValue_Range_By_Quantity_To(rs
						.getString("value_range_by_quantity_to"));
				objBean.setValue_Range_By_Dollar_To(rs
						.getString("value_range_by_doller_to"));
				objBean.setValue_Range_By_Quantity_From(rs
						.getString("value_range_by_quantity_from"));
				objBean.setSupplier(rs.getString("supplier"));
				objBean.setBorder(rs.getString("border"));
				objBean.setMotion_Key(rs.getString("motion_key"));
				objBean.setRegimes(rs.getString("regimes"));
				objBean.setTransportation(rs.getString("transportation"));
				objBean.setOrigin_Destination_Country_Code(rs
						.getString("origin_destination_country_code"));
				objBean.setBuyer_seller_Country_Code(rs
						.getString("buyer_seller_country_code"));
				objBean.setBounded_Warehouse(rs.getString("bounded_warehouse"));
				objBean.setIdentifiers(rs.getString("identifiers"));
				objBean.setRegulation_Restriction(rs
						.getString("regulation_restriction"));
				objBean.setContainer_type(rs.getString("container_type"));
				objBean.setBilling(rs.getString("billing"));
				objBean.setMerchandise_Destination(rs
						.getString("merchandise_destination"));
				objBean.setBar_Code(rs.getString("bar_code"));
				objBean.setStarategic_Fiscal_Area(rs
						.getString("starategic_fiscal_area"));
				objBean.setReport_By(rs.getString("report_by"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
		return objBean;
	}

	public void getHsCoseData() {
		ResultSet rs = null;
		hscodeList = new ArrayList<String>();
		try {
			String hsquery = "select DISTINCT FRACCION from temp_report";
			PreparedStatement pstmt = conn.prepareStatement(hsquery);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				hscodeList.add(rs.getString("FRACCION"));
			}

			for (int i = 0; i < hscodeList.size(); i++) {
				System.out.println("HSCODE: " + hscodeList.get(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public void closeAll() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getTaxNoData() {
		taxNumberList = new ArrayList<String>();
		ResultSet rs = null;
		try {
			String taxNoquery = "select DISTINCT tm.taxno_id from temp_report t JOIN taxno_master_inc tm on t.taxnumber = tm.tax_no";
			PreparedStatement pstmt = conn.prepareStatement(taxNoquery);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				taxNumberList.add(rs.getString("taxno_id"));
			}
			/*for (int i = 0; i < taxNumberList.size(); i++) {
				System.out.println("HSCODE: " + taxNumberList.get(i));
				}*/

		} catch (SQLException e) {
					e.printStackTrace();
		} finally {
			
		}

	}

	public void getAllData(CustomReportBean2 bean) {
		String subQuery = "";
		String fromClause = "";
		String where = " WHERE a.AGENTE = b.AGENTE  AND a.ADUANA = b.ADUANA "
				+ " AND a.SECCION = b.SECCION  AND a.PEDIMENTO = b.PEDIMENTO "
				+ " AND c.border = a.ADUANA AND c.section = a.SECCION "
				+ " AND a.TIPO = d.tipo AND a.transporte = e.transporte "
				+ " AND a.ORIGEN = f.cc_key  AND a.RUTA = g.cc_key "
				+ " AND a.DEST_ORIG = i.id  AND j.hscode = a.FRACCION AND k.rfc = a.RFC";

		String deletequery = "truncate table temp_report2";

		subQuery = "SELECT CASE  WHEN a.IE = '1' THEN 'Import'  WHEN a.IE = '2'"
				+ " THEN 'Export'  END as IE, a.FRACCION,a.CANTIDAD, a.MONTO,taxnumber, k.razon, c.description as"
				+ " border, concat('20',a.ANIO,'-',a.mes,'-',a.dia ) as date,f.country_name as ccOrigDest,"
				+ " g.country_name as ccBuyerSeller, d.regimen,e.medio_transporte_esp,"
				+ " h.description as regulation, i.description as merDest,"
				+ " j.hscode_description, k.razon as mexican_company ";

		if (bean.getReport_By() != null && bean.getReport_By().equals("hscode")) {
			fromClause = "from DATA_RAW a use index(fraccion_idx) "
					+ "LEFT OUTER JOIN regulations_restrictions_master h "
					+ " ON (a.TPRIM_PERM = h.id OR a.TSEGU_PERM =h.id "
					+ "OR a.TTERC_PERM = h.id OR a.TCUA_PERM = h.id "
					+ "OR a.TQUI_PERM = h.id OR a.TSEX_PERM = h.id) "
					+ ",COMMERCIAL_PARTNER b,border_master c,TIPOREGIMEN d, "
					+ " Transporte e, country_code f, country_code g,"
					+ " merchandise_destination_master i, hscode_master j, CATALOGORFC_INC k";
		} else {
			fromClause = "from DATA_RAW a LEFT OUTER JOIN "
					+ "regulations_restrictions_master h "
					+ " ON (a.TPRIM_PERM = h.id OR a.TSEGU_PERM = h.id "
					+ " OR a.TTERC_PERM = h.id OR a.TCUA_PERM = h.id "
					+ " OR a.TQUI_PERM = h.id OR  a.TSEX_PERM = h.id) "
					+ ",COMMERCIAL_PARTNER b use index(taxnumber_idx), border_master c,TIPOREGIMEN d, "
					+ " Transporte e, country_code f, country_code g, "
					+ " merchandise_destination_master i, hscode_master j, CATALOGORFC_INC k";
		}
		// ===========EmportExport Filter===================

		if (bean.getImport_export() != null
				&& !bean.getImport_export().equals("-1")) {
			where = where + " and a.IE = '" + bean.getImport_export() + "'";
		}

		// ===========HSCODE Filter===================

		// System.out.println("Hscode:"+bean.getHsCode().trim().length());

		if (bean.getReport_By() != null && bean.getReport_By().equals("hscode")) {
			// String hsCodeArr[] = bean.getIs_Hscode().split(",");
			String hsWhere = " a.FRACCION in (";
			for (int i = 0; i < hscodeList.size(); i++) {
				if (i == 0)
					hsWhere = hsWhere + "'" + hscodeList.get(i) + "'";
				else
					hsWhere = hsWhere + ",'" + hscodeList.get(i) + "' ";
			}
			where = where + " and " + hsWhere + ")";
		}
		// System.out.println("vauesrangebydollar :"+bean.getValueRangeByDollerFrom().trim().length());
		// ===========valueRangeByDollar Filter===================

		if (bean.getValue_Range_By_Dollar_From().trim().length() > 0
				&& bean.getValue_Range_By_Quantity_To().trim().length() > 0) {
			where = where + "and a.usd between "
					+ bean.getValue_Range_By_Quantity_From() + " and "
					+ bean.getValue_Range_By_Dollar_To();
		}
		// System.out.println("valuerange by quantity :"+bean.getValueRangeByQuantityFrom().trim().length());

		// ===========valueRangeByQuantity Filter===================

		if (bean.getValue_Range_By_Quantity_From().trim().length() > 0
				&& bean.getValue_Range_By_Quantity_To().trim().length() > 0) {
			where = where + " and a.CANTIDAD between "
					+ bean.getValue_Range_By_Quantity_From() + " and "
					+ bean.getValue_Range_By_Quantity_To();

		}
		// System.out.println("border :"+bean.getBorder().trim().length());

		// ===========Border Filter===================

		if (bean.getBorder() != null && !bean.getBorder().equals("")) {
			where = where + " AND c.border_id IN ( " + bean.getBorder() + ") "
					+ " AND a.ADUANA = c.border AND a.SECCION = c.section ";
		}

		// System.out.println("transportation :"+bean.getTransportation().trim().length());

		// ===========Transportation Filter===================

		if (bean.getTransportation().trim().length() != 0) {
			String transportString = "";
			String transportList[] = bean.getTransportation().split(",");
			for (int i = 0; i < transportList.length; i++) {
				if (i == 0)
					transportString = "'" + transportList[i].trim() + "'";
				else
					transportString = transportString + ",'"
							+ transportList[i].trim() + "'";
			}

			where = where + " AND a.transporte IN (" + transportString + ")";

		}
		// System.out.println("Origin :"+bean.getOriginDestinationCountryCode().trim().length());

		// ===========OriginCountryCode Filter===================

		if (bean.getOrigin_Destination_Country_Code().trim().length() != 0) {
			String origDestString = "";
			String origDestList[] = bean.getOrigin_Destination_Country_Code()
					.split(",");
			for (int i = 0; i < origDestList.length; i++) {
				if (i == 0)
					origDestString = "'" + origDestList[i].trim() + "'";
				else
					origDestString = origDestString + ",'"
							+ origDestList[i].trim() + "'";
			}

			where = where + " AND a.ORIGEN IN (" + origDestString + ")";

		}
		// System.out.println("buyer seller :"+bean.getBuyerSellerCountryCode().trim().length());

		// ===========BuyerSeller Filter===================

		if (bean.getBuyer_seller_Country_Code().trim().length() != 0) {
			String buyerSellerString = "";
			String buyerSellerList[] = bean.getBuyer_seller_Country_Code()
					.split(",");
			for (int i = 0; i < buyerSellerList.length; i++) {
				if (i == 0)
					buyerSellerString = "'" + buyerSellerList[i].trim() + "'";
				else
					buyerSellerString = buyerSellerString + ",'"
							+ buyerSellerList[i].trim() + "'";
			}

			where = where + " AND a.RUTA IN(" + buyerSellerString + ")";

		}

		// ===========Regulation Filter===================
		// System.out.println("regulation :"+bean.getRegulationRestriction().trim().length());

		if (bean.getRegulation_Restriction().trim().length() != 0) {
			String regulationString = "";
			String regulationList[] = bean.getRegulation_Restriction().split(
					",");
			for (int i = 0; i < regulationList.length; i++) {
				if (i == 0)
					regulationString = "'" + regulationList[i].trim() + "'";
				else
					regulationString = regulationString + ",'"
							+ regulationList[i].trim() + "'";
			}

			where = where + " AND (a.TPRIM_PERM IN (" + regulationString + ") "
					+ " OR a.TSEGU_PERM IN (" + regulationString + ") "
					+ " OR a.TTERC_PERM IN (" + regulationString + ") "
					+ " OR a.TCUA_PERM IN (" + regulationString + ") "
					+ " OR a.TQUI_PERM IN (" + regulationString + ") "
					+ " OR a.TSEX_PERM IN (" + regulationString + ")) ";
		}
		// ================Supplier Filter==============================
		// System.out.println("Supplier :"+bean.getSupplier().trim().length());

		if (bean.getSupplier() != null && !bean.getSupplier().equals("")) {
			fromClause = fromClause + " , supplier_master_inc sm ";
			where = where + "AND sm.supplier_id " + "IN ( '"
					+ bean.getSupplier() + "' ) "
					+ " AND (b.name = sm.supplier_name) ";

		}

		// ==================Regimes Filter==========================
		// System.out.println("regimes :"+bean.getRegimes().trim().length());
		if (bean.getRegimes().trim().length() != 0) {
			String regimeString = "";
			String regimeList[] = bean.getRegimes().split(",");
			for (int i = 0; i < regimeList.length; i++) {
				if (i == 0)
					regimeString = "'" + regimeList[i].trim() + "'";
				else
					regimeString = regimeString + ", '" + regimeList[i].trim()
							+ "'";
			}

			where = where + " AND a.TIPO IN(" + regimeString + ")";
		}

		// ===========MerchandiseDestination Filter===================
		// System.out.println("merchandise :"+bean.getMerchandiseDestination().trim().length());
		if (bean.getMerchandise_Destination().trim().length() != 0) {
			String merDestString = "";
			String mDestList[] = bean.getMerchandise_Destination().split(",");
			for (int i = 0; i < mDestList.length; i++) {
				if (i == 0)
					merDestString = "'" + mDestList[i].trim() + "'";
				else
					merDestString = merDestString + ",'" + mDestList[i].trim()
							+ "'";

			}

			where = where + " AND i.id IN (" + merDestString + ") ";
		}

		// ================Tax Number Filter=========================
		// System.out.println("TaxNumber :"+bean.getTaxNumber().trim().length());
		if (bean.getReport_By() != null && bean.getReport_By().equals("taxNo")) {
			fromClause = fromClause + " , taxno_master_inc tm ";

			String taxNumberString = "";
			// String taxNumberList[] = bean.getIs_Taxno().split(",");
			for (int i = 0; i < this.taxNumberList.size(); i++) {
				if (i == 0)
					taxNumberString = "" + taxNumberList.get(i) + "";
				else
					taxNumberString = taxNumberString + ","
							+ taxNumberList.get(i).trim() + "";
			}

			where = where + " AND b.taxnumber = tm.tax_no and "
					+ "tm.taxno_id IN (" + taxNumberString + ")";

		}

		// ================Date Filter=========================
		if (bean.getFrom_Date().trim().length() > 0
				&& bean.getTo_Date().trim().length() > 0) {
			where = where + "AND DATE(CONCAT('20',ANIO,'-',mes,'-',dia)) "
					+ " BETWEEN '" + bean.getFrom_Date() + "' and '"
					+ bean.getTo_Date() + "'";

		}

		String finalQuery = "insert into temp_report2 (IE,FRACCION ,CANTIDAD,MONTO,taxnumber, "
				+ "supplier, border, date, "
				+ "ccOrigDest,ccBuyerSeller,"
				+ " regimen, medio_transporte_esp,regulation, merDest,"
				+ " hscode_description, mexican_company)"
				+ subQuery
				+ fromClause + where;
		System.out.println("final query for Input To Other = " + finalQuery);

		try {
			stmt = conn.createStatement();
			int delete = stmt.executeUpdate(deletequery);
			System.out.println("record deleted from temp table 2  " + delete);

			int record = stmt.executeUpdate(finalQuery);
			System.out.println("record inserted into temp_table 2  " + record);

		} catch (Exception e) {

			System.out.println("exception in getdata() in InputToOther ");
			e.printStackTrace();
		} finally {}

	}

	public void genarateExcel() {
		String excelquery = "select * from temp_report2 limit 50000";
		// String excelPath = prop.getProperty("ExcelTemplatePath");
		String storagePath = prop.getProperty("reportStoragePath");
		System.out.println(reportName + "   " + storagePath);
		String sourceFileName = storagePath + "/" + reportName + ".xlsx";
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(excelquery);
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet spreadsheet = workbook.createSheet("sheet");
			XSSFRow row = spreadsheet.createRow(0);
			XSSFCell cell;
			
			XSSFCellStyle my_style = workbook.createCellStyle();
			//XSSFFont my_font=workbook.createFont();
			my_style.setFillForegroundColor(XSSFCellStyle.ALIGN_GENERAL);
	        my_style.setFillPattern(HSSFCellStyle.BORDER_MEDIUM);
	       // my_style.setBorderTop((short) 1); // single line border
	       // my_style.setBorderBottom((short) 1);
	       // my_style.setBorderLeft((short) 1);
	       // my_style.setBorderRight((short) 1);
			//my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
           //my_style.setFont(my_font);
	        
	        XSSFCellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            
            
			cell = row.createCell(0);
			cell.setCellValue("IE");
			cell.setCellStyle(my_style);
			cell = row.createCell(1);
			cell.setCellValue("FRACCION");
			cell.setCellStyle(my_style);
			cell = row.createCell(2);
			cell.setCellValue("CANTIDAD");
			cell.setCellStyle(my_style);	
			cell = row.createCell(3);
			cell.setCellValue("MONTO");
			cell.setCellStyle(my_style);
			cell = row.createCell(4);
			cell.setCellValue("TaxNumber");
			cell.setCellStyle(my_style);
			cell = row.createCell(5);
			cell.setCellValue("Supplier");
			cell.setCellStyle(my_style);
			cell = row.createCell(6);
			cell.setCellValue("Customs");
			cell.setCellStyle(my_style);
			cell = row.createCell(7);
			cell.setCellValue("Date");
			cell.setCellStyle(my_style);
			cell = row.createCell(8);
			cell.setCellValue("Origin");
			cell.setCellStyle(my_style);
			cell = row.createCell(9);
			cell.setCellValue("RUTA");
			cell.setCellStyle(my_style);
			cell = row.createCell(10);
			cell.setCellValue("Regulation");
			cell.setCellStyle(my_style);
			cell = row.createCell(11);
			cell.setCellValue("Destination");
			cell.setCellStyle(my_style);
			cell = row.createCell(12);
			cell.setCellValue("FRACCION Desc");
			cell.setCellStyle(my_style);
			cell = row.createCell(13);
			cell.setCellValue("Regimen");
			cell.setCellStyle(my_style);
			cell = row.createCell(14);
			cell.setCellValue("Transporte");
			cell.setCellStyle(my_style);
			cell = row.createCell(15);
			cell.setCellValue("Mexican Company");
			cell.setCellStyle(my_style);

			int i = 1;
			while (rs.next()) {
				row = spreadsheet.createRow(i);
				cell = row.createCell(0);
				cell.setCellValue(rs.getString(1));
				cell = row.createCell(1);
				row.getCell(0).setCellStyle(style);
				cell.setCellValue(rs.getString(2));
				cell = row.createCell(2);
				  row.getCell(1).setCellStyle(style);
				cell.setCellValue(rs.getString(3));
				cell = row.createCell(3);
				  row.getCell(2).setCellStyle(style);
				cell.setCellValue(rs.getString(4));
				cell = row.createCell(4);
				  row.getCell(3).setCellStyle(style);
				cell.setCellValue(rs.getString(5));
				cell = row.createCell(5);
				  row.getCell(4).setCellStyle(style);
				cell.setCellValue(rs.getString(6));
				cell = row.createCell(6);
				  row.getCell(5).setCellStyle(style);
				cell.setCellValue(rs.getString(7));
				cell = row.createCell(7);
				  row.getCell(6).setCellStyle(style);
				cell.setCellValue(rs.getString(8));
				cell = row.createCell(8);
				  row.getCell(7).setCellStyle(style);
				cell.setCellValue(rs.getString(9));
				cell = row.createCell(9);
				  row.getCell(8).setCellStyle(style);
				cell.setCellValue(rs.getString(10));
				cell = row.createCell(10);
				  row.getCell(9).setCellStyle(style);
				cell.setCellValue(rs.getString(11));
				cell = row.createCell(11);
				  row.getCell(10).setCellStyle(style);
				cell.setCellValue(rs.getString(12));
				cell = row.createCell(12);
				  row.getCell(11).setCellStyle(style);
				cell.setCellValue(rs.getString(13));
				cell = row.createCell(13);
				row.getCell(12).setCellStyle(style);
				cell.setCellValue(rs.getString(14));
				cell = row.createCell(14);
				row.getCell(13).setCellStyle(style);
				cell.setCellValue(rs.getString(15));
				cell = row.createCell(15);
				row.getCell(14).setCellStyle(style);
				cell.setCellValue(rs.getString(16));
				cell=row.createCell(16);
				row.getCell(15).setCellStyle(style);
				i++;

			}

			FileOutputStream out = new FileOutputStream(
					new File(sourceFileName));

			workbook.write(out);

			out.close();

			System.out.println("excel file generate in InputToOther successfully");

		} catch (Exception e) {
			System.out.println("Error In InputToOtherClass genarateExcel() method");
			e.printStackTrace();
		}finally {}
	}

}
