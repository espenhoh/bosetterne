/*global require, $ */


require(['main', ], function () {
    'use strict';
    require(['jquery'], function($) {
        document.write('RequireJS virker!');
    });
});

/*
import 'modules/login/login_token.dart' as token;

void main(){
    token.main();
    print("Tester dart");
}*/