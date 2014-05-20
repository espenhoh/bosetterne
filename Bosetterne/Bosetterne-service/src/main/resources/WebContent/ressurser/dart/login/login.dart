
import 'dart:html';

class BasicCred {
  String username;
  String password;

  BasicCred(this.username, this.password);
}


class Login {
  Login() {
  }
}

void main() {onSubmit();}

void onSubmit(){//}Event e) {
  //e.preventDefault();


  InputElement spiller = querySelector('#txtKalleNavn');
  InputElement passord = querySelector('#pass');



  BasicCred creds = new BasicCred(spiller.value,passord.value);
  HttpRequest request = new HttpRequest(); // create a new XHR

  // add an event handler that is called when the request finishes
  request.onReadyStateChange.listen((_) {
    if (request.readyState == HttpRequest.DONE &&
    (request.status == 200 || request.status == 0)) {
      // data saved OK.
      print(request.responseText); // output the response from the server
    }
  });

  // POST the data to the server
  var url = "/authorize/implicit";
  request.open("POST", url, async: false);

  request.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
  request.
  print(request);
  //request.setRequestHeader();

  Map

  String jsonData = '{"language":"dart"}'; // etc...
  request.postFormData(url,);
  request.send(jsonData); // perform the async POST
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