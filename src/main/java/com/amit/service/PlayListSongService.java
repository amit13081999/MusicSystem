package com.amit.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.amit.entity.PlayList;
import com.amit.entity.PlayListSongs;
import com.amit.entity.Songs;
import com.amit.entity.Users;
import com.amit.utility.SessionUtility;

public class PlayListSongService {

    public PlayListSongService() {
    	
    }
    
    public static final String DELETE_BY_SONG = "delete from PlayListSongs as p where p.song=:song";
    public static final String DELETE_BY_PLAYLIST = "delete from PlayListSongs as p where p.playList=:playList";
    public static final String All_SONGS = "from PlayListSongs where playList=:playList";
    public static final String DELETE_BY_SONG_PLAYLIST  ="delete from PlayListSongs where song in (:list) and playList=:playList";
    
	public void deleteBySong(Songs pp) {
		Session session = SessionUtility.getSession();
		Transaction tx = session.beginTransaction();
		
		Query<PlayListSongs> qq =  session.createQuery(DELETE_BY_SONG);
		qq.setParameter("song", pp);
		qq.executeUpdate();
		tx.commit();
		
	}

	public void deleteByPlayList(PlayList pp) {
		Session session = SessionUtility.getSession();
		Transaction tx = session.beginTransaction();
		
		Query<PlayListSongs> qq =  session.createQuery(DELETE_BY_PLAYLIST);
		qq.setParameter("playList", pp);
		qq.executeUpdate();
		tx.commit();
		
	}


}
