package com.amit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.amit.entity.PlayList;
import com.amit.entity.PlayListSongs;
import com.amit.entity.Songs;
import com.amit.entity.Users;
import com.amit.utility.SessionUtility;

public class PlayListService {
	private PlayListSongService playListSongService;
	private Scanner sc;

	public PlayListService() {

		this.sc = new Scanner(System.in);
		this.playListSongService = new PlayListSongService();
	}

	public static final String GET_ALL_PLAYLIST = "from PlayList as p where p.user=:user";
	public static final String PLAYLIST_BY_NAME = "from PlayList as p where p.pName=:pName and p.user=:user";

	public void home(Users u) {
		while (true) {
			int ch;
			System.out.println("1 -> all Playlists");
			System.out.println("2 -> create Playlists");
			System.out.println("3 -> Update   Playlist");
			System.out.println("4 -> Delete Playlist");
			System.out.println("5 -> View PlayList");
			System.out.println("6 -> Back");
			ch = sc.nextInt();

			switch (ch) {

			case 1:
				allPlayLists(u);
				break;

			case 2:
				createPlayList(u);
				break;
	

			case 6:
				return;

			default:
				System.out.println("Invalid choice..");
			}
		}

	}

	private void createPlayList(Users u) {
		System.out.println("Enter PlayList name :");
		String pName = sc.next();

		Session session = SessionUtility.getSession();
		Transaction tx = session.beginTransaction();

		Query<PlayList> p = session.createQuery(PLAYLIST_BY_NAME, PlayList.class);
		p.setParameter("user", u);
		p.setParameter("pName", pName);
		PlayList pp = (PlayList) p.uniqueResult();
		if (pp != null) {
			System.out.println("PlayList Already exists by this name !!");

		} else {
			PlayList playList = new PlayList(pName, u);
			
			System.out.println("No. of Songs you want to add: ");
			int n = sc.nextInt();
			sc.next();
			List<String> songList = new ArrayList();
			for (int i = 0; i < n; i++) {
				songList.add(sc.nextLine());
			}

			Query<Songs> qqq = session.createQuery(SongService.GET_SONGS_IN_LIST, Songs.class);
			qqq.setParameter("list", songList);
			qqq.setParameter("user", u);
			List<Songs> songs = qqq.list();
			if (songs.isEmpty())
				return;
			List<PlayListSongs> playListSongs = new ArrayList();
			for (Songs song : songs) {
				playListSongs.add(new PlayListSongs(song,playList));
			}
			playList.setPlayListSongs(playListSongs);

			session.save(playList);
			tx.commit();
			session.close();
		}

	}

	private void allPlayLists(Users u) {
		Session session = SessionUtility.getSession();

		List<PlayList> q = session.createQuery(GET_ALL_PLAYLIST, PlayList.class).setParameter("user", u).list();

		if (q.isEmpty()) {
			System.out.println("Zero PlayLists !! Go to create PlayList to create new playlist....");

		} else {

			for (PlayList p : q) {
				System.out.println(p.getName());
			}
		}
		session.close();
	}

}
