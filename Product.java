package EIRM14S2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Product {
	private String name = "", boughton = "", useby = "31-12-2055", discard = "";
	private double boughtat = 0, profit = 0;
	private ArrayList<String> soldon;
	private ArrayList<Double> soldat;
	private int quantity = 0, beginquantity = 0, endquantity = 0 ;
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * used to create object for each product
     * @param name
     * @param boughton
     * @param boughtat
     * @param soldat
     * @param soldon
     * @param useby
     * @param quantity
     */
	public Product(String name, String boughton, double boughtat, double soldat, String soldon, String useby, int quantity) {
		// TODO Auto-generated constructor stub
		this.soldon = new ArrayList<String>();
		this.soldat = new ArrayList<Double>();
		try{
		this.name = name;
		this.useby = format.format(format.parse(useby));
		this.quantity = quantity;
		this.boughton =format.format(format.parse(boughton));
		this.boughtat = boughtat;
		this.soldat.add(soldat);
		this.soldon.add(format.format(format.parse(soldon)));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 * used to create object for each sold record
	 * @param name
	 * @param boughton
	 * @param soldon
	 * @param quantity
	 * @param profit
	 */
	public Product(String name, String boughton,String soldon, int quantity, double profit){
		this.soldon = new ArrayList<String>();
		this.soldat = new ArrayList<Double>();
		try{
		this.name = name;
		this.boughton = boughton;
		this.soldon.add(format.format(format.parse(soldon)));
		this.quantity = quantity;
		this.profit = profit;
		} catch (Exception e){
			System.out.println(e.getLocalizedMessage());
		}
	}
    /**
     * used to create object for each discard item
     * @param name
     * @param boughton
     * @param boughtat
     * @param useby
     * @param quantity
     * @param discard
     */
	public Product(String name, String boughton, double boughtat, String useby, int quantity, String discard) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.useby = useby;
		this.quantity = quantity;
		this.boughton = boughton;
		this.boughtat = boughtat;
		this.discard = discard;
	}
   /**
    * used to create object for each available product item
    * @param name
    * @param quantity
    * @param useby
    */
	public Product(String name, int quantity, String useby) {
		this.name = name;
		this.quantity = quantity;
		this.useby = useby;
	}
	/**
	 * used to create object for each product(for profit purpose)
	 * @param name
	 * @param begin
	 * @param end
	 * @param profit
	 */
	public Product(String name, int begin, int end, double profit){
		this.endquantity = end;
		this.name = name;
		this.profit = profit;
		this.beginquantity = begin;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double GetProfit() {
		return this.profit;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public void setBeginquantity(int begin){
		this.beginquantity= begin;
	}
	public int getBeginquantity (){
		return this.beginquantity;
	}
	public void setEndquantity(int end){
		this.endquantity = end;
	}
	public int getEndquantity(){
		return this.endquantity;
	}

	public void setBoughton(String boughton) {
		this.boughton = boughton;
	}

	public String getBoughton() {
		return this.boughton;
	}

	public void setDiscard(String discard) {
		this.discard = discard;
	}

	public String getDiscard() {
		return this.discard;
	}

	public void setBoughtat(double boughtat) {
		this.boughtat = boughtat;
	}

	public double getBoughtat() {
		return this.boughtat;
	}

	public void setSoldon(String soldon) {
		try {
			this.soldon.add(format.format(format.parse(soldon)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> getSoldon() {
		return this.soldon;
	}

	public void setSoldat(double soldat) {
		this.soldat.add(soldat);
	}

	public ArrayList<Double> getSoldat() {
		return this.soldat;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setUseby(String useby) {
		this.useby = useby;
	}

	public String getUseby() {
		return this.useby;
	}

}
