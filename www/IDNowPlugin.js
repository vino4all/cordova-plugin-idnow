var exec = require('cordova/exec');

var idnow = {};
var pluginName = "IDnowPlugin";

idnow.startVideoIdent = function(companyId, transactionToken, apiHost, showVideoOverviewCheck, showErrorSuccessScreen, success, error) {
    exec(success, error, pluginName, "startVideoIdent", [companyId, transactionToken, apiHost, showVideoOverviewCheck, showErrorSuccessScreen]);
};

module.exports = idnow;