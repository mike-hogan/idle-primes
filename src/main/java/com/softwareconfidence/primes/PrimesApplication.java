package com.softwareconfidence.primes;

import com.googlecode.utterlyidle.Application;
import com.googlecode.utterlyidle.BasePath;
import com.googlecode.utterlyidle.RestApplication;

import static com.googlecode.utterlyidle.ApplicationBuilder.application;
import static com.googlecode.utterlyidle.annotations.AnnotatedBindings.annotatedClass;

public class PrimesApplication extends RestApplication {

    public PrimesApplication(BasePath basePath) {
        super(basePath);
    }

    public static Application setupApplication() {
        return application(PrimesApplication.class)
                .add(annotatedClass(PrimesResource.class))
                .build();
    }
}
