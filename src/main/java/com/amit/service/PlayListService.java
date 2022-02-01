package com.amit.service;

import java.util.Scanner;

import com.amit.repository.PlayListRepository;
import com.amit.repository.PlayListSongRepository;

public class PlayListService {

	PlayListSongRepository playListSongRepository = new PlayListSongRepository();
	PlayListRepository playListRepository = new PlayListRepository();

	public void home(Long uId) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			int ch;
			System.out.println("1 -> all Playlists");
			System.out.println("2 -> Create Playlist");
			System.out.println("3 -> Update Meta data of a Playlist");
			System.out.println("4 -> Delete Playlist");
			System.out.println("5 -> View PlayList");
			System.out.println("6 -> Add Song in PlayList");
			System.out.println("7 -> Delete song in PlayList");
			System.out.println("10 -> Back");
			ch = sc.nextInt();

			switch (ch) {

			case 1:
				playListRepository.allPlayLists(uId);
				break;

			case 2:
				System.out.println("Enter PlayList name :");
				String pName = sc.next();
				playListRepository.createPlayList(pName, uId);
				break;

			case 3:
				System.out.println("Enter PlayList(Name) needed to be updated :");
				String playListToBeUpdated = sc.next();
				System.out.println("Enter new-PlayList(Name) :");
				String newName = sc.next();
				playListRepository.updatePlaylist(uId, playListToBeUpdated, newName);
				break;

			case 4:
				System.out.println("Enter Playlist to be deleted :");
				String pName1 = sc.next();
				playListRepository.deletePlayList(uId, pName1);
				break;

			case 5:
				System.out.println("Enter PlayList name :");
				String pName2 = sc.next();
				playListSongRepository.viewPlayList(pName2, uId);
				break;

			case 6:
				System.out.println("Choose Playlist  :");
				String pListName = sc.next();
				System.out.println("Enter Song to be added :");
				String sName = sc.next();
				playListSongRepository.addSong(uId, sName, pListName);
				break;
				
			case 7:
				System.out.println("Enter PlayList name :");
				String pName3 = sc.next();
				System.out.println("Enter Song to be deleted :");
				String sName3 = sc.next();
				playListSongRepository.deleteFromPlayList(pName3,sName3, uId);
				break;
				
			case 10:
				return;

			default:
				System.out.println("Invalid choice..");
			}
		}

	}

}
