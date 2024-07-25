package io.github.kamilszewc.simplerouter;

import java.util.ArrayList;
import java.util.List;

public class SimpleRouter {

    public enum Method {
        GET,
        POST,
        PUT,
        PATCH,
        DELETE,
        ANY
    }

    private List<Route> routes = new ArrayList<>();

    public SimpleRouter() {}

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void addRoute(Method method, String path, MethodHandler methodHandler) {
        Route route = new Route(method, path, methodHandler);
        routes.add(route);
    }

    public Object route(Request request) throws NoRoutingException {
        for (var route : routes) {

            try {
                var context = route.checkRouting(request);
                return route.callMethod(context);

            } catch (Exception ignore) {}
        }

        throw new NoRoutingException();
    }
}
