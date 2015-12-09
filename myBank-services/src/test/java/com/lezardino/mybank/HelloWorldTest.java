package com.lezardino.mybank;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.lezardino.mybank.HelloWorld;

/**
 * Unit test for simple App.
 */
public class HelloWorldTest {

    @Test
    public void testHelloWorld() {
        HelloWorld helloWorld = new HelloWorld();
        Response response = helloWorld.getMsg("test");
        assertThat(response.getStatus()).isEqualTo(200);

    }
}
