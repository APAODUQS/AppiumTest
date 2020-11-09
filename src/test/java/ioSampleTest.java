import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import entities.URLCodeResponse;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
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

import static java.lang.Thread.sleep;

public class ioSampleTest {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;
    private final String user = "testing";
    private final Gson gson = new Gson();

    @BeforeMethod
    public void setup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setCapability("deviceName", "Pixel 3 API 28");
//        caps.setCapability("udid", "emulator-5554");
//        caps.setCapability("platformName", "Android");
//        caps.setCapability("appPackage", "com.easysolutions.sdk.test");
        // adb shell dumpsys package com.easysolutions.sdk.test | grep Activity
//        caps.setCapability("appActivity", ".SplashActivity");
        caps.setCapability("noReset", "false");
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait = new WebDriverWait(driver, 10);

        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:postgresql://IP:5432/DATABASE","", "");
            Statement stmt = c.createStatement();
            String sql_1 = "DELETE FROM mobile_token WHERE mobile_auth_device_id IN\n" +
                    "(SELECT mobile_auth_device_id FROM public.mobile_auth_device WHERE\n" +
                    "client_id IN (SELECT cli_id_client FROM client WHERE cli_shared_key = 'testing'))\n";
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


    @Test
    public void firstTest() {
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
//        driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.easysolutions.sdk.test:id/registroURL"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.EditText"))).sendKeys("HOLA");
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("android:id/button1"))).click();
        String response = driver.findElement(By.xpath("/hierarchy/android.widget.Toast")).getText();
//        String response = driver.findElement(By.xpath("/hierarchy/android.widget.Toast[@text='400']")).getText();
        System.out.println("The response is: " + response);
        Assert.assertEquals(response, "500", "Bad Response: " + response);
    }

    @Test
    public void secondTest() throws UnirestException, InterruptedException {
        String URL = "http://192.168.243.189:8081/mobile-auth-service/api/v1/clients/" + user + "/activation/codes";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        String body = "{\"notify\": false }";
        HttpResponse responseURLCode = APIrest.postRequest(URL, header, body);
        URLCodeResponse urlCode = gson.fromJson(responseURLCode.getBody().toString(), URLCodeResponse.class);
        String code = urlCode.details.registrationUrl;

        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.easysolutions.sdk.test:id/registroURL"))).click();
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


    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
