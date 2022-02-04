package com.amit.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionUtility {
	private static SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	public static Session session;

	public static  Session getSession() {
		if (session == null || !session.isOpen()) {
			session = factory.openSession();
		}
		return session;

	}
}
