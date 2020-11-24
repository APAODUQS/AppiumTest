import com.mashape.unirest.http.exceptions.UnirestException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ioSampleTest {

    public AppiumDriver<MobileElement> driver = null;
    private DesiredCapabilities caps = new DesiredCapabilities();
    private WebDriverWait wait;
    private final String user = "aduquino";
    private Logic logic = new Logic();
    private Capabilities capabilities;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        caps.setCapability("noReset", "false");
        driver = new AppiumDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        System.out.println("Capabilities are: " + driver.getCapabilities());
        capabilities = driver.getCapabilities();
        logic.cleanDB(user);
        setDriver();
    }

    public void setDriver() throws MalformedURLException {
        if (capabilities.getPlatform().name().equals("MAC")) {
//            caps.setCapability("deviceName", "QA - iPhone X - 12.4.1");
//            caps.setCapability("udid", "d1130e5eb308addc6aa9c509a6748a9709302dac");
//            caps.setCapability("platformName", "iOS");
//            caps.setCapability("platformVersion", "12.4.1");
//            caps.setCapability("app", "/Users/angela.duquino/Documents/Idea/AppiumTest/app/app.ipa");
//            caps.setCapability("automationName", "XCUITest");
//            caps.setCapability("xcodeSigningId", "iPhone Developer");
//            caps.setCapability("xcodeOrgId", "MBXZA6Z65J");
//            caps.setCapability("bundleId", "com.easysolutions.did.implementation");
            driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        } else {
//            caps.setCapability("deviceName", "Pixel 3 API 28");
//            caps.setCapability("udid", "emulator-5554");
//            caps.setCapability("platformName", "Android");
//            caps.setCapability("appPackage", "com.easysolutions.sdk.test");
//            caps.setCapability("appActivity", ".SplashActivity");
            driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        }
        wait = new WebDriverWait(driver, 10);
    }

    public void bkAndroid() {
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
        driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.easysolutions.sdk.test:id/registroURL"))).click();
    }

    @Test
    public void firstTestAndroid() {
        if (capabilities.getPlatform().name().equals("LINUX")) {
            bkAndroid();
            wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.EditText"))).sendKeys("HOLA");
            wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.id("android:id/button1"))).click();
            String response = driver.findElement(By.xpath("/hierarchy/android.widget.Toast")).getText();
//        String response = driverAndroid.findElement(By.xpath("/hierarchy/android.widget.Toast[@text='400']")).getText();
            System.out.println("The response is: " + response);
            Assert.assertEquals(response, "500", "Bad Response: " + response);
        }
    }

    @Test
    public void secondTestAndroid() throws UnirestException {
        if (capabilities.getPlatform().name().equals("LINUX")) {
            String code = logic.getCode(user);
            bkAndroid();
            wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.EditText"))).sendKeys(code);
            wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.id("android:id/button1"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.id("com.easysolutions.sdk.test:id/recycler"))).getText();
            String response = driver.findElement(By.xpath("/hierarchy/android.widget.Toast")).getText();
            System.out.println("The response is: " + response);
            Assert.assertEquals(response, "200", "Bad Response: " + response);
        }
    }

    public void bkiOS() {
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("Allow"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//XCUIElementTypeButton[@name=\"URL\"]"))).click();
        //     Alert alert = driver.switchto().alert - alert.accept();
    }

    @Test
    public void firstTestiOS() {
        if (capabilities.getPlatform().name().equals("MAC")) {
            bkiOS();
            wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.xpath("//XCUIElementTypeApplication[@name=\"Easy Solutions Bank\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeTextField"))).sendKeys("HOLA");
            wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.xpath("//XCUIElementTypeButton[@name=\"Aceptar\"]"))).click();
            String response = driver.findElement(By.id("Error del sistema")).getText();
            System.out.println("The response is: " + response);
            Assert.assertEquals(response, "Error del sistema", "Bad Response: " + response);
            wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.id("OK"))).click();
        }
    }

    @Test
    public void secondTestiOS() throws UnirestException, InterruptedException {
        if (capabilities.getPlatform().name().equals("MAC")) {
            String code = logic.getCode(user);
            bkiOS();
            wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.xpath("//XCUIElementTypeApplication[@name=\"Easy Solutions Bank\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeTextField"))).sendKeys(code);
            wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.xpath("//XCUIElementTypeButton[@name=\"Aceptar\"]"))).click();
            String response = wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.id("Cuentas registradas"))).getText();
            System.out.println("The response is: " + response);
            Assert.assertEquals(response, "Cuentas registradas", "Bad Response: " + response);
            wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.id("menu"))).click();
//        TouchActions action = new TouchActions(driver);
//        Dimension size = driver.manage().window().getSize();
//        int startX = size.width / 2;
//        int endY = (int) (size.height * .2);
//        action.scroll(startX, endY);
//        action.perform();
            //https://appiumpro.com/editions/30-ios-specific-touch-action-methods
            HashMap<String, String> scrollObject = new HashMap<String, String>();
            scrollObject.put("direction", "down");
            driver.executeScript("mobile:scroll", scrollObject);
            driver.executeScript("mobile:scroll", scrollObject);
            wait.until(ExpectedConditions.visibilityOfElementLocated
                    (By.xpath("//XCUIElementTypeApplication[@name=\"Easy Solutions Bank\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeTable/XCUIElementTypeCell[29]/XCUIElementTypeTextView"))).click();
//        deleteC, otp
        }
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
