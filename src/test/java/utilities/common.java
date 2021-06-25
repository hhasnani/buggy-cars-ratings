package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.FileInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import static org.junit.Assert.fail;

public class common {

    public  static  String testURL = getConfigValue("url");
    //public static String browser= "firefox";

    public static WebDriver driver;
    public static final Logger logger = LoggerFactory.getLogger(common.class);


    public static void waitForPageToBeReady() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //This loop can run for 120 times to check If page Is ready after every 0.5 second.
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            //To check page ready state.
            if (js.executeScript("return document.readyState").toString().equals("complete")) {
                break;
            }
        }
    }


    public static String getConfigValue(String key) {
        Properties config = new Properties();

        try {
            String filename = "buggycarratings.properties";
            if (System.getProperty("os.name").contains("Win")) {
                filename = System.getProperty("user.dir") + "//" + filename;
            } else {
                filename = "buggycarratings.properties";
            }
            config.load(new FileInputStream(filename));
        } catch (Throwable t) {
            System.out.print("Issue loading properties file");
            t.printStackTrace();
        }
        return config.getProperty(key);
    }

    public static void setSystemProperties() {

        String path = System.getProperty("user.dir") + "/drivers/";
        logger.info("Driver Path : " + path);
        if (System.getProperty("os.name").contains("Windows 10")) {
            logger.info("Got Windows 10");
            System.setProperty("webdriver.edge.driver", path + "windows/MicrosoftWebDriver.exe");
            System.setProperty("webdriver.ie.driver", path + "windows/MicrosoftWebDriver.exe");
            System.setProperty("webdriver.chrome.driver", path + "windows/chromedriver.exe");
            System.setProperty("webdriver.gecko.driver", path + "windows/geckodriver.exe");
        }
        else if (System.getProperty("os.name").contains("Windows 7")) {
            logger.info("Got Windows 7");
            //System.setProperty("java.net.preferIPv4Stack", "true");
            System.setProperty("webdriver.ie.driver", path + "windows/IEDriverServer.exe --port=5555 --host=localhost");
            System.setProperty("webdriver.chrome.driver", path + "windows/chromedriver.exe");
            System.setProperty("webdriver.gecko.driver", path + "windows/geckodriver.exe");
        } else if (System.getProperty("os.name").contains("Mac")) {
            System.setProperty("webdriver.chrome.driver", path + "mac/chromedriver");
            System.setProperty("webdriver.gecko.driver", path + "mac/geckodriver");
        } else {
            System.setProperty("webdriver.chrome.driver", path + "linux/chromedriver");
            System.setProperty("webdriver.gecko.driver", path + "linux/geckodriver");
        }
    }

    private static String getBrowser() {
        Random ran = new Random();
        int intRandom = ran.nextInt(1);
        String strBrowser = "chrome";

        logger.info("Select browser - Random (0-Firefox; 2-Chrome): " + intRandom);

        intRandom=0;
        if (intRandom == 0) { strBrowser = "firefox"; }
        if (intRandom == 1) { strBrowser = "chrome"; }

        return strBrowser;
    }

    public static WebDriver InitiateDriver() throws Exception {

        String browser= "firefox";


        logger.info("Initiating WebDriver");

        browser = getBrowser();
        logger.info("Browser is : " + browser);


        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions option = new ChromeOptions();
                // Disable extensions and hide infobars
                option.setExperimentalOption("useAutomationExtension", false);
                //option.addArguments("--disable-extensions");
                option.addArguments("disable-infobars");

                Map<String, Object> prefs = new HashMap<>();

                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                option.setExperimentalOption("prefs", prefs);

                driver = new ChromeDriver(option);
                break;

            case "ie":
                driver = new InternetExplorerDriver();
                driver.get("webui.ssnnzcp.co.nz");
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "firefox":
                /*//http://www.seleniumeasy.com/selenium-tutorials/firefoxoptions-running-webdriver-tests
                FirefoxProfile profile = new FirefoxProfile();
                //Set a preference for this particular profile.
                //profile.setPreference('dom.ipc.plugins.enabled.libflashplayer.so', 'true');
                profile.setPreference("plugin.state.flash", 2);
                profile.setPreference("security.insecure_field_warning.contextual.enabled",0);
                profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so","true");
                profile.setPreference("browser.cache.disk.enable", false);
                profile.setPreference("media.eme.enabled", true);
                System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, ".\\firefox_logs.txt");
                //profile.setAcceptUntrustedCertificates(true);
                //profile.setAssumeUntrustedCertificateIssuer(false);

                FirefoxOptions ffoptions = new FirefoxOptions();
                ffoptions.setProfile(profile);
                driver = new FirefoxDriver(ffoptions);*/
                String path = System.getProperty("user.dir") + "/drivers/";
                System.setProperty("webdriver.gecko.driver", path + "windows/geckodriver.exe");
                driver = new FirefoxDriver();

                break;
            case "safari":
                SafariOptions options = new SafariOptions();
                options.setUseTechnologyPreview(true);
                driver = new SafariDriver(options);
                break;

        }

        driver.manage().window().maximize();

        return driver;
    }

    public static void clickByXpath(String xpath) {
        new WebDriverWait(driver,20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).click();
    }

    public static boolean elementVisible(String findByType, String findBy) throws Exception {
        // findByType = xpath ; id ; classname
        //logger.info("In elementVisible...");
        boolean elementPresent = false;
        //driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        //WebElement weProgress = new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.ProgressBar")));

        try {
            switch (findByType.toLowerCase()) {
                case "xpath":
                    //WebElement wePresent = new WebDriverWait(driver, 0).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(findBy)));
                    new WebDriverWait(driver, 0).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(findBy)));
                    //driver.findElement(By.xpath(findBy));
                    elementPresent = true;
                    break;

                case "id":
                    //WebElement wePresent = new WebDriverWait(driver, 0).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(findBy)));
                    new WebDriverWait(driver, 0).until(ExpectedConditions.visibilityOfElementLocated(By.id(findBy)));
                    //driver.findElement(By.id(findBy));
                    elementPresent = true;
                    break;

                case "classname":
                    //driver.findElement(By.className(findBy));
                    new WebDriverWait(driver, 0).until(ExpectedConditions.visibilityOfElementLocated(By.className(findBy)));
                    elementPresent = true;
                    break;
            }
            return elementPresent;

        } catch (Exception e) {
            logger.info("Element not visible; Type and Path: " + findByType + findBy + " elementVisible Exception : " + e);
            return false;
        }
    }


}
