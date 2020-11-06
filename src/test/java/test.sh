#!/bin/sh
/Users/angela.duquino/Library/Android/sdk/emulator/emulator -avd Pixel_3_API_28 -port 5554 &
adb install -r ./apk/app.apk
source ~/.bash_profile
appium --address 127.0.0.1 --port 4723 --default-capabilities '{"deviceName": "Pixel 3 API 28", "udid": "emulator-5554", "platformName": "Android", "appPackage": "com.easysolutions.sdk.test", "appActivity": ".SplashActivity"}' &
sleep 3
mvn test
adb uninstall com.easysolutions.sdk.test