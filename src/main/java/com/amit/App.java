package com.amit;

import java.util.Scanner;
import com.amit.service.UserService;

public class App {
	public static void main(String[] args) {
		System.out.println("App start..");
		Scanner sc = new Scanner(System.in);
		UserService userService = new UserService();
		while (true) {
			int ch;
			System.out.println();
			System.out.println("1 -> Login");
			System.out.println("2 -> Register");
			System.out.println("3 -> View all users");
			System.out.println("4 -> Exit");
			ch = sc.nextInt();
			switch (ch) {
			case 1:
				userService.loginUser();
				break;
				
			case 2:
				userService.registerUser();
				break;
				
			case 3:
				userService.getAllUsers();
				break;
				
			case 4:
				System.out.println("App closing...");
				return;
				
			default:
				System.out.println("Invalid choice..");
			}

		}

	}
}