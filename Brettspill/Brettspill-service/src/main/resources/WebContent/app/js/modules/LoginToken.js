/**
 * Created by Espen on 27.04.2015.
 */
/*global define*/

var $ = require("jquery");

export default class LoginToken {
    constructor() {

        var a = $("#loggut");
        if(a.length){
            console.log(a);

            var token = window.sessionStorage.getItem('bosetterne_token');
            if(token) {
                token = JSON.parse(token);
                this._access_token = token.access_token;
                this._autorisering = new Oauth2Cred(this._access_token);
            } else {
                this._access_token = '';
            }
            
            
            this.menyLenker();
            a.click(function(){
                window.sessionStorage.removeItem("bosetterne_token");
            });
        } else {
            window.sessionStorage.removeItem("bosetterne_token");
            console.log("token fjernet");
        }
    }

    /** Plukker opp alle menylenkene og Legger ved access token hvis den eksisterer.
     * Hvis access_ token ikke eksisterer, legges den ikke ved.
     */
    menyLenker() {
        if(this._access_token) {
            this._menyLinker = $('a.meny');
            this._menyLinker.each(function (index, value){
                this._setToken($(this));
            });
        } else {
            console.log("Ikke innlogget");
        }
    };

    _setToken(anchorElement) {
        var link = anchorElement.getAttribute('href');
        var queryPos = link.indexOf('?');
        if (queryPos > -1) {
            link = link.substring(0, queryPos);
        }
        var tokenQuery = link+"?innlogget_token="+this._access_token;

        anchorElement.attr('href',tokenQuery);
    };

    get access_token() {
        return this._access_token;
    };
}

class Oauth2Cred {

    constructor(token) {
        this.token = token;
    }

    toString() {
        return 'Bearer ' + window.btoa(this.token);
    }
}