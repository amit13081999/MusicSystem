package com.amit.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.amit.entity.PlayList;
import com.amit.entity.PlayListSongs;
import com.amit.entity.Songs;
import com.amit.entity.Users;

public class PlayListSongRepository {

	public void addSong(Long uId, String sName, String pListName) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Users u = (Users) session.get(Users.class, uId);

		String query1 = "from PlayList as p where p.user=:user and p.pName=:pName";
		Query<PlayList> p = (Query<PlayList>) session.createQuery(query1);
		p.setParameter("user", u);
		p.setParameter("pName", pListName);
		PlayList p1 = (PlayList) p.uniqueResult();

		String query2 = "from Songs as s where s.user=:user and s.sName=:sName";
		Query<Songs> q = (Query<Songs>) session.createQuery(query2);
		q.setParameter("sName", sName);
		q.setParameter("user", u);
		Songs q1 = (Songs) q.uniqueResult();

		if (p1 != null && q1 != null) {
			PlayListSongs playListSongs = new PlayListSongs(q1, p1);
			session.save(playListSongs);
			tx.commit();
			session.close();

		} else {
			System.out.println("Provide correct playlist name and Song name");
		}

	}

	public void viewPlayList(String pName, Long uId) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Users u = (Users) session.get(Users.class, uId);

		String query2 = "from PlayList  where user=:user and pName=:pName";
		Query<PlayList> q = (Query<PlayList>) session.createQuery(query2);
		q.setParameter("pName", pName);
		q.setParameter("user", u);
		PlayList q1 = (PlayList) q.uniqueResult();

		if (q1 == null) {
			System.out.println("No Such PlayList Exists!!");
		} else {
			String query1 = "from PlayListSongs where playList=:playList";
			Query<PlayListSongs> p = (Query<PlayListSongs>) session.createQuery(query1);
			p.setParameter("playList", q1);
			List<PlayListSongs> p1 = (List<PlayListSongs>) p.list();
			for (PlayListSongs qq : p1) {
				System.out.println(qq.getSong());
			}
			session.close();
		}

	}

	public void deleteFromPlayList(String pName, String sName, Long uId) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Users u = (Users) session.get(Users.class, uId);

		String query1 = "from PlayList as p where p.user=:user and p.pName=:pName";
		Query<PlayList> p = (Query<PlayList>) session.createQuery(query1);
		p.setParameter("user", u);
		p.setParameter("pName", pName);
		PlayList p1 = (PlayList) p.uniqueResult();

		String query2 = "from Songs as s where s.user=:user and s.sName=:sName";
		Query<Songs> q = (Query<Songs>) session.createQuery(query2);
		q.setParameter("sName", sName);
		q.setParameter("user", u);
		Songs q1 = (Songs) q.uniqueResult();

		if (p1 != null && q1 != null) {
			String query123 = "delete from PlayListSongs as p where p.song=:song and p.playList=:playList";
			Query<PlayList> qq = (Query<PlayList>) session.createQuery(query123);
			qq.setParameter("song", q1);
			qq.setParameter("playList", p1);
			int r = qq.executeUpdate();
			tx.commit();
			session.close();
			if (r == 0)
				System.out.println("Song already not in  PlayList !!");
			else
				System.out.println("Song deleted from PlayList !!");

		} else {
			System.out.println("Provide correct playlist name and Song name");
		}

	}

}
