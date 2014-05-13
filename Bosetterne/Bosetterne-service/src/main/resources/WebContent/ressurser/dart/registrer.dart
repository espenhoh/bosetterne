import 'dart:html';

InputElement brukernavn;
InputElement kallenavn;
InputElement passord;
InputElement passord2;
InputElement submit;
SpanElement feilmeldingPassord1;
SpanElement feilmeldingPassord2;
SpanElement feilmeldingEpost;
InputElement epostfelt;

bool validEpost = true;
bool validPass = true;

void main() {

  brukernavn = querySelector('#txtBrukernavn');
  kallenavn = querySelector('#txtKallenavn');
  passord = querySelector('#pass1');
  passord2 = querySelector('#pass2');
  submit = querySelector('#send_inn');
  feilmeldingPassord1 = querySelector('#pass_fail1');
  feilmeldingPassord2 = querySelector('#pass_fail2');
  feilmeldingEpost = querySelector('#epost_fail');
  epostfelt = querySelector('#txtEpost');

  brukernavn.onChange.listen(oppdaterKallenavn);
  passord.onKeyUp.listen(validerPassord);
  passord2.onKeyUp.listen(validerPassord);
  epostfelt.onKeyUp.listen(validerEpost);

  print('init ferdig');
}

oppdaterKallenavn(Event e) => kallenavn.value = brukernavn.value;

validerEpost(Event e){
  bool korrekt = validPass && sjekkEpostOK();
  submit.disabled = !korrekt;
}

validerPassord(Event e){
  bool korrekt = sjekkPassordOK() && validEpost;
  submit.disabled = !korrekt;
}

bool sjekkPassordOK() {
  bool likePassord = passordLike();
  bool langtNok = passordLangtNok();
  feilmeldingPassord1.hidden = langtNok;
  feilmeldingPassord2.hidden = likePassord;
  return likePassord && langtNok;
}

bool sjekkEpostOK(){
  bool erEpost = isEmail(epostfelt.value);
  feilmeldingEpost.hidden = erEpost;
  return erEpost;
}

bool passordLike() => passord.value == passord2.value;

bool passordLangtNok() => passord.value.length >= 6;

bool isEmail(String em) =>
  new RegExp(r'^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$').hasMatch(em);