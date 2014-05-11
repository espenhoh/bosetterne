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
  passord.onKeyUp.listen(validerEpostOgPassord);
  passord2.onKeyUp.listen(validerEpostOgPassord);
  epostfelt.onKeyUp.listen(validerEpostOgPassord);

  print('init ferdig');
}

oppdaterKallenavn(Event e) => kallenavn.value = brukernavn.value;

validerEpostOgPassord(Event e){
  bool korrekt = sjekkPassordOK() && sjekkEpostOK();
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