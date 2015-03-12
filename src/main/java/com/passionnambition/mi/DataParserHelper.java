package com.passionnambition.mi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.passionnambition.constants.ValueTypeEnum;


public class DataParserHelper {
	
	public static final String AMT_REGEX = "\\b([$]?[0-9][,0-9]*\\.[0-9]{1,2})\\b"; 
	public static final String PARSE_AMT_REGEX  = "\\b([0-9][,0-9]*\\.[0-9]{1,2})\\b";
//	public static final String ACCT_NUM_REGEX = "[0-9][- 0-9A-Z]{5,}";
	//TODO: Check why the space is needed?
	public static final String ACCT_NUM_REGEX = "[0-9][-0-9A-Z]{5,}";
	public static final String ACCOUNT_LABEL_REGEX = "([Aa]ccount|CCOUNT)|[Nn]o|Number|Acct|Policy Number|No.|AccouRUiumber|[Mm]ember|[Mm]embership";
	public static final String AMT_LABEL_REGEX = "Minimum |[Aa][Mm][Oo][Uu][Nn][Tt] [Dd][Uu][Ee] |Assessment [Aa][Mm][Oo][Uu][Nn][Tt]| Full [Aa][Mm][Oo][Uu][Nn][Tt] [Dd][Uu][Ee]| BALANCE [Dd][Uu][Ee]| TOTAL [Dd][Uu][Ee]| TOTAL [Aa][Mm][Oo][Uu][Nn][Tt] [Dd][Uu][Ee]|TOTAL DUE BY|PAY THIS AMOUNT";
	public static final String DATE_REGEX = "([0-1]?[0-9][-/](([0-3][0-9])|([1-9]))[-/]'?[0-9]{2,4})|([A-Za-z]{3,9}\\.? *[0-9]{1,2}((,)|( +)|(,. *))'?[0-9]{2,4})";
	public static final String ADDRESS_REGEX = "[^A-Z0-9,.&#_\\s-]*";
	public static final String US_ADDRESS_REGEX = "(?n:(?(\\d{1,5}(\\ 1\\/[234])?(\\x20[A-Z]([a-z])+)+ )|(P\\.O\\.\\ Box\\ \\d{1,5}))\\s{1,2}(?i:(?(((APT|B LDG|DEPT|FL|HNGR|LOT|PIER|RM|S(LIP|PC|T(E|OP))|TRLR|UNIT)\\x20\\w{1,5})|(BSMT|FRNT|LBBY|LOWR|OFC|PH|REAR|SIDE|UPPR)\\.?)\\s{1,2})?)(?[A-Z]([a-z])+(\\.?)(\\x20[A-Z]([a-z])+){0,2})\\, \\x20(?A[LKSZRAP]|C[AOT]|D[EC]|F[LM]|G[AU]|HI|I[ADL N]|K[SY]|LA|M[ADEHINOPST]|N[CDEHJMVY]|O[HKR]|P[ARW]|RI|S[CD] |T[NX]|UT|V[AIT]|W[AIVY])\\x20(?(?!0{5})\\d{5}(-\\d {4})?))$";

	public static final String US_STATES_REGEX = "\\b(C[AOT]|D[EC]|F[LM]|G[AU]|HI|I[ADLN]|K[SY]|LA|M[ADEHINOPST]|N[CDEHJMVY]|O[HKR]|P[ARW]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])\\b";
	public static final String US_ZIP_CODES_REGEX  = "([0-9]{5}-[0-9]{4})";
	public static final String US_PO_BOX = "P[oO]|B[oO][xX]|P[oO][sS][tT]";   
	public static final String ABBYY_STRING = "ABBYY_";
	public static final String NO_MATCH = "Unable to detect";
	public static final String GARBAGE = "(\\*|\\¥|\"|'|&|\\^|\\<|\\>|\\*\\*|È|Ç|http|yt|;|\\|@)";
	public static final String ONLY_ALPHA_NUMBERS = "[a-zA-Z0-9-]+$";
	public static final String US_PHONE_NUMBERS = "\\b([235689][0-9]{6}([0-9]{3})?)\\b";
	public static final String ADDRESS_FILTER_REGEX = "(Make|check|payable|to|\\|)";
	public static final String ONLY_ALPHA = "\\b([a-zA-Z]+)\\b";
	//Changed the above to filter all unwanted characters
	public static final String GARBAGE_ACCT_NUM = "\\$|,|([0-9]{3,}/)+";

	// From http://www.regular-expressions.info/creditcard.html
	public static final String VISA_REGEX = "\\b4[0-9]{12}(?:[0-9]{3})?\\b";
	public static final String MASTERCARD_REGEX = "\\b5[1-5][0-9]{14}\\b";
	public static final String AMEX_REGEX = "\\b3[47][0-9]{13}\\b";
	public static final String DINERS_REGEX = "\\b3(?:0[0-5]|[68][0-9])[0-9]{11}\\b";
	public static final String DISCOVER_REGEX = "\\b6(?:011|5[0-9]{2})[0-9]{12}\\b";
	public static final String JCB_REGEX = "\\b(?:2131|1800|35\\d{3})\\d{11}\\b";
	
	public DataParserHelper()
	{
		
	}
	
	public static boolean parseUsingRegex(String pattern,String valueToMatch) {
		//Check if the value sent is blank
		if(!StringUtils.hasText(valueToMatch))
		{
			return false;
		}
		//Trim any trailing and starting whitespaces
		valueToMatch = valueToMatch.trim();
		boolean valueMatchFound = false;
        
		Pattern r = Pattern.compile(pattern);
		
		Matcher m = r.matcher(valueToMatch);
		if(m.find())
		{
			valueMatchFound = true;
		}
        return valueMatchFound;
	}
	
	
	
	public static String parseUsingRegexAndReturnMatchedValue(String pattern,String valueToMatch) {
		//Check if the value sent is blank
		String matchedValue = "";
		if(!StringUtils.hasText(valueToMatch))
		{
			return matchedValue;
		}
		//Trim any trailing and starting whitespaces
		valueToMatch = valueToMatch.trim();
		
		Pattern r = Pattern.compile(pattern);
		
		Matcher m = r.matcher(valueToMatch);
		if(m.find())
		{
			matchedValue = m.group(0);
		}
		return matchedValue;
	} 
	
	public static boolean isDate(String dateStr)
	{
		return parseUsingRegex(DATE_REGEX, dateStr);
	}
	
	public static String matchedDate(String dateStr)
	{
		return parseUsingRegexAndReturnMatchedValue(DATE_REGEX,dateStr);
	}
	
	public static String formatDate(String dateStr) {
		Date date = null;
		
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			date = formatter.parse(dateStr);

		} catch (ParseException e) {
			
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd yyyy");
				date = formatter.parse(dateStr);
			} catch (ParseException e1) {
				// e.printStackTrace();
				try {
					SimpleDateFormat formatter = new SimpleDateFormat(
							"MMMM dd, yyyy");
					date = formatter.parse(dateStr);
				} catch (ParseException e2) {
				}
			}
		}
		
		String formattedDateString = null;
		//Change all dates to MM/dd/yyyy format
		if(date !=null){
			SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");
			formattedDateString = formatter2.format(date);
			 
		}
		return formattedDateString;
	}
	
	public static String matchedAmount(String dateStr)
	{
		return parseUsingRegexAndReturnMatchedValue(AMT_REGEX,dateStr);
	}
	
	public static String matchedAcctNo(String dateStr)
	{
		return parseUsingRegexAndReturnMatchedValue(ACCT_NUM_REGEX,dateStr);
	}
	
	 public static boolean isAddressCorrect(String str){
		 boolean addressFound = false;
		 if(isDate(str) || containsAbbyyString(str))
		 {
			 return false;
		 }
		 
		 if(containsUsPoBox(str) || containsUSStates(str) || containsUsZipCodes(str))
		 {
			addressFound = true;
		 }
		 
		 return addressFound;
	 }
	
	 public static boolean isDueDateCorrect(String str){
		 if(StringUtils.hasText(str) 
				 && (containsGarbageString(str)
					 || str.length() < 5 	 
				 	)
		 	)
	 	{
			 return false;
	 	}
		return isDate(str);
	 }
	 
	 public static boolean isAmtCorrect(String str){
		 if(StringUtils.hasText(str) 
				 && (containsGarbageString(str)
					 || containsAbbyyString(str)
					 || str.length() < 2				 	)
		 	)
		 {
			 return false;
		 }
		 if(containsAmount(str))
		 {
			 return true;
		 }
		 return false;
	 }
	 
	 //TODO:Implement logic to check in dictionary
	 /**
	  * Account number rules
	  * Account number length is between 5-16 
	  * No spaces in the middle
	  * @param str
	  * @return
	  */
	 public static boolean isAcctNumCorrect(String str){
		 if(StringUtils.hasText(str) && (containsGarbageString(str)
					 ||	containsAbbyyString(str)
					 || containsAccountGarbageString(str)
					 ||	str.length() < 5 
					 || containsUsZipCodes(str)
					 || containsAmount(str)
					 )
			)
		 {
			 return false;
		 }
		 
		 //Account number may contain strings like AccountNo. or Billing Account No. 
		 if(containsAccountLabel(str))
		 {
			 str = str.replaceAll("\\s", "");
			 if(containsAccountNumber(str))
			 {
				 return true;
			 }
		 }
		 
		 //Filter out any cases that has failed in the previous scenario
		 if(!containsOnlyAlphaNumbers(str) || containsOnlyAlpha(str))
		 {
			 return false;
		 }
		 
		 return true;
	 }
	 
	 public static boolean containsAbbyyString(String str){
		 boolean matchFound = false;
		 if(StringUtils.hasText(str))
		 {
			 str = str.toUpperCase();
			 matchFound = str.contains(ABBYY_STRING);
		 }
		 return matchFound;
	 }
	 
	 public static boolean containsSpaces(String str){
		 boolean matchFound = false;
		 if(StringUtils.hasText(str))
		 {
			matchFound = str.trim().split("[\\s]").length > 1 ? true : false;
		 }
		 return matchFound;
	 }
	 
	 public static boolean containsGarbageString(String str){
		 return parseUsingRegex(GARBAGE, str);
	 }
	 
	 public static boolean containsAccountGarbageString(String str){
		 return parseUsingRegex(GARBAGE_ACCT_NUM, str);
	 }
	 
	 public static boolean containsAddressGarbageString(String str)
	 {
		 return parseUsingRegex(ADDRESS_FILTER_REGEX, str);
	 }
	 
	 public static boolean containsUsPoBox(String str)
	 {
		 return parseUsingRegex(US_PO_BOX, str);
	 }
	 
	 public static boolean containsUSStates(String str)
	 {
		 return parseUsingRegex(US_STATES_REGEX, str);
	 }
	 
	 public static boolean containsUsZipCodes(String str)
	 {
		 return parseUsingRegex(US_ZIP_CODES_REGEX, str);
	 }
	 
	 public static boolean containsAmount(String str)
	 {
		 return parseUsingRegex(AMT_REGEX, str);
	 }
	 
	 public static boolean containsOnlyAlphaNumbers(String str)
	 {
		 return parseUsingRegex(ONLY_ALPHA_NUMBERS, str);
	 }
	 
	 
	 public static boolean containsOnlyAlpha(String str)
	 {
		 return parseUsingRegex(ONLY_ALPHA, str);
	 }
	 
	 public static boolean containsAccountLabel(String str)
	 {
		 return parseUsingRegex(ACCOUNT_LABEL_REGEX, str);
	 }
	 
	 public static boolean containsAccountNumber(String str)
	 {
		 return parseUsingRegex(ACCT_NUM_REGEX, str);
	 }
	 
	 
	 public static ValueTypeEnum getValueType(String str){
		 
		 if (isDate(str)) {
			return ValueTypeEnum.DATE; 
		 }
		 
		 else if(isAmtCorrect(str)){
			 return ValueTypeEnum.AMOUNT;
		 }
		 else if(containsUSStates(str) || containsUsZipCodes(str)){
			 
			 return ValueTypeEnum.ADDRESS;
		 }
		 
//		 else if(containsOnlyAlpha(str)){
//			 return "ONLY_ALPHA";
//		 }
//		 
//		 else if(containsOnlyAlphaNumbers(str)) {
//			 return "ONLY_ALPHA_NUMBERS";
//		 }
		 
		 else
			 return ValueTypeEnum.NO_MATCH;

		 
	 }


public static boolean parseUsingRegexAfterPatternConversion(String pattern,String valueToMatch) 
{
	String convertedPattern = null;
	if(pattern.equals("DATE_REGEX")) {
		
		convertedPattern = DATE_REGEX;
	}
	else if(pattern.equals("AMT_REGEX")) {
		
		convertedPattern = AMT_REGEX;
	}
	else if(pattern.equals("ADDRESS_REGEX")) {
		
		convertedPattern = US_STATES_REGEX;
	}
	else if(pattern.equals("ONLY_ALPHA")) {
		
		convertedPattern = ONLY_ALPHA;
	}
	else if(pattern.equals("ONLY_ALPHA_NUMBERS")) {
		
		convertedPattern = ONLY_ALPHA_NUMBERS;
	}
	
	else if(pattern.equals("NO_MATCH")){
		
		convertedPattern = NO_MATCH;
	}
	return (convertedPattern != null) ? parseUsingRegex(convertedPattern, valueToMatch) : false;
}

}
