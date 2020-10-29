import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.Thread.sleep;

public class ioSampleTest {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;
//    source ~/.bash_profile
    // appium --address 127.0.0.1 --port 4723
    // http://appium.io/docs/en/writing-running-appium/server-args/   -> -dc, --default-capabilities
    // http://appium.io/docs/en/writing-running-appium/default-capabilities-arg/  -> --default-capabilities [ '{"app": "myapp.app", "deviceName": "iPhone Simulator"}' | /path/to/caps.json ]
    // --platform-name --platform-version --device-name -U, --udid	 --app-pkg --app-activity --app

    @BeforeMethod
    public void setup () throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Pixel 3 API 28");
        caps.setCapability("udid", "emulator-5554");
        caps.setCapability("platformName", "Android");
        caps.setCapability("appPackage", "com.easysolutions.sdk.test");
        // adb shell dumpsys package com.easysolutions.sdk.test | grep Activity
        caps.setCapability("appActivity", ".SplashActivity");
        caps.setCapability("noReset","false");
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),caps);
        wait = new WebDriverWait(driver, 10);
    }


    @Test
    public void firstTest() {
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        wait.until(ExpectedConditions.visibilityOfElementLocated
//                (By.id("com.android.packageinstaller:id/permission_allow_button"))).click();
//        driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")).click();
        driver.findElement(By.id("com.easysolutions.sdk.test:id/registroURL")).click();
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void basicTest () throws InterruptedException {
//        //Click and pass Splash
//        wait.until(ExpectedConditions.visibilityOfElementLocated
//                (By.id("com.isinolsun.app:id/animation_view"))).click();
//
//        //Click I am searching a job
//        wait.until(ExpectedConditions.visibilityOfElementLocated
//                (By.id("com.isinolsun.app:id/bluecollar_type_button"))).click();
//
//
//        //Notification Allow
//        if (driver.findElements(By.id("com.android.packageinstaller:id/permission_allow_button")).size()>0) {
//            driver.findElements(By.id("com.android.packageinstaller:id/permission_allow_button")).get(0).click();
//        }
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated
//                (By.xpath(secondNewJob)));
//    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }
}
