# Appium-test

## Information App
Get the name Activity
```
 adb shell dumpsys package ${APP_PACKAGE} | grep Activity get Activity
```

## Install Appium
http://appium.io/docs/en/about-appium/getting-started/

```
npm install -g appium
```

## Execute appium & Set capabilities
#### Android
```
appium --address ${APPIUM_IP} --port ${APPIUM_PORT} --default-capabilities '{"udid": "${DEVICE_ID}", "platformName": "Android", "appPackage": "${APP_PACKAGE}", "appActivity": "${APP_ACTIVITY}"}'
```
#### iOS
```
appium --address ${APPIUM_IP} --port ${APPIUM_PORT} --default-capabilities '{"deviceName": "${DEVICE_NAME}", "platformName": "iOS", "platformVersion": "${DEVICE_VERSION}", "udid": "${DEVICE_ID}", "automationName": "XCUITest", "xcodeSigningId": "iPhone Developer", "xcodeOrgId": "${XCODE_ORG_ID}", "bundleId": "${BUNDLE_ID}", "app": "${DIR_APP}/${APP}.ipa"}'
```

#### Information Capabilities
http://appium.io/docs/en/writing-running-appium/server-args/

http://appium.io/docs/en/writing-running-appium/default-capabilities-arg/

## Execute scripts into the device
http://appium.io/docs/en/commands/mobile-command/

## Run Tests
```
mvn test
```

## Run Tests using tags
```
mvn test -Dcucumber.filter.tags="(@TAG_1 or @TAG_2) and (not @TAG_3)"
```

### Information about using tags
https://cucumber.io/docs/cucumber/api/#tags


### Information about Selenium Commands
- https://www.selenium.dev/selenium-ide/docs/en/api/commands#click
- https://ui.vision/rpa/docs/selenium-ide
