package bean;

import java.util.ArrayList;

public class MasterBean {
	private ArrayList<HSBean> hsBean;
	private ArrayList<HSMonthBean> hsMonthBean;
	private ArrayList<HSBeanQuarter> hsBeanQuarter;
	private ArrayList<HSBeanSemister> hsBeanSemister;
	private ArrayList<HSMonthBean> hsBeanMonthQty;
	private ArrayList<HSBeanQuarter> hsBeanQuarterQty;
	private ArrayList<HSBeanSemister> hsBeanSemisterQty;
	private ArrayList<MaxicanMonthBean> maxicanMonthBean;
	private ArrayList<MaxicanQuarterBean> maxicanQuarterBean;
	private ArrayList<MaxicanSemisterBean> maxicanSemisterBean;
	private ArrayList<MaxicanMonthBean> maxicanMonthBeanQty;
	private ArrayList<MaxicanQuarterBean> maxicanQuarterBeanQty;
	private ArrayList<MaxicanSemisterBean> maxicanSemisterBeanQty;
	private ArrayList<CustomsMonthBean> customsMonthBean;
	private ArrayList<CustomsQuarterBean> customsQuarterBean;
	private ArrayList<CustomsSemisterBean> customsSemisterBean;
	private ArrayList<CustomsMonthBean> customsMonthBeanQty;
	private ArrayList<CustomsQuarterBean> customsQuarterBeanQty;
	private ArrayList<CustomsSemisterBean> customsSemisterBeanQty;
	private ArrayList<CountryOriginMonthBean> countryOriginMonthBean;
	private ArrayList<CountryOriginQuarterBean> countryOriginQuarterBean;
	private ArrayList<CountryOriginSemisterBean> CountryOriginSemisterBean;
	private ArrayList<CountryOriginMonthBean> countryOriginMonthBeanQty;
	private ArrayList<CountryOriginQuarterBean> countryOriginQuarterBeanQty;
	private ArrayList<CountryOriginSemisterBean> CountryOriginSemisterBeanQty;
	private ArrayList<ForeignMonthBean> foreignMonthBean;
	private ArrayList<ForeignQuarterBean> foreignQuarterBean;
	private ArrayList<ForeignSemBean> foreignSemBean;
	private ArrayList<ForeignMonthBean> foreignMonthBeanQty;
	private ArrayList<ForeignQuarterBean> foreignQuarterBeanQty;
	private ArrayList<ForeignSemBean> foreignSemBeanQty;
	private ArrayList<MexicanCustomPortBean> mexicanCustomPortBean;
	private ArrayList<MexicanCustomPortBean> mexicanCustomPortBeanQty;
	private ArrayList<CustomPortBean> customPortBean;
	private ArrayList<CustomPortBean> customPortBeanQty;
	private ArrayList<MexicanSupplierBean> mexicanSupplierBean;
	private ArrayList<MexicanSupplierBean> mexicanSupplierBeanQty;
	private ArrayList<HSCodeYearBean> HSCodeYearBean;
	private ArrayList<HSCodeYearBean> HSCodeYearBeanQty;
	private ArrayList<MexicanYearDollarBean> MexicanYearBean;
	private ArrayList<MexicanYearDollarBean> MexicanYearBeanQty;
	private ArrayList<CustomsYearDollarBean> CustomsYearBean;
	private ArrayList<CustomsYearDollarBean> CustomsYearBeanQty;
	private ArrayList<CountryYearDollarBean> CountryYearBean;
	private ArrayList<CountryYearDollarBean> CountryYearBeanQty;
	private ArrayList<ForeignYearBean> ForeignYearBean;
	private ArrayList<ForeignYearBean> ForeignYearBeanQty;
	
	
	

	
	public ArrayList<ForeignYearBean> getForeignYearBean() {
		return ForeignYearBean;
	}
	public ArrayList<ForeignYearBean> getForeignYearBeanQty() {
		return ForeignYearBeanQty;
	}
	public void setForeignYearBean(ArrayList<ForeignYearBean> foreignYearBean) {
		ForeignYearBean = foreignYearBean;
	}
	public void setForeignYearBeanQty(ArrayList<ForeignYearBean> foreignYearBeanQty) {
		ForeignYearBeanQty = foreignYearBeanQty;
	}
	public ArrayList<CountryYearDollarBean> getCountryYearBean() {
		return CountryYearBean;
	}
	public ArrayList<CountryYearDollarBean> getCountryYearBeanQty() {
		return CountryYearBeanQty;
	}
	public void setCountryYearBean(ArrayList<CountryYearDollarBean> countryYearBean) {
		CountryYearBean = countryYearBean;
	}
	public void setCountryYearBeanQty(
			ArrayList<CountryYearDollarBean> countryYearBeanQty) {
		CountryYearBeanQty = countryYearBeanQty;
	}
	public ArrayList<CustomsYearDollarBean> getCustomsYearBean() {
		return CustomsYearBean;
	}
	public ArrayList<CustomsYearDollarBean> getCustomsYearBeanQty() {
		return CustomsYearBeanQty;
	}
	public void setCustomsYearBean(ArrayList<CustomsYearDollarBean> customsYearBean) {
		CustomsYearBean = customsYearBean;
	}
	public void setCustomsYearBeanQty(
			ArrayList<CustomsYearDollarBean> customsYearBeanQty) {
		CustomsYearBeanQty = customsYearBeanQty;
	}
	public ArrayList<MexicanYearDollarBean> getMexicanYearBean() {
		return MexicanYearBean;
	}
	public ArrayList<MexicanYearDollarBean> getMexicanYearBeanQty() {
		return MexicanYearBeanQty;
	}
	public void setMexicanYearBean(ArrayList<MexicanYearDollarBean> mexicanYearBean) {
		MexicanYearBean = mexicanYearBean;
	}
	public void setMexicanYearBeanQty(
			ArrayList<MexicanYearDollarBean> mexicanYearBeanQty) {
		MexicanYearBeanQty = mexicanYearBeanQty;
	}
	public ArrayList<MexicanCustomPortBean> getMexicanCustomPortBean() {
		return mexicanCustomPortBean;
	}
	public ArrayList<MexicanCustomPortBean> getMexicanCustomPortBeanQty() {
		return mexicanCustomPortBeanQty;
	}
	public ArrayList<CustomPortBean> getCustomPortBean() {
		return customPortBean;
	}
	public ArrayList<CustomPortBean> getCustomPortBeanQty() {
		return customPortBeanQty;
	}
	public ArrayList<MexicanSupplierBean> getMexicanSupplierBean() {
		return mexicanSupplierBean;
	}
	public ArrayList<MexicanSupplierBean> getMexicanSupplierBeanQty() {
		return mexicanSupplierBeanQty;
	}
	public void setMexicanCustomPortBean(
			ArrayList<MexicanCustomPortBean> mexicanCustomPortBean) {
		this.mexicanCustomPortBean = mexicanCustomPortBean;
	}
	public void setMexicanCustomPortBeanQty(
			ArrayList<MexicanCustomPortBean> mexicanCustomPortBeanQty) {
		this.mexicanCustomPortBeanQty = mexicanCustomPortBeanQty;
	}
	public void setCustomPortBean(ArrayList<CustomPortBean> customPortBean) {
		this.customPortBean = customPortBean;
	}
	public void setCustomPortBeanQty(ArrayList<CustomPortBean> customPortBeanQty) {
		this.customPortBeanQty = customPortBeanQty;
	}
	public void setMexicanSupplierBean(
			ArrayList<MexicanSupplierBean> mexicanSupplierBean) {
		this.mexicanSupplierBean = mexicanSupplierBean;
	}
	public void setMexicanSupplierBeanQty(
			ArrayList<MexicanSupplierBean> mexicanSupplierBeanQty) {
		this.mexicanSupplierBeanQty = mexicanSupplierBeanQty;
	}
	public ArrayList<ForeignMonthBean> getForeignMonthBeanQty() {
		return foreignMonthBeanQty;
	}
	public ArrayList<ForeignQuarterBean> getForeignQuarterBeanQty() {
		return foreignQuarterBeanQty;
	}
	public ArrayList<ForeignSemBean> getForeignSemBeanQty() {
		return foreignSemBeanQty;
	}
	public void setForeignMonthBeanQty(
			ArrayList<ForeignMonthBean> foreignMonthBeanQty) {
		this.foreignMonthBeanQty = foreignMonthBeanQty;
	}
	public void setForeignQuarterBeanQty(
			ArrayList<ForeignQuarterBean> foreignQuarterBeanQty) {
		this.foreignQuarterBeanQty = foreignQuarterBeanQty;
	}
	public void setForeignSemBeanQty(ArrayList<ForeignSemBean> foreignSemBeanQty) {
		this.foreignSemBeanQty = foreignSemBeanQty;
	}
	public ArrayList<ForeignQuarterBean> getForeignQuarterBean() {
		return foreignQuarterBean;
	}
	public ArrayList<ForeignSemBean> getForeignSemBean() {
		return foreignSemBean;
	}
	public void setForeignQuarterBean(
			ArrayList<ForeignQuarterBean> foreignQuarterBean) {
		this.foreignQuarterBean = foreignQuarterBean;
	}
	public void setForeignSemBean(ArrayList<ForeignSemBean> foreignSemBean) {
		this.foreignSemBean = foreignSemBean;
	}
	public ArrayList<ForeignMonthBean> getForeignMonthBean() {
		return foreignMonthBean;
	}
	public void setForeignMonthBean(ArrayList<ForeignMonthBean> foreignMonthBean) {
		this.foreignMonthBean = foreignMonthBean;
	}
	public ArrayList<HSMonthBean> getHsMonthBean() {
		return hsMonthBean;
	}
	public void setHsMonthBean(ArrayList<HSMonthBean> hsMonthBean) {
		this.hsMonthBean = hsMonthBean;
	}
	public ArrayList<CountryOriginMonthBean> getCountryOriginMonthBean() {
		return countryOriginMonthBean;
	}
	public ArrayList<CountryOriginQuarterBean> getCountryOriginQuarterBean() {
		return countryOriginQuarterBean;
	}
	public ArrayList<CountryOriginSemisterBean> getCountryOriginSemisterBean() {
		return CountryOriginSemisterBean;
	}
	public void setCountryOriginMonthBean(
			ArrayList<CountryOriginMonthBean> countryOriginMonthBean) {
		this.countryOriginMonthBean = countryOriginMonthBean;
	}
	public void setCountryOriginQuarterBean(
			ArrayList<CountryOriginQuarterBean> countryOriginQuarterBean) {
		this.countryOriginQuarterBean = countryOriginQuarterBean;
	}
	public void setCountryOriginSemisterBean(
			ArrayList<CountryOriginSemisterBean> countryOriginSemisterBean) {
		CountryOriginSemisterBean = countryOriginSemisterBean;
	}
	public ArrayList<CustomsMonthBean> getCustomsMonthBean() {
		return customsMonthBean;
	}
	public ArrayList<CustomsQuarterBean> getCustomsQuarterBean() {
		return customsQuarterBean;
	}
	public ArrayList<CustomsSemisterBean> getCustomsSemisterBean() {
		return customsSemisterBean;
	}
	public void setCustomsMonthBean(ArrayList<CustomsMonthBean> customsMonthBean) {
		this.customsMonthBean = customsMonthBean;
	}
	public void setCustomsQuarterBean(
			ArrayList<CustomsQuarterBean> customsQuarterBean) {
		this.customsQuarterBean = customsQuarterBean;
	}
	public void setCustomsSemisterBean(
			ArrayList<CustomsSemisterBean> customsSemisterBean) {
		this.customsSemisterBean = customsSemisterBean;
	}
	public ArrayList<MaxicanMonthBean> getMaxicanMonthBean() {
		return maxicanMonthBean;
	}
	public ArrayList<MaxicanQuarterBean> getMaxicanQuarterBean() {
		return maxicanQuarterBean;
	}
	public ArrayList<MaxicanSemisterBean> getMaxicanSemisterBean() {
		return maxicanSemisterBean;
	}
	public void setMaxicanMonthBean(ArrayList<MaxicanMonthBean> maxicanMonthBean) {
		this.maxicanMonthBean = maxicanMonthBean;
	}
	public void setMaxicanQuarterBean(
			ArrayList<MaxicanQuarterBean> maxicanQuarterBean) {
		this.maxicanQuarterBean = maxicanQuarterBean;
	}
	public void setMaxicanSemisterBean(
			ArrayList<MaxicanSemisterBean> maxicanSemisterBean) {
		this.maxicanSemisterBean = maxicanSemisterBean;
	}
	public ArrayList<HSBean> getHsBean() {
		return hsBean;
	}
	public ArrayList<HSBeanQuarter> getHsBeanQuarter() {
		return hsBeanQuarter;
	}
	public ArrayList<HSBeanSemister> getHsBeanSemister() {
		return hsBeanSemister;
	}
	public void setHsBean(ArrayList<HSBean> hsBean) {
		this.hsBean = hsBean;
	}
	public void setHsBeanQuarter(ArrayList<HSBeanQuarter> hsBeanQuarter) {
		this.hsBeanQuarter = hsBeanQuarter;
	}
	public void setHsBeanSemister(ArrayList<HSBeanSemister> hsBeanSemister) {
		this.hsBeanSemister = hsBeanSemister;
	}
	public ArrayList<HSMonthBean> getHsBeanMonthQty() {
		return hsBeanMonthQty;
	}
	public ArrayList<HSBeanQuarter> getHsBeanQuarterQty() {
		return hsBeanQuarterQty;
	}
	public ArrayList<HSBeanSemister> getHsBeanSemisterQty() {
		return hsBeanSemisterQty;
	}
	public void setHsBeanMonthQty(ArrayList<HSMonthBean> hsBeanQty) {
		this.hsBeanMonthQty = hsBeanQty;
	}
	public void setHsBeanQuarterQty(ArrayList<HSBeanQuarter> hsBeanQuarterQty) {
		this.hsBeanQuarterQty = hsBeanQuarterQty;
	}
	public void setHsBeanSemisterQty(ArrayList<HSBeanSemister> hsBeanSemisterQty) {
		this.hsBeanSemisterQty = hsBeanSemisterQty;
	}
	public ArrayList<MaxicanMonthBean> getMaxicanMonthBeanQty() {
		return maxicanMonthBeanQty;
	}
	public ArrayList<MaxicanQuarterBean> getMaxicanQuarterBeanQty() {
		return maxicanQuarterBeanQty;
	}
	public ArrayList<MaxicanSemisterBean> getMaxicanSemisterBeanQty() {
		return maxicanSemisterBeanQty;
	}
	public void setMaxicanMonthBeanQty(
			ArrayList<MaxicanMonthBean> maxicanMonthBeanQty) {
		this.maxicanMonthBeanQty = maxicanMonthBeanQty;
	}
	public void setMaxicanQuarterBeanQty(
			ArrayList<MaxicanQuarterBean> maxicanQuarterBeanQty) {
		this.maxicanQuarterBeanQty = maxicanQuarterBeanQty;
	}
	public void setMaxicanSemisterBeanQty(
			ArrayList<MaxicanSemisterBean> maxicanSemisterBeanQty) {
		this.maxicanSemisterBeanQty = maxicanSemisterBeanQty;
	}
	public ArrayList<CustomsMonthBean> getCustomsMonthBeanQty() {
		return customsMonthBeanQty;
	}
	public ArrayList<CustomsQuarterBean> getCustomsQuarterBeanQty() {
		return customsQuarterBeanQty;
	}
	public ArrayList<CustomsSemisterBean> getCustomsSemisterBeanQty() {
		return customsSemisterBeanQty;
	}
	public void setCustomsMonthBeanQty(
			ArrayList<CustomsMonthBean> customsMonthBeanQty) {
		this.customsMonthBeanQty = customsMonthBeanQty;
	}
	public void setCustomsQuarterBeanQty(
			ArrayList<CustomsQuarterBean> customsQuarterBeanQty) {
		this.customsQuarterBeanQty = customsQuarterBeanQty;
	}
	public void setCustomsSemisterBeanQty(
			ArrayList<CustomsSemisterBean> customsSemisterBeanQty) {
		this.customsSemisterBeanQty = customsSemisterBeanQty;
	}
	public ArrayList<CountryOriginMonthBean> getCountryOriginMonthBeanQty() {
		return countryOriginMonthBeanQty;
	}
	public ArrayList<CountryOriginQuarterBean> getCountryOriginQuarterBeanQty() {
		return countryOriginQuarterBeanQty;
	}
	public ArrayList<CountryOriginSemisterBean> getCountryOriginSemisterBeanQty() {
		return CountryOriginSemisterBeanQty;
	}
	public void setCountryOriginMonthBeanQty(
			ArrayList<CountryOriginMonthBean> countryOriginMonthBeanQty) {
		this.countryOriginMonthBeanQty = countryOriginMonthBeanQty;
	}
	public void setCountryOriginQuarterBeanQty(
			ArrayList<CountryOriginQuarterBean> countryOriginQuarterBeanQty) {
		this.countryOriginQuarterBeanQty = countryOriginQuarterBeanQty;
	}
	public void setCountryOriginSemisterBeanQty(
			ArrayList<CountryOriginSemisterBean> countryOriginSemisterBeanQty) {
		CountryOriginSemisterBeanQty = countryOriginSemisterBeanQty;
	}
	public ArrayList<HSCodeYearBean> getHSCodeYearBean() {
		return HSCodeYearBean;
	}
	public void setHSCodeYearBean(ArrayList<HSCodeYearBean> hSCodeYearBean) {
		HSCodeYearBean = hSCodeYearBean;
	}
	public ArrayList<HSCodeYearBean> getHSCodeYearBeanQty() {
		return HSCodeYearBeanQty;
	}
	public void setHSCodeYearBeanQty(ArrayList<HSCodeYearBean> hSCodeYearBeanQty) {
		HSCodeYearBeanQty = hSCodeYearBeanQty;
	}
	
	
}
