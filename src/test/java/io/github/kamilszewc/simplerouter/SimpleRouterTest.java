package io.github.kamilszewc.simplerouter;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static io.github.kamilszewc.simplerouter.SimpleRouter.Method.ANY;
import static io.github.kamilszewc.simplerouter.SimpleRouter.Method.GET;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleRouterTest {

    @Test
    public void routingWithNoPathVariables() {

        try {
            Request request = new Request(GET, "/api/v2/something1?par1=val1&par2=val2");

            var simpleRouter = new SimpleRouter();
            simpleRouter.addRoute(GET, "/api/v2/something1", (c) -> {
                assertSame(request, c.getRequest());
                assertEquals(c.getPathVariables().size(), 0);
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
            simpleRouter.addRoute(GET, "/api/v2/something1/:var1/something2/:var2", (c) -> {
                assertSame(request, c.getRequest());
                assertEquals(c.getPathVariables().size(), 2);
                assertEquals(c.getPathVariables().get("var1"), "value1");
                assertEquals(c.getPathVariables().get("var2"), "value2");
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
            simpleRouter.addRoute(GET, "/api/v2/something1/*/something2/:var2", (c) -> {
                assertSame(request, c.getRequest());
                assertEquals(c.getPathVariables().size(), 1);
                assertEquals(c.getPathVariables().get("var2"), "value2");
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
    public void routingWithNoPathVariablesAndAnyRouteMethod() {

        try {
            Request request = new Request(GET, "/api/v2/something1?par1=val1&par2=val2");

            var simpleRouter = new SimpleRouter();
            simpleRouter.addRoute(ANY, "/api/v2/something1", (c) -> {
                assertSame(request, c.getRequest());
                assertEquals(c.getPathVariables().size(), 0);
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
    public void routingWithNoPathVariablesWithMethodsAsString() {

        try {
            Request request = new Request("GeT", "/api/v2/something1?par1=val1&par2=val2");

            var simpleRouter = new SimpleRouter();
            simpleRouter.addRoute("gEt", "/api/v2/something1", (c) -> {
                assertSame(request, c.getRequest());
                assertEquals(c.getPathVariables().size(), 0);
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
