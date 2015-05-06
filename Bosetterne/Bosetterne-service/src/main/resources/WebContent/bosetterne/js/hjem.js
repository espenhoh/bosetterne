/*global require, $ */


require(['main'], function() {
    'use strict';
    require(['jquery', 'modules/Carousel'], function($, Carousel) {

    });
});


import 'modules/login/login_token.dart' as token;

void main(){
    token.main();
    print("Tester dart");
}