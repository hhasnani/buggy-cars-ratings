package testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
//@CucumberOptions(features = "Features/pageTitle",glue={"StepDefinition"},plugin= {"pretty","html:HTML-Reports"})
@CucumberOptions(features = "src/test/java/Features",glue={"StepDefinition"})
//runTests

public class runner {


}
