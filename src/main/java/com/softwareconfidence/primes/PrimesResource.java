package com.softwareconfidence.primes;

import com.googlecode.utterlyidle.MediaType;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;
import com.googlecode.utterlyidle.annotations.Produces;
import com.googlecode.utterlyidle.annotations.QueryParam;

import java.util.List;

import static com.googlecode.totallylazy.numbers.Numbers.lessThanOrEqualTo;
import static com.googlecode.totallylazy.numbers.Numbers.primes;

public class PrimesResource {

    @GET
    @Path("/primes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Number> getPrimes(@QueryParam("upto") Long upto) {
        return primes().takeWhile(lessThanOrEqualTo(upto)).toList();
    }
}
