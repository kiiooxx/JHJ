package vo;

public class ProDetBean extends StockBean{
	private String pro_det_num;
	private int pro_num;
	private String pro_size;
	private String color;
	
	public ProDetBean() {
	}
	
	public ProDetBean(String color, String pro_size, int stock) {
		this.color = color;
		this.pro_size = pro_size;
		setStock_qnt(stock);
	}
	
	public String getPro_det_num() {
		return pro_det_num;
	}
	public void setPro_det_num(String pro_det_num) {
		this.pro_det_num = pro_det_num;
	}
	public int getPro_num() {
		return pro_num;
	}
	public void setPro_num(int pro_num) {
		this.pro_num = pro_num;
	}
	public String getPro_size() {
		return pro_size;
	}
	public void setPro_size(String pro_size) {
		this.pro_size = pro_size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
}
