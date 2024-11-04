package io.github.kamilszewc.simplerouter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.Map;

import static io.github.kamilszewc.simplerouter.SimpleRouter.Method.ANY;
import static io.github.kamilszewc.simplerouter.SimpleRouter.Method.GET;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleRouterTest {

    @Test
    public void routingWithNoPathVariables() {

        assertDoesNotThrow(() -> {
            Request request = new Request(GET, "/api/v2/something1?par1=val1&par2=val2");

            var simpleRouter = new SimpleRouter();
            simpleRouter.addRoute(GET, "/api/v2/something1", (c) -> {
                assertSame(request, c.getRequest());
                assertEquals(c.getPathVariables().size(), 0);
                return "OK";
            });

            Object response = simpleRouter.route(request);
            System.out.println(response);
        });
    }

    @Test
    public void routingWithNoPathVariablesExplicitParametersPass() {

        assertDoesNotThrow(() -> {
            Request request = new Request(GET, "/api/v2/something1?par1=val1&par2=val2", Map.of("par1", "val1", "par2", "val2"));

            var simpleRouter = new SimpleRouter();
            simpleRouter.addRoute(GET, "/api/v2/something1", (c) -> {
                assertSame(request, c.getRequest());
                assertEquals(c.getPathVariables().size(), 0);
                assertEquals(c.getRequest().getParameters().get("par2"), "val2");
                return "OK";
            });

            Object response = simpleRouter.route(request);
            System.out.println(response);
        });
    }

    @Test
    public void routingWithPathVariables() {

        assertDoesNotThrow(() -> {
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
        });
    }

    @Test
    public void routingWithPathVariablesAndAnyArgument() {

        assertDoesNotThrow(() -> {
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
        });
    }

    @Test
    public void routingWithNoPathVariablesAndAnyRouteMethod() {

        assertDoesNotThrow(() -> {
            Request request = new Request(GET, "/api/v2/something1?par1=val1&par2=val2");

            var simpleRouter = new SimpleRouter();
            simpleRouter.addRoute(ANY, "/api/v2/something1", (c) -> {
                assertSame(request, c.getRequest());
                assertEquals(c.getPathVariables().size(), 0);
                return "OK";
            });

            Object response = simpleRouter.route(request);
            System.out.println(response);
        });
    }

    @Test
    public void routingWithNoPathVariablesWithMethodsAsString() {

        assertDoesNotThrow(() -> {
            Request request = new Request("GeT", "/api/v2/something1?par1=val1&par2=val2");

            var simpleRouter = new SimpleRouter();
            simpleRouter.addRoute("gEt", "/api/v2/something1", (c) -> {
                assertSame(request, c.getRequest());
                assertEquals(c.getPathVariables().size(), 0);
                return "OK";
            });

            Object response = simpleRouter.route(request);
            System.out.println(response);
        });
    }


    @Test
    public void routingComplex() {

        assertDoesNotThrow(() -> {
            Request request = new Request(GET, "/api/v2/something1/value1/value2?par1=val1&par2=val2");

            var simpleRouter = new SimpleRouter();
            simpleRouter.addRoute(GET, "/api/v2/something1/:var1/something2", (c) -> {
                return "ERROR";
            });
            simpleRouter.addRoute("get", "/api/v2/something1/something2", (c) -> {
                return "ERROR";
            });
            simpleRouter.addRoute("gEt", "/api/v2", (c) -> {
                return "ERROR";
            });
            simpleRouter.addRoute(GET, "/api/v2/something1/:var1/:var2", (c) -> {
                assertSame(request, c.getRequest());
                assertEquals(c.getPathVariables().size(), 2);
                assertEquals(c.getPathVariables().get("var1"), "value1");
                assertEquals(c.getPathVariables().get("var2"), "value2");
                return "OK";
            });
            simpleRouter.addRoute(ANY, "/api/v2/*", (c) -> {
                return "ERROR";
            });

            Object response = simpleRouter.route(request);
            System.out.println(response);

        });
    }

    @Test
    public void headerBasedRouting() {

        assertDoesNotThrow(() -> {
            Multimap<String, String> headers = ArrayListMultimap.create();
            headers.put("key", "value");
            Request request = new Request(GET, "/api/v2/something1", headers);

            var simpleRouter = new SimpleRouter();
            simpleRouter.addHeaderRoute(GET, "key", (c) -> {
                assertSame(request, c.getRequest());
                return "OK";
            });

            Object response = simpleRouter.route(request);
            System.out.println(response);
        });
    }
}
