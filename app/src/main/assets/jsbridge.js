(function(window) {

    var JSBRIDGE_PROTOCAL = "JSBridge";

    var JSBridge = {
        callbacks: {},
        call: function(obj, method, params, callback) {
            var port = Utils.getPort();
            this.callbacks[port] = callback;

            var uri = Utils.getUri(obj, method, params, port);
            console.log("method uri ===>" , uri);
            window.prompt(uri, "");
        },
        onFinish: function (port, jsonObj) {
            var callback = this.callbacks[port];
            callback && callback(jsonObj);
            delete this.callbacks[port];
        }
    }

    var Utils = {
        getPort: function () {
            return Math.floor(Math.random() *(1 << 30));
        },

        getUri: function (obj, method, params, port) {
            params = this.getParams(params);
            var uri = JSBRIDGE_PROTOCAL + "://" + obj + ":" + port + "/" + method + "?" + params;
            return uri;
        },

        getParams: function (obj) {
            if (obj && typeof obj == 'object') {
                return JSON.stringify(obj);
            } else {
                return '';
            }
        }

    }

    window.JSBridge = JSBridge;

})(window)