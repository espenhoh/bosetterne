package com.holtebu.bosetterne.service.filter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.qos.logback.classic.Level;

import com.holtebu.bosetterne.BaseLoggerTest;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;

@RunWith(MockitoJUnitRunner.class)
public class PolettFilterTest extends BaseLoggerTest {

	private PolettFilter polettFilter;

	@Mock
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;

	//@Mock
	private ContainerRequestContext crc;
	

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		polettFilter = new PolettFilter();//tokenStore);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void whenMethodIsNotGetNothingShouldHappen() throws Exception {
		crc = crc(token(""));
		crc.setMethod("PUT");
		polettFilter.filter(crc);

		verifyLog("PUT not filtered", Level.INFO);
	}
	
	@Test
	public void whenTokenNotPresentNothingShouldHappen() throws IOException {
		crc = crc(token(null));
		crc.setMethod("GET");
		polettFilter.filter(crc);

		verifyLog("Spiller ikke logget inn", Level.INFO);
	}
	
	@Test
	public void whenTokenIsAnEmptyStringNothingShouldHappen() throws IOException {
		crc = crc(token(""));
		crc.setMethod("GET");
		polettFilter.filter(crc);

		verifyLog("Spiller ikke logget inn", Level.INFO);
	}
	
	@Test
	public void whenTokenIsAStringItshouldBePutInHeader() throws IOException {
		crc = crc(token("String"));
		crc.setMethod("GET");
		polettFilter.filter(crc);

		verifyLog("Putter token i header", Level.INFO);
		assertThat(crc.getHeaders().getFirst(HttpHeaders.AUTHORIZATION),is(equalTo("Bearer String")));
	}

	private UriInfo token(String token) {
		@SuppressWarnings("unchecked")
		MultivaluedMap<String, String> mapMock = mock(MultivaluedMap.class);
		when(mapMock.getFirst(isA(String.class))).thenReturn(token);
		UriInfo infoMock = mock(UriInfo.class);
		when(infoMock.getQueryParameters()).thenReturn(mapMock);
		return infoMock;
	}
	
	private ContainerRequestContext crc(final UriInfo uinfo) {
		return new ContainerRequestContext() {
			private UriInfo uriInfo = uinfo;
			private String method;
			private MultivaluedMap<String, String> headers = new MultivaluedStringMap();

			@Override
			public void setSecurityContext(SecurityContext context) {
				
			}
			
			@Override
			public void setRequestUri(URI baseUri, URI requestUri) {
				
			}
			
			@Override
			public void setRequestUri(URI requestUri) {
				
			}
			
			@Override
			public void setProperty(String name, Object object) {
				
			}
			
			@Override
			public void setMethod(String method) {
				this.method = method;
				
			}
			
			@Override
			public void setEntityStream(InputStream input) {
				
			}
			
			@Override
			public void removeProperty(String name) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean hasEntity() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public UriInfo getUriInfo() {
				return uriInfo;
			}
			
			@Override
			public SecurityContext getSecurityContext() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Request getRequest() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<String> getPropertyNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getProperty(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getMethod() {
				// TODO Auto-generated method stub
				return method;
			}
			
			@Override
			public MediaType getMediaType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getLength() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Locale getLanguage() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public MultivaluedMap<String, String> getHeaders() {
				return headers;
			}
			
			@Override
			public String getHeaderString(String name) {
				return headers.getFirst(name);
			}
			
			@Override
			public InputStream getEntityStream() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Date getDate() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, Cookie> getCookies() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<MediaType> getAcceptableMediaTypes() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Locale> getAcceptableLanguages() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void abortWith(Response response) {
				// TODO Auto-generated method stub
				
			}
		};
	}

}
