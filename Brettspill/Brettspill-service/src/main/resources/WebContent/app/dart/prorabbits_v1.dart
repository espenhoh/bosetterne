import 'dart:math';


final YEARS_TOT = 10;

final GROWTH_FACTOR = 15;

void main() {
  var noOfRabbits = 0;
  print("The number of rabbits increases as:\n");
  for (int years = 0; years <= YEARS_TOT; years++) {
    noOfRabbits = calcRabbits(years);
    print("After $years years:\t $noOfRabbits animals");
  }
}

calcRabbits(int years) {
  return (2 * pow(E, log(GROWTH_FACTOR) * years)).round().toInt();
}