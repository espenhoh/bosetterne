/**
 * Created by espen on 13.05.14.
 */


(function() {
    if (navigator.userAgent.indexOf('(Dart)') === -1) {
        top.location.replace("ressurser/restricted.html");
    }
})();