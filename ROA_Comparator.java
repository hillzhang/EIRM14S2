/**
 * This class used to sort the product based on the ROA
 */
package EIRM14S2;

import java.util.Comparator;
import java.util.HashMap;


	public class ROA_Comparator implements Comparator<String> {

	HashMap<String, Double> sortMap;

	public ROA_Comparator(HashMap<String, Double> sortMap) {
		this.sortMap = sortMap;
	}

	public int compare(String arg0, String arg1) {

		if (!sortMap.containsKey(arg0) || !sortMap.containsKey(arg1)) {
			return 0;
		}
		if (sortMap.get(arg0) < sortMap.get(arg1))
		{
			return 1;
		}
		else if (sortMap.get(arg0) == sortMap.get(arg1))
		{
			if(arg0.compareTo(arg1) > 0)
				return 1;
			else
				return -1;
		} 
		else 
		{
			return -1;
		}
	}
}
	
	
	
	

	