package vo;

public class ProductBean extends CategoryBean{
	private int pro_num;
	private String pro_name;
	private int pro_price;
	private String pro_detail;
	private String pro_content;
	private String pro_photo;
	private int cate_num;
	private char active;
	private char main_nb;
	private String pro_date;
	
	
	
	public int getCate_num() {
		return cate_num;
	}
	public void setCate_num(int cate_num) {
		this.cate_num = cate_num;
	}
	public int getPro_num() {
		return pro_num;
	}
	public void setPro_num(int pro_num) {
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
	public String getPro_content() {
		return pro_content;
	}
	public void setPro_content(String pro_content) {
		this.pro_content = pro_content;
	}
	public String getPro_photo() {
		return pro_photo;
	}
	public void setPro_photo(String pro_photo) {
		this.pro_photo = pro_photo;
	}
	
	
	
}
