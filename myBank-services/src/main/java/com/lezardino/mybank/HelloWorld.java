package com.lezardino.mybank;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Hello world!
 *
 */
@Path("/hello")
public class HelloWorld {
    @GET
    @Path("/{param}")
    public Response getMsg(@PathParam("param") String msg) {
        String output = "MyBank say : " + msg;

        return Response.status(200).entity(output).build();
    }
}
