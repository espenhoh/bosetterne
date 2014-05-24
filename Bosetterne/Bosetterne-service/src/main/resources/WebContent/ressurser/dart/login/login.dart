
import 'dart:html';

class BasicCred {
  String username;
  String password;

  BasicCred(this.username, this.password);

  String toString() {
    return 'Basic ' + window.btoa(username + ':' + password);
  }
}


void main() {
  FormElement loginForm = querySelector('form#frmLogin');
  //print(loginForm.toString());

  Map data = new Map();

  for(Element e in loginForm.children) {
    if (e is InputElement) {
      InputElement ie = e as InputElement;
      print('${ie.name} ,${ie.value}');
      data[ie.name] = ie.value;
    }
  }

  HttpRequest.postFormData("/authorize/implicit", method: "post", sendData: data)
  .then((HttpRequest resp){
    print(resp);
  });

  /*
  HttpRequest.request("/authorize/implicit", method: "post", withCredentials: true, requestHeaders: reqHeader, sendData: data)
  .then((HttpRequest resp){
    print(resp);
  });*/

  /*
  HttpRequest.request(loginForm.action, method: 'POST', sendData: data)
    .then((resp) {

  });*/

  //onSubmit();
}

void onSubmit(){//}Event e) {
  //e.preventDefault();


  InputElement spiller = querySelector('#txtKalleNavn');
  InputElement passord = querySelector('#pass');
  InputElement redirect_uri = querySelector('#redirect_uri');
  InputElement client_id = querySelector('#client_id');
  InputElement scope = querySelector('#scope');
  InputElement reponse_type = querySelector('#reponse_type');
  FormElement loginForm = querySelector('form#frmLogin');

  final xhr = new HttpRequest();
  var url = "/authorize/implicit";
  var method = 'POST';

  xhr.open(method, url);

  // add an event handler that is called when the request finishes
  xhr.onReadyStateChange.listen((e) {
    if (xhr.readyState == HttpRequest.DONE && (xhr.status == 200 || xhr.status == 0)) {
      // on OK
      print(xhr.responseText);
    }
  });

  BasicCred creds = new BasicCred(spiller.value,passord.value);

  Map reqHdr = new Map();
  reqHdr['Content-Type'] = '';

  xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
  xhr.setRequestHeader('Authorization',creds.toString());


  // send datas by using an existing form and adding some additional datas
  xhr.send(new FormData(loginForm));

  /*
  Map formData = new Map();
  formData['redirect_uri'] = redirect_uri.value;
  formData['client_id'] = client_id.value;
  formData['scope'] = scope.value;
  formData['reponse_type'] = reponse_type.value;





  // POST the data to the server
  request.open("POST", "/authorize/implicit", async: false, user: spiller.value, password: passord.value);



  print(request);

  var myForm = querySelector('form#frmLogin');
  var data = new FormData();
  data.append("redirect_uri",redirect_uri.value);
  data.append("client_id",client_id.value);
  data.append("scope",scope.value);
  data.append("reponse_type",reponse_type.value);

  HttpRequest.request("/authorize/implicit", method: "post", withCredentials: true, requestHeaders: reqHeader, sendData: data)
  .then((HttpRequest resp){
    print(resp);
  });*/

  //request.send(formData);
  //request.send(jsonData); // perform the async POST
}
/*
Request URL:http://localhost:8080/authorize/implicit
Request Method:POST
Status Code:415 Unsupported Media Type
Request Headersview source
Accept:
Accept-Encoding:gzip,deflate,sdch
Accept-Language:nb-NO,nb;q=0.8,no;q=0.6,nn;q=0.4,en-US;q=0.2,en;q=0.2
Connection:keep-alive
Content-Length:19
Content-Type:text/plain;charset=UTF-8
Host:localhost:8080
Origin:http://localhost:8080
Referer:http://localhost:8080/logg_inn
User-Agent:Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.0 (Dart) Safari/537.36
Request Payloadview source
{language:dart}
language: "dart"*/