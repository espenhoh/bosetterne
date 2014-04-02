package com.holtebu.bosetterne.service.bosetterne.auth.sesjon;


@SuppressWarnings("serial")
public class AutorisasjonsUnntak extends RuntimeException {

  public AutorisasjonsUnntak(String message) {
    super(message);
  }

}