package Runners;




import StepDefinition.stepDefinition;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/feature",
glue = {"StepDefinition"},
plugin = {"pretty","html:target/cucumber-html-report"})

public class TestRunner extends AbstractTestNGCucumberTests  {
	
}