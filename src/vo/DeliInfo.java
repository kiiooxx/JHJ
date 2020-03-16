package vo;

public class DeliInfo {
	
	private String deli_num;
	private String deli_postcode;
	private String deli_addr1;
	private String deli_addr2;
	private String deli_content;
	private String rec_name; //수령인 이름
	private String rec_tel; //수령인 전화번호
	private String user_id; //주문인 아이디
	
	public String getDeli_num() {
		return deli_num;
	}
	public void setDeli_num(String deli_num) {
		this.deli_num = deli_num;
	}
	public String getDeli_postcode() {
		return deli_postcode;
	}
	public void setDeli_postcode(String deli_postcode) {
		this.deli_postcode = deli_postcode;
	}
	public String getDeli_addr1() {
		return deli_addr1;
	}
	public void setDeli_addr1(String deli_addr1) {
		this.deli_addr1 = deli_addr1;
	}
	public String getDeli_addr2() {
		return deli_addr2;
	}
	public void setDeli_addr2(String deli_addr2) {
		this.deli_addr2 = deli_addr2;
	}
	public String getDeli_content() {
		return deli_content;
	}
	public void setDeli_content(String deli_content) {
		this.deli_content = deli_content;
	}
	public String getRec_name() {
		return rec_name;
	}
	public void setRec_name(String rec_name) {
		this.rec_name = rec_name;
	}
	public String getRec_tel() {
		return rec_tel;
	}
	public void setRec_tel(String rec_tel) {
		this.rec_tel = rec_tel;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
