package bean;

public class HSBeanQuarter {

	private String FRACCION;
	private String year;
	private String month;
	private double MONTO;
	public String getFRACCION() {
		return FRACCION;
	}
	public String getYear() {
		return year;
	}
	public String getMonth() {
		return month;
	}
	public double getMONTO() {
		return MONTO;
	}
	public void setFRACCION(String fRACCION) {
		FRACCION = fRACCION;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public void setMONTO(double mONTO) {
		MONTO = mONTO;
	}
	

}
