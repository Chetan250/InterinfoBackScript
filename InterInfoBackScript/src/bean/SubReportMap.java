package bean;

public class SubReportMap {
	int reportId;
	int subReportId;
	String timeBy;
	String valueBy;
/*	String fromDate;
	String toDate;*/
	public int getReportId() {
		return reportId;
	}
	public int getSubReportId() {
		return subReportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public void setSubReportId(int subReportId) {
		this.subReportId = subReportId;
	}
	public String getTimeBy() {
		return timeBy;
	}
	public String getValueBy() {
		return valueBy;
	}
/*	public String getFromDate() {
		return fromDate;
	}
	public String getToDate() {
		return toDate;
	}*/

	public void setTimeBy(String timeBy) {
		this.timeBy = timeBy;
	}
	public void setValueBy(String valueBy) {
		this.valueBy = valueBy;
	}
/*	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}*/
		
	
}
