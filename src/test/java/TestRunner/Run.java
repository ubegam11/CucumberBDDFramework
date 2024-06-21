package TestRunner;
//below are needed when we run by jnuit
//import org.junit.runner.RunWith;
//import io.cucumber.tes.Cucumber;
//import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

import io.cucumber.testng.CucumberOptions;


//@RunWith(Cucumber.class)
@CucumberOptions(features = {"C:\\Users\\91790\\eclipse-workspace\\CucumberBDDFramework_CodeStudio\\Features\\Customer.feature", 
"C:\\Users\\91790\\eclipse-workspace\\CucumberBDDFramework_CodeStudio\\Features\\Login.feature"},
                  glue = {"StepDefinition" },
                  monochrome = true, //to make the output in readable condition
                  dryRun = false,
                 // tags="@sanity and @regression",
                //tags="@sanity or @regression",
                tags="@sanity",
				// plugin= {"pretty","html:target/cucumberReport/html_report.html"}
				//plugin = { "pretty","json:target/cucumberReport/json_report.json"}
				//plugin = { "pretty","junit:target/cucumberReport/xml_report.xml"}
                plugin="com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
		)

public class Run extends AbstractTestNGCucumberTests{

}
