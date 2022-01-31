package com.amit.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.amit.entity.Users;

public class UserService {

	SongService songService = new SongService();
	public void loginUser(String name, String password) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		String query = "from Users as u where u.name=:name";
		Users q = (Users) session.createQuery(query).setParameter("name", name).uniqueResult();
		if (q != null && q.getPassword().equals(password)) {
	    	Long uId=q.getuId();
			session.close();
			songService.home(uId);
		} else
			System.out.println("no user exists");
		
	}

	public void registerUser(String name, String password) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		String query = "from Users as u where u.name=:name";
		Users q = (Users) session.createQuery(query).setParameter("name", name).uniqueResult();
		if (q != null) {
			System.out.println("Name already exists, Registration failed");
		} else {
			System.out.println("Registration Successful");
			Users user=new Users();
			user.setName(name);
			user.setPassword(password);
			session.save(user);
			tx.commit();
			session.close();
		}
		
	}

	public  void getAllUsers() {
		SessionFactory factory1 = new Configuration().configure().buildSessionFactory();
		Session session1 = factory1.openSession();
		List<Users> l1 = session1.createQuery("from Users", Users.class).list();
		session1.close();
		for (Users list : l1) {
			System.out.println(list);
		}
		
	}
}
