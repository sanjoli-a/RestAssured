package Apitesting.sampleproject;

import org.hamcrest.core.IsEqual;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;

import java.util.HashMap;
public class TestCases {

	ResponseSpecification res;
	@BeforeClass
	public void setSpecification() {
		res = RestAssured.expect();
		res.statusLine("HTTP/1.1 200 OK");
		res.contentType(ContentType.JSON);
		ExtentReportManager.createReport();
	}
	
	@Test
	public void testFetchUser() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("testFetchUser","Fetching the user details");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://reqres.in/");
		RestAssured.baseURI = "https://reqres.in/";
		given().when().get("api/users?page=2").then().statusCode(200);
	}
	@Test
	public void testGetUserPage() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("testPageUser","Getting page number");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://reqres.in/");
		RestAssured.baseURI = "https://reqres.in/";
		given().when().get("api/users?page=2").then().spec(res).assertThat().body("page",IsEqual.equalTo(2));
	}
	@Test
	public void testPostUsers() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("testPost","testing post of user");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://reqres.in/");
		RestAssured.baseURI = "https://reqres.in/";
		JSONObject params = new JSONObject();
		params.put("name", "Sanjoli");
		params.put("job", "QA");
		given().when().contentType("application/json").body(params).post("api/users").then().assertThat().body("name", IsEqual.equalTo("Sanjoli"));
	}
	@Test
	public void testLoginUnsuccessful() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("testLoginUnsuccessful","testing post of unsuccessful login");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://reqres.in/");
		RestAssured.baseURI = "https://reqres.in/";
		JSONObject params = new JSONObject();
		params.put("email", "eve@reqres");
		given().when().contentType("application/json").body(params).post("api/login").then().assertThat().statusCode(400).body("error", IsEqual.equalTo("Missing password"));
	}
	
	@Test
	public void testGetResourceNotFound() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("testGetResourceNotFound","testing get of resource not found");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://reqres.in/");
		ExtentReportManager.test = ExtentReportManager.reports.startTest("testGetResourceNotFound","Fetching the user details");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://reqres.in/");
		RestAssured.baseURI = "https://reqres.in/";
		given().when().get("api/unknown/23").then().statusCode(404);
	}
	
	@Test
	public void testGetList() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("testGetList","get list of users");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://reqres.in/");
		RestAssured.baseURI = "https://reqres.in/";
		given().when().get("api/unknown").then().statusCode(200).spec(res).assertThat().body("page",IsEqual.equalTo(1))
		.body("total_pages",IsEqual.equalTo(2)).body("data[0].name",IsEqual.equalTo("cerulean"));
	}
	
	@AfterClass
	public void closeReport() {
		ExtentReportManager.reports.flush();
		
	}
}
