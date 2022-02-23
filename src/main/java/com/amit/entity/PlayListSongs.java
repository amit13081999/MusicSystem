package com.amit.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.amit.entity.idClass.PlayListSongsId;

@Entity
@IdClass(PlayListSongsId.class)
public class PlayListSongs {
	@Id
	@ManyToOne
	@JoinColumn(name = "song_id")
	private Songs song;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "playlist_id")
	private PlayList playList;
	
	public PlayListSongs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlayListSongs(Songs song, PlayList playList) {
		this.song = song;
		this.playList = playList;
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
