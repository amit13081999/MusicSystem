package com.amit.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.amit.entity.PlayList;
import com.amit.entity.Songs;
import com.amit.entity.Users;

public class SongRepository {

	public void addSong(String sName, String album, Long uId) {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Songs song = new Songs();
		song.setsName(sName);
		song.setAlbum(album);
		Users u = (Users) session.get(Users.class, uId);
		song.setUser(u);
		List<Songs> songs = new ArrayList<Songs>();
		songs.add(song);
		u.setSongs(songs);
		session.save(u);
		tx.commit();
		session.close();
	}

	public void allSongs(Long uId) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Users user = (Users) session.get(Users.class, uId);
		String query = "from Songs as s where s.user=:user";
		ArrayList<Songs> q = (ArrayList<Songs>) session.createQuery(query).setParameter("user", user).list();
		session.close();
		for (Songs s : q) {
			System.out.println(s);
		}

	}

	public void deleteSong(Users u, String sName) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		String query1 = "from Songs as s where s.user=:user and s.sName=:sName";
		Query<Songs> p = (Query<Songs>) session.createQuery(query1);
		p.setParameter("user", u);
		p.setParameter("sName", sName);
		Songs pp = (Songs) p.uniqueResult();
		String query123 = "delete from PlayListSongs as p where p.song=:song";
		Query<PlayList> qq = (Query<PlayList>) session.createQuery(query123);
		qq.setParameter("song", pp);
		qq.executeUpdate();

		String query = "delete from Songs as s where s.user=:user and s.sName=:sName";
		Query<Songs> q = (Query<Songs>) session.createQuery(query);
		q.setParameter("sName", sName);
		q.setParameter("user", u);
		int r = q.executeUpdate();
		tx.commit();
		session.close();
		if (r == 0) {
			System.out.println("No such song Exists!!");

		} else {
			System.out.println(r + " Song deleted");
		}
	}

	public void updateSong(Users u, String songToBeUpdated, String newName, String newAlbum) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		String query = "update Songs  set sName=:newName,album=:newAlbum  where user=:user and sName=:sName";
		Query<Songs> q = (Query<Songs>) session.createQuery(query);
		q.setParameter("sName", songToBeUpdated);
		q.setParameter("user", u);
		q.setParameter("newName", newName);
		q.setParameter("newAlbum", newAlbum);
		int r = q.executeUpdate();
		tx.commit();
		session.close();
		if (r == 0) {
			System.out.println("No such song Exists!!");

		} else {
			System.out.println(r + " Song updated");
		}

	}

}
