package com.holtebu.bosetterne.service.auth.sesjon;

/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements. See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership. The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.google.common.base.Optional;

/**
* Token store
*
*/
public class PolettlagerIMinne implements Polettlager<AccessToken, Spiller, String> {

  private final Map<String, Spiller> accessTokens = new HashMap<String, Spiller>();
  private final Map<String, Spiller> codes = new HashMap<String, Spiller>();

  @Override
  public AccessToken storeAccessToken(Spiller clientDetails) {
//    verifyClientId(clientDetails);
//    verifyClientSecret(clientDetails);
    AccessToken accessToken = new AccessToken(UUID.randomUUID().toString(), Long.MAX_VALUE);
    accessTokens.put(accessToken.getAccess_token(), clientDetails);
    return accessToken;
  }

  @Override
  public Optional<Spiller> getSpillerByAccessToken(String accessToken) {
	  Spiller clientDetails = accessTokens.get(accessToken);
    return safeOptional(clientDetails);
  }

  @Override
  public String storeAuthorizationCode(Spiller spiller) {
    //verifyClientId(spiller);
    String code = UUID.randomUUID().toString();
    //spiller.setCode(code);
    codes.put(code, spiller);
    return code;
  }

  @Override
  public Optional<Spiller> getSpillerByAuthorizationCode(String code) {
    Spiller spiller = codes.get(code);
    return safeOptional(spiller);
  }

  
  private Optional<Spiller> safeOptional(Spiller spiller) {
    return spiller != null ? Optional.of(spiller) : Optional.fromNullable(spiller);
  }

}