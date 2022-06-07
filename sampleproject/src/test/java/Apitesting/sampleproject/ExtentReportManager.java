package Apitesting.sampleproject;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentReportManager {
	public static ExtentTest test;
	public static ExtentReports reports;
	
	public static void createReport() {
		reports = new ExtentReports(System.getProperty("user.dir")+"/test-output/extentreport.html");
	}
}
