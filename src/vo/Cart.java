package vo;

public class Cart {
   
   private int bas_num;
   private String pro_det_num;//세부상품번호
   private int bas_pro_qnt;//장바구니 상품수량
   private int pro_num;
   private String pro_name;
   private String pro_photo;
   private int pro_price;
   private String color;
   private String pro_size;
   
   
   public String getPro_name() {
	return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getPro_photo() {
		return pro_photo;
	}
	public void setPro_photo(String pro_photo) {
		this.pro_photo = pro_photo;
	}
	public int getPro_price() {
		return pro_price;
	}
	public void setPro_price(int pro_price) {
		this.pro_price = pro_price;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getPro_size() {
		return pro_size;
	}
	public void setPro_size(String pro_size) {
		this.pro_size = pro_size;
	}
	public int getPro_num() {
		return pro_num;
	}
	public void setPro_num(int pro_num) {
		this.pro_num = pro_num;
	}
	public int getBas_num() {
      return bas_num;
   }
   public void setBas_num(int bas_num) {
      this.bas_num = bas_num;
   }
   public String getPro_det_num() {
      return pro_det_num;
   }
   public void setPro_det_num(String pro_det_num) {
      this.pro_det_num = pro_det_num;
   }
   public int getBas_pro_qnt() {
      return bas_pro_qnt;
   }
   public void setBas_pro_qnt(int bas_pro_qnt) {
      this.bas_pro_qnt = bas_pro_qnt;
   }

}