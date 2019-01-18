package NewGUITest;

import java.util.ArrayList;
import java.util.List;

public class Utility {
	
	public Utility() {
		
	}
	
	public static boolean isInteger(String s) {
		System.out.println("UTILITY! isInteger");
		if (s.isEmpty()) return false;
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static int findLord(String lord_name) {
		System.out.println("UTILITY! findLord");
		for (int i=0;i<NationHandler.listoflords.size();i++) {
			if (NationHandler.listoflords.get(i).name.equals(lord_name))
				return i;
		}
		return 0;
	}
	
	public static String[] stringSplitter(String s,String delimiter) {
		System.out.println("UTILITY! stringSplitter");
		if (s.isEmpty()||delimiter.isEmpty()) {
			System.out.println("STRING SPLITTER INPUT INVALID");
			System.out.println("String wished to split: "+s+"\nWith the delimiter: "+delimiter);
			return null;
		}else {
			List<String> outputarray = new ArrayList<String>();
			String k;
			List<String> c = new ArrayList<String>();
			for (int i=0;i<s.length();i++) {
				c.add(String.valueOf(s.charAt(i)));
				if (String.valueOf(s.charAt(i)).equals(delimiter)){
					k = "";
					for (int j=0;j<c.size()-1;j++)
						k = k+c.get(j);
					outputarray.add(k);
					c.clear();
				}
			}
			k = "";
			for (int j=0;j<c.size();j++)
				k = k+c.get(j);
			outputarray.add(k);
			String[] output = new String[outputarray.size()];
			for (int i=0;i<output.length;i++)
				output[i] = outputarray.get(i);
			return output;
		}
	}
}