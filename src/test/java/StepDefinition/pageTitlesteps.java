package StepDefinition;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utilities.common;

import static utilities.common.*;


public class pageTitlesteps {

    @Given("Existing user or visitor")
    public void existing_user_or_visitor() throws Exception {
        logger.info("All good");
    }

    @When("Launch home page")
    public void launch_home_page() {

        //this piece needs to go somewhere in common
        String path = System.getProperty("user.dir") + "/drivers/";
        System.setProperty("webdriver.gecko.driver", path + "windows/geckodriver.exe");
        driver = new FirefoxDriver();

        driver.navigate().to(testURL);
        //waitForPageToBeReady();

    }

    @Then("Page is titled {string}")
    public void page_is_titled(String string) {

        String title = driver.getTitle();

        logger.info("Page Title: " + title);
        //Assertion
        Assert.assertEquals(driver.getTitle(), "Buggy Cars Rating");
        //waitForPageToBeReady();
        driver.close();
    }

}
