package com.amit.service;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.amit.entity.Users;
import com.amit.utility.RandomUtility;
import com.amit.utility.SessionUtility;

public class UserService {

	private Scanner sc;

	public UserService() {
		this.sc = new Scanner(System.in);
	}

	private static final String USER_BY_NAME = "from Users as u where u.name=:name";

	public void loginUser() {
		System.out.println("Enter your name :");
		String name = sc.next();

		System.out.println("Enter your password :");
		String password = sc.next();

		Session session = SessionUtility.getSession();

		Users user = session.createQuery(USER_BY_NAME, Users.class).setParameter("name", name).uniqueResult();
		session.close();

		if (Objects.isNull(user)) {
			System.out.println("no user exists");
		} else if (user.getPassword().equals(password)) {
			new SongService().home(user);
		} else {
			System.out.println("Incorrect password !!");
		}

	}

	public void registerUser() {
		System.out.println("Enter your name :");
		String name = sc.next();

		Session session = SessionUtility.getSession();

		Users u = session.createQuery(USER_BY_NAME, Users.class).setParameter("name", name).uniqueResult();

		if (!Objects.isNull(u)) {
			System.out.println("Name already exists, Registration failed");

		} else {
			System.out.println("Enter your password :");
			String password = sc.next();

			String uniqueName = RandomUtility.getUniqueName();
			Users user = new Users(name, uniqueName, password);

			Transaction tx = session.beginTransaction();
			session.save(user);
			tx.commit();

			System.out.println("Registration Successful");
		}
		session.close();

	}

	public void getAllUsers() {
		Session session = SessionUtility.getSession();
		List<Users> l1 = session.createQuery("from Users", Users.class).list();

		if (l1.isEmpty()) {
			System.out.println("0 users!!");

		} else {
			for (Users users : l1)
				System.out.println(users.getName());

		}
		session.close();
	}
}
