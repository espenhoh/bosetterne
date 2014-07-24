
import 'dart:html';

InputElement spiller = querySelector('#txtBrukerNavn');
InputElement passord = querySelector('#pass');
InputElement redirect_uri = querySelector('#redirect_uri');
InputElement client_id = querySelector('#client_id');
InputElement scope = querySelector('#sel_scope');
InputElement reponse_type = querySelector('#response_type');
FormElement loginForm = querySelector('form#frmLogin');

class BasicCred {
  String username;
  String password;

  BasicCred(this.username, this.password);

  String toString() {
    return 'Basic ' + window.btoa(username + ':' + password);
  }
}


void main() {
  InputElement submit = querySelector('#logg_inn');
  submit.onClick.listen( onSubmit );
}

void onSubmit(Event e) {
  e.preventDefault();

  Map data = new Map();
  data['username'] = spiller.value;
  data['password'] = passord.value;
  data['redirect_uri'] = redirect_uri.value;
  data['client_id'] = client_id.value;
  data['scope'] = scope.value;
  data['reponse_type'] = reponse_type.value;
  data['state'] = 'beta';

  print(data['response_type']);
  print(data['client_id']);

  BasicCred creds = new BasicCred(spiller.value,passord.value);

  Map reqHdr = {'Authorization': creds.toString()};

  HttpRequest.postFormData("/authorize", data, withCredentials: false, requestHeaders: reqHdr)
  .then((HttpRequest resp){
    print(resp.responseText);
  });
/*
  HttpRequest.postFormData("/token/implicit", data, withCredentials: true, requestHeaders: reqHdr)
  .then((HttpRequest resp){
    print(resp.responseText);
  });*/
}