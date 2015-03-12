package com.passionnambition.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;


public class PropertyHelper extends Properties {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger=Logger.getLogger(PropertyHelper.class);
	
	public static String systemType=StringUtils.hasText(System.getenv("system")) ? System.getenv("system") : "" ;
	public static boolean isDevInstance=systemType!=null && systemType.equalsIgnoreCase("dev");
	static{
    	System.out.println("SYSTEM INSTANCE TYPE: "+ systemType);
    }	
    
    public static String getSystemType(){
    	return systemType;
    }
    
    private PropertyHelper(){}
    
    private static Map<String,PropertyHelper> map=new HashMap<String, PropertyHelper>();
    
    public static PropertyHelper getInstance(String name){
    	
    	PropertyHelper prop=map.get(name);
    	if(prop!=null)
    		return prop;
    	
    	prop=new PropertyHelper();
    	
    	InputStream is=null;
    	boolean loadedSystemTypeSpecificProp=false;
		try{
			is = PropertyHelper.class.getResourceAsStream("/"+name+".properties");
			if(is!=null)
				prop.load(is);
			
			//Override w/ instance specific properties
			
			if(systemType!=null && systemType.trim().length()>0){
				try{
					is=PropertyHelper.class.getResourceAsStream("/"+name+".properties."+systemType);
					if(is==null)
						loadedSystemTypeSpecificProp=false;
					else{
						prop.load(is);
						loadedSystemTypeSpecificProp=true;
					}
				}catch(Exception e){
					loadedSystemTypeSpecificProp=false;
				}
				if(systemType!=null && systemType.length()>0 && !loadedSystemTypeSpecificProp)
					logger.info("["+name+".properties."+systemType+" not found.  Reverting to default: "+name+".properties]");
			}
			logger.info("[LOADED "+name+".properties"+(loadedSystemTypeSpecificProp?"."+systemType:"")+"]");
		}catch(Exception e){
			e.printStackTrace();
			logger.info("[Unable to load "+name+".properties from CLASSPATH!]");
		}finally{
			try{is.close();}catch(Exception e){}
		}
		
		return prop;
    }

	public boolean getTrueFalse(String key){
        String x=super.getProperty(key);
        return x!=null && (x.equalsIgnoreCase("true") || x.equalsIgnoreCase("yes"));
    }

	public Integer getInt(String key){
        try {
			return Integer.parseInt(super.getProperty(key));
		} catch (NumberFormatException e) {
			return null;
		}
    }
    
    
    
    

}


