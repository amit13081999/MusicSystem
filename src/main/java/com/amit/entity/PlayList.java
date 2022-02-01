package com.amit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class PlayList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pId;
	private String pName;
	@ManyToOne
	Users user;
	@OneToMany(mappedBy = "playList",cascade = CascadeType.ALL)
	private List<PlayListSongs> playListSongs;
	
	public PlayList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlayList(Long pId, String pName, Users user) {
		super();
		this.pId = pId;
		this.pName = pName;
		this.user = user;
	}

	@Override
	public String toString() {
		return "PlayList [pId=" + pId + ", pName=" + pName + "]";
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	
}
