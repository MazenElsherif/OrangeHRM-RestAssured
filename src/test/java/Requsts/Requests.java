package Requsts;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import org.hamcrest.collection.HasItemInArray;
import org.hamcrest.core.IsEqual;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import Utilties.Constants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import static org.hamcrest.Matchers.hasItems;

import io.restassured.response.Response;

public  class Requests {
	String baseURI = Constants.BASE_URL;
	public String orangeHRM;
	public String csrfToken;
	public String ValidateorangeHRM;
	public int deletedId;
	public void Generate_Token() {
		Response res= given().baseUri(baseURI)
				.when().get("/auth/login");
				res.then().statusCode(200).log().all();
				String html = res.getBody().asString();
		        Document document = Jsoup.parse(html);
		         csrfToken = document.select("auth-login").attr(":token");
		        csrfToken = csrfToken.substring(1, csrfToken.length() - 1);

		        // Print the CSRF token value without quotes
		        System.out.println("token" + csrfToken);
		         orangeHRM = res.getCookie("orangehrm");
		        System.out.println("first cokkie   " + orangeHRM);
		        
	}
	public void Generate_cookie() {
		Response res= given().baseUri(baseURI).contentType(ContentType.URLENC).header("Cookie", "orangehrm="+orangeHRM).
	            formParams("_token",csrfToken
	            		, "username", "Admin","password","admin123")
				.when().post("/auth/validate");
				res.then().statusCode(302).log().all();
		   ValidateorangeHRM = res.getCookie("orangehrm");
	     System.out.println("sec cookie  " + ValidateorangeHRM);
				
	}
	public void getCandidtes() {
		
		Response response =
			    given().baseUri(baseURI).header("Cookie","orangehrm="+ValidateorangeHRM)
			    .param("limit", 50)
			    .param("offset", 0)
			    .param("model", "list")
			    .param("sortField", "candidate.dateOfApplication")
			    .param("sortOrder", "DESC")
			    .get("/api/v2/recruitment/candidates");
		response.then().statusCode(200).log().all();
		System.out.println("Response Body: " + response.getBody().asString());
		deletedId=response.jsonPath().getInt("data[0].id");
		System.out.println("ID: " + deletedId);

		
	}
	public void Delete() {
		HashMap<String, Object> data = new HashMap(); 
		 int[] ids = {deletedId};
		data.put("ids",ids);
		Response res= given().baseUri(baseURI).log().all().contentType(ContentType.JSON).header("Cookie", "orangehrm="+ValidateorangeHRM).
				body(data).when().delete("/api/v2/recruitment/candidates")
				.then().extract().response();
		res.then().assertThat().statusCode(200).body("data", contains(deletedId)).log().all();
		System.out.println("Response Body: " + res.getBody().asString());
				
	}
	public void Add() {
		
		File payLoad=new File("src/test/resources/payload.json");
		Response res= given().baseUri(baseURI).log().all().contentType(ContentType.JSON).header("Cookie", "orangehrm="+ValidateorangeHRM).
				body(payLoad).when().post("/api/v2/recruitment/candidates")
				.then().extract().response();
		res.then().assertThat().statusCode(200);
		System.out.println("Response Body: " + res.getBody().asString());
				
	}
public void Search(int limit,int vacancyId,String sortOrder) {
	
	Response response =
		    given().baseUri(baseURI).header("Cookie","orangehrm="+ValidateorangeHRM).log().all()
		    .queryParam("limit", limit)
		    .queryParam("offset", 0)
		    .queryParam("vacancyId", vacancyId)
		    .queryParam("model", "list")
		    .queryParam("sortField", "candidate.dateOfApplication")
		    .queryParam("sortOrder", sortOrder)
		    .get("/api/v2/recruitment/candidates");
	response.then().assertThat().statusCode(200).body("data.vacancy.name", everyItem(equalTo("Software Engineer"))).log().all();

	System.out.println("Response Body: " + response.getBody().asString());
}





}
