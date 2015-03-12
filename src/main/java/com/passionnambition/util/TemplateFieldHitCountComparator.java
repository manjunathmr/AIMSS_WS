package com.passionnambition.util;
import java.util.Comparator;

import com.mongodb.BasicDBObject;
/**
 * @author asreepathy
 * 
 */
public class TemplateFieldHitCountComparator implements Comparator<BasicDBObject>{
	
	@Override
	public int compare(BasicDBObject arg0, BasicDBObject arg1) {
		int score0 = (Integer) arg0.get("fieldHitCount");
		int score1 = (Integer) arg1.get("fieldHitCount");
		return (score1 > score0 ? 1 : 0);
	}
}




