package com.holtebu.brettspill.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

import com.google.common.base.Optional;
import com.holtebu.brettspill.api.lobby.Spiller;

import io.dropwizard.auth.basic.BasicCredentials;

public class Imports {

}
