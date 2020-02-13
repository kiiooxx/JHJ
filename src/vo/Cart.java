package vo;

public class Cart {
   
   private int bas_num;
   private String pro_det_num;//세부상품번호
   private int bas_pro_qnt;//장바구니 상품수량
   private int pro_num;
   
   
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