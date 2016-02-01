package com.lezardino.mybank.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

public class RestRessourceConfig extends ResourceConfig {
    public RestRessourceConfig() {

        //
        // JACKSON
        //
        register(JacksonJsonProvider.class);

        packages("com.lezardino.mybank");


    }
}
