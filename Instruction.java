package EIRM14S2;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Instruction {
	private Operation operate;

	public Instruction(ArrayList<Product> productList) {
		// TODO Auto-generated constructor stub
		operate = new Operation(productList);
	}

	public void execute(String instruction, String output, String report) {
		// TODO Auto-generated method stub
		try {
			File instruct = new File(instruction + ".txt");
			Scanner eachLine = new Scanner(instruct);
			String temp;
			while (eachLine.hasNextLine()) {
				temp = eachLine.nextLine();
				Scanner reader = new Scanner(temp);
				if (reader.hasNext()) {
					String tempword = reader.next();
					if (tempword.equalsIgnoreCase("sell")) {
						operate.sell(reader.nextLine().trim());
					}

					if (tempword.equalsIgnoreCase("buy")) {
						operate.buy(reader.nextLine().trim());
					}

					if (tempword.equalsIgnoreCase("discard")) {
						operate.discard(reader.nextLine().trim());
					}

					if (tempword.equalsIgnoreCase("query")) {
						operate.query(reader.nextLine().trim(), report);
					}
					if (tempword.equalsIgnoreCase("sort")) {
						operate.sort(reader.nextLine().trim());
					}
				}
			}
			operate.saveOutput(output);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
