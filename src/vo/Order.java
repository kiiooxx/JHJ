package vo;

public class Order {
	private String sel_num;
	private String sel_date;
	private String user_id;
	private String deli_num;
	private String sel_status;
	private int deli_price;
	private int point_use;
	private int final_price;
	
	public String getSel_num() {
		return sel_num;
	}
	public void setSel_num(String sel_num) {
		this.sel_num = sel_num;
	}
	public String getSel_date() {
		return sel_date;
	}
	public void setSel_date(String sel_date) {
		this.sel_date = sel_date;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getDeli_num() {
		return deli_num;
	}
	public void setDeli_num(String deli_num) {
		this.deli_num = deli_num;
	}
	public String getSel_status() {
		return sel_status;
	}
	public void setSel_status(String sel_status) {
		this.sel_status = sel_status;
	}
	public int getDeli_price() {
		return deli_price;
	}
	public void setDeli_price(int deli_price) {
		this.deli_price = deli_price;
	}
	public int getPoint_use() {
		return point_use;
	}
	public void setPoint_use(int point_use) {
		this.point_use = point_use;
	}
	public int getFinal_price() {
		return final_price;
	}
	public void setFinal_price(int final_price) {
		this.final_price = final_price;
	} 
	
}
