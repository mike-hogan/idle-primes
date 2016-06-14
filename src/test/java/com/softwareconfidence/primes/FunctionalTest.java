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

public class FunctionalTest {

    @Rule
    public Timeout globalTimeout = new Timeout(1000);

    private final Application app = setupApplication();

    @Test
    public void returnsPrimesAsJsonArray() throws Exception {
        assertReturns(app.handle(get("/primes?upto=20")), OK, APPLICATION_JSON, is("[2, 3, 5, 7, 11, 13, 17, 19]"));
    }

    @Test
    public void returnsPrimesInclusiveOfUpToValue() throws Exception {
        assertReturns(app.handle(get("/primes?upto=11")), OK, APPLICATION_JSON, is("[2, 3, 5, 7, 11]"));
    }

    @Test
    public void handlesZeroAndNegativeUpToValues() throws Exception {
        // Deciding that negative and zero are not BAD_REQUEST, but OK with empty result.  Exact semantics debatable
        assertReturns(app.handle(get("/primes?upto=-1")), OK, APPLICATION_JSON, is("[]"));
        assertReturns(app.handle(get("/primes?upto=0")), OK, APPLICATION_JSON, is("[]"));
    }

    @Test
    public void handlesMissingUpToValues() throws Exception {
        assertReturns(app.handle(get("/primes")), BAD_REQUEST, TEXT_HTML, containsString("Unsatisfiable Parameters"));
    }

    private void assertReturns(Response response, Status status, String contentType, Matcher<String> entityMatcher) {
        assertThat(response.status(), is(status));
        assertThat(response.header(CONTENT_TYPE).getOrElse("Not provided"), containsString(contentType));
        assertThat(response.entity().toString(), entityMatcher);
    }

}
