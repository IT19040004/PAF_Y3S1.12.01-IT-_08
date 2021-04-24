package com;
import model.Payment; 
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 
@Path("/Payment") 
public class PaymentService 
{ 
	Payment PaymentObj = new Payment(); 
@GET
@Path("/") 
@Produces(MediaType.TEXT_HTML) 
public String readPayment() 
{ 
return PaymentObj.readPayment(); 
}

@POST
@Path("/") 
@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
@Produces(MediaType.TEXT_PLAIN) 
public String insertPayment(@FormParam("ItemID") String ItemID, 
 @FormParam("quantity") String quantity, 
 @FormParam("payer") String payer, 
 @FormParam("paymentMethod") String paymentMethod,
 @FormParam("date") String date,
 @FormParam("amount") String amount) 
{ 
 String output = PaymentObj.insertPayment(ItemID, quantity, payer, paymentMethod,date,amount); 
return output; 
}

@PUT
@Path("/") 
@Consumes(MediaType.APPLICATION_JSON) 
@Produces(MediaType.TEXT_PLAIN) 
public String updatePayment(String itemData) 
{ 
//Convert the input string to a JSON object 
 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject(); 
//Read the values from the JSON object
 String paymentID = itemObject.get("paymentID").getAsString(); 
 String ItemID = itemObject.get("ItemID").getAsString(); 
 String quantity = itemObject.get("quantity").getAsString(); 
 String payer = itemObject.get("payer").getAsString(); 
 String paymentMethod = itemObject.get("paymentMethod").getAsString(); 
 String date = itemObject.get("date").getAsString(); 
 String amount = itemObject.get("amount").getAsString(); 
 String output = PaymentObj.updatePayment(paymentID, ItemID, quantity, payer, paymentMethod,date,amount); 
return output; 
}

@DELETE
@Path("/") 
@Consumes(MediaType.APPLICATION_XML) 
@Produces(MediaType.TEXT_PLAIN) 
public String deletePayment(String itemData) 
{ 
//Convert the input string to an XML document
 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
 
//Read the value from the element <itemID>
 String paymentID = doc.select("paymentID").text(); 
 String output = PaymentObj.deletePayment(paymentID); 
return output; 
}

}
