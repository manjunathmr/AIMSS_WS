package com.passionnambition.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;



public class DateComparator<T> implements Comparator<T> {

		@Override
		

		public int compare(T arg0, T arg1) {
			
			if (arg0 instanceof String) {
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				try {
					Date date1 = formatter.parse((String) arg0);
					Date date2 = formatter.parse((String) arg1);
					return date1.compareTo(date2);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			else if (arg0 instanceof JSONObject) {
			try {
				String score0 = ((JSONObject) arg0).getString("tagValue");
				String score1 = ((JSONObject) arg1).getString("tagValue");
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				try {
					Date date1 = formatter.parse(score0);
					Date date2 = formatter.parse(score1);
					return date1.compareTo(date2);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			return 0;
		}
	}

