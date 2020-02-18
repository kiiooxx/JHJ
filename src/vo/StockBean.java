package vo;

public class StockBean {
	private String stock_num;
	private String pro_det_num;
	private int stock_qnt;
	private String stock_date;
	private int out_stock_qnt;
	
	
	
	public int getOut_stock_qnt() {
		return out_stock_qnt;
	}
	public void setOut_stock_qnt(int out_stock_qnt) {
		this.out_stock_qnt = out_stock_qnt;
	}
	public String getStock_num() {
		return stock_num;
	}
	public void setStock_num(String stock_num) {
		this.stock_num = stock_num;
	}
	public String getPro_det_num() {
		return pro_det_num;
	}
	public void setPro_det_num(String pro_det_num) {
		this.pro_det_num = pro_det_num;
	}
	public int getStock_qnt() {
		return stock_qnt;
	}
	public void setStock_qnt(int stock_qnt) {
		this.stock_qnt = stock_qnt;
	}
	public String getStock_date() {
		return stock_date;
	}
	public void setStock_date(String stock_date) {
		this.stock_date = stock_date;
	}
	
}
