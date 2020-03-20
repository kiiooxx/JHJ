package vo;

public class BoardBean {
	private int board_num;	// 게시글 시퀀스
	private String board_type;	//게시글 종류 (공지사항, 리뷰게시판, QnA게시판)
	private String board_title;	//게시글 제목
	private String board_writer;	//게시글 작성자
	private String board_content;	//게시글 내용
	private String board_date;	//게시글 작성 시간
	private String board_photo;	//게시글 첨부파일
	private int pro_num;	//상품 번호
	private String sel_num;	//주문 번호
	private String board_step;	//답변 여부
	private String qna_email;	//이메일
	private String qna_type;	//문의 구분
	private String qna_open;	//공개 여부
	private int review_score;	//리뷰 평점
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getBoard_type() {
		return board_type;
	}
	public void setBoard_type(String board_type) {
		this.board_type = board_type;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_writer() {
		return board_writer;
	}
	public void setBoard_writer(String board_writer) {
		this.board_writer = board_writer;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public String getBoard_date() {
		return board_date;
	}
	public void setBoard_date(String board_date) {
		this.board_date = board_date;
	}
	public String getBoard_photo() {
		return board_photo;
	}
	public void setBoard_photo(String board_photo) {
		this.board_photo = board_photo;
	}
	public int getPro_num() {
		return pro_num;
	}
	public void setPro_num(int pro_num) {
		this.pro_num = pro_num;
	}
	public String getSel_num() {
		return sel_num;
	}
	public void setSel_num(String sel_num) {
		this.sel_num = sel_num;
	}
	public String getBoard_step() {
		return board_step;
	}
	public void setBoard_step(String board_step) {
		this.board_step = board_step;
	}
	public String getQna_email() {
		return qna_email;
	}
	public void setQna_email(String qna_email) {
		this.qna_email = qna_email;
	}
	public String getQna_type() {
		return qna_type;
	}
	public void setQna_type(String qna_type) {
		this.qna_type = qna_type;
	}
	public String getQna_open() {
		return qna_open;
	}
	public void setQna_open(String qna_open) {
		this.qna_open = qna_open;
	}
	public int getReview_score() {
		return review_score;
	}
	public void setReview_score(int review_score) {
		this.review_score = review_score;
	}
	
	
	
	
}
