package apiChainingEmployee;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndEmployeeTest {
	
	Response response;
	String BaseURI = "http://54.237.119.44:8088/employees" ;
	
	@Test
	public void test1() {
		response = GetMethodAll();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		response = PostMethod("Samuel", "Doctor", 2000,"samuel@abc.com" );
		Assert.assertEquals(response.getStatusCode(), 201);
		JsonPath jpath  = response.jsonPath(); 
		int EmployeeID = jpath.get("id");
        System.out.println("Employee id : "+EmployeeID);
        
        response = PutMethod(EmployeeID, "Samuel", "Dureja", 2000,"samuel@abc.com");
        Assert.assertEquals(response.getStatusCode(), 200);
        jpath = response.jsonPath(); 
        Assert.assertEquals(jpath.get("lastName"), "Dureja");
        
        response = DeleteMethod(EmployeeID);
        Assert.assertEquals(response.getStatusCode(),200);
        String ResponseBody = response.getBody().asString();
        Assert.assertEquals(ResponseBody, ""); 
        
        response = GetMethod(EmployeeID);
        Assert.assertEquals(response.getStatusCode(),400);

	}
	
public Response GetMethodAll() {
		
		RestAssured.baseURI = BaseURI ;
		RequestSpecification request = RestAssured.given(); 
		Response response = request.get();  
		
		return response;
	}

public Response PostMethod(String firstName, String lastName, int salary, String email) {
	
	RestAssured.baseURI = BaseURI ;
	
	JSONObject jobj = new JSONObject();
	jobj.put("firstName", firstName);
	jobj.put("lastName", lastName);
	jobj.put("salary", salary);
	jobj.put("email", email);
	
	
	RequestSpecification request = RestAssured.given();
	
	Response response =	request.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(jobj.toString())
				.post();
	
	return response;
}

public Response PutMethod(int EmpID, String firstName, String lastName, int salary, String email) {
	
	RestAssured.baseURI = BaseURI ;
	
	JSONObject jobj = new JSONObject();
	jobj.put("firstName", firstName);
	jobj.put("lastName", lastName);
	jobj.put("salary", salary);
	jobj.put("email", email);
	
	RequestSpecification request = RestAssured.given();
	
	Response response =	request.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(jobj.toString())
				.put("/" + EmpID);
	
	return response;
}

public Response DeleteMethod(int EmpID) {
	
	RestAssured.baseURI = BaseURI ;
	RequestSpecification request = RestAssured.given();
	Response response = request.delete("/" +EmpID);
	
	return response;
}

public Response GetMethod(int EmpID) {
	
	RestAssured.baseURI = BaseURI ;
	RequestSpecification request = RestAssured.given();
	Response response = request.get("/" +EmpID);
	
	return response;
}



}
