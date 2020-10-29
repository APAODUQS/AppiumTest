source ~/.bash_profile
appium --address 127.0.0.1 --port 4723 --default-capabilities '{"deviceName": "Pixel 3 API 28", "udid": "emulator-5554", "platformName": "Android", "appPackage": "com.easysolutions.sdk.test", "appActivity": ".SplashActivity"}' &
sleep 3
mvn test