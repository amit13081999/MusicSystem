package com.amit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Songs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
   
	@Column(name = "unique_name",unique = true)
	private String uniqueName;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
	private List<PlayListSongs> playListSongs;

	public Songs() {

		// TODO Auto-generated constructor stub
	}

	public Songs( String name, String uniqueName, Users user) {
		super();
		this.name = name;
		this.uniqueName = uniqueName;
		this.user = user;
	}

	@Override
	public String toString() {
		return " name = " + name + ", uniqueName = " + uniqueName ;
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
