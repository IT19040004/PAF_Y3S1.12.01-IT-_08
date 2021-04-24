package com;

import model.Register;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Regester")
public class RegisterService {
	Register registerObj = new Register();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return registerObj.readItems();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertRegister(@FormParam("name") String name, @FormParam("email") String email,
			@FormParam("password") String password, @FormParam("repassword") String repassword) {
		String output = registerObj.insertRegister(name, email, password, repassword);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateRegister(String itemData)
	{
	//Convert the input string to a JSON object
	 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
	//Read the values from the JSON object
	 String registerid = itemObject.get("registerid").getAsString();
	 String name = itemObject.get("name").getAsString();
	 String email = itemObject.get("email").getAsString();
	 String password = itemObject.get("password").getAsString();
	 String repassword = itemObject.get("repassword").getAsString();
	 String output = registerObj.updateRegister(registerid, name, email, password, repassword);
	return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String registerid = doc.select("registerid").text();
	 String output = registerObj.deleteRegister(registerid);
	return output;
	}

}
