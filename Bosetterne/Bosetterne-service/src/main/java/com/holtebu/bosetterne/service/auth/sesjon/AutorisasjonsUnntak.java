package com.holtebu.bosetterne.service.auth.sesjon;


@SuppressWarnings("serial")
public class AutorisasjonsUnntak extends Exception {

  public AutorisasjonsUnntak(String message) {
    super(message);
  }

}