package com.passionnambition.util;

import java.util.Comparator;

import org.json.JSONException;
import org.json.JSONObject;

public class AmountComparator<T> implements Comparator<T> {

	
	// If Sorting strings , parse the double value directly. If JSONObject , get the tagValue and then parse
	@Override
	public int compare(T arg0, T arg1) {

		if (arg0 instanceof Double) {
//			double num0 = Double.parseDouble((String) arg0);
//			double num1 = Double.parseDouble((String) arg1);
			if ((Double)arg0 < (Double)arg1)
				return -1;
			if ((Double)arg0 > (Double)arg1)
				return 1;
			else
				return 0;
		}

		else if (arg0 instanceof JSONObject) {
			try {
				String amt0 = ((JSONObject) arg0).getString("tagValue");
				String amt1 = ((JSONObject) arg1).getString("tagValue");
				double num0 = Double.parseDouble(amt0);
				double num1 = Double.parseDouble(amt1);
				if (num0 < num1)
					return -1;
				if (num0 > num1)
					return 1;
				else
					return 0;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return 0;
	}

}
