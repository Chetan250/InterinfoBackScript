package bean;

public class CustomsMonthBean {
	private String border;
	private String year;
	private String month;
	private int MONTO;
	
	public String getBorder() {
		return border;
	}
	public void setBorder(String border) {
		this.border = border;
	}
	public String getYear() {
		return year;
	}
	public String getMonth() {
		return month;
	}
	public int getMONTO() {
		return MONTO;
	}

	public void setYear(String year) {
		this.year = year;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public void setMONTO(int mONTO) {
		MONTO = mONTO;
	}
	
	
}
