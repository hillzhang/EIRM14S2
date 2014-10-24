/**
 * This class used to validate each value's format
 */
package EIRM14S2;

import java.text.SimpleDateFormat;

public class Validation {
	// validate the product name
	public boolean isVaildName(String name) {
		if (name.trim().isEmpty()) {
			return false;
		} else
			return true;
	}

	// validate the quantity
	public boolean isValidQuantity(String quantity) {
		try {
			if (Integer.parseInt(quantity) > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}
    // validate the boughtat
	public boolean isValidPrice(String boughtat) {
		if (!boughtat.startsWith("$"))
			return false;
		else {
			boughtat = boughtat.substring(1).trim();
			try {
				if (Double.parseDouble(boughtat) < 0)
					return false;
				else
					return true;
			} catch (Exception e) {
				return false;
			}
		}
	}
    // validate the date
	public boolean isValidDate(String date) {
		try {
			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat format2 = new SimpleDateFormat("d-M-yyyy");
			SimpleDateFormat format3 = new SimpleDateFormat("dd-M-yyyy");
			SimpleDateFormat format4 = new SimpleDateFormat("d-MM-yyyy");
			if (date.equals(format1.format(format1.parse(date)))
					|| date.equals(format2.format(format2.parse(date)))
					|| date.equals(format3.format(format3.parse(date)))
					|| date.equals(format4.format(format4.parse(date)))) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public double price(String boughtat) {
		double price = Double.parseDouble(boughtat.substring(1));
		return price;
	}

	public int quantity(String tempquantity) {
		int quantity = Integer.parseInt(tempquantity);
		return quantity;
	}
	

}
