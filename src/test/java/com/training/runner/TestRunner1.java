package com.training.runner;




import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;


import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "/Users/Ravi/Desktop/Selenium/TestNG_Maven/BDDCucumberRestAssured_Framework/src/main/java/com/training/features/book.feature" //path of the feature files
		,glue = {"stepDefinitions"} // path of the stepDefinition files
		,format = {"pretty","html:test-output","json:json-output/cucumber.json","junit:junit-xml/cucumber.xml"}// To generate different types of reporting
		,monochrome = true // To display the output proper readable format
		,strict = true // It will check if step is not defined in stepDefinition file
		,dryRun = false // To check mapping is proper between feature files and stepDefinition files
	    ,plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/Extentreport.html"}
		//,tags= {"@Publisher_details"}
		
		)







public class TestRunner1 {
	
	@AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig("src/extent-config.xml");
    }

}
