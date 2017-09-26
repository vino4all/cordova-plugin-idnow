# cordova-plugin-idnow
Cordova plugin for IDnow ID verification mobile SDKs which supports Andrid and iOS platforms. This repository wraps the IDnow Android and iOS SDKs to be used in Cordova project.

## Usage

To start the video identification, use the below code.
```
        var companyId = 'COMPANY_ID';
        var transactionToken = 'TST-ABCDE';
        var apiHost = 'https://gateway.test.idnow.de';
        var showVideoOverviewCheck = true;
        var showErrorSuccessScreen = true;
        IDNowPlugin.startVideoIdent(companyId, transactionToken, apiHost, showVideoOverviewCheck, showErrorSuccessScreen, successCallback, failureCallback);
```

Define your successcallback and failurecallback functions

```
function successCallback(message) {

    console.log("js successCallback method");
    console.log("Success Result from native sdk :" + message);
}

function failureCallback(message) {
    console.log("js failureCallback method");
    console.log("Failure Result from native sdk :" + message);
}
```

## References
Please refer the below repositories for more information on the SDK usage.

Android SDK: https://github.com/idnow/de.idnow.android

iOS SDK: https://github.com/idnow/de.idnow.ios

Android native sample project: https://github.com/idnow/de.idnow.android-sample

## Disclaimer
This plugin is not an official release by IDnow GmBH (https://www.idnow.eu/). This plugin is not affiliated to IDnow GmBH or any of its products or employees. All SDK content and services are represented as is and does not have any modifications to the original data and servies.

## License

Licensed under https://github.com/ShyykoSerhiy/skyweb/blob/master/LICENSE.md
