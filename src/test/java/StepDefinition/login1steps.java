package StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import static utilities.common.*;

public class login1steps {

    public static final String EMAIL_FIELD = "/html/body/my-app/header/nav/div/my-login/div/form/div/input[1]";
    public static final String PASS_FIELD = "/html/body/my-app/header/nav/div/my-login/div/form/div/input[2]";
    public static final String LOGIN_BUTTON = "/html/body/my-app/header/nav/div/my-login/div/form/button";


    @Given("Input {string} and {string}")
    public void input_test_buggycar_mailinator_com_and_test_buggy(String userId, String Password) {
    //public void input_and(String string, String string2) {

        //this piece needs to go somewhere in common
        String path = System.getProperty("user.dir") + "/drivers/";
        System.setProperty("webdriver.gecko.driver", path + "windows/geckodriver.exe");
        driver = new FirefoxDriver();
        //

        driver.navigate().to(testURL);
        //waitForPageToBeReady();

        driver.findElement(By.xpath(EMAIL_FIELD)).sendKeys(userId);
        driver.findElement(By.xpath(PASS_FIELD)).sendKeys(Password);

    }

    @When("Click Login button")
    public void click_login_button() {
        logger.info("Credentials Entered.. Clicking Sign In Button");
        clickByXpath(LOGIN_BUTTON);
    }
    @Then("Logout button is displayed")
    public void button_is_displayed() throws Exception {

        //Assertion
        Assert.assertEquals(driver.getTitle(), "Buggy Cars Rating");
        Assert.assertEquals(elementVisible("xpath",LOGIN_BUTTON),true);

        driver.close();

        }

    }
