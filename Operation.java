package EIRM14S2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;

public class Operation {
	private ArrayList<Product> productList = new ArrayList<Product>();
	private ArrayList<Product> discard = new ArrayList<Product>();
	private ArrayList<Product> soldList = new ArrayList<Product>();
	private ArrayList<Product> ROA = new ArrayList<Product>();
	private ArrayList<Product> queryList = new ArrayList<Product>();
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	private Validation validation = new Validation();

	public Operation(ArrayList<Product> productList) {
		this.productList = productList;
	}
	/**
	 * Sell a certain quantity of a special type of product
	 * @param details
	 * @throws ParseException
	 */
	public void sell(String selldetails) {
		// TODO Auto-generated method stub
		String name = "", soldon = "", soldat = "", quantity = "";
		String[] string = selldetails.split(";");
		ArrayList<Product> temppro = new ArrayList<Product>();
		int tempquantity = 0, quantityAva = 0;
		double profit = 0, tempsoldat = 0;
		boolean expire = true;
		// create a  temporary to store the sorted products info
		for (Product pro : productList) {
			temppro.add(pro);
		}
		temppro = sortbyuseby(temppro);

		for (int i = 0; i < string.length; i++) {
			Scanner eachString = new Scanner(string[i]);
			String title = eachString.next();
			if (title.equalsIgnoreCase("product")) {
				name = eachString.nextLine().trim();
			}
			if (title.equalsIgnoreCase("soldon")) {
				soldon = eachString.nextLine().trim();
			}
			if (title.equalsIgnoreCase("soldat")) {
				soldat = eachString.nextLine().trim();
			}
			if (title.equalsIgnoreCase("quantity")) {
				quantity = eachString.nextLine().trim();
			}
		}
		//Calculate the available stocks for each product and whether
		//the current product is expired
		if (validation.isVaildName(name) && validation.isValidPrice(soldat) && validation.isValidDate(soldon) && validation.isValidQuantity(quantity)
				&& !productList.isEmpty()){
			tempquantity = validation.quantity(quantity);
			tempsoldat = validation.price(soldat);
		for (Product pro : productList) {
			Date soldon_date = null, useby_date = null, boughton_date = null;
			try {
				if (name.equalsIgnoreCase(pro.getName())) {
					soldon_date = format.parse(soldon);
					useby_date = format.parse(pro.getUseby());
					boughton_date = format.parse(pro.getBoughton());
					if ((soldon_date.getTime() - useby_date.getTime()) / (24 * 60 * 60 * 1000) - 1 < 0) {
						expire = false;
					}
					if ((soldon_date.getTime() - useby_date.getTime()) / (24 * 60 * 60 * 1000) - 1 < 0
							&& soldon_date.getTime() - boughton_date.getTime() > 0) {
						quantityAva += pro.getQuantity();
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// if there are sufficient available stocks, do sell method
		if (!expire && (quantityAva >= tempquantity)) {
			boolean beforeuseby = false, afterboughton = false;
			for (Product pro : temppro) {
				try {
					beforeuseby = (format.parse(soldon).getTime() - format.parse(pro.getUseby()).getTime()) / (24 * 60 * 60 * 1000) - 1 < 0;
					afterboughton = (format.parse(soldon).getTime() - format.parse(pro.getBoughton()).getTime()) / (24 * 60 * 60 * 1000) - 1 > 0;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (name.equalsIgnoreCase(pro.getName()) && beforeuseby
						&& afterboughton) {
					if (pro.getQuantity() - tempquantity <= 0) {
						profit = pro.getQuantity() * (tempsoldat - pro.getBoughtat());
						tempquantity = tempquantity - pro.getQuantity();
						soldList.add(new Product(pro.getName(), pro.getBoughton(), soldon, pro.getQuantity(), profit));
						pro.setQuantity(0);
						pro.setSoldat(tempsoldat);
						pro.setSoldon(soldon);
					} else {
						profit = tempquantity * (tempsoldat - pro.getBoughtat());
						pro.setQuantity(pro.getQuantity() - tempquantity);
						soldList.add(new Product(pro.getName(), pro.getBoughton(), soldon, tempquantity, profit));
						tempquantity = 0;
						pro.setSoldat(tempsoldat);
						pro.setSoldon(soldon);
						}
					}
				}
			}
			for (Iterator<Product> it = soldList.iterator(); it.hasNext();) {
				Product pro = it.next();
				if (pro.GetProfit() == 0)
					it.remove();
			}
		}
	}

	/**
	 * Buy a certain quantity of products and add all the products into stock
	 * if all the value format are correct
	 * @param buydetails
	 */
	public void buy(String buydetails) {
		// TODO Auto-generated method stub
		String name = "", boughton = "", useby = "31-12-2055", boughtat = "", quantity = "";
		boolean exist = false;
		ArrayList<Product> temppro = new ArrayList<Product>();
		String[] string;
		string = buydetails.split(";");
		for (int i = 0; i < string.length; i++) {
			Scanner eachString = new Scanner(string[i]);
			String title = eachString.next();
			if (title.equalsIgnoreCase("product")) {
				name = eachString.nextLine().trim();
			}
			if (title.equalsIgnoreCase("boughton")) {
				boughton = eachString.nextLine().trim();
			}
			if (title.equalsIgnoreCase("useby")) {
				useby = eachString.nextLine().trim();
			}
			if (title.equalsIgnoreCase("boughtat")) {
				boughtat = eachString.nextLine().trim();
			}
			if (title.equalsIgnoreCase("quantity")) {
				quantity = eachString.nextLine().trim();
			}
		}
		// if all the value format are correct, add the record to
		// the original Product ArrayList
		if (validation.isVaildName(name) && validation.isValidQuantity(quantity) && validation.isValidPrice(boughtat)
				&& validation.isValidDate(boughton)
				&& validation.isValidDate(useby)) {
			if (!productList.isEmpty()) {
				for (Product pro : productList) {
					if (name.equalsIgnoreCase(pro.getName()) && boughton.equalsIgnoreCase(pro.getBoughton())
							&& useby.equalsIgnoreCase(pro.getUseby()) && validation.price(boughtat) == pro.getBoughtat()) {
						pro.setQuantity(validation.quantity(quantity) + pro.getQuantity());
						exist = true;
					}
				}
			}
			if (!exist) {
				temppro.add(new Product(name, boughton, validation.price(boughtat), 0, "00-00-0000", useby, validation.quantity(quantity)));
			}
			productList.addAll(temppro);
		}
	}
	/**
	 * Discard the expired product items
	 * @param discarddetails
	 */
	public void discard(String discarddetails) {
		// TODO Auto-generated method stub
		Date discard_date = null, discard_useby = null;
		if (validation.isValidDate(discarddetails) && !productList.isEmpty()){
		for (Product pro : productList) {
			try {
				discard_date = format.parse(pro.getUseby());
				discard_useby = format.parse(discarddetails);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// add the discarded items into Discard ArrayList, and set the quantiy in 
			// in the original ArrayList to 0
			if ((discard_date.getTime() - discard_useby.getTime()) / (24 * 60 * 60 * 1000) - 1 <= 0) {
				discard.add(new Product(pro.getName(), pro.getBoughton(), pro.getBoughtat(),
						                pro.getUseby(), pro.getQuantity(),discarddetails));
				pro.setQuantity(0);
				}
			}
		}
	}
	/**
	 * query certain information from the collection based on field values
	 * (bestsales/worstsales,profit)
	 * @param querydetails
	 * @param reportfile
	 */

	public void query(String querydetails, String reportfile) {
		// TODO Auto-generated method stub
		String[] string = querydetails.split(" ");
		// Calculate the best and worst sale
		if (string[0].equalsIgnoreCase("bestsales") || string[0].equalsIgnoreCase("worstsales")) {
			if (validation.isValidDate(string[1]) && validation.isValidDate(string[2]) && !productList.isEmpty()){
			HashMap<String, Double> sale = new HashMap<String, Double>();
			ROA_Comparator compare = new ROA_Comparator(sale);
			TreeMap<String, Double>  bestandworst = new TreeMap<String, Double>(compare);
			int beginquantity = 0, endquantity = 0;
			double profit = 0;
			Date start_date = null, end_date = null, valid_date = null, soldon_date = null;
			for (Product pro : productList) {
				try {
					start_date = format.parse(string[1]);
					end_date = format.parse(string[2]);
					valid_date = format.parse(pro.getBoughton());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(valid_date.before(end_date)){
					if (valid_date.before(start_date)) {
						boolean exist_current = false;
						for (Product sold: soldList){
							try {
								soldon_date = format.parse(sold.getSoldon().get(0));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(pro.getName().equalsIgnoreCase(sold.getName()) && pro.getBoughton().equalsIgnoreCase(sold.getBoughton())
									 && soldon_date.before(end_date) && soldon_date.after(start_date)){
								for(String each: sold.getSoldon()){
									if(each.equalsIgnoreCase(sold.getSoldon().get(0))){
										beginquantity += sold.getQuantity();
										profit += sold.GetProfit();
										exist_current = true;
									}
								}		
								endquantity = pro.getQuantity();
								beginquantity += pro.getQuantity();
							} 
						}
						if (!exist_current){
							beginquantity = pro.getQuantity();
							endquantity = pro.getQuantity();
							profit = 0;
						}
						if (!discard.isEmpty()){
						for (Product dis: discard){
							Date discard_date = null;
							try {
								discard_date = format.parse(dis.getDiscard());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(pro.getName().equalsIgnoreCase(dis.getName()) && pro.getBoughton().equalsIgnoreCase(dis.getBoughton())
									&& pro.getBoughtat() == dis.getBoughtat() && pro.getUseby().equalsIgnoreCase(dis.getUseby())
									&& discard_date.after(start_date)){
								beginquantity += dis.getQuantity();
							  } 
						   }
						}
						if (ROA.isEmpty()){
							ROA.add(new Product(pro.getName(), beginquantity, endquantity, profit));
						} else {
							boolean exist = false;
							for (Product roa: ROA){
									if (pro.getName().equalsIgnoreCase(roa.getName())){
										exist = true;
										roa.setBeginquantity(beginquantity + roa.getBeginquantity()); 
										roa.setEndquantity(roa.getEndquantity() + endquantity);
										roa.setProfit(profit + roa.GetProfit());
									}
							}
							if (!exist){
								ROA.add(new Product(pro.getName(), beginquantity, endquantity, profit));
							}
						}
						beginquantity = 0;
						endquantity = 0;
						profit = 0;
					}
					else {
						boolean exist_current = false;
							for (Product sold1: soldList){
								try {
									soldon_date = format.parse(sold1.getSoldon().get(0));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(pro.getName().equalsIgnoreCase(sold1.getName()) && soldon_date.before(end_date) && soldon_date.after(start_date)
										&& pro.getBoughton().equalsIgnoreCase(sold1.getBoughton())){
									for(String each: sold1.getSoldon()){
										if(each.equalsIgnoreCase(sold1.getSoldon().get(0))){
											endquantity = pro.getQuantity();
											profit += sold1.GetProfit();
											exist_current = true;
										}
									}
								} 
							}
							if (!exist_current){
								endquantity = pro.getQuantity();
								profit = 0;
							}
							if (!discard.isEmpty()){
							for (Product dis: discard){
								Date discard_date = null;
								try {
									discard_date = format.parse(dis.getDiscard());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (pro.getName().equalsIgnoreCase(dis.getName()) && pro.getBoughton().equalsIgnoreCase(dis.getBoughton())
								&& pro.getBoughtat() == dis.getBoughtat() && pro.getUseby().equalsIgnoreCase(dis.getUseby())
								&& discard_date.after(end_date)){
									endquantity +=dis.getQuantity();
									}
								}
							}
							if (ROA.isEmpty()){
								ROA.add(new Product(pro.getName(), 0, endquantity, profit));
							} else {
								boolean exist = false;
								for (Product roa: ROA){
									if (pro.getName().equalsIgnoreCase(roa.getName())){
										exist = true;
										roa.setEndquantity(roa.getEndquantity() + endquantity);
										roa.setProfit(profit + roa.GetProfit());
									}
								}
								if (!exist){
									ROA.add(new Product(pro.getName(), 0, endquantity, profit));
								}
							}
							profit = 0;
							endquantity = 0;
						}
					} 
				}
			for (Product roa: ROA){
				double ROA = 0;
				ROA = roa.GetProfit() / ((roa.getBeginquantity() + roa.getEndquantity()) / 2);
				sale.put(roa.getName(), ROA);
			}
			if (string[0].equalsIgnoreCase("bestsales")){
				HashMap<String, Double> best = new HashMap<String, Double>();
				bestandworst.putAll(sale);
				best.put(bestandworst.firstKey(), bestandworst.get(bestandworst.firstKey()));
				for (String name: bestandworst.keySet()){
					if (sale.get(bestandworst.firstKey()) == sale.get(name).doubleValue()){
						best.put(name, sale.get(name));
					}
				}
				saveReportsales(best, reportfile, querydetails);
			} else if (string[0].equalsIgnoreCase("worstsales")){
				HashMap<String, Double> worst = new HashMap<String, Double>();
				bestandworst.putAll(sale);
				worst.put(bestandworst.lastKey(), sale.get(bestandworst.lastKey()));
				for (String name: bestandworst.keySet()){
					if (sale.get(bestandworst.lastKey()) == sale.get(name).doubleValue()){
						worst.put(name, sale.get(name));
						}
					}
					saveReportsales(worst, reportfile, querydetails);
				}
				// Calculate the profit based on a period
			}
		} else if (string[0].equalsIgnoreCase("profit")) {
			if (validation.isValidDate(string[1]) && validation.isValidDate(string[2])){
			double totalprofit = 0, lost = 0;
			Date start_date = null, end_date = null, sold_date = null, discard_date = null;
			if (!soldList.isEmpty()){
					for (Product sold : soldList) {
						try {
							start_date = format.parse(string[1]);
							end_date = format.parse(string[2]);
							sold_date = format.parse(sold.getSoldon().get(0));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (sold_date.getTime() <= end_date.getTime() && sold_date.getTime() >= start_date.getTime()) {
							totalprofit += sold.GetProfit();
						}
					}
				}
			if(!discard.isEmpty()){
			for (Product dis: discard){
				try {
					start_date = format.parse(string[1]);
					end_date = format.parse(string[2]);
					discard_date = format.parse(dis.getDiscard());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (discard_date.after(start_date) && discard_date.before(end_date)){
					lost += dis.getQuantity() * dis.getBoughtat();
					}
				}
			}
				profitReport(reportfile, querydetails, totalprofit, lost);
			}
		} else {
			//query a list of available product items in stock based on the 
			//given date
			if (validation.isValidDate(string[0]) && !productList.isEmpty()){
			Date query_date = null, useby_date = null, boughton_date = null;
			for (Product pro : productList) {
				try {
					query_date = format.parse(string[0]);
					useby_date = format.parse(pro.getUseby());
					boughton_date = format.parse(pro.getBoughton());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (query_date.getTime() - useby_date.getTime() <= 0 && query_date.getTime() - boughton_date.getTime() >= 0){
					if (queryList.isEmpty()){
						queryList.add(new Product(pro.getName(), pro.getQuantity(), pro.getUseby()));
					} else {
						boolean add = false;
						while (!add) {
							for(Product query: queryList){
								if(pro.getName().equalsIgnoreCase(query.getName()) && pro.getUseby().equalsIgnoreCase(query.getUseby())){
									add = true;
									query.setQuantity(pro.getQuantity() + query.getQuantity());
								}
							}
							if(!add){
								queryList.add(new Product(pro.getName(), pro.getQuantity(), pro.getUseby()));
								add = true;
							}
						}
						}
					}
				}
			if (!queryList.isEmpty()){
				queryList = sortbyuseby(queryList);
			}
				sortreport(querydetails, reportfile);
			}
		}
	}
	/**
	 * Sort the records in ascending order based on the value of
	 * a field (useby/boughton/boughtat/quantity/productname)
	 * @param sortdetails
	 */
	public void sort(String sortdetails) {
		// TODO Auto-generated method stub
		// sort the record based on useby
		if (sortdetails.equalsIgnoreCase("useby")) {
			Product min = productList.get(0);
			for (int i = 0; i < productList.size(); i++) {
				for (int j = i; j < productList.size(); j++) {
					try {
						if (format.parse(productList.get(i).getUseby()).getTime() - format.parse(productList.get(j).getUseby()).getTime() > 0) {
							min = productList.get(i);
							productList.set(i, productList.get(j));
							productList.set(j, min);
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		//sort the records based on boughton
		if (sortdetails.equalsIgnoreCase("bougthton")) {
			Product min = productList.get(0);
			for (int i = 0; i < productList.size(); i++) {
				for (int j = i; j < productList.size(); j++) {
					try {
						if (format.parse(productList.get(i).getUseby()).getTime() - format.parse(productList.get(j).getUseby()).getTime() > 0) {
							min = productList.get(i);
							productList.set(i, productList.get(j));
							productList.set(j, min);
						}
					} catch (Exception e) {
						e.getMessage();
					}
				}
			}
		}
		//sort the records based on boughtat
		if (sortdetails.equalsIgnoreCase("boughtat")) {
			Product min = productList.get(0);
			for (int i = 0; i < productList.size(); i++) {
				for (int j = i; j < productList.size(); j++) {
					if (productList.get(i).getBoughtat() > productList.get(j).getBoughtat()) {
						min = productList.get(i);
						productList.set(i, productList.get(j));
						productList.set(j, min);
					}
				}
			}
		}
		//sort the records based on the quantity
		if (sortdetails.equalsIgnoreCase("quantity")) {
			Product min = productList.get(0);
			for (int i = 0; i < productList.size(); i++) {
				for (int j = i; j < productList.size(); j++) {
					if (productList.get(i).getQuantity() > productList.get(j).getQuantity()) {
						min = productList.get(i);
						productList.set(i, productList.get(j));
						productList.set(j, min);
					}
				}
			}
		}
		//sort the records based on the product name
		if (sortdetails.equalsIgnoreCase("product")) {
			Product min = productList.get(0);
			for (int i = 0; i < productList.size(); i++) {
				for (int j = i; j < productList.size(); j++) {
					if (productList.get(i).getName().compareTo(productList.get(j).getName()) > 0) {
						min = productList.get(i);
						productList.set(i, productList.get(j));
						productList.set(j, min);
					}
				}
			}
		}
	}
	/**
	 * Sort the products in ascending order based on useby
	 * @param temppro
	 * @return
	 */
	public ArrayList<Product> sortbyuseby(ArrayList<Product> temppro) {
		Product min = temppro.get(0);
		for (int i = 0; i < temppro.size(); i++) {
			for (int j = i; j < temppro.size(); j++) {
				try {
					if (format.parse(temppro.get(i).getUseby()).getTime() - format.parse(temppro.get(j).getUseby()).getTime() > 0) {
						min = temppro.get(i);
						temppro.set(i, temppro.get(j));
						temppro.set(j, min);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return temppro;
	}
	/**
	 * Sort the products in descending order based on the cost lost
	 * @param temppro
	 * @return
	 */
	public ArrayList<Product> sortbycost(ArrayList<Product> temppro) {
		Product min = temppro.get(0);
		for (int i = 0; i < temppro.size(); i++) {
			for (int j = i; j < temppro.size(); j++) {
					if (temppro.get(i).getQuantity() * temppro.get(i).getBoughtat() - temppro.get(j).getQuantity() * temppro.get(j).getBoughtat() < 0) {
						min = temppro.get(i);
						temppro.set(i, temppro.get(j));
						temppro.set(j, min);
				}
			}
		}
		return temppro;
	}
	/**
	 * Write the product to the result file
	 * @param outputfile
	 */
	public void saveOutput(String outputfile) {
		// TODO Auto-generated method stub
		try {
			File file = new File(outputfile + ".txt");
			PrintWriter out = new PrintWriter(file);
			for (Product pro : productList) {
				if (pro.getQuantity() != 0) {
					out.println("Product         " + pro.getName());
					if (pro.getBoughton().isEmpty()) {

					} else {
						out.println("boughton        " + pro.getBoughton());
					}

					if (!pro.getUseby().equals("31-12-2055")) {
						out.println("useby           " + pro.getUseby());
					}
					if (pro.getBoughtat() == 0) {

					} else {
						out.println("bouthtat        " + "$" + pro.getBoughtat());
					}
					out.println("Quantity        " + pro.getQuantity());
					out.println();
				}
			}
			out.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Write the bestsales or worstsales to the report file
	 * @param ROA
	 * @param report
	 * @param details
	 */
	public void saveReportsales(HashMap<String,Double> ROA, String report, String details) {
		File file = new File(report + ".txt");
		PrintWriter reportout = null;
		try {
			reportout = new PrintWriter(new FileWriter(file,true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			reportout.println("------------------------- query " + details + " --------------------------");
			reportout.println();
			for(String name: ROA.keySet()){
			   reportout.println("Product        " + name);
			   reportout.println("ROA            " + "$" +  Math.round(ROA.get(name) * 100) * 1.0 / 100);
			   reportout.println();
			}
			reportout.println();
			reportout.println("--------------------------- end ----------------------------");
			reportout.println();
		    reportout.close();
	}
	/**
	 * Write a list of available product items into report file
	 * @param details
	 * @param report
	 */
	public void sortreport(String details, String report) {
		try {
			TreeMap<String, Integer> suggest = new TreeMap<String, Integer>();
			String minname = null;
			int minquantity;
			if (!queryList.isEmpty()) {
				for (Product pro : queryList) {
					if (!suggest.containsKey(pro.getName()) && pro.getQuantity() < 10) {
						suggest.put(pro.getName(), 0);
					}
				}

				for (String name : suggest.keySet()) {
					for (Product pro : queryList) {
						if (name.equalsIgnoreCase(pro.getName()))
							suggest.put(name, suggest.get(name) + pro.getQuantity());
					}
				}
			}
			File file = new File(report + ".txt");
			PrintWriter query = new PrintWriter(new FileWriter(file, true));
			query.println("------------------------ query " + details + " ---------------------");
			query.println();
			if (!queryList.isEmpty()) {
				for (Product pro : queryList) {
					if (pro.getQuantity() == 0) {
					} else {
						query.println("Product        " + pro.getName());
						if (!pro.getUseby().equals("31-12-2055")) {
							query.println("useby          " + pro.getUseby());
						}
						query.println("Quantity       " + pro.getQuantity());
					}
					query.println();
				}
			}
			query.println();
			query.println("suggestion:");
			query.println();
			if (!suggest.isEmpty()) {
				minquantity = suggest.get(suggest.firstKey());
				minname = suggest.firstKey();
				for (String name : suggest.keySet()) {
					if (suggest.get(name) < minquantity) {
						minname = name;
					}
				}
				query.println("Product:     "  + minname);
			}
			query.println();
			query.println();
			query.println();
			query.println("---------------------- end ------------------------");
			query.println();
			query.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		queryList = new ArrayList<Product>();
	}
	/**
	 * Write the profit detials into report file
	 * @param report
	 * @param details
	 * @param profit
	 * @param loss
	 */
	public void profitReport (String report, String details, double profit, double loss){
		File file = new File(report + ".txt");
		PrintWriter out = null;
		String[] string = details.split(" ");
		Date start_date = null, end_date = null, discard_date = null;
		try {
			out = new PrintWriter(new FileWriter(file,true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("---------------- query " + details + " --------------------");
		out.println();
		out.println("net income:    " + "$" + Math.round(profit * 100) * 1.0 / 100);
		out.println();
		out.println("loss:          " + "$" + Math.round(loss * 100) * 1.0 / 100);
		out.println();
		out.println("discarded items:  ");
		out.println();
		if (!discard.isEmpty()) {
			discard = sortbycost(discard);
			for (Product pro : discard) {
				try {
					start_date = format.parse(string[1]);
					end_date = format.parse(string[2]);
					discard_date = format.parse(pro.getDiscard());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (discard_date.after(start_date) && discard_date.before(end_date)) {
					if (pro.getQuantity() != 0) {
						out.println("product       " + pro.getName());
						out.println("boughton      " + pro.getBoughton());
						out.println("useby         " + pro.getUseby());
						out.println("boughtat      $" + pro.getBoughtat());
						out.println("quantity      " + pro.getQuantity());
						out.println();
					}
				}
			}
		}
		out.println("---------------------- end ---------------------");
		out.close();
	}
	
	public ArrayList<Product> getProductList(){
		return productList;
	}
	public ArrayList<Product> getSoldList(){
		return soldList;
	}
	public ArrayList<Product> getQueryList(){
		return queryList;
	}
	public ArrayList<Product> getROA(){
		return ROA;
	}
	public ArrayList<Product> getDiscard(){
		return discard;
	}
}
