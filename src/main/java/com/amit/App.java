package com.amit;

import java.util.Scanner;
import java.util.logging.Level;

import com.amit.service.UserService;

public class App {
	public static void main(String[] args) {
		System.out.println("App start..");
		Scanner sc = new Scanner(System.in);	
		UserService userService = new UserService();
		while (true) {
			int ch;
			System.out.println("1 -> Login");
			System.out.println("2 -> Register");
			System.out.println("3 -> View all users");
			System.out.println("4 -> Exit");
			ch = sc.nextInt();
			switch (ch) {
			case 1:
				System.out.println("Enter your name :");
				String name = sc.next();
				System.out.println("Enter your password :");
				String password = sc.next();
				userService.loginUser(name, password);
				break;
			case 2:
				System.out.println("Enter your name :");
				String name2 = sc.next();
				System.out.println("Enter your password :");
				String password2 = sc.next();
				userService.registerUser(name2, password2);
				break;
			case 3:
				userService.getAllUsers();
				break;
			case 4:
				System.out.println("App closing...");
				sc.close();
				return;
			default:
				System.out.println("Invalid choice..");
			}
			
		}
		
	}
}