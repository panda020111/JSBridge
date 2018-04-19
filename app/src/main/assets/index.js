(function(){

    var btn = document.getElementById("btn");
    btn.addEventListener("click", function(e) {
        console.log("hello click the js");
        JSBridge.call("bridge", 'showToast', {'msg': 'Hello Bridge'}, function(res) {
            console.log(JSON.stringify(res));
        })
    });

})();