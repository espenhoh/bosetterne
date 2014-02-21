import 'dart:html';
import 'dart:math';

const int GROWTH_FACTOR = 15 ;

void main() {
//  querySelector("#submit").onClick.listen( (e) => querySelector("#output").innerHtml = "LOL");
  querySelector("#submit").onClick.listen( (e) => calcRabbits());
}

calcRabbits() {
// binding variables to html elements:
  InputElement yearsInput = querySelector("#years");
  LabelElement output = querySelector("#output");
// getting input
  String yearsString = yearsInput.value;
  int years = int.parse(yearsString);
// calculating and setting output:
  output.innerHtml = "${calculateRabbits(years)}";
}

int calculateRabbits(int years) {
  return (2 * pow(
      E,
      log(GROWTH_FACTOR) * years)
  ).round().toInt();
}