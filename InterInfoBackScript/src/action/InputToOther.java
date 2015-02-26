package action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import bean.CustomReportBean;
import connection.MyConnection;

public class InputToOther {
	Connection conn;
	Statement stmt;
	Properties prop;
	String reportName;
	
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

	public void getOtherData(String reportId) {

//		String subQuery = "";
//		String fromClause = "";
//		String where = " WHERE a.AGENTE = b.AGENTE  AND a.ADUANA = b.ADUANA "
//				+ " AND a.SECCION = b.SECCION  AND a.PEDIMENTO = b.PEDIMENTO "
//				+ " AND c.border = a.ADUANA AND c.section = a.SECCION "
//				+ " AND a.TIPO = d.tipo AND a.transporte = e.transporte "
//				+ " AND a.ORIGEN = f.cc_key  AND a.RUTA = g.cc_key "
//				+ " AND a.DEST_ORIG = i.id  AND j.hscode = a.FRACCION AND k.rfc = a.RFC";
//
//		String deletequery = "delete from temp_report";
//
//		subQuery = "SELECT CASE  WHEN a.IE = '1' THEN 'Import'  WHEN a.IE = '2'"
//				+ " THEN 'Export'  END as IE, a.FRACCION,a.CANTIDAD, a.MONTO,taxnumber, k.razon, c.description as"
//				+ " border, concat('20',a.ANIO,'-',a.mes,'-',a.dia ) as date,f.country_name as ccOrigDest,"
//				+ " g.country_name as ccBuyerSeller, d.regimen,e.medio_transporte_esp,"
//				+ " h.description as regulation, i.description as merDest,"
//				+ " j.hscode_description, k.razon as mexican_company ";
//
//		if (bean.getHsCode().trim().length() != 0) {
//			fromClause = "from DATA_RAW a use index(fraccion_idx) "
//					+ "LEFT OUTER JOIN regulations_restrictions_master h "
//					+ " ON (a.TPRIM_PERM = h.id OR a.TSEGU_PERM =h.id "
//					+ "OR a.TTERC_PERM = h.id OR a.TCUA_PERM = h.id "
//					+ "OR a.TQUI_PERM = h.id OR a.TSEX_PERM = h.id) "
//					+ ",COMMERCIAL_PARTNER b,border_master c,TIPOREGIMEN d, "
//					+ " Transporte e, country_code f, country_code g,"
//					+ " merchandise_destination_master i, hscode_master j, CATALOGORFC k";
//		} else {
//
//			fromClause = "from DATA_RAW a LEFT OUTER JOIN "
//					+ "regulations_restrictions_master h "
//					+ " ON (a.TPRIM_PERM = h.id OR a.TSEGU_PERM = h.id "
//					+ " OR a.TTERC_PERM = h.id OR a.TCUA_PERM = h.id "
//					+ " OR a.TQUI_PERM = h.id OR  a.TSEX_PERM = h.id) "
//					+ ",COMMERCIAL_PARTNER b use index(taxnumber_idx), border_master c,TIPOREGIMEN d, "
//					+ " Transporte e, country_code f, country_code g, "
//					+ " merchandise_destination_master i, hscode_master j, CATALOGORFC k";
//		}
//		// ===========EmportExport Filter===================
//
//		if (bean.getImportExport() != null
//				&& !bean.getImportExport().equals("-1")) {
//			where = where + " and a.IE = '" + bean.getImportExport() + "'";
//		}
//
//		// ===========HSCODE Filter===================
//
//		// System.out.println("Hscode:"+bean.getHsCode().trim().length());
//
//		if (bean.getHsCode().trim().length() != 0) {
//			String hsCodeArr[] = bean.getHsCode().split(",");
//			String hsWhere = "";
//			for (int i = 0; i < hsCodeArr.length; i++) {
//				if (hsWhere.equals(""))
//					hsWhere = " a.FRACCION like '" + hsCodeArr[i] + "%' ";
//				else
//					hsWhere = hsWhere + " or a.FRACCION like '" + hsCodeArr[i]
//							+ "%' ";
//			}
//			where = where + " and (" + hsWhere + ")";
//		}
//		// System.out.println("vauesrangebydollar :"+bean.getValueRangeByDollerFrom().trim().length());
//		// ===========valueRangeByDollar Filter===================
//
//		if (bean.getValueRangeByDollerFrom().trim().length() > 0
//				&& bean.getValueRangeByQuantityTo().trim().length() > 0) {
//			where = where + "and a.usd between "
//					+ bean.getValueRangeByDollerFrom() + " and "
//					+ bean.getValueRangeByDollerTo();
//		}
//		// System.out.println("valuerange by quantity :"+bean.getValueRangeByQuantityFrom().trim().length());
//
//		// ===========valueRangeByQuantity Filter===================
//
//		if (bean.getValueRangeByQuantityFrom().trim().length() > 0
//				&& bean.getValueRangeByQuantityTo().trim().length() > 0) {
//			where = where + " and a.CANTIDAD between "
//					+ bean.getValueRangeByQuantityFrom() + " and "
//					+ bean.getValueRangeByQuantityTo();
//
//		}
//		// System.out.println("border :"+bean.getBorder().trim().length());
//
//		// ===========Border Filter===================
//
//		if (bean.getBorder() != null && !bean.getBorder().equals("")) {
//			where = where + " AND c.border_id IN ( " + bean.getBorder() + ") "
//					+ " AND a.ADUANA = c.border AND a.SECCION = c.section ";
//		}
//
//		// System.out.println("transportation :"+bean.getTransportation().trim().length());
//
//		// ===========Transportation Filter===================
//
//		if (bean.getTransportation().trim().length() != 0) {
//			String transportString = "";
//			String transportList[] = bean.getTransportation().split(",");
//			for (int i = 0; i < transportList.length; i++) {
//				if (i == 0)
//					transportString = "'" + transportList[i].trim() + "'";
//				else
//					transportString = transportString + ",'"
//							+ transportList[i].trim() + "'";
//			}
//
//			where = where + " AND a.transporte IN (" + transportString + ")";
//
//		}
//		// System.out.println("Origin :"+bean.getOriginDestinationCountryCode().trim().length());
//
//		// ===========OriginCountryCode Filter===================
//
//		if (bean.getOriginDestinationCountryCode().trim().length() != 0) {
//			String origDestString = "";
//			String origDestList[] = bean.getOriginDestinationCountryCode()
//					.split(",");
//			for (int i = 0; i < origDestList.length; i++) {
//				if (i == 0)
//					origDestString = "'" + origDestList[i].trim() + "'";
//				else
//					origDestString = origDestString + ",'"
//							+ origDestList[i].trim() + "'";
//			}
//
//			where = where + " AND a.ORIGEN IN (" + origDestString + ")";
//
//		}
//		// System.out.println("buyer seller :"+bean.getBuyerSellerCountryCode().trim().length());
//
//		// ===========BuyerSeller Filter===================
//
//		if (bean.getBuyerSellerCountryCode().trim().length() != 0) {
//			String buyerSellerString = "";
//			String buyerSellerList[] = bean.getBuyerSellerCountryCode().split(
//					",");
//			for (int i = 0; i < buyerSellerList.length; i++) {
//				if (i == 0)
//					buyerSellerString = "'" + buyerSellerList[i].trim() + "'";
//				else
//					buyerSellerString = buyerSellerString + ",'"
//							+ buyerSellerList[i].trim() + "'";
//			}
//
//			where = where + " AND a.RUTA IN(" + buyerSellerString + ")";
//
//		}
//
//		// ===========Regulation Filter===================
//		// System.out.println("regulation :"+bean.getRegulationRestriction().trim().length());
//
//		if (bean.getRegulationRestriction().trim().length() != 0) {
//			String regulationString = "";
//			String regulationList[] = bean.getRegulationRestriction()
//					.split(",");
//			for (int i = 0; i < regulationList.length; i++) {
//				if (i == 0)
//					regulationString = "'" + regulationList[i].trim() + "'";
//				else
//					regulationString = regulationString + ",'"
//							+ regulationList[i].trim() + "'";
//			}
//
//			where = where + " AND (a.TPRIM_PERM IN (" + regulationString + ") "
//					+ " OR a.TSEGU_PERM IN (" + regulationString + ") "
//					+ " OR a.TTERC_PERM IN (" + regulationString + ") "
//					+ " OR a.TCUA_PERM IN (" + regulationString + ") "
//					+ " OR a.TQUI_PERM IN (" + regulationString + ") "
//					+ " OR a.TSEX_PERM IN (" + regulationString + ")) ";
//		}
//		// ================Supplier Filter==============================
//		// System.out.println("Supplier :"+bean.getSupplier().trim().length());
//
//		if (bean.getSupplier() != null && !bean.getSupplier().equals("")) {
//			fromClause = fromClause + " , supplier_master sm ";
//			where = where + "AND sm.supplier_id " + "IN ( '"
//					+ bean.getSupplier() + "' ) "
//					+ " AND (b.name = sm.supplier_name) ";
//
//		}
//
//		// ==================Regimes Filter==========================
//		// System.out.println("regimes :"+bean.getRegimes().trim().length());
//		if (bean.getRegimes().trim().length() != 0) {
//			String regimeString = "";
//			String regimeList[] = bean.getRegimes().split(",");
//			for (int i = 0; i < regimeList.length; i++) {
//				if (i == 0)
//					regimeString = "'" + regimeList[i].trim() + "'";
//				else
//					regimeString = regimeString + "'" + regimeList[i].trim()
//							+ "',";
//			}
//
//			where = where + " AND a.TIPO IN(" + regimeString + ")";
//		}
//
//		// ===========MerchandiseDestination Filter===================
//		// System.out.println("merchandise :"+bean.getMerchandiseDestination().trim().length());
//		if (bean.getMerchandiseDestination().trim().length() != 0) {
//			String merDestString = "";
//			String mDestList[] = bean.getMerchandiseDestination().split(",");
//			for (int i = 0; i < mDestList.length; i++) {
//				if (i == 0)
//					merDestString = "'" + mDestList[i].trim() + "'";
//				else
//					merDestString = merDestString + ",'" + mDestList[i].trim()
//							+ "'";
//
//			}
//
//			where = where + " AND i.id IN (" + merDestString + ") "
//					+ " AND a.DEST_ORIG = i.id ";
//		}
//
//		// ================Tax Number Filter=========================
//		// System.out.println("TaxNumber :"+bean.getTaxNumber().trim().length());
//		if (bean.getTaxNumber().trim().length() != 0) {
//			fromClause = fromClause + " , taxno_master tm ";
//
//			String taxNumberString = "";
//			String taxNumberList[] = bean.getTaxNumber().split(",");
//			for (int i = 0; i < taxNumberList.length; i++) {
//				if (i == 0)
//					taxNumberString = "'" + taxNumberList[i].trim() + "'";
//				else
//					taxNumberString = taxNumberString + ",'"
//							+ taxNumberList[i].trim() + "'";
//			}
//
//			where = where + " AND (b.taxnumber = tm.tax_no) and "
//					+ "tm.taxno_id IN (" + taxNumberString + ")";
//
//		}
//
//		// ================Date Filter=========================
//		if (bean.getFromDate().trim().length() > 0
//				&& bean.getToDate().trim().length() > 0) {
//			where = where + "AND DATE(CONCAT('20',ANIO,'-',mes,'-',dia)) "
//					+ " BETWEEN '" + bean.getFromDate() + "' and '"
//					+ bean.getToDate() + "'";
//
//		}
//
//		String finalQuery = "insert into temp_report (IE,FRACCION ,CANTIDAD,MONTO,taxnumber, "
//				+ "supplier, border, date, "
//				+ "ccOrigDest,ccBuyerSeller,"
//				+ " regimen, medio_transporte_esp,regulation, merDest,"
//				+ " hscode_description, mexican_company)"
//				+ subQuery
//				+ fromClause + where;
//		System.out.println("final query = " + finalQuery);
//
//		try {
//			stmt = conn.createStatement();
//			// int delete = stmt.executeUpdate(deletequery);
//			// System.out.println("record deleted from temp table "+delete);
//
//			// int record = stmt.executeUpdate(finalQuery);
//			// System.out.println("record inserted into temp_table " + record);
//
//		} catch (Exception e) {
//
//			System.out.println("exception in final dao");
//			e.printStackTrace();
//		}

	
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

	public CustomReportBean createTempReport2(String reportId) {
		CustomReportBean objBean = null;
		ResultSet rs = null;
		try {
			String query = "SELECT import_export, from_date, to_date , "
					+ " value_range_by_doller_from, value_range_by_doller_to, "
					+ " value_range_by_quantity_from, value_range_by_quantity_to , "
					+ " supplier, border, regimes, transportation, " //11
					+ " origin_destination_country_code, buyer_seller_country_code, "
					+ " regulation_restriction, merchandise_destination, report_by  " //16
					+ " FROM report_report_mapping "
					+ " WHERE main_report_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, reportId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				objBean = new CustomReportBean();
				objBean.setImportExport(rs.getString("import_export"));
				objBean.setFromDate(rs.getString("from_date"));
				objBean.setToDate(rs.getString("to_date"));
				objBean.setValueRangeByDollerFrom(rs.getString("value_range_by_doller_from"));
				objBean.setValueRangeByDollerTo(rs.getString("value_range_by_doller_to"));
				objBean.setValueRangeByQuantityFrom(rs.getString("value_range_by_quantity_from"));
				objBean.setValueRangeByQuantityTo(rs.getString("value_range_by_quantity_to"));
				objBean.setSupplier(rs.getString("supplier"));
				objBean.setBorder(rs.getString("border"));
				objBean.setRegimes(rs.getString("regimes"));
				objBean.setTransportation(rs.getString("transportation"));
				objBean.setOriginDestinationCountryCode(rs.getString("origin_destination_country_code"));
				objBean.setBuyerSellerCountryCode(rs.getString("buyer_seller_country_code"));
				objBean.setRegulationRestriction(rs.getString("regulation_restriction"));
				objBean.setMerchandiseDestination(rs.getString("merchandise_destination"));
				objBean.setReportBy(rs.getString("report_by"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objBean;
	}
	

}
