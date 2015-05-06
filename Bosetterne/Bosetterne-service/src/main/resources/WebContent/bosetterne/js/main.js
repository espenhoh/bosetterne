/**
 * Created by Espen on 06.05.2015.
 */

require.config({
    baseUrl: 'js',
    paths: {
        jquery: 'libs/jquery'
    }
});

require(['jquery'], function(){});

define(function(require) {
   var $ = require('jquery');
});