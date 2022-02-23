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
    
	public void home(String pName2, Long uId) {
//		Scanner sc = new Scanner(System.in);
//		while (true) {
//			int count = playListSongRepository.viewPlayList(pName2, uId);
//			if (count == 0) {
//				return;
//			}
//			int ch;
//			System.out.println("1 -> update playlist name");
//			System.out.println("2 -> Add Song in PlayList");
//			System.out.println("3 -> Delete song in PlayList");
//			System.out.println();
//			ch = sc.nextInt();
//
//			switch (ch) {
//			case 1:
//				System.out.println("Enter Song to be added :");
//				String sName = sc.next();
//				playListSongRepository.addSong(uId, sName, pName2);
//				break;
//
//			case 2:
//				System.out.println("Enter Song to be deleted :");
//				String sName3 = sc.next();
//				int c = playListSongRepository.deleteFromPlayList(pName2, sName3, uId);
//				if (c == 0) {
//					System.out.println("Song already not in  PlayList !!");
//				} else if (c == 1) {
//					System.out.println("Song deleted from PlayList !!");
//				} else {
//					System.out.println("Provide correct playlist name and Song name");
//				}
//				break;
//
//			case 3:
//				return;
//
//			default:
//				System.out.println("Invalid choice...");
//				break;
//			}
//
//		}
	}

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
