package com.passionnambition.util;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.BadSecurityHandlerException;
import org.apache.pdfbox.util.ImageIOUtil;

import com.passionnambition.constants.AIMSSConstants;

public class PDFOperations {
	
	private static final Logger logger = Logger.getLogger(PDFOperations.class);
	
	//Default constructor
	public PDFOperations()
	{
		
	}
	
	private static PDFOperations pdfOperations;
	
	public static PDFOperations getInstance()
	{
		if(pdfOperations == null)
		{
			pdfOperations = new PDFOperations();
		}
		return pdfOperations;
	}
	
	public static void main(String[] args) throws IOException, BadSecurityHandlerException, CryptographyException
	{
		PDFOperations abbyCLI = new PDFOperations();
		URL fileStream = new URL("http://10.136.234.134/ies/fileService1/getPdfFile?fileId=1903f493fd1b14df35e0ac6d5283f55a");
		abbyCLI.convertPDFToImageAndWriteToFile(fileStream.openStream());
	}
	
	@SuppressWarnings("rawtypes")
	public PDFToImageConversion writeImage(PDDocument document, String imageFormat, String password, int startPage, int endPage, int imageType, int resolution)
			throws IOException
			{
		List pages = document.getDocumentCatalog().getAllPages();
		ByteArrayOutputStream baos = null;
		PDFToImageConversion convertedImage = new PDFToImageConversion();
		for (int i = startPage - 1; (i < endPage) && (i < pages.size()); i++)
		{
			PDPage page = (PDPage)pages.get(i);
			BufferedImage image = page.convertToImage(imageType, resolution);
			baos = new ByteArrayOutputStream();
			String randomInt = String.valueOf(Math.abs(new Random().nextInt()));
			String fileName = AIMSSConstants.IMAGE_LOCATION_VALUE + "invoice" + randomInt;
			ImageIOUtil.writeImage(image, imageFormat, fileName, imageType, resolution);
			convertedImage.setFileName(randomInt);
			convertedImage.setImageByteArray(baos.toByteArray());
		}
		return convertedImage;
			}
	
	public PDFToImageConversion convertPDFToImageAndWriteToFile(InputStream file) throws IOException {
		String password = "";
	    String imageFormat = "png";
	    int endPage = 1;
	    int startPage = 1;
	    PDFToImageConversion convertedImage = null;
	    int resolution;
	    try
	    {
	      resolution = Toolkit.getDefaultToolkit().getScreenResolution();
	      logger.info("Resolution:"+resolution);
//	      resolution = 96;
	    }
	    catch (HeadlessException e)
	    {
	      resolution = 96;
	    }

	      PDDocument document = null;
	      try
	      {
	      document = PDDocument.load(file);
	      if (document.isEncrypted())
	      {
	        try
	        {
	          document.decrypt(password);
	        }
	        catch (InvalidPasswordException e)
	        {
	        	logger.error("Invalid Password Exception caught in the method - convertPDFToImageAndWriteToFile ",e);
	        }
	      }
	        int imageType = 1;
	
	        convertedImage = writeImage(document, imageFormat, password, startPage, endPage, imageType, resolution);
			
	      }
	      catch (Exception e)
	      {
	        System.err.println(e);
	      }
	      finally
	      {
	        if (document != null)
	        {
	          document.close();
	        }
	      }
	      
	      return convertedImage;
		
	}
	
	public class PDFToImageConversion{
		private String fileName;
		private byte[] imageByteArray;
		
		public PDFToImageConversion(){
			
		}
		
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public byte[] getImageByteArray() {
			return imageByteArray;
		}
		public void setImageByteArray(byte[] imageByteArray) {
			this.imageByteArray = imageByteArray;
		}
		
	}
	    
}
