package io.github.kamilszewc.simplerouter;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static io.github.kamilszewc.simplerouter.SimpleRouter.Method.GET;

public class SimpleRouterTest {

    @Test
    public void basicTest() {

        try {
            Request request = new Request(GET, "https://test.com/api/v2/something1?par1=val1&par2=val2");

            var simpleRouter = new SimpleRouter();
            simpleRouter.addRoute(GET, "/api/v2/something1", (req) -> {
                return "Something1";
            });
            simpleRouter.addRoute(GET, "/api/v2/something2", (req) -> {
                return "Something2";
            });

            Object response = simpleRouter.route(request);
            System.out.println(response);

        } catch (NoRoutingException ex) {
            System.out.println("No routing");

        } catch (MalformedURLException e) {
            System.out.println("Wrong request url");
        }
    }
}
