package actionCommonInc;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import bean.CustomReportBean;
import bean.ExtendedBean;
import connection.LoadProperty;
import connection.MyConnection;

public class RetrieveDataFromExtendedReport {
	
	
	Connection conn;
	Statement stmt;
	java.sql.ResultSet rs;
	Properties objProperties;
	
	public RetrieveDataFromExtendedReport() {
		initalize();
	}

	public void initalize(){
		
		try {
			objProperties = new LoadProperty().getProperty();
			conn = new MyConnection().getConnection(objProperties);
			
		} catch (Exception e) {
			System.out.println("Error in RetrieveDataFromExtendedReport");
		}
	}
	
	
	public void getExtendedData(CustomReportBean bean)
	{
		String selectQuery = "";
		String fromClause = "";
		String where = " where  a.ADUANA = ad.aduana and a.SECCION = ad.seccion "
				+ "and b.estadocve = e.EstadoCve and a.RUTA = o.origen "
				+ "and a.ORIGEN = oo.origen and a.transporte = d.transporte "
				+ "and a.unidad = u.unidad and a.AGENTE = cp.AGENTE "
				+ "and a.ADUANA = cp.ADUANA and a.SECCION = cp.SECCION "
				+ "and a.PEDIMENTO = cp.PEDIMENTO and a.TIPO = t.tipo "
				+ "and a.FRACCION = hm.hscode and a.rfc = b.rfc "; 
				
		selectQuery = " select CASE  WHEN a.IE = '1' THEN 'Import'  WHEN a.IE = '2' then 'Export' end as IE "
				+ ",a.FECENTRA,a.AGENTE,ad.nombre_aduana,a.TIPO,t.regimen,"
				+ "a.PEDIMENTO,a.DIA,a.MES,a.ANIO,a.FRACCION,hm.hscode_description,a.RFC,"
				+ "b.razon,b.direccion,b.cp,b.municipiocve,e.Estado,a.RUTA,o.pais_origen_destino_ing,"
				+ "a.ORIGEN,oo.pais_origen_destino_ing,d.medio_transporte_ing,u.unidad_medida_ing,"
				+ "a.CANTCOMER,a.PESOBRUTO,a.CANTIDAD,u.unidad_medida_esp,a.MONTO,a.USD,a.FP_ADVAL1,"
				+ "a.IMPUESTO,a.FP_TRAMAD,a.IMP_TRAMAD,a.FP_CCOMP1,a.CUOTACOMP,a.FP_IVA1,a.IMP_IVA1,"
				+ "a.FP_ISAN,a.IMP_ISAN,a.FP_IEPS,a.IMP_IEPS,a.IMP_FLETE,a.IMP_SEGURO,a.TIPOCAMBIO,"
				+ "a.FECPAGO,a.VALORCOM,a.VALOR_AGR,a.DEST_ORIG,a.TASA_ADV,a.TASA_IVA,a.VINCULA,"
				+ "a.MET_VALORA,a.TASA_IEPS,a.PRIM_PERM,a.TPRIM_PERM,a.SEGU_PERM,a.TSEGU_PERM,"
				+ "a.TERC_PERM,a.TTERC_PERM,a.FP_RECAR,a.IMP_RECAR ,cp.taxnumber,ifnull(ccn.cleanned_name,cp.name),"
				+ "cp.address,'' as CITY,cp.city_state,cp.zip";
		
		fromClause=" FROM DATA_RAW a,COMMERCIAL_PARTNER cp left outer join clean_company_names ccn"
				+ " on cp.name = ccn.mexican_name,ADUANA ad,TIPOREGIMEN t,hscode_master hm,"
				+ "CATALOGORFC_INC b,Estado e,origen o,origen oo,Transporte d,UnidadMedida u";
		// ===========EmportExport Filter===================

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
					hsWhere = hsWhere + " or a.FRACCION ='" + hsCodeArr[i].trim()
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
		System.out.println("Final query Of Extended INC = " + finalQuery);

		ArrayList<ExtendedBean> list = new ArrayList<ExtendedBean>();
		ExtendedBean ebean = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(finalQuery);
			while (rs.next()) {
				ebean = new ExtendedBean();
				ebean .setIE(rs.getString(1));
				ebean .setFECENTRA(rs.getString(2));
				ebean .setAGENTE(rs.getString(3));
				ebean .setNOMBRE_ADUANA(rs.getString(4));
				ebean .setTIPO(rs.getString(5));
				ebean .setREGIMEN(rs.getString(6));
				ebean .setPEDIMENTO(rs.getString(7));
				ebean .setDIA(rs.getString(8));
				ebean .setMES(rs.getString(9));
				ebean .setAno(rs.getString(10));
				ebean.setFRACCION(rs.getString(11));
				ebean .setDESCRIPTION(rs.getString(12));
				ebean .setRFC(rs.getString(13));
				ebean .setRAZON(rs.getString(14));
				ebean .setDIRECCION(rs.getString(15));
				ebean .setCP(rs.getString(16));
				ebean .setMUNICIPIO(rs.getString(17));
				ebean .setESTADO(rs.getString(18));
				ebean .setRUTA(rs.getString(19));
				ebean .setPAIS_RUTA(rs.getString(20));
				ebean .setORIGEN(rs.getString(21));
				ebean .setPAIS_ORIGEN(rs.getString(22));
				ebean .setMEDIO(rs.getString(23));
				ebean .setMED_COMER(rs.getString(24));
				ebean .setCANT_COMER(rs.getString(25));
				ebean .setPESO_BRUTO(rs.getString(26));
				ebean .setCANTIDAD(rs.getString(27));
				ebean .setUNIDAD_DE_MEDIDA(rs.getString(28));
				ebean .setMONTO(rs.getString(29));
				ebean .setUSD(rs.getString(30));
				ebean .setFP_ADVAL1(rs.getString(31));
				ebean .setIMPUESTO(rs.getString(32));
				ebean .setFP_TRAMAD(rs.getString(33));
				ebean .setIMP_TRAMAD(rs.getString(34));
				ebean .setFP_CCOMP1(rs.getString(35));
				ebean .setCUOTACOMP(rs.getString(36));
				ebean .setFP_IVA1(rs.getString(37));
				ebean .setIMP_IVA1(rs.getString(38));
				ebean .setFP_ISAN(rs.getString(39));
				ebean .setIMP_ISAN(rs.getString(40));
				ebean .setFP_IEPS(rs.getString(41));
				ebean .setIMP_IEPS(rs.getString(42));
				ebean .setIMP_FLETE(rs.getString(43));
				ebean .setIPM_SECURO(rs.getString(44));
				ebean .setTIPO_CAMBIO(rs.getString(45));
				ebean .setFECPAGO(rs.getString(46));
				ebean .setVALORCOM(rs.getString(47));
				ebean .setVALORAGR(rs.getString(47));
				ebean .setDEST_ORIG(rs.getString(49));
				ebean .setTASA_ADV(rs.getString(50));
				ebean .setTASA_IVA(rs.getString(51));
				ebean .setVINCULA(rs.getString(52));
				ebean .setMETVALORA(rs.getString(53));
				ebean .setTASAIEPS(rs.getString(54));
				ebean .setPRIM_PERM(rs.getString(55));
				ebean .setTPRIM_PERM(rs.getString(56));
				ebean .setSEGU_PERM(rs.getString(57));
				ebean .setTSEGU_PERM(rs.getString(58));
				ebean .setTERC_PERM(rs.getString(59));
				ebean .setTTERC_PERM(rs.getString(60));
				ebean .setFP_RECAR(rs.getString(61));
				ebean .setIPM_RECAR(rs.getString(62));
				ebean .setTAXNUMBER(rs.getString(63));
				ebean .setNAME(rs.getString(64));
				ebean .setADDRESS(rs.getString(65));
				ebean .setCITY(rs.getString(66));
				ebean .setSTATE(rs.getString(67));
				ebean .setZIP(rs.getString(68));
				list.add(ebean);
					
			}
			String fileNameString=bean.getReportName().replace(" ", "")+"_u"+bean.getUserId()+".xls";
			
			System.out.println("Size of list IN Extended = "+list.size());
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
			Map<String, Object> parameters = new HashMap<String, Object>();			
			String sourceFileName = objProperties.getProperty("pathOfStdIncRepTemplate")+"ExtendedInc.jasper";
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					sourceFileName, parameters, beanColDataSource);
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(objProperties.getProperty("ouputFolderForExtendedInc")+fileNameString));
		
			exporter.exportReport();
			System.out.println("Extended Report Created...");
	
		

		} catch (Exception e) {

			System.out.println("exception in RetrieveDataFromExtendedReport");
			e.printStackTrace();
		}
		
	}
	public void closeAll(){
				
		try {
			if(conn!=null)
				conn.close();
			if(rs!=null)
				rs.close();
			if(stmt!=null)
				stmt.close();
		System.out.println("Connection is closed RetrieveDataFromExtendedReport..");
		} catch (Exception e) {
			System.out.println("Error in Extended closing connection");
			e.printStackTrace();
			
			
		}		
			}
	}
	


