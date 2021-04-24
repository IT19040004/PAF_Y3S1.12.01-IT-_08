package com;

import model.Fund;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Fund")
public class FundService {
	Fund fundObj = new Fund();

	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readItems() 
	 { 
	 return fundObj.readItems(); 
	 }
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertFund(@FormParam("name") String name, 
	 @FormParam("nic") String nic, 
	 @FormParam("address") String address,
	 @FormParam("phone") String phone,
	 @FormParam("email") String email,
	 @FormParam("amount") String amount,
	@FormParam("description") String description)
	{ 
	 String output = fundObj.insertFund(name, nic, address, phone,email,amount,description); 
	return output; 
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateFund(String fundData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject fundObject = new JsonParser().parse(fundData).getAsJsonObject(); 
	//Read the values from the JSON object
	 String fundID = fundObject.get("fundID").getAsString(); 
	 String name = fundObject.get("name").getAsString(); 
	 String nic = fundObject.get("nic").getAsString(); 
	 String address = fundObject.get("address").getAsString();
	 String phone = fundObject.get("phone").getAsString();
	 String email = fundObject.get("email").getAsString();
	 String amount = fundObject.get("amount").getAsString();
	 String description = fundObject.get("description").getAsString();
	 String output = fundObj.updateFund(fundID, name, nic, address,phone, email,amount,description); 
	return output; 
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteFund(String fundData) 
	{ 
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(fundData, "", Parser.xmlParser()); 
	 
	//Read the value from the element <itemID>
	 String fundID = doc.select("fundID").text(); 
	 String output = fundObj.deletefunds(fundID); 
	return output; 
	}


}
