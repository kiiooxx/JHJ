package vo;

public class Point {
	private int point_num;
	private String point_date;
	private int point_price;
	private int point_final;
	private char increase;
	private String user_id;
	private String point_reason;
	
	public int getPoint_num() {
		return point_num;
	}
	public void setPoint_num(int point_num) {
		this.point_num = point_num;
	}
	public String getPoint_date() {
		return point_date;
	}
	public void setPoint_date(String point_date) {
		this.point_date = point_date;
	}
	public int getPoint_price() {
		return point_price;
	}
	public void setPoint_price(int point_price) {
		this.point_price = point_price;
	}
	public int getPoint_final() {
		return point_final;
	}
	public void setPoint_final(int point_final) {
		this.point_final = point_final;
	}
	public char getIncrease() {
		return increase;
	}
	public void setIncrease(char increase) {
		this.increase = increase;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPoint_reason() {
		return point_reason;
	}
	public void setPoint_reason(String point_reason) {
		this.point_reason = point_reason;
	}

}
