package com.amit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	@Column(name = "unique_name",unique = true)
	private String uniqueName;

	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Songs> songs;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<PlayList> playLists;

	public Users() {
		// TODO Auto-generated constructor stub
	}

	public Users(Long id, String name, String uniqueName, String password, List<Songs> songs,
			List<PlayList> playLists) {
		super();
		this.id = id;
		this.name = name;
		this.uniqueName = uniqueName;
		this.password = password;
		this.songs = songs;
		this.playLists = playLists;
	}

	

	public Users(String name, String uniqueName, String password) {
		super();
		this.name = name;
		this.uniqueName = uniqueName;
		this.password = password;
	}

	@Override
	public String toString() {
		return "[ name = " + name + ", uniqueName = " + uniqueName + ", password = " + password + "]";
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

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Songs> getSongs() {
		return songs;
	}

	public void setSongs(List<Songs> songs) {
		this.songs = songs;
	}

	public List<PlayList> getPlayLists() {
		return playLists;
	}

	public void setPlayLists(List<PlayList> playLists) {
		this.playLists = playLists;
	}

}
