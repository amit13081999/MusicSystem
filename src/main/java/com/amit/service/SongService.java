package com.amit.service;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.amit.entity.Songs;
import com.amit.entity.Users;
import com.amit.utility.RandomUtility;
import com.amit.utility.SessionUtility;

public class SongService {
	private PlayListService playListService;
	private PlayListSongService playListSongService;
	private Scanner sc;

	public SongService() {
		this.playListService = new PlayListService();
		this.sc = new Scanner(System.in);
		this.playListSongService = new PlayListSongService();
	}

	private static final String GET_ALL_SONGS = "from Songs as s where s.user=:user";
	private static final String SONG_BY_NAME = "from Songs as s where s.name=:sName and s.user=:user";
	private static final String DELETE_BY_SONG_NAME = "delete from Songs as s where s.user=:user and s.name=:sName";
	public static final String GET_SONGS_IN_LIST = "from Songs as s where s.name in (:list) and s.user=:user";

	public void home(Users u) {
		System.out.println("");
		System.out.println("logged in successfully");

		while (true) {
			int ch;
			System.out.println();
			System.out.println("1 -> All Songs");
			System.out.println("2 -> Add Song");
			System.out.println("3 -> Update Song");
			System.out.println("4 -> Delete Song");
			System.out.println("5 -> Playlist");
			System.out.println("6 -> Log Out");
			ch = sc.nextInt();

			switch (ch) {
			case 1:
				allSongs(u);
				break;

			case 2:
				addSong(u);
				break;

			case 3:
				updateSong(u);
				break;

			case 4:
				deleteSong(u);
				break;

			case 5:
				playListService.home(u);
				break;

			case 6:
				System.out.println("logging out...");
				return;

			default:
				System.out.println("Invalid choice..");
			}
		}

	}

	private void deleteSong(Users u) {
		Session session = SessionUtility.getSession();

		System.out.println("Enter song to be deleted :");
		String sName = sc.next();

		Query<Songs> p = session.createQuery(SONG_BY_NAME, Songs.class);
		p.setParameter("user", u);
		p.setParameter("sName", sName);
		Songs pp = p.uniqueResult();

		if (pp == null) {
			System.out.println("No such song exists!!");

		} else {
			playListSongService.deleteBySong(pp);
			Transaction tx = session.beginTransaction();

			Query<Songs> q = session.createQuery(DELETE_BY_SONG_NAME);
			q.setParameter("sName", sName);
			q.setParameter("user", u);
			q.executeUpdate();
			tx.commit();

			System.out.println(sName + " deleted");
		}
		session.close();
	}

	private void updateSong(Users u) {
		System.out.println("Enter song name needed to be updated :");
		String songToBeUpdated = sc.next();

		Session session = SessionUtility.getSession();

		Query<Songs> p = session.createQuery(SONG_BY_NAME, Songs.class);
		p.setParameter("user", u);
		p.setParameter("sName", songToBeUpdated);
		Songs pp = p.uniqueResult();

		if (pp == null) {
			System.out.println("No such song exists!!");

		} else {
			System.out.println("Enter new-song name :");
			String newName = sc.next();

			Query<Songs> p1 = session.createQuery(SONG_BY_NAME, Songs.class);
			p1.setParameter("user", u);
			p1.setParameter("sName", newName);
			Songs pq = (Songs) p1.uniqueResult();

			if (pq != null) {
				System.out.println("Song with this name already present. !!");

			} else {

				Transaction tx = session.beginTransaction();

				pp.setName(newName);
				session.save(pp);

				tx.commit();

			}

		}
		session.close();
	}

	private void addSong(Users u) {
		Session session = SessionUtility.getSession();

		System.out.println("Enter song name :");
		String sName = sc.next();

		Query<Songs> p = session.createQuery(SONG_BY_NAME, Songs.class);
		p.setParameter("user", u);
		p.setParameter("sName", sName);
		Songs pp = (Songs) p.uniqueResult();

		if (pp != null) {
			System.out.println("Song Already exists by this name !!");

		} else {
			Transaction tx = session.beginTransaction();

			String uniqueName = RandomUtility.getUniqueName();
			Songs song = new Songs(sName, uniqueName, u);

			session.save(song);
			tx.commit();

		}
		session.close();

	}

	private void allSongs(Users u) {
		Session session = SessionUtility.getSession();

		List<Songs> q = session.createQuery(GET_ALL_SONGS, Songs.class).setParameter("user", u).list();

		if (q.isEmpty()) {
			System.out.println("0 songs!!");

		} else {
			for (Songs s : q) {
				System.out.println(s);
			}

		}
	}

}
