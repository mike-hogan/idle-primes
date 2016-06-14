package com.softwareconfidence.primes;

import com.googlecode.utterlyidle.Application;
import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.Status;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static com.googlecode.utterlyidle.HttpHeaders.CONTENT_TYPE;
import static com.googlecode.utterlyidle.MediaType.APPLICATION_JSON;
import static com.googlecode.utterlyidle.MediaType.TEXT_HTML;
import static com.googlecode.utterlyidle.Request.get;
import static com.googlecode.utterlyidle.Status.BAD_REQUEST;
import static com.googlecode.utterlyidle.Status.OK;
import static com.softwareconfidence.primes.PrimesApplication.setupApplication;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;

// To be run as CD pipeline stage following running of FunctionalTest
public class SizingTest {

    // Timeout value will need to be parameterised to suit continuous delivery pipeline hardware performance
    // 8 seconds is fine on my machine, being generous with 30
    @Rule
    public Timeout globalTimeout = new Timeout(30000);

    private final Application app = setupApplication();

    @Test
    public void handlesReasonablyLargeValues() throws Exception {
        // Could stream the result as an option, or implement paging
        final Response response = app.handle(get("/primes?upto=2000000"));
        assertThat(response.status(), is(OK));
        assertThat(response.header(CONTENT_TYPE).getOrElse("Not provided"), containsString(APPLICATION_JSON));
    }
}
