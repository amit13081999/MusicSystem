package com.amit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PlayListSongs {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private Long id;
	@ManyToOne
	Songs song;
	@ManyToOne
	PlayList playList;
	
	public PlayListSongs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlayListSongs(Songs song, PlayList playList) {
		super();
		this.song = song;
		this.playList = playList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Songs getSong() {
		return song;
	}

	public void setSong(Songs song) {
		this.song = song;
	}

	public PlayList getPlayList() {
		return playList;
	}

	public void setPlayList(PlayList playList) {
		this.playList = playList;
	}
	
	
}
