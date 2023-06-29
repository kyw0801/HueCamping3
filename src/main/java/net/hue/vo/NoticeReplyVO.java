package net.hue.vo;

import lombok.Data;

@Data
public class NoticeReplyVO {

	private int reply_no;
	private int board_no;
	private String reply_name;
	private String reply_title;
	private String reply_pwd;
	private String reply_cont;
	private int reply_hit;
	private int reply_ref;
	private int reply_step;
	private int reply_level;
	private String reply_date;
}
