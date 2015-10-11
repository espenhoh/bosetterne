/**
 * Created by Espen on 06.05.2015.
 */
/*global require, define */


require.config({
    baseUrl: 'app/js',
    paths: {
        jquery: 'libs/jquery',
        login_token: 'modules/login/login_token'
    }
});

require(['jquery'], function () {
    "use strict";
});

define(function require) {
    "use strict";
    var $ = require('jquery');
});