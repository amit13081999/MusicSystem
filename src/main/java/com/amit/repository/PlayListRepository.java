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

public class PlayListRepository {

	public void allPlayLists(Long uId) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Users u = (Users) session.get(Users.class, uId);
		String query = "from PlayList as p where p.user=:user";
		ArrayList<PlayList> q = (ArrayList<PlayList>) session.createQuery(query).setParameter("user", u).list();
		if (q.isEmpty()) {
			System.out.println("Zero PlayLists !! Go to create PlayList to create new playlist....");
		}
		session.close();
		for (PlayList p : q) {
			System.out.println(p);
		}

	}

	public void createPlayList(String pName, Long uId) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Users u = (Users) session.get(Users.class, uId);
		PlayList playList = new PlayList();
		playList.setpName(pName);
		playList.setUser(u);
		List<PlayList> playLists = new ArrayList<PlayList>();
		playLists.add(playList);
		u.setPlayLists(playLists);
		session.save(u);
		tx.commit();
		session.close();
	}

	public void updatePlaylist(Long uId, String playListToBeUpdated, String newName) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Users u = (Users) session.get(Users.class, uId);
		String query = "update PlayList  set pName=:newName where user=:user and pName=:pName";
		Query<PlayList> q = (Query<PlayList>) session.createQuery(query);
		q.setParameter("pName", playListToBeUpdated);
		q.setParameter("user", u);
		q.setParameter("newName", newName);
		int r = q.executeUpdate();
		tx.commit();
		session.close();
		if (r == 0) {
			System.out.println("No such PlayList Exists!!");

		} else {
			System.out.println("PlayList name updated");
		}

	}

	public void deletePlayList(Long uId, String pName) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Users u = (Users) session.get(Users.class, uId);

		String query1 = "from PlayList as p where p.user=:user and p.pName=:pName";
		Query<PlayList> p = (Query<PlayList>) session.createQuery(query1);// .uniqueResult();
		p.setParameter("user", u);
		p.setParameter("pName", pName);
		PlayList pp = (PlayList) p.uniqueResult();
		String query123 = "delete from PlayListSongs as p where p.playList=:playList";
		Query<PlayList> qq = (Query<PlayList>) session.createQuery(query123);
		qq.setParameter("playList", pp);
		qq.executeUpdate();

		PlayList p1 = (PlayList) p.uniqueResult();
		String query = "delete from PlayList as p where p.user=:user and pName=:pName";
		Query<PlayList> q = (Query<PlayList>) session.createQuery(query);
		q.setParameter("pName", pName);
		q.setParameter("user", u);
		int r = q.executeUpdate();
		tx.commit();
		session.close();
		if (r == 0) {
			System.out.println("No such PlayList Exists!!");

		} else {
			System.out.println(r + " PlayList deleted");
		}

	}

}
