package stepDefinitions;

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

import static io.restassured.RestAssured.baseURI;
public class Author_Details {
	
	Response response;
	static int aid;
	static String aname,Autho_address,acountry;
	
	@Given("^Creating the author \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
	public void creating_the_author(String name, String address, String country) {
		
		Map<String,Object> request = new HashMap<String,Object>();
		request.put( "name", name);
		request.put( "address", address);
		request.put( "country", country);
		
		JSONObject Jsonreq = new JSONObject(request);
		
		baseURI	= "http://localhost:8080/";
        response =
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Content-Type","application/JSON")
			.body(Jsonreq.toJSONString())
		.when()
			.post("authors/author")
		.then()
			.statusCode(200)
			.log().all()
			.contentType(ContentType.JSON).extract().response();
		
		String Res = response.asString();
		JsonPath jp=new JsonPath(Res);
		aid=jp.getInt("authorId");
		aname = jp.get("name");
		Autho_address = jp.get("address");
		acountry = jp.get("country");
        System.out.println("Author Id created : "+aid);
        
        //Validations
        int author_Id = response.jsonPath().getInt("authorId");
        Assert.assertEquals(author_Id, aid);
        
         String Author_name = response.jsonPath().getString("name");
         Assert.assertEquals(Author_name, aname);
         
         String Author_address = response.jsonPath().getString("address");
         Assert.assertEquals(Author_address, Autho_address);
         
         String Author_country = response.jsonPath().getString("country");
         Assert.assertEquals(Author_country, acountry);
		
        
	}

	@Then("^Delete the author by authorId$")
	public void delete_the_author_by_authorId() {
		
		JSONObject request = new JSONObject();
		baseURI	= "http://localhost:8080/";
		RestAssured.defaultParser = Parser.JSON;
	    response = 
	    given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Content-Type","application/JSON")
			.body(request.toJSONString())
			.queryParam("authorId",aid)
	   .when()
			.delete("/authors/author")
	   .then()
			.statusCode(200)
			.log().all()
			.assertThat().body("message", equalTo("Successfully deleted author"))
			.extract().response();
	    
	    String Delete_Author_by_authorId = response.getBody().asString();
	    System.out.println("Delete_Author_by_authorId: "+Delete_Author_by_authorId);
	    System.out.println("AuthorId: "+aid);


		
	}

}
