package com.passionnambition.dao;

//MongoDBConnectionFactory
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.mongodb.util.JSON;
import com.passionnambition.model.Notes;
import com.passionnambition.model.Result;
import com.passionnambition.util.BaHashFn;
import com.passionnambition.util.DateComparator;

public class MongoDBConnectionFactory {
	
	private final String host = "127.0.0.1";
	
	private final int port = 27017;
	
	private final String dbname = "Aimss";
	
	private static DB db;
	
	private GridFS fs;
	
	private final String filedbname = "aimssfile";

	private static final Logger logger = Logger.getLogger(MongoDBConnectionFactory.class);
	
	private static MongoDBConnectionFactory instance = new MongoDBConnectionFactory();

	public static MongoDBConnectionFactory getInstance() {
		return instance;
	}

	private DB getDb() throws UnknownHostException, MongoException {
		if (db == null) {
			db = new Mongo(host, port).getDB(dbname);
		}
		return db;
	}

	private DBCollection getNotesCollection() throws UnknownHostException {
		return getDb().getCollection("Notes");
	}

	public void insertObject(String tagDetail) throws UnknownHostException,
			MongoException {
		DB db = this.getDb();
		DBCollection coll = db.getCollection("testCollection");

		BasicDBObject doc = new BasicDBObject("name", "MongoDB")
				.append("type", "database").append("count", 1)
				.append("info", new BasicDBObject("x", 203).append("y", 102));

		coll.insert(doc);
		DBObject myDoc = coll.findOne();
		logger.info(myDoc);
	}


	public DBObject findInConfidenceTableById(String id)
			throws UnknownHostException, MongoException {
		DBObject templateDetail = getNotesCollection().findOne(
				buildDbObject(id));
		return templateDetail;
	}

	private void sortDates(List<JSONObject> dates){
		Collections.sort(dates,new DateComparator<JSONObject>());
	}
	
	private ArrayList<Double> getUniqueAmountList(ArrayList<String> allValuesList) {
		
		//Get only unique values
		HashSet<String> valueSet = new HashSet<String>(allValuesList);
		
		//Convert to double arrayList
		ArrayList<Double> valueList = new ArrayList<Double>();
		
		for(String s: valueSet) {
			
			Double num = Double.parseDouble(s);
			valueList.add(num);
		}
		
		
		return  valueList;
		
	}

//private ArrayList<Double> getUniqueList(ArrayList<String> allValuesList) {
//		
//		//Get only unique values
//		HashSet<String> valueSet = new HashSet<String>(allValuesList);
//		
//		//Convert to double arrayList
//		ArrayList<Double> valueList = new ArrayList<Double>();
//		
//		for(String s: valueSet) {
//			
//			Double num = Double.parseDouble(s);
//			valueList.add(num);
//		}
//		
//		
//		return  valueList;
//		
//	}
private ArrayList<String> getUniqueDateList(ArrayList<String> allValuesList) {
		
		//Get only unique values
		HashSet<String> valueSet = new HashSet<String>(allValuesList);
		
		//Convert to double arrayList
		
		
		return  new ArrayList<String>(valueSet);
		
	}
	private void findAbsoluteAndRelativePositions(ArrayList<JSONObject> foiList,ArrayList<String> allList){
		for(JSONObject o : foiList){
			try {
				//Position of foi in the valueTypeArray
				int index = Collections.binarySearch(allList,o.getString("tagValue"));
				o.put("absoluteInAll",index+1);
				
				//Relative position of foi in valueTypeArray - MIN OR MAX
				if(index == 0) {
					o.put("relativeInAll", "MIN");
				}
				if(index == allList.size()-1) {
					o.put("relativeInAll", "MAX");
				}
				
				//Position of foi in foiArray
				int i = foiList.indexOf(o);
				o.put("absoluteInSelected",i+1);
				
				//Relative position of foi in foiArray - MIN OR MAX
				if(i == 0) {
					o.put("relativeInSelected", "MIN");
				}
				if(i == foiList.size()-1) {
					o.put("relativeInSelected", "MAX");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void findAbsoluteAndRelativePositionsForAmt(ArrayList<JSONObject> foiList,ArrayList<Double> allList){
		for(JSONObject o : foiList){
			try {
				//Position of foi in the valueTypeArray
				int index = Collections.binarySearch(allList,Double.parseDouble(o.getString("tagValue")));
				o.put("absoluteInAll",index+1);
				
				//Relative position of foi in valueTypeArray - MIN OR MAX
				if(index == 0) {
					o.put("relativeInAll", "MIN");
				}
				if(index == allList.size()-1) {
					o.put("relativeInAll", "MAX");
				}
				
				//Position of foi in foiArray
				int i = foiList.indexOf(o);
				o.put("absoluteInSelected",i+1);
				
				//Relative position of foi in foiArray - MIN OR MAX
				if(i == 0) {
					o.put("relativeInSelected", "MIN");
				}
				if(i == foiList.size()-1) {
					o.put("relativeInSelected", "MAX");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private ArrayList<String> fetchValueList(ArrayList<JSONObject> objectList) {
		ArrayList<String> valueList = new ArrayList<String> ();
		for(JSONObject a : objectList){
			try {
				valueList.add(a.getString("tagValue"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return valueList;
		
	}
	
	private void insertIntoMLTable(String templateName,String valueType,JSONArray valueTypesArray, JSONArray foiArray, String fileId) {
		try {
			DBObject valueTypesObj = (DBObject)JSON.parse(valueTypesArray.toString());
			DBObject foiObj = (DBObject)JSON.parse(foiArray.toString());
			BasicDBObject contextDoc = new BasicDBObject();
			contextDoc.put("Template", templateName);
			contextDoc.put("valueType", valueType);
			contextDoc.put("valueTypeList", valueTypesObj);
			contextDoc.put("foiList", foiObj);
			contextDoc.put("fileId", fileId);
			
			getNotesCollection().insert(contextDoc);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private BasicDBObject buildDbObject(String id) {
		BasicDBObject doc = new BasicDBObject();
		doc.put("_id", new org.bson.types.ObjectId(id));
		return doc;
	}

	
	public List<BasicDBObject> getAllDBObjectsInCollectionForApp(
			DBCollection dbCollection , String apiKey) throws UnknownHostException,
			MongoException {
		BasicDBObject whereQuery = new BasicDBObject();
		List<BasicDBObject> allDBObjects = new ArrayList<BasicDBObject>();

		DBCursor cursor = dbCollection.find(whereQuery);
		try {
			while (cursor.hasNext()) {
				BasicDBObject obj = (BasicDBObject) cursor.next();
				long ts = ((ObjectId) obj.get("_id")).getTime();
				obj.put("timestamp", ts);
				allDBObjects.add(obj);
			}
		} finally {
			cursor.close();
		}
		return allDBObjects;
	}


	public Result saveArtifact(MultipartFormDataInput input)
			throws MongoException, IOException {
		String fileHash = null;
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

		List<InputPart> inputParts = uploadForm.get("file");

		for (InputPart inputPart : inputParts) {
			byte[] buffer = inputPart.getBodyAsString().getBytes();

			GridFS dbFs = getFs();

			fileHash = new BaHashFn().apply(buffer);

			GridFSDBFile dbFile = dbFs.findOne(fileHash);

			if (dbFile == null) {
				final GridFSInputFile gfsFile = dbFs.createFile(
						new ByteArrayInputStream(buffer), fileHash);

				gfsFile.save();
			}
		}
		final String finalFileHash = fileHash;

		return new Result() {
			@SuppressWarnings("unused")
			public String success = "true";

			@SuppressWarnings("unused")
			public String fileId = finalFileHash;
		};

	}

	public String saveFile(byte[] input) throws MongoException, IOException {
		String fileId = "";
		String fileHash = null;

		String encodedFileDataStr = new String(Base64.encodeBase64(input));

		GridFS dbFs = getFs();

		fileHash = new BaHashFn().apply(encodedFileDataStr.getBytes());

		GridFSDBFile dbFile = dbFs.findOne(fileHash);

		if (dbFile == null) {
			final GridFSInputFile gfsFile = dbFs.createFile(
					new ByteArrayInputStream(encodedFileDataStr.getBytes()),
					fileHash);

			gfsFile.save();
		}
		final String finalFileHash = fileHash;

		fileId = finalFileHash;
		return fileId;
	}


	private GridFS getFs() throws UnknownHostException, MongoException {
		if (fs == null) {
			fs = new GridFS(new Mongo(host, port).getDB(filedbname));
		}
		return fs;
	}

	public ByteArrayOutputStream retreiveArtifact(String fileId)
			throws MongoException, IOException {
		GridFS gFs = getFs();

		GridFSDBFile gFile = gFs.findOne(fileId);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		if (gFile == null) {
			return outputStream;
		}

		gFile.writeTo(outputStream);
		return outputStream;
	}

	public List<BasicDBObject> getAllDBObjectsInCollection(
			DBCollection dbCollection) throws UnknownHostException,
			MongoException {

		List<BasicDBObject> allDBObjects = new ArrayList<BasicDBObject>();

		DBCursor cursor = dbCollection.find();
		try {
			while (cursor.hasNext()) {
				BasicDBObject obj = (BasicDBObject) cursor.next();
				long ts = ((ObjectId) obj.get("_id")).getTime();
				obj.put("timestamp", ts);
				allDBObjects.add(obj);
			}
		} finally {
			cursor.close();
		}
		return allDBObjects;
	}
	
	public List<BasicDBObject> findAllInNotesCollection() throws UnknownHostException,
			MongoException {
		return getAllDBObjectsInCollection(getNotesCollection());
	}
	
	public Result resetDB() throws UnknownHostException, MongoException {
		try {
			getNotesCollection().drop();
		} catch (MongoException me) {
			return new Result() {
				@SuppressWarnings("unused")
				public String completed = "false";
			};
		}

		return new Result() {
			@SuppressWarnings("unused")
			public String completed = "true";
		};
	}

	public Result resetConfidenceTable() throws UnknownHostException,
			MongoException {
		try {

			getNotesCollection().drop();

		} catch (MongoException me) {
			return new Result() {
				@SuppressWarnings("unused")
				public String completed = "false";
			};
		}

		return new Result() {
			@SuppressWarnings("unused")
			public String completed = "true";
		};
	}

	public void updateBaseIntelligence(List<JSONObject> fieldRules)
			throws MongoException, UnknownHostException {
		for (JSONObject fieldRule : fieldRules) {
			DBObject dbObject = (DBObject) JSON.parse(fieldRule.toString());
			getNotesCollection().insert(dbObject);
		}
	}

	public boolean updateNotes(Notes note) {
		
		boolean success = false;
		if(note == null){
			return success;
		}
		try {
			DBObject queryDbObject = new BasicDBObject("id", note.getId());
			DBObject dbObject = (DBObject) JSON.parse(convertObjectToString(note));
			WriteResult result = getNotesCollection().update(queryDbObject,new BasicDBObject("$set", dbObject), true, true);

			success = (result.getN() > 0);
		}  catch (IOException e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	public boolean deleteAccount(String id) {
		
		boolean success = false;
		if(!StringUtils.hasText(id)){
			return success;
		}
		
		try {
			DBObject queryDbObject = new BasicDBObject("id", id.trim());
			WriteResult result = getNotesCollection().remove(queryDbObject);

			success = (result.getN() > 0);
		}  catch (IOException e) {
			e.printStackTrace();
		}
		
		return success;
	}

	private String convertObjectToString(Object o) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, o);
		return writer.toString();
	}
}