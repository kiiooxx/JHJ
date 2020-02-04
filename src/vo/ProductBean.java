package vo;

public class ProductBean {
	private String pro_num;
	private String pro_detail;
	private String pro_name;
	private String category;
	private String sub_category;
	private int pro_price;
	private char main_nb;
	private char active;
	private String pro_date;
	
	public String getPro_num() {
		return pro_num;
	}
	public void setPro_num(String pro_num) {
		this.pro_num = pro_num;
	}
	public String getPro_detail() {
		return pro_detail;
	}
	public void setPro_detail(String pro_detail) {
		this.pro_detail = pro_detail;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSub_category() {
		return sub_category;
	}
	public void setSub_category(String sub_category) {
		this.sub_category = sub_category;
	}
	public int getPro_price() {
		return pro_price;
	}
	public void setPro_price(int pro_price) {
		this.pro_price = pro_price;
	}
	public char getMain_nb() {
		return main_nb;
	}
	public void setMain_nb(char main_nb) {
		this.main_nb = main_nb;
	}
	public char getActive() {
		return active;
	}
	public void setActive(char active) {
		this.active = active;
	}
	public String getPro_date() {
		return pro_date;
	}
	public void setPro_date(String pro_date) {
		this.pro_date = pro_date;
	}
	
}
