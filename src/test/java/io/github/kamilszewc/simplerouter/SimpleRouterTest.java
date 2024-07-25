package io.github.kamilszewc.simplerouter;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static io.github.kamilszewc.simplerouter.SimpleRouter.Method.GET;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleRouterTest {

    @Test
    public void routingWithNoPathVariables() {

        try {
            Request request = new Request(GET, "/api/v2/something1?par1=val1&par2=val2");

            var simpleRouter = new SimpleRouter();
            simpleRouter.addRoute(GET, "/api/v2/something1", (r, v) -> {
                assertSame(request, r);
                assertEquals(v.size(), 0);
                return "OK";
            });

            Object response = simpleRouter.route(request);
            System.out.println(response);

        } catch (NoRoutingException ex) {
            System.out.println("No routing");

        } catch (URISyntaxException e) {
            System.out.println("Wrong request uri");
        }
    }

    @Test
    public void routingWithPathVariables() {

        try {
            Request request = new Request(GET, "/api/v2/something1/value1/something2/value2?par1=val1&par2=val2");

            var simpleRouter = new SimpleRouter();
            simpleRouter.addRoute(GET, "/api/v2/something1/:var1/something2/:var2", (r, v) -> {
                assertSame(request, r);
                assertEquals(v.size(), 2);
                assertEquals(v.get("var1"), "value1");
                assertEquals(v.get("var2"), "value2");
                return "OK";
            });

            Object response = simpleRouter.route(request);
            System.out.println(response);

        } catch (NoRoutingException ex) {
            System.out.println("No routing");

        } catch (URISyntaxException e) {
            System.out.println("Wrong request uri");
        }
    }

    @Test
    public void routingWithPathVariablesAndAnyArgument() {

        try {
            Request request = new Request(GET, "https://test.com/api/v2/something1/value1/something2/value2?par1=val1&par2=val2");

            var simpleRouter = new SimpleRouter();
            simpleRouter.addRoute(GET, "/api/v2/something1/*/something2/:var2", (r, v) -> {
                assertSame(request, r);
                assertEquals(v.size(), 1);
                assertEquals(v.get("var2"), "value2");
                return "OK";
            });

            Object response = simpleRouter.route(request);
            System.out.println(response);

        } catch (NoRoutingException ex) {
            System.out.println("No routing");

        } catch (URISyntaxException e) {
            System.out.println("Wrong request uri");
        }
    }
}
