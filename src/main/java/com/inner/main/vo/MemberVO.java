package com.inner.main.vo;

public class MemberVO {
	
	private int sm_seq;
	private String sm_name;
	private String sm_phone;
	private String sm_year;
	private String sm_activity_yn;
	private String sm_memo;
	private String sm_delete_yn;
	private int cnt;
	public int getSm_seq() {
		return sm_seq;
	}
	public String getSm_name() {
		return sm_name;
	}
	public String getSm_phone() {
		return sm_phone;
	}
	public String getSm_year() {
		return sm_year;
	}
	public String getSm_activity_yn() {
		return sm_activity_yn;
	}
	public String getSm_memo() {
		return sm_memo;
	}
	public String getSm_delete_yn() {
		return sm_delete_yn;
	}
	public int getCnt() {
		return cnt;
	}
	public void setSm_seq(int sm_seq) {
		this.sm_seq = sm_seq;
	}
	public void setSm_name(String sm_name) {
		this.sm_name = sm_name;
	}
	public void setSm_phone(String sm_phone) {
		this.sm_phone = sm_phone;
	}
	public void setSm_year(String sm_year) {
		this.sm_year = sm_year;
	}
	public void setSm_activity_yn(String sm_activity_yn) {
		this.sm_activity_yn = sm_activity_yn;
	}
	public void setSm_memo(String sm_memo) {
		this.sm_memo = sm_memo;
	}
	public void setSm_delete_yn(String sm_delete_yn) {
		this.sm_delete_yn = sm_delete_yn;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}	

}
