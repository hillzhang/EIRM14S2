package EIRM14S2;

import java.io.File;
import java.util.ArrayList;

public class EIRM {

	public EIRM() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	/**
	 * This is the main method
	 */
	public static void main(String[] args) {
		ArrayList<Product> productList = new ArrayList<Product>();
		// create an object of Inventory class
		Inventory inventory = new Inventory();
		// invoke the Initialization method in Inventory class
		productList = inventory.Initialization(args[0]);
		Instruction instruction = new Instruction(productList);
		// invoke the execute method 
		if (args.length == 4) {
			File currentfile = new File (args[3] + ".txt");
			if(currentfile.exists())
				currentfile.delete();
			instruction.execute(args[1], args[2], args[3]);
		}
	}

}
