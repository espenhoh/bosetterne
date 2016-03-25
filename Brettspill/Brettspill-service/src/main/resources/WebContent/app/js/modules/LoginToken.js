/**
 * Created by Espen on 27.04.2015.
 */
/*global define*/

export default class LoginToken {
    constructor() {
        
    }
}

/*
    List<AnchorElement> _menyLinker;
    String _access_token;
    Oauth2Cred _autorisering;

    Login_token() {
        if(window.sessionStorage["bosetterne_token"] != null) {
            Map token = JSON.decode(window.sessionStorage["bosetterne_token"]);
            _access_token = token['access_token'];
            _autorisering = new Oauth2Cred(_access_token);
        }
    }

    String get access_token => _access_token == null ? "" :_access_token;

/** Plukker opp alle menylenkene og Legger ved access token hvis den eksisterer.
 * Hvis access_ token ikke eksisterer, legges den ikke ved.
 *

 void menyLenker() {
    if(_access_token != null) {
        _menyLinker = querySelectorAll('a.meny');
        _menyLinker.forEach((a) => _setToken(a));
    } else {
        print("Ikke innlogget");
    }
}

/*


import 'dart:html';
import 'dart:convert';


void main(){
    AnchorElement a = querySelector("#loggut");
    if(a == null){
        window.sessionStorage.remove("bosetterne_token");
        print("token fjernet");
    } else {
        new Login_token().menyLenker();
        a.onClick.listen((e) => window.sessionStorage.remove("bosetterne_token"));
    }
}

class Login_token {
    List<AnchorElement> _menyLinker;
    String _access_token;
    Oauth2Cred _autorisering;

    Login_token() {
        if(window.sessionStorage["bosetterne_token"] != null) {
            Map token = JSON.decode(window.sessionStorage["bosetterne_token"]);
            _access_token = token['access_token'];
            _autorisering = new Oauth2Cred(_access_token);
        }
    }

    String get access_token => _access_token == null ? "" :_access_token;

    /** Plukker opp alle menylenkene og Legger ved access token hvis den eksisterer.
     * Hvis access_ token ikke eksisterer, legges den ikke ved.
     *

    void menyLenker() {
    if(_access_token != null) {
        _menyLinker = querySelectorAll('a.meny');
        _menyLinker.forEach((a) => _setToken(a));
    } else {
        print("Ikke innlogget");
    }
}

    void _setToken(AnchorElement anchorElement) {

    String link = anchorElement.getAttribute('href');
    int queryPos = link.indexOf('?');
    if (queryPos > -1) {
    link = link.substring(0, queryPos);
}

StringBuffer tokenQuery = new StringBuffer();
tokenQuery.write(link);
tokenQuery.write("?innlogget_token=");
tokenQuery.write(_access_token);

anchorElement.setAttribute('href',tokenQuery.toString());
}
}

class Oauth2Cred {
    String token;

    Oauth2Cred(this.token);

    String toString() {
    return 'Bearer ' + window.btoa(token);
}
}
*/