/*global require, document */

var $ = require("jquery");
import LoginToken from './modules/LoginToken';

$(document).ready(function () {
    console.log('hei!')
    var token = new LoginToken();
})



/*



import 'modules/login/login_token.dart' as token;

void main(){
    token.main();
    print("Tester dart");
}*/