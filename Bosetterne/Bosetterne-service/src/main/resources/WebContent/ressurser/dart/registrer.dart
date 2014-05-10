import 'dart:html';

InputElement brukernavn;
InputElement kallenavn;
InputElement passord;
InputElement passord2;
InputElement submit;

void main() {
  brukernavn = querySelector('#txtBrukernavn');
  kallenavn = querySelector('#txtKallenavn');
  passord = querySelector('#pass1');
  passord2 = querySelector('#pass2');
  submit = querySelector('#send_inn');

  brukernavn.onKeyUp.listen(oppdaterKallenavn);

}

oppdaterKallenavn(Event e) => kallenavn.value = brukernavn.value;

sjekkPassord(Event e) {
  if (passordLike()) {

  } else {

  }
}

bool passordLike() => passord.value == passord2.value;

/*
class Registrer {
  Registrer() {
  }
}*/
