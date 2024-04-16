package com.virtual.gift.card.main;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.virtual.gift.card.handler.RequestHandler;

public class VirtualGiftCardSystem {

	private static Logger LOGGER = LoggerFactory.getLogger(VirtualGiftCardSystem.class);
	
	
	public static void main(String[] args) {
		RequestHandler requestHandler = new RequestHandler();
		Scanner ss = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			LOGGER.info("\n1.Bank \n2.Gift Card \n3.Transaction \n4.Exit");
			int option = ss.nextInt();
			requestHandler.handleOperations(option);
			if (option == 4)
				break;

		}
		ss.close();
	}
}
