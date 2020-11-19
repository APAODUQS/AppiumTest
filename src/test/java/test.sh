#!/bin/sh
#RUN ANDROID
#/Users/angela.duquino/Library/Android/sdk/emulator/emulator -avd Pixel_3_API_28 -port 5554 &
##adb install -r ./app/app.apk
#source ~/.bash_profile
#appium --address 127.0.0.1 --port 4723 --default-capabilities '{"deviceName": "Pixel 3 API 28", "udid": "emulator-5554", "platformName": "Android", "appPackage": "com.easysolutions.sdk.test", "appActivity": ".SplashActivity"}' &
#sleep 3
#mvn test
##adb uninstall com.easysolutions.sdk.test
#exit

# RUN IOS
#xcrun simctl boot 02A9FB78-C7E1-469A-9F95-20B8C7F313F0
#open /Applications/Xcode.app/Contents/Developer/Applications/Simulator.app
#ideviceinstaller -i ./app/app.ipa
appium --address 127.0.0.1 --port 4724 --default-capabilities '{"deviceName": "QA - iPhone XR - 14.0.1", "platformName": "iOS", "platformVersion": "14.0.1", "udid": "00008020-000314E034D1002E", "automationName": "XCUITest", "xcodeSigningId": "iPhone Developer", "xcodeOrgId": "MBXZA6Z65J", "bundleId": "com.easysolutions.did.implementation", "app": "/Users/angela.duquino/Documents/Idea/AppiumTest/app/app.ipa"}' &
sleep 3
mvn test
#ideviceinstaller -l #LIST
#ideviceinstaller --uninstall com.easysolutions.did.implementation
