package stepDefinitions;

import static io.restassured.RestAssured.baseURI;



import org.json.simple.JSONObject;
import org.junit.Assert;




import static io.restassured.RestAssured.given;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;






public class Get_Book  {
	
	
	Response response;
    static int Bid,Pid, Aid,customer_Id,create_bookingId,save_bookingId,billing_Id,booking_Cost;
    static String BookName,cus_name,cus_address,cus_country,booking_Date,return_Date,billing_Address,zip_Code,State_s;

	@Given("^Create Book entry with \"(.*)\",\"(.*)\",\"(.*)\",\"(.*)\",\"(.*)\",\"(.*)\",\"(.*)\",\"(.*)\"$")
	public void create_Book_entry_with(String bn, int authorId, int publisherId, String authorName, String publisherName, String genre, int price, int numberOfPages) {
	    
		Map<String,Object> request = new HashMap<String,Object>();
		request.put("bookName", bn);
		request.put("authorId", authorId);
		request.put("publisherId", publisherId);
		request.put("authorName", authorName);
		request.put("publisherName", publisherName);
		request.put("genre", genre);
		request.put("price", price);
		request.put("numberOfPages", numberOfPages);
		
		JSONObject Jsonreq = new JSONObject(request);
		
		baseURI = "http://localhost:8080/";
		response =
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("ContentType","application/JSON")
			.body(Jsonreq.toJSONString())
		 .when()
			.post("/books/book")
		 .then()
		 	.statusCode(200)
		 	.log().all()
		 	.contentType(ContentType.JSON).extract().response();

		 	
		 	
		
		String Create_Book = response.asString();
		System.out.println("Create_Book: "+Create_Book);
		
		String Res = response.asString();
		JsonPath jp=new JsonPath(Res);
        Bid=jp.getInt("bookId");
        Pid = jp.getInt("publisherId");
        Aid = jp.getInt("authorId");
        BookName = jp.get("bookName");
        System.out.println("Book id created : "+Bid);
        System.out.println("Publisher id created : "+Pid);	
        System.out.println("Author id created : "+Aid);
        System.out.println("Book Name is: "+BookName);
        
       //Validations Response Body:
         int book_Id = response.jsonPath().getInt("bookId");
         Assert.assertEquals(book_Id, Bid);
         
         int publisher_Id = response.jsonPath().getInt("publisherId");
         Assert.assertEquals(publisher_Id, Pid);
         
         int author_Id = response.jsonPath().getInt("authorId");
         Assert.assertEquals(author_Id, Aid);
        
         String book_Name = response.jsonPath().getString("bookName");
         Assert.assertEquals(book_Name, BookName);
       
		
	}
		
	    @Then("^Get books details using Book Id$")
	    public void get_books_details_using_Book_Id() {
	    	
	    	JSONObject request = new JSONObject();
			baseURI	= "http://localhost:8080/";
			RestAssured.defaultParser = Parser.JSON;
			response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Content-Type","application/JSON")
			.body(request.toJSONString())
			.queryParam("bookId",Bid)
		.when()
			.get("/books/book")
		.then()
			.statusCode(200)
			.log().all()
		    .contentType(ContentType.JSON).extract().response();
			
			String Get_book_details_by_Id = response.getBody().asString();
			System.out.println("Get_book_details_by_Id: "+Get_book_details_by_Id);
			System.out.println("BookId: "+Bid);
			
		

	    	
	    }

	    @Then("^Get books details by PublisherId$")
	    public void get_books_details_by_PublisherId() {
	    	
	    	JSONObject request = new JSONObject();
			baseURI	= "http://localhost:8080/";
			 RestAssured.defaultParser = Parser.JSON;
			response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Content-Type","application/JSON")
			.body(request.toJSONString())
			.queryParam("publisherId",Pid)
		.when()
			.get("/books/publisherbooks")
		.then()
			.statusCode(200)
			.log().all()
			.extract().response();
			
			String Get_book_details_By_publisherId = response.getBody().asString();
			System.out.println("Get_book_details_By_publisherId: "+Get_book_details_By_publisherId);
			System.out.println("PublisherId: "+Pid);
	    	
	    }

	    @Then("^Get books deatails by authorId$")
	    public void get_books_deatails_by_authorId() {
	    	
	    	JSONObject request = new JSONObject();
			baseURI	= "http://localhost:8080/";
			 RestAssured.defaultParser = Parser.JSON;
			response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Content-Type","application/JSON")
			.body(request.toJSONString())
			.queryParam("authorId",Aid)
		.when()
			.get("/books/booksauthor")
		.then()
			.statusCode(200)
			.log().all()
			.extract().response();
			
			String Get_book_details_by_AuthorId = response.getBody().asString();
			System.out.println("Get_book_details_by_AuthorId: "+Get_book_details_by_AuthorId);
            System.out.println("AuthorId: "+Aid);

	    	
	    }
	    
	    
	    @Then("^Delete the book deatail by Book Id$")
	    public void delete_the_book_deatail_by_Book_Id() {
	    	
	    	JSONObject request = new JSONObject();
			baseURI	= "http://localhost:8080/";
			 RestAssured.defaultParser = Parser.JSON;
			response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Content-Type","application/JSON")
			.body(request.toJSONString())
			.queryParam("bookId",Bid)
		.when()
			.delete("/books/book")
		.then()
			.statusCode(200)
			.log().all()
			.assertThat().body("message", equalTo("Book Deleted Successfully"))
			.extract().response();
			
			String Delete_book_details_by_bookId = response.getBody().asString();
			System.out.println("Delete_book_details_by_bookId: "+Delete_book_details_by_bookId);
            System.out.println("Delete_Book_Id: "+Bid);
            
	    	
	    	
	    	
	    }
	    @Then("^Create the customer \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
		public void create_the_customer(String name, String address, String country) {
			
	    	Map<String,Object> request = new HashMap<String,Object>();
			request.put("name", name);
			//request.put("customerId", customerId);
			request.put("address", address);
			request.put("country", country);
			
			JSONObject Jsonreq = new JSONObject(request);
			
			baseURI = "http://localhost:8080/";
			response =
			given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("ContentType","application/JSON")
				.body(Jsonreq.toJSONString())
			 .when()
				.post("/customers/customer")
			 .then()
			 	.statusCode(200)
			 	.log().all()
			 	.extract().response();
			
			String Create_customer = response.getBody().asString();
			System.out.println("Create_customer: "+Create_customer);
			
			String Res = response.asString();
			JsonPath jp=new JsonPath(Res);
	        customer_Id=jp.getInt("customerId");
	        cus_name = jp.get("name");
	        cus_address = jp.get("address");
	        cus_country = jp.get("country");
	        
	        System.out.println("customerId created : "+customer_Id);
			
			System.out.println("bookId: "+Bid);
			
			//validations:
			int custo_Id = response.jsonPath().getInt("customerId");
			Assert.assertEquals(custo_Id, customer_Id);
			
			String custo_name = response.jsonPath().getString("name");
			Assert.assertEquals(custo_name, cus_name);
			
			String custo_address = response.jsonPath().getString("address");
			Assert.assertEquals(custo_address, cus_address);
			
			String custo_country = response.jsonPath().getString("country");
			Assert.assertEquals(custo_country, cus_country);
			
		}

		@Then("^Create the booking$")
		public void create_the_booking() {
		
			
			
			Map<String,Object> request = new HashMap<String,Object>();
			request.put("bookId", Bid);
			request.put("customerId", customer_Id);
			
			JSONObject Jsonreq = new JSONObject(request);
			
			baseURI = "http://localhost:8080/";
			response =
			given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("ContentType","application/JSON")
				.body(Jsonreq.toJSONString())
			 .when()
				.post("/bookings/booking")
			 .then()
			 	.statusCode(200)
			 	.log().all()
			 	.extract().response();
			
			String Res = response.asString();
			JsonPath jp=new JsonPath(Res);
	        create_bookingId=jp.getInt("bookingId");
	        booking_Date = jp.get("bookingDate");
	        return_Date = jp.get("returnDate");
			
	        System.out.println("create_bookingId: "+create_bookingId);
			String Create_booking = response.getBody().asString();
			System.out.println("Create_booking: "+Create_booking);
			
			//validations :
			int Expected_bookingId = response.jsonPath().getInt("bookingId");
			Assert.assertEquals(Expected_bookingId, create_bookingId);
			
			String Expected_bookingDate = response. jsonPath().getString("bookingDate"); 
			Assert.assertEquals(Expected_bookingDate, booking_Date);
			
			String Expected_returnDate = response.jsonPath().getString("returnDate");
			Assert.assertEquals(Expected_returnDate, return_Date);
	    	
			
		}
		@Then("^Create the SaveBooking$")
		public void create_the_SaveBooking() {
			

			Map<String,Object> request = new HashMap<String,Object>();
			request.put("bookId", Bid);
			request.put("customerId", customer_Id);
			
			System.out.println("BoohId: "+Bid);
			System.out.println("CustomerId: "+customer_Id);
			
			JSONObject Jsonreq = new JSONObject(request);
			
			baseURI = "http://localhost:8080/";
			response =
			given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("ContentType","application/JSON")
				.body(Jsonreq.toJSONString())
			 .when()
				.post("/bookings/booking")
			 .then()
			 	.statusCode(200)
			 	.log().all()
			 	.extract().response();
			
			String Res = response.asString();
			JsonPath jp=new JsonPath(Res);
	        save_bookingId=jp.getInt("bookingId");
	        
			System.out.println("save_bookingId: "+save_bookingId);
			String Save_booking = response.getBody().asString();
			System.out.println("Save_booking: "+Save_booking);
			
		}
		@Then("^Create the saveBilling \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
		public void create_the_saveBilling(String billingAddress, int bookingCost, int zipzode, String state) {
			

			Map<String,Object> request = new HashMap<String,Object>();
			request.put("billingAddress", billingAddress);
			request.put("bookingCost",bookingCost);
			request.put("zipzode", zipzode);
			request.put("state", state);
			request.put("bookingId", create_bookingId);
			request.put("customerId", customer_Id);
			
			JSONObject Jsonreq = new JSONObject(request);
			
			baseURI = "http://localhost:8080/";
			response =
			given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("ContentType","application/JSON")
				.body(Jsonreq.toJSONString())
			 .when()
				.post("/billings/billing")
			 .then()
			 	.statusCode(200)
			 	.log().all()
			 	.extract().response();
			
			String save_billing = response.getBody().asString();
			System.out.println("save_billing: "+save_billing);
			
			String Res = response.asString();
			JsonPath jp=new JsonPath(Res);
			billing_Id = jp.getInt("billingId");
			booking_Cost = jp.getInt("bookingCost");
			billing_Address = jp.get("billingAddress");
			zip_Code = jp.get("zipzode");
			State_s = jp.get("state");
			
			//validations:
			int Expected_billingId = response.jsonPath().getInt("billingId");
			Assert.assertEquals(Expected_billingId, billing_Id);
			
			int Expected_bookingCost = response.jsonPath().getInt("bookingCost");
     		Assert.assertEquals(Expected_bookingCost, booking_Cost);
			
			String Expected_billingAddress = response.jsonPath().getString("billingAddress");
			Assert.assertEquals(Expected_billingAddress, billing_Address);
			
			String Expected_zipcode = response.jsonPath().getString("zipzode");
			Assert.assertEquals(Expected_zipcode, zip_Code);
			
			String Expected_state = response.jsonPath().getString("state");
			Assert.assertEquals("It is  Telangana", Expected_state, State_s);
		
			
		}
		@Then("^Verify number of books hold by customer$")
		public void verify_number_of_books_hold_by_customer() {
			

	    	JSONObject request = new JSONObject();
			baseURI	= "http://localhost:8080/";
			 RestAssured.defaultParser = Parser.JSON;
			response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Content-Type","application/JSON")
			.body(request.toJSONString())
			.queryParam("customerId",customer_Id)
		.when()
			.get("/bookings/customer")
		.then()
			.statusCode(200)
			.log().all()
			.extract().response();
			
			String Number_of_hold_by_customer = response.getBody().print();
			System.out.println("Number_of_hold_by_customer: "+Number_of_hold_by_customer);
		}

	    
	
}


