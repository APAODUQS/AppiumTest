# Appium-test

### Run Tests
Start the emulator *Pixel 3 API 28* and execute:
```
sh ./src/test/java/test.sh
``` 
### Information App
Get the name Activity
```
 adb shell dumpsys package com.easysolutions.sdk.test | grep Activity
```

### Information Capabilities
http://appium.io/docs/en/writing-running-appium/server-args/   -> --dc, --default-capabilities
http://appium.io/docs/en/writing-running-appium/default-capabilities-arg/  -> --default-capabilities [ '{"app": "myapp.app", "deviceName": "iPhone Simulator"}' | /path/to/caps.json ]