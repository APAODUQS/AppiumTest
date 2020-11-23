import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import entities.URLCodeResponse;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class ioSampleTest {

    public AndroidDriver<MobileElement> driverAndroid;
    public IOSDriver<IOSElement> driveriOS;
    public WebDriverWait waitAndroid;
    public WebDriverWait waitiOS;
    private final String user = "aduquino";
    private final Gson gson = new Gson();

    @BeforeMethod
    public void setup() throws MalformedURLException {
        DesiredCapabilities capsAndroid = new DesiredCapabilities();
        DesiredCapabilities capsiOS = new DesiredCapabilities();

        capsAndroid.setCapability("deviceName", "Pixel 3 API 28");
        capsAndroid.setCapability("udid", "emulator-5554");
        capsAndroid.setCapability("platformName", "Android");
        capsAndroid.setCapability("appPackage", "com.easysolutions.sdk.test");
        capsAndroid.setCapability("appActivity", ".SplashActivity");
//        capsiOS.setCapability("deviceName", "QA - iPhone XR - 14.0.1");
//        capsiOS.setCapability("udid", "00008020-000314E034D1002E");
//        capsiOS.setCapability("platformVersion", "14.0.1");
        capsiOS.setCapability("deviceName", "QA - iPhone X - 12.4.1");
        capsiOS.setCapability("udid", "d1130e5eb308addc6aa9c509a6748a9709302dac");
        capsiOS.setCapability("platformName", "iOS");
        capsiOS.setCapability("platformVersion", "12.4.1");
        capsiOS.setCapability("app", "/Users/angela.duquino/Documents/Idea/AppiumTest/app/app.ipa");
        capsiOS.setCapability("automationName", "XCUITest");
        capsiOS.setCapability("xcodeSigningId", "iPhone Developer");
        capsiOS.setCapability("xcodeOrgId", "MBXZA6Z65J");
        capsiOS.setCapability("bundleId", "com.easysolutions.did.implementation");
        capsiOS.setCapability("noReset", "false");

        driverAndroid = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capsAndroid);
        driveriOS = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4724/wd/hub"), capsiOS);
        System.out.println("Capabilities Android are: " + driverAndroid.getCapabilities());
        System.out.println("Capabilities iOS are: " + driverAndroid.getCapabilities());

        waitAndroid = new WebDriverWait(driverAndroid, 10);
        waitiOS = new WebDriverWait(driveriOS, 10);

        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:postgresql://IP:5432/detectidQA2", "U", "P");
            Statement stmt = c.createStatement();
            String sql_1 = "DELETE FROM mobile_token WHERE mobile_auth_device_id IN\n" +
                    "(SELECT mobile_auth_device_id FROM public.mobile_auth_device WHERE\n" +
                    "client_id IN (SELECT cli_id_client FROM client WHERE cli_shared_key = '" + user + "'))\n";
            System.out.println("Opened database successfully, executing query: " + sql_1);
            stmt.executeUpdate(sql_1);
            String sql_2 = "DELETE FROM public.mobile_auth_device WHERE client_id IN\n" +
                    "(SELECT cli_id_client FROM client WHERE cli_shared_key = '" + user + "')";
            System.out.println("Opened database successfully, executing query: " + sql_2);
            stmt.executeUpdate(sql_2);
            stmt.close();
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getCode() throws UnirestException {
        String URL = "http://192.168.243.189:8080/mobile-auth-service/api/v1/clients/" + user + "/activation/codes";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        String body = "{\"notify\": false }";
        HttpResponse responseURLCode = APIrest.postRequest(URL, header, body);
        URLCodeResponse urlCode = gson.fromJson(responseURLCode.getBody().toString(), URLCodeResponse.class);
        return urlCode.details.registrationUrl;
    }

    @Test
    public void firstTestAndroid() {
        waitAndroid.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
        driverAndroid.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")).click();
        waitAndroid.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
        waitAndroid.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.easysolutions.sdk.test:id/registroURL"))).click();
        waitAndroid.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.EditText"))).sendKeys("HOLA");
        waitAndroid.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("android:id/button1"))).click();
        String response = driverAndroid.findElement(By.xpath("/hierarchy/android.widget.Toast")).getText();
//        String response = driverAndroid.findElement(By.xpath("/hierarchy/android.widget.Toast[@text='400']")).getText();
        System.out.println("The response is: " + response);
        Assert.assertEquals(response, "500", "Bad Response: " + response);
    }

    @Test
    public void secondTestAndroid() throws UnirestException {
        String code = getCode();
        waitAndroid.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
        waitAndroid.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
        waitAndroid.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.easysolutions.sdk.test:id/registroURL"))).click();
        waitAndroid.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.EditText"))).sendKeys(code);
        waitAndroid.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("android:id/button1"))).click();
        waitAndroid.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.easysolutions.sdk.test:id/recycler"))).getText();
        String response = driverAndroid.findElement(By.xpath("/hierarchy/android.widget.Toast")).getText();
        System.out.println("The response is: " + response);
        Assert.assertEquals(response, "200", "Bad Response: " + response);
    }

    @Test
    public void firstTestiOS() {
        waitiOS.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("Allow"))).click();
        waitiOS.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//XCUIElementTypeButton[@name=\"URL\"]"))).click();
        waitiOS.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//XCUIElementTypeApplication[@name=\"Easy Solutions Bank\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeTextField"))).sendKeys("HOLA");
        waitiOS.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//XCUIElementTypeButton[@name=\"Aceptar\"]"))).click();
        String response = driveriOS.findElement(By.id("Error del sistema")).getText();
        System.out.println("The response is: " + response);
        Assert.assertEquals(response, "Error del sistema", "Bad Response: " + response);
        waitiOS.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("OK"))).click();
    }

    @Test
    public void secondTestiOS() throws UnirestException, InterruptedException {
        String code = getCode();
        waitiOS.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("Allow"))).click();
//     Alert alert = driver.switchto().alert - alert.accept();
        waitiOS.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//XCUIElementTypeButton[@name=\"URL\"]"))).click();
        waitiOS.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//XCUIElementTypeApplication[@name=\"Easy Solutions Bank\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeTextField"))).sendKeys(code);
        waitiOS.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//XCUIElementTypeButton[@name=\"Aceptar\"]"))).click();
        String response = waitiOS.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("Cuentas registradas"))).getText();
        System.out.println("The response is: " + response);
        Assert.assertEquals(response, "Cuentas registradas", "Bad Response: " + response);
        waitiOS.until(ExpectedConditions.visibilityOfElementLocated
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
        driveriOS.executeScript("mobile:scroll", scrollObject);
        driveriOS.executeScript("mobile:scroll", scrollObject);
        waitiOS.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//XCUIElementTypeApplication[@name=\"Easy Solutions Bank\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeTable/XCUIElementTypeCell[29]/XCUIElementTypeTextView"))).click();
//        deleteC, otp
    }

    @AfterMethod
    public void teardown() {
        driverAndroid.quit();
        driveriOS.quit();
    }
}
