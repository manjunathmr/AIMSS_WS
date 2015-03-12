package com.passionnambition.websvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.passionnambition.model.Notes;
import com.passionnambition.model.Result;

@Path("/aimss_service")
public interface AIMSSService {

	@GET
	@Path("/resetDB")
	@Produces("application/json")
	public Result resetDB(@Context HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException;

	@GET
	@Path("/findAllInNotesCollection")
	@Produces("application/json")
	public Response findAllInNotesCollection(
			@Context HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException;
	
	@PUT
	@Path("/updateInNotesCollection")
	@Produces("application/json")
	@Consumes("application/json")
	public Result updateInNotesCollection(@Context HttpServletRequest request, Notes acc);
	
	@DELETE
	@Path("/deleteInNotesCollection")
	@Produces("application/json")
	@Consumes("application/json")
	public Result deleteInNotesCollection(@Context HttpServletRequest request, String accNo);;

}
