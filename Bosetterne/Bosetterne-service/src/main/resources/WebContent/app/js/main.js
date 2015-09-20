/**
 * Created by Espen on 06.05.2015.
 */
/* global: require */

require.config({
    baseUrl: 'app/js',
    paths: {
        jquery: 'libs/jquery',
        login_token: 'modules/login/login_token'
    }
});

require(['jquery'], function(){});

define(function(require) {
   var $ = require('jquery');
});