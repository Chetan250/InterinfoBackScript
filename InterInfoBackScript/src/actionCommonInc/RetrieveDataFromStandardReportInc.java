package actionCommonInc;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import com.mysql.jdbc.ResultSet;

import connection.LoadProperty;
import connection.MyConnection;
import bean.CommonInternational;
import bean.CustomReportBean;

public class RetrieveDataFromStandardReportInc {
	
	
	Connection conn;
	Statement stmt;
	java.sql.ResultSet rs;
	Properties objProperties;
	
	
	public RetrieveDataFromStandardReportInc() {
		initalize();
	}

	public void initalize(){
		
		try {
			objProperties = new LoadProperty().getProperty();
			conn = new MyConnection().getConnection(objProperties);
			
		} catch (Exception e) {
			System.out.println("Error in RetrieveDataFromStandardReportInc");
		}
	}

	public void getStandardData(CustomReportBean bean) {
	
		String selectQuery = "";
		String fromClause = "";
		String where = " where b.estadocve = e.EstadoCve "
				+ " and a.RUTA = o.origen and a.ORIGEN = oo.origen "
				+ " and a.transporte = d.transporte and a.unidad = u.unidad "
				+ " and a.AGENTE = cp.AGENTE and a.ADUANA = cp.ADUANA "
				+ " and a.SECCION = cp.SECCION and a.PEDIMENTO = cp.PEDIMENTO "
				+ " and a.FRACCION = c.hscode and a.TIPO = t.tipo "
				+ "and a.rfc = b.rfc";
				
		selectQuery = "SELECT CASE  WHEN a.IE = '1' THEN 'Import'  WHEN a.IE = '2'"
				+ " then 'Export' end as IE,"
				+ "a.FRACCION,c.hscode_description,b.rfc,"
				+ "b.razon,b.direccion,b.cp,b.municipiocve,e.Estado,a.ADUANA,a.AGENTE,"
				+ "a.PEDIMENTO,a.DIA,a.MES,a.ANIO,a.TIPO,t.regimen,a.RUTA,"
				+ "o.pais_origen_destino_ing,a.ORIGEN ,oo.pais_origen_destino_ing,"
				+ "a.CANTIDAD,a.unidad,a.MONTO,a.IMPUESTO,a.USD,a.PESOBRUTO,"
				+ "d.medio_transporte_ing,a.CANTCOMER,u.unidad_medida_ing,"
				+ "'' as CUOTACOMP1 ,a.CUOTACOMP,a.TIPOCAMBIO,"
				+ "a.VALORCOM,cp.taxnumber,ifnull(ccn.cleanned_name,cp.name),cp.address,"
				+ "'' as CIUDAD,cp.city_state ,cp.zip";
		
		fromClause=" from DATA_RAW a, CATALOGORFC_INC b,hscode_master c,Estado e,TIPOREGIMEN t,"
				+ "origen o,Transporte d,UnidadMedida u,COMMERCIAL_PARTNER cp left outer join "
				+ " clean_company_names ccn"
				+ " on cp.name = ccn.mexican_name,"
				+ "origen oo ";

		

		if (bean.getImportExport() != null
				&& !bean.getImportExport().equals("-1")) {
			where = where + " and a.IE = '" + bean.getImportExport() + "'";
		}

		// ===========HSCODE Filter===================

		// System.out.println("Hscode:"+bean.getHsCode().trim().length());

		if (bean.getHsCode()!=null && bean.getHsCode().trim().length() != 0) {
			String hsCodeArr[] = bean.getHsCode().split(",");
			String hsWhere = "";
			for (int i = 0; i < hsCodeArr.length; i++) {
				if (hsWhere.equals(""))
					hsWhere = " a.FRACCION = '" + hsCodeArr[i].trim() + "' ";
				else
					hsWhere = hsWhere + " or a.FRACCION = '" + hsCodeArr[i].trim()
							+ "' ";
			}
			where = where + " and (" + hsWhere + ")";
		}
		// System.out.println("vauesrangebydollar :"+bean.getValueRangeByDollerFrom().trim().length());
		// ===========valueRangeByDollar Filter===================

		if (bean.getValueRangeByDollerFrom().trim().length() > 0
				&& bean.getValueRangeByQuantityTo().trim().length() > 0) {
			where = where + "and a.usd between "
					+ bean.getValueRangeByDollerFrom() + " and "
					+ bean.getValueRangeByDollerTo();
		}
		// System.out.println("valuerange by quantity :"+bean.getValueRangeByQuantityFrom().trim().length());

		// ===========valueRangeByQuantity Filter===================

		if (bean.getValueRangeByQuantityFrom().trim().length() > 0
				&& bean.getValueRangeByQuantityTo().trim().length() > 0) {
			where = where + " and a.CANTIDAD between "
					+ bean.getValueRangeByQuantityFrom() + " and "
					+ bean.getValueRangeByQuantityTo();

		}
		// System.out.println("border :"+bean.getBorder().trim().length());

		// ===========Border Filter===================

		if (bean.getBorder() != null && !bean.getBorder().equals("")) {
			where = where + " AND bm.border_id IN ( " + bean.getBorder() + ") "
					+ " AND a.ADUANA = bm.border AND a.SECCION = bm.section ";
			fromClause = fromClause + ", border_master bm";
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

		if (bean.getOriginDestinationCountryCode().trim().length() != 0) {
			String origDestString = "";
			String origDestList[] = bean.getOriginDestinationCountryCode()
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

		if (bean.getBuyerSellerCountryCode().trim().length() != 0) {
			String buyerSellerString = "";
			String buyerSellerList[] = bean.getBuyerSellerCountryCode().split(
					",");
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

		if (bean.getRegulationRestriction().trim().length() != 0) {
			String regulationString = "";
			String regulationList[] = bean.getRegulationRestriction()
					.split(",");
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
			where = where + " AND sm.supplier_id " + "IN ( '"
					+ bean.getSupplier() + "' ) "
					+ " AND (cp.name = sm.supplier_name) ";

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
					regimeString = regimeString + ",'" + regimeList[i].trim() + "'";
			}

			where = where + " AND a.TIPO IN(" + regimeString + ")";
		}

		// ===========MerchandiseDestination Filter===================
		// System.out.println("merchandise :"+bean.getMerchandiseDestination().trim().length());
		if (bean.getMerchandiseDestination().trim().length() != 0) {
			String merDestString = "";
			String mDestList[] = bean.getMerchandiseDestination().split(",");
			for (int i = 0; i < mDestList.length; i++) {
				if (i == 0)
					merDestString = "'" + mDestList[i].trim() + "'";
				else
					merDestString = merDestString + ",'" + mDestList[i].trim()
							+ "'";

			}

			where = where + " AND i.id IN (" + merDestString + ") "
					+ " and a.DEST_ORIG = i.id ";
			fromClause = fromClause + " ,merchandise_destination_master i ";
			
		}

		// ================Tax Number Filter=========================
		// System.out.println("TaxNumber :"+bean.getTaxNumber().trim().length());
		if (bean.getTaxNumber()!=null && bean.getTaxNumber().trim().length() != 0) {
			fromClause = fromClause + " , taxno_master_inc tm ";

			String taxNumberString = "";
			String taxNumberList[] = bean.getTaxNumber().split(",");
			for (int i = 0; i < taxNumberList.length; i++) {
				if (i == 0)
					taxNumberString = "" + taxNumberList[i].trim() + "";
				else
					taxNumberString = taxNumberString + ","
							+ taxNumberList[i].trim() + "";
			}

			where = where + " AND cp.taxnumber = tm.tax_no and "
					+ "tm.taxno_id IN (" + taxNumberString + ")";

		}

		// ================Date Filter=========================
		if (bean.getFromDate().trim().length() > 0
				&& bean.getToDate().trim().length() > 0) {
			where = where + "AND DATE(CONCAT('20',ANIO,'-',mes,'-',dia)) "
					+ " BETWEEN '" + bean.getFromDate() + "' and '"
					+ bean.getToDate() + "'";

		}

		String finalQuery = selectQuery + fromClause + where ;
		System.out.println("Final query Inc Common = " + finalQuery);

		ArrayList<CommonInternational> list = new ArrayList<CommonInternational>();
		CommonInternational cInc = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(finalQuery);
			while (rs.next()) {
				
				cInc = new CommonInternational();
				cInc .setIE(rs.getString(1));
				cInc .setFraccion(rs.getString(2));
				cInc .setDescription(rs.getString(3));
				cInc .setEmpresaMexico(rs.getString(4));
				cInc .setRazonSocial(rs.getString(5));
				cInc .setDireccionSend(rs.getString(6));
				cInc .setCPSend(rs.getString(7));
				cInc .setMunicipio(rs.getString(8));
				cInc .setEstadoSend(rs.getString(9));
				cInc .setAduana(rs.getString(10));
				cInc .setAgente(rs.getString(11));
				cInc .setPedimento(rs.getString(12));
				cInc .setDia(rs.getString(13));
				cInc .setMes(rs.getString(14));
				cInc .setAno(rs.getString(15));
				cInc .setTipo(rs.getString(16));
				cInc .setRegimen(rs.getString(17));
				cInc .setComprador(rs.getString(18));
				cInc .setPaisComprador(rs.getString(19));
				cInc .setOrigen(rs.getString(20));
				cInc .setPaisOrigen(rs.getString(21));
				cInc .setCantidadSend(rs.getString(22));
				cInc .setUnidad(rs.getString(23));
				cInc .setMonto(rs.getString(24));
				cInc .setImpuesto(rs.getString(25));
				cInc .setUsd(rs.getString(26));
				cInc .setPesoBruto(rs.getString(27));
				cInc .setMedio(rs.getString(28));
				cInc .setCantidadReceive(rs.getString(29));
				cInc .setMedida(rs.getString(30));
				cInc .setCuotacomp1(rs.getString(31));
				cInc .setCuotacomp2(rs.getString(32));
				cInc .setTipocambio(rs.getString(33));
				cInc .setValor(rs.getString(34));
				cInc .setSocioComercial(rs.getString(35));
				cInc .setNombre(rs.getString(36));
				cInc .setDireccionReceive(rs.getString(37));
				cInc .setCiudad(rs.getString(38));
				cInc .setEstadoReceive(rs.getString(39));
				cInc .setCPReceive(rs.getString(40));
				list.add(cInc);
					
			}
			String fileNameString=bean.getReportName().replace(" ", "")+"_u"+bean.getUserId()+".xls";
			
			System.out.println("Size of list -= "+list.size());
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
			Map<String, Object> parameters = new HashMap<String, Object>();			
			//JasperReport jasperReport = JasperCompileManager.compileReport(objProperties.getProperty("pathOfStdIncRepTemplate").toString()+"CommonInc.jasper");
			//JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
			
			String sourceFileName = objProperties.getProperty("pathOfStdIncRepTemplate")+"CommonInc.jasper";
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					sourceFileName, parameters, beanColDataSource);
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(objProperties.getProperty("ouputFolderForCommonInc")+fileNameString));
			/*SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			configuration.setOnePagePerSheet(true);
			configuration.setDetectCellType(true);
			configuration.setCollapseRowSpan(false);
			exporter.setConfiguration(configuration);*/
			exporter.exportReport();
			System.out.println("Standard INC Report Created...");

		} catch (Exception e) {

			System.out.println("exception Common INC");
			e.printStackTrace();
		}
		
	}
	public void closeAll(){
				
		try {
			if(conn!=null)
				conn.close();if(rs!=null)
					rs.close();
				if(stmt!=null)
					stmt.close();
		System.out.println("Connection is closed..");
		} catch (Exception e) {
			System.out.println("Error in closing connection");
			e.printStackTrace();
			
			
		}		
			}

}
