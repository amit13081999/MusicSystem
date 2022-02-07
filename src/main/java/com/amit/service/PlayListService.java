package com.amit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
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
	public static final String PLAYLIST_BY_NAME = "from PlayList as p where p.name=:pName and p.user=:user";
	private static final String DELETE_BY_PLAYLIST_NAME = "delete from PlayList as p where p.user=:user and p.name=:pName";

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

			case 3:
				updatePlayList(u);
				break;

			case 4:
				deletePlayList(u);
				break;

			case 5:
				viewPlayList(u);
				break;

			case 6:
				return;

			default:
				System.out.println("Invalid choice..");
			}
		}

	}

	private void updatePlayList(Users u) {
		Session session = SessionUtility.getSession();
		Transaction tx = session.beginTransaction();

		System.out.println("Enter PlayList to be updated :");
		String pName = sc.next();

		Query<PlayList> q = session.createQuery(PLAYLIST_BY_NAME, PlayList.class);
		q.setParameter("pName", pName);
		q.setParameter("user", u);
		PlayList q1 = q.uniqueResult();

		if (q1 == null) {
			System.out.println("No Such PlayList Exists!!");
		} else {
			System.out.println("Change Name :");
			String newName = sc.nextLine();

			if (Objects.nonNull(newName) && !newName.equals("")) {

				Query<PlayList> qq = session.createQuery(PLAYLIST_BY_NAME, PlayList.class);
				qq.setParameter("pName", newName);
				qq.setParameter("user", u);
				PlayList q2 = qq.uniqueResult();

				if (q2 != null) {
					System.out.println("Playlist with given name already Exists!!");
					return;
				} else {
					q1.setName(newName);
					session.save(q1);
				}

			}

			System.out.println("Enter no of songs you want to add");
			int n = sc.nextInt();

			if (n > 0) {
				System.out.println("Enter unique Name of songs");
				List<String> songList = new ArrayList();
				for (int i = 0; i < n; i++) {
					songList.add(sc.next());
				}

				Query<Songs> qqq = session.createQuery(SongService.GET_SONGS_IN_LIST_BY_UNIQUE_NAME, Songs.class);
				qqq.setParameter("list", songList);
				qqq.setParameter("user", u);
				List<Songs> songs = qqq.list();

				if (songs.isEmpty())
					return;

				Query<PlayListSongs> xx = session.createQuery(PlayListSongService.All_SONGS, PlayListSongs.class);
				xx.setParameter("playList", q1);
				List<PlayListSongs> p1 = xx.list();

				for (PlayListSongs song : p1) {
					if (songs.contains(song.getSong())) {
						songs.remove(song.getSong());
					}
				}

				if (songs.isEmpty())
					return;

				for (Songs song : songs) {
					PlayListSongs obj = new PlayListSongs(song, q1);
					session.save(obj);

				}

			}

			System.out.println("Enter no of songs you want to delete");
			int num = sc.nextInt();

			if (num > 0) {
				System.out.println("Enter unique Name of songs");

				List<String> songUserInput = new ArrayList();
				for (int i = 0; i < num; i++) {
					songUserInput.add(sc.next());
				}

				Query<Songs> allSongs = session.createQuery(SongService.GET_SONGS_IN_LIST_BY_UNIQUE_NAME, Songs.class);
				allSongs.setParameter("list", songUserInput);
				allSongs.setParameter("user", u);
				List<Songs> allsongs = allSongs.list();
				
				if(allsongs.isEmpty())
					return;

				Query<PlayListSongs> allSongsInPlayList = session.createQuery(PlayListSongService.All_SONGS,PlayListSongs.class);
				allSongsInPlayList.setParameter("playList", q1);
				List<PlayListSongs> allSongsList = allSongsInPlayList.list();

				for (PlayListSongs p : allSongsList) {
					if (!allsongs.contains(p.getSong())) {
						allsongs.remove(p.getSong());
					}
				}

				if (allsongs.isEmpty()) {
					return;
				}

				Query<PlayListSongs> deleteSongs = session.createQuery("delete from playListSongs where song in (:list) and playList=:playList");
				deleteSongs.setParameter("list", allsongs);
				deleteSongs.setParameter("playList", q1);
				deleteSongs.executeUpdate();

			}

		}
		tx.commit();
		session.close();
	}

	private void viewPlayList(Users u) {
		Session session = SessionUtility.getSession();

		System.out.println("Enter PlayList :");
		String pName = sc.next();

		Query<PlayList> q = session.createQuery(PLAYLIST_BY_NAME, PlayList.class);
		q.setParameter("pName", pName);
		q.setParameter("user", u);
		PlayList q1 = (PlayList) q.uniqueResult();

		if (q1 == null) {
			System.out.println("No Such PlayList Exists!!");
			session.close();
		} else {
			Query<PlayListSongs> p = session.createQuery(PlayListSongService.All_SONGS, PlayListSongs.class);
			p.setParameter("playList", q1);
			List<PlayListSongs> p1 = p.list();
			if (p1.isEmpty()) {
				System.out.println("PlayList Empty !!");
			} else {
				for (PlayListSongs qq : p1) {
					System.out.println(qq.getSong());
				}
			}
			session.close();
		}
	}

	private void deletePlayList(Users u) {
		Session session = SessionUtility.getSession();

		System.out.println("Enter PlayList to be deleted :");
		String pName = sc.next();

		Query<PlayList> p = session.createQuery(PLAYLIST_BY_NAME, PlayList.class);
		p.setParameter("user", u);
		p.setParameter("pName", pName);
		PlayList pp = p.uniqueResult();

		if (Objects.isNull(pp)) {
			System.out.println("No such PlayList exists!!");
		} else {
			playListSongService.deleteByPlayList(pp);

			Transaction tx = session.beginTransaction();
			Query<PlayList> q = session.createQuery(DELETE_BY_PLAYLIST_NAME);
			q.setParameter("pName", pName);
			q.setParameter("user", u);
			q.executeUpdate();
			tx.commit();
			session.close();
		}

	}

	private void createPlayList(Users u) {
		System.out.println("Enter PlayList name :");
		String pName = sc.next();

		Session session = SessionUtility.getSession();

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
			List<String> songList = new ArrayList();
			for (int i = 0; i < n; i++) {
				songList.add(sc.next());
			}

			Query<Songs> qqq = session.createQuery(SongService.GET_SONGS_IN_LIST, Songs.class);
			qqq.setParameter("list", songList);
			qqq.setParameter("user", u);
			List<Songs> songs = qqq.list();
			if (songs.isEmpty())
				return;
			List<PlayListSongs> playListSongs = new ArrayList();
			for (Songs song : songs) {
				playListSongs.add(new PlayListSongs(song, playList));
			}
			playList.setPlayListSongs(playListSongs);

			Transaction tx = session.beginTransaction();
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
