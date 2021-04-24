package com;

import model.Cart;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Cart")
public class CartService {
	Cart cartObj = new Cart();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readCart() {
		return cartObj.readCart();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertCart(
			@FormParam("itemId") String itemId, 
			@FormParam("itemName") String itemName,
			@FormParam("itemPrice") String itemPrice, 
			@FormParam("quantity") String quantity,
			@FormParam("description") String description)
	{
		String output = cartObj.insertCart(itemId, itemName,itemPrice, quantity, description);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCart(String itemData)
	{
	//Convert the input string to a JSON object
	 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
	//Read the values from the JSON object
	 String cartId = itemObject.get("cartId").getAsString();
	 String itemId = itemObject.get("itemId").getAsString();
	 String itemName = itemObject.get("itemName").getAsString();
	 String itemPrice = itemObject.get("itemPrice").getAsString();
	 String quantity = itemObject.get("quantity").getAsString();
	 String description = itemObject.get("description").getAsString();
	 String output = cartObj.updateCart(cartId, itemId, itemName, itemPrice, quantity,description);
	return output;
	}
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCart(String itemData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String cartId = doc.select("cartId").text();
	 String output = cartObj.deleteCart(cartId);
	return output;
	}


}