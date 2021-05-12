import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverConfig {

    public AppiumDriver<MobileElement> driver = null;
    private DesiredCapabilities caps = new DesiredCapabilities();

    private Capabilities setCapabilities() throws MalformedURLException {
        caps.setCapability("noReset", "false");
        driver = new AppiumDriver<MobileElement>(new URL(Constants.APPIUM_SERVER), caps);
        System.out.println("Capabilities are: " + driver.getCapabilities());
        return driver.getCapabilities();
    }

    public AppiumDriver<MobileElement> setDriver() throws MalformedURLException {
        Capabilities capabilities = setCapabilities();
        if (capabilities.getPlatform().name().equals(Constants.APPLE_OS)) {
//            caps.setCapability("deviceName", "QA - iPhone X - 12.4.1");
//            caps.setCapability("udid", "d1130e5eb308addc6aa9c509a6748a9709302dac");
//            caps.setCapability("platformName", "iOS");
//            caps.setCapability("platformVersion", "12.4.1");
//            caps.setCapability("app", "./app/app.ipa");
//            caps.setCapability("automationName", "XCUITest");
//            caps.setCapability("xcodeSigningId", "iPhone Developer");
//            caps.setCapability("xcodeOrgId", "MBXZA6Z65J");
//            caps.setCapability("bundleId", "com.appgate.authenticator");
            driver = new IOSDriver<MobileElement>(new URL(Constants.APPIUM_SERVER), caps);
        } else {
//            caps.setCapability("deviceName", "Pixel 3 API 28");
//            caps.setCapability("udid", "emulator-5554");
//            caps.setCapability("platformName", "Android");
//            caps.setCapability("appPackage", "com.cyxtera.authenticator.debug");
//            caps.setCapability("appActivity", "net.easysol.authenticator.splash.SplashActivity");
//            caps.setCapability("app", "./app/app.apk");
            driver = new AndroidDriver<MobileElement>(new URL(Constants.APPIUM_SERVER), caps);
        }

        return driver;
    }

    public WebDriverWait getDriverWait() {

        return new WebDriverWait(driver, Constants.TIMEOUT);
    }
}
