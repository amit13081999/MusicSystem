package com.amit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class PlayList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@OneToMany(mappedBy = "playList", cascade = CascadeType.ALL)
	private List<PlayListSongs> playListSongs;

	public PlayList() {

		// TODO Auto-generated constructor stub
	}

	public PlayList(String name, Users user) {
		this.name = name;
		this.user = user;
	}

	public PlayList(String name, Users user, List<PlayListSongs> playListSongs) {
		this.name = name;
		this.user = user;
		this.playListSongs = playListSongs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<PlayListSongs> getPlayListSongs() {
		return playListSongs;
	}

	public void setPlayListSongs(List<PlayListSongs> playListSongs) {
		this.playListSongs = playListSongs;
	}

}
