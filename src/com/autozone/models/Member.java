package com.autozone.models;

import com.autozone.annotations.MembersName;
import com.autozone.annotations.NotNull;

public class Member {
	
	private int id;
	@NotNull
	@MembersName
	private String member_name;
	@NotNull
	private String member_id;
	
	public Member(String member_name, String member_id) {
		this.member_name = member_name;
		this.member_id = member_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
}
