package com.amit.service;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.amit.entity.Users;
import com.amit.repository.SongRepository;

public class SongService {
	PlayListService playListService = new PlayListService();
	SongRepository songRepository = new SongRepository();
	public  void home(Long uId) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Users u = (Users) session.get(Users.class, uId);
		session.close();
		String n = u.getName();
		System.out.println("logged in successfully");
		System.out.println("Hi, " + n);
		Scanner sc = new Scanner(System.in);
		while (true) {
			int ch;
			System.out.println("1 -> All Songs");
			System.out.println("2 -> Add Song");
			System.out.println("3 -> Update Song");
			System.out.println("4 -> Delete Song");
			System.out.println("5 -> Playlist");
			System.out.println("10 -> Log Out");
			ch = sc.nextInt();
			switch (ch) {
			case 1:
				songRepository.allSongs(uId);
				break;
			case 2:
				System.out.println("Enter song name :");
				String sName = sc.next();
				System.out.println("Enter album :");
				String album = sc.next();
				songRepository.addSong(sName, album, uId);
				break;
			case 3:
				System.out.println("Enter song(Name) needed to be updated :");
				String songToBeUpdated = sc.next();
				System.out.println("Enter new-song(Name) :");
				String newName = sc.next();
				System.out.println("Enter new-song(Album) :");
				String newAlbum = sc.next();
				songRepository.updateSong(u,songToBeUpdated,newName,newAlbum);
				break;
			case 4:
				System.out.println("Enter song to be deleted :");
				String sName1 = sc.next();
				songRepository.deleteSong(u, sName1);
				break;
			case 5:
				playListService.home(uId);
				break;
			case 10:
				System.out.println("logging out...");
				return;
			default:
				System.out.println("Invalid choice..");
			}
		}

	}

}
