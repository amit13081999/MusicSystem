package com.amit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Songs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sId;
	@Column()
	private String sName;
	private String album;
	@ManyToOne
	private Users user;
	@OneToMany(mappedBy = "song",cascade = CascadeType.ALL)
	private List<PlayListSongs> playListSongs;
	
	public Songs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Songs(Long sId, String sName, String album, Users user) {
		super();
		this.sId = sId;
		this.sName = sName;
		this.album = album;
		this.user = user;
	}

	public Long getsId() {
		return sId;
	}

	public void setsId(Long sId) {
		this.sId = sId;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Songs [sId=" + sId + ", sName=" + sName + ", album=" + album + "]";
	}
	
	
}
