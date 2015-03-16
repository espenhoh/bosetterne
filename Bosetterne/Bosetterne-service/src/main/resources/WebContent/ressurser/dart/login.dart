
import 'dart:html';
import 'dart:convert';
import 'login/login_token.dart' as token;

InputElement spiller = querySelector('#txtBrukerNavn');
InputElement passord = querySelector('#pass');
InputElement redirect_uri = querySelector('#redirect_uri');
InputElement client_id = querySelector('#client_id');
SelectElement scope = querySelector('#sel_scope');
InputElement response_type = querySelector('#response_type');
FormElement loginForm = querySelector('form#frmLogin');

class BasicCred {
  String username;
  String password;

  BasicCred(this.username, this.password);

  String toString() {
    return 'Basic ' + window.btoa(username + ':' + password);
  }
}

void assertCheckMode() {
  try {
    int i = '';
    throw new Exception("Check Mode is disabled!");
  } on TypeError {
  }

}

void main() {
  assertCheckMode();
  token.main();
  InputElement submit = querySelector('#logg_inn');
  submit.onClick.listen( onSubmit );
  print('This will be logged to the console in the browser. main');

}



void onSubmit(Event e) {
  e.preventDefault();

  Map data = new Map();
  data['username'] = spiller.value;
  data['password'] = passord.value;
  data['redirect_uri'] = redirect_uri.value;
  data['client_id'] = client_id.value;
  data['scope'] = scope.value;
  data['response_type'] = response_type.value;
  data['state'] = 'beta';

  BasicCred creds = new BasicCred(spiller.value,passord.value);

  Map reqHdr = {'Authorization': creds.toString()};
/*
  HttpRequest.postFormData("/authorize/authcode", data, withCredentials: false)
  .then((HttpRequest resp){

    Map data2 = {'client_id':client_id.value,'client_secret':'secret','code':resp.responseText,'grant_type':'token','redirect_uri':redirect_uri.value};
      HttpRequest.postFormData("/token", data2, withCredentials: true, requestHeaders: reqHdr)
      .then((HttpRequest resp){
        window.sessionStorage["bosetterne_token"] = resp.responseText;
        print("Token stored in session storage");
        new token.Login_token().menyLenker();
        querySelector("#logginn").click();
      });
    print(resp.responseText);
  });*/
/*
  */
  print('This will be logged to the console in the browser. onsubmit');
}