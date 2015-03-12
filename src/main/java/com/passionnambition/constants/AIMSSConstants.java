package com.passionnambition.constants;

import org.apache.log4j.Logger;

import com.passionnambition.util.PropertyHelper;

public class AIMSSConstants {
	
	private static final Logger logger = Logger.getLogger(AIMSSConstants.class);

	// Mongo constants
	public static String VENDOR = "vendorName";
	public static String TEMPLATEID = "templateId";
	public static String SHOWN_COUNT = "shownCount";
	public static String ACCEPTED_COUNT = "acceptedCount";
	public static String FIELD_HIT_COUNT = "fieldHitCount";
	public static int TOTAL_FIELD_COUNT = 8;
	public static String MONGO_ID = "_id";
	public static String APP_USER = "appUser";
	public static String API_KEY = "apiKey";
    public static String ITEM_NAME = "itemName";
    
  //Keys
	private static String IMAGE_LOCATION = "IMAGE_LOCATION";
	public static String IMAGE_LOCATION_VALUE;
	
	private static String CONVERT_SCRIPT_LOCATION = "CONVERT_SCRIPT_LOCATION";
	public static String CONVERT_SCRIPT_LOCATION_VALUE;
    
 // Init block
 		static {
 			PropertyHelper ph = PropertyHelper.getInstance("rd");
 			IMAGE_LOCATION_VALUE = ph.getProperty(IMAGE_LOCATION);
 			CONVERT_SCRIPT_LOCATION_VALUE = ph.getProperty(CONVERT_SCRIPT_LOCATION);
 			
 			logger.info("Property loaded " + IMAGE_LOCATION_VALUE);
 			logger.info("Property loaded " + CONVERT_SCRIPT_LOCATION_VALUE);
 		}
}
