package EIRM14S2;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Inventory {
    private String tempName, tempBoughton, tempUseby;
    private String tempQuantity;
    private String tempBoughtat;
    public ArrayList<Product> productList;
    private Validation validation;
    // the constructor, to initialize the value for each variable
    public Inventory (){
    	tempName = "";
    	tempBoughton = "";
    	tempUseby = "31-12-2055";
    	tempQuantity = "";
    	tempBoughtat = "0";
    	productList = new ArrayList<Product>();
    	validation = new Validation ();
	}
	/**
	 * Used to read the Inventory file and add the records to a ArrayList
	 * 
	 * @param inventory
	 * @return
	 */
	public ArrayList<Product> Initialization(String inventory) {
		// TODO Auto-generated method stub
		try{
			File product = new File (inventory + ".txt");
			Scanner sc = new Scanner (product);
			String reader;
			boolean nameactive = false;
			while (sc.hasNextLine()){
				reader = sc.nextLine ();
				Scanner temp = new Scanner (reader);
				if(!temp.hasNext()){
					if (validation.isVaildName(tempName) && validation.isValidQuantity(tempQuantity)
							&& validation.isValidPrice(tempBoughtat) && validation.isValidDate(tempBoughton)
							&& validation.isValidDate(tempUseby)){
						productList.add(new Product(tempName, tempBoughton, validation.price(tempBoughtat),0,"00-00-0000", tempUseby, validation.quantity(tempQuantity)));
					}
					 tempName ="";
					 tempBoughton ="";
					 tempBoughtat = "0";
					 tempUseby = "31-12-2055";
					 tempQuantity = "";
				}
				else{
					
					String eachWord = temp.next();
					if (eachWord.equalsIgnoreCase("product")){
						tempName = temp.nextLine().trim();
						nameactive = true;
					}
					else if (eachWord.equalsIgnoreCase("quantity")){
						tempQuantity = temp.nextLine().trim();
						nameactive = false;
					}
					else if (eachWord.equalsIgnoreCase("useby")){
						tempUseby = temp.nextLine().trim();
						nameactive = false;
					}
					else if (eachWord.equalsIgnoreCase("boughton")){
						tempBoughton = temp.nextLine().trim();
						nameactive = false;
					}
					else if (eachWord.equalsIgnoreCase("boughtat")){
						tempBoughtat = temp.nextLine().trim();
						nameactive = false;
					}
					else if (nameactive){
						tempName = tempName + " " + eachWord;
					}
				}
			}
			// to read the last record from the original file
			if (validation.isVaildName(tempName) && validation.isValidQuantity(tempQuantity)
					&& validation.isValidPrice(tempBoughtat) && validation.isValidDate(tempBoughton)
					&& validation.isValidDate(tempUseby)){
				productList.add(new Product(tempName, tempBoughton, validation.price(tempBoughtat),0,"00-00-0000", tempUseby, validation.quantity(tempQuantity)));
			}
		}catch(Exception e){
			System.out.println (e.getMessage());
		}
		return productList;
	}
	
}
