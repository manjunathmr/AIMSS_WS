package com.passionnambition.websvc.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import nl.bitwalker.useragentutils.UserAgent;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.pdfbox.PDFToImage;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.passionnambition.constants.AIMSSConstants;
import com.passionnambition.dao.MongoDBConnectionFactory;
import com.passionnambition.model.Notes;
import com.passionnambition.model.Result;
import com.passionnambition.websvc.AIMSSService;

public class AIMSSServiceImpl implements AIMSSService{

	private static final Logger logger = Logger.getLogger(AIMSSServiceImpl.class);
	
	/**
	 * Extract all the text for all of the fields and tables in the form and update the form accordingly.
	 */

	public Result saveFile(MultipartFormDataInput input	)
			throws JsonGenerationException, JsonMappingException, IOException {

		MongoDBConnectionFactory fac = new MongoDBConnectionFactory();
		Result result = null;
		try {
			result=	fac.saveArtifact(input);
		} catch (UnknownHostException e) {
			logger.error("UnknownHostException while saveFile ", e);
		} catch (MongoException e) {
			logger.error("MongoException while saveFile ", e);
		}
		return result;    
	}

	public String getImageFileLocation(String fileId) throws MongoException,IOException
	{
		MongoDBConnectionFactory fac = new MongoDBConnectionFactory();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String randomInt = "";
		try {
			//Retrieve the file from mongo
			outputStream =	fac.retreiveArtifact(fileId);
			byte[] data = Base64.decodeBase64(outputStream.toByteArray());
			randomInt = String.valueOf(Math.abs(new Random().nextInt()));
			String pdfFileName = AIMSSConstants.IMAGE_LOCATION_VALUE + "invoice" + randomInt + ".pdf";
			String imgFileName = AIMSSConstants.IMAGE_LOCATION_VALUE + "invoice" + randomInt + ".png";
			//Save the location on a file system
			FileOutputStream fos = new FileOutputStream(pdfFileName);
			fos.write(data);
			fos.close();
			String s = "";
			
			//Convert pdf to image
		 	String args3 = AIMSSConstants.CONVERT_SCRIPT_LOCATION_VALUE + " -density 200 -background white -alpha off " + pdfFileName +" -resize 100% " + imgFileName; 
            Process p = Runtime.getRuntime().exec(args3);
            
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command
            
            logger.info("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                logger.info(s);
            }
            
            // read any errors from the attempted command

            logger.info("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
            	logger.info(s);
            }
		} catch (UnknownHostException e) {
			logger.error("UnknownHostException while getImageFile ", e);
		} catch (MongoException e) {
			logger.error("MongoException while getImageFile ", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return randomInt;
	}
	
	public static void main(String[] args) throws Exception
	{
		String arg[] = {"-imageType","png","/Users/mshenoy/Desktop/samples/pdf/invoicable1.pdf"};
		PDFToImage.main(arg);
	}

	
	 public Response getPdf(
	    		@QueryParam("fileId") String fileId) throws MongoException,IOException
		{
		 MongoDBConnectionFactory fac = new MongoDBConnectionFactory();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			//Retrieve the file from mongo
			outputStream =	fac.retreiveArtifact(fileId);
			byte[] data = Base64.decodeBase64(outputStream.toByteArray());
			return Response.status(200).entity(data).build();
		}
	


	@Override
	public Response findAllInNotesCollection(HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("AIMSSServiceImpl.findAllInNotesCollection()");
		logDetails("FIND ALL In Notes request ==> ",request);
		
		List<BasicDBObject> notesList = null;
		MongoDBConnectionFactory dbFactory = MongoDBConnectionFactory.getInstance();
		try {
			notesList = dbFactory.findAllInNotesCollection();
		} catch (UnknownHostException e) {
			logger.error("UnknownHostException while findAllInMITable ", e);
		} catch (MongoException e) {
			logger.error("MongoException while findAllInMITable ", e);
		}

		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, notesList);

		logDetails("FIND ALL In MITable Response Details : " + writer.toString(), request);
		return Response.status(200).entity(writer.toString()).build();
	}
	
	
	@Override
	public Result resetDB(HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		Result result = null;
		MongoDBConnectionFactory fac = new MongoDBConnectionFactory();
		try {
			result = fac.resetDB();
		} catch (UnknownHostException e) {
			logger.error("UnknownHostException while resetDB ", e);
		} catch (MongoException e) {
			logger.error("MongoException while resetDB ", e);
		}
		return result;
	}

	public String saveFile(byte[] input) throws JsonGenerationException,
			JsonMappingException, IOException {
		String fileId = MongoDBConnectionFactory.getInstance().saveFile(input);
		return fileId;
	}

	public Result updateInNotesCollection(
			@Context HttpServletRequest request, Notes acc) {
		final boolean result = MongoDBConnectionFactory.getInstance().updateNotes(acc);

		return new Result() {
			@SuppressWarnings("unused")
			public boolean res = result;
		};
	}

	@Override
	public Result deleteInNotesCollection(
			@Context HttpServletRequest request, String accNo) {
		final boolean result = MongoDBConnectionFactory.getInstance().deleteAccount(accNo);

		return new Result() {
			@SuppressWarnings("unused")
			public boolean res = result;
		};
	}

	private void logDetails(String message,HttpServletRequest request)
	{
		String userAgent = request.getHeader("user-agent");


		UserAgent ua = UserAgent.parseUserAgentString(userAgent);

		if(ua.getBrowserVersion() == null)
		{
			logger.info(message + " IP address : " + request.getRemoteAddr() + " UserAgent : " + userAgent);
			return;
		}
		logger.info(message + " IP address : " + request.getRemoteAddr() 
				+ " BrowserName : " + ua.getBrowser().toString() 
				+ " BrowserVersion : " + ua.getBrowserVersion() );
	}

}
