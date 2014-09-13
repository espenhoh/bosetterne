
import 'dart:html';
import 'dart:convert';
import 'package:html5lib/parser.dart' show parse;
import 'package:html5lib/dom.dart';


class Login_token {
  List<AnchorElement> _menyLinker;
  String _access_token;
  Oauth2Cred _autorisering;

  Login_token() {
    if(window.localStorage["bosetterne_token"] != null) {
      Map token = JSON.decode(window.localStorage["bosetterne_token"]);
      _access_token = token['access_token'];
      _autorisering = new Oauth2Cred(_access_token);
    }
  }

  String get access_token => _access_token == null ? "" :_access_token;

  /** Plukker opp alle menylenkene og Legger ved access token hvis den eksisterer.
   * Hvis access_ token ikke eksisterer, legges den ikke ved.
   *
   */
  void menyLenker() {
    if(_access_token != null) {
      _menyLinker = querySelectorAll('a');
      _menyLinker.forEach((a) => _setToken(a));
    }
  }

  void _setToken(AnchorElement anchorElement) {

    print(anchorElement.getAttribute('href'));
    StringBuffer tokenQuery = new StringBuffer();
    tokenQuery.write(anchorElement.getAttribute('href'));
    tokenQuery.write("?token=");
    tokenQuery.write(_access_token);

    anchorElement.setAttribute('href',tokenQuery.toString());
    print(anchorElement.getAttribute('href'));
  }
}

class Oauth2Cred {
  String token;

  Oauth2Cred(this.token);

  String toString() {
    return 'Bearer ' + window.btoa(token);
  }
}
