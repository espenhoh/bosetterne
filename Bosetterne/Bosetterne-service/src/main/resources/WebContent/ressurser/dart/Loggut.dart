import 'dart:html';

class Loggut {
  Loggut() {
    AnchorElement a = querySelector("#loggut");
    a.onClick.listen((e) => window.sessionStorage.remove("bosetterne_token"));
  }
}
