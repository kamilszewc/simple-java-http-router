package io.github.kamilszewc.simplerouter;

import java.util.ArrayList;
import java.util.List;

public class SimpleRouter {

    public enum Method {
        GET,
        POST,
        PUT,
        PATCH,
        DELETE
    }

    private List<Route> routes = new ArrayList<>();

    protected SimpleRouter() {
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void addRoute(Method method, String path, MethodHandler methodHandler) {
        Route route = new Route(method, path, methodHandler);
        routes.add(route);
    }

    public Object route(Request request) throws NoRoutingException {
        for (var route : routes) {
            if (route.allowsRouting(request)) {
                System.out.println("Fits with route " + route.getMethod() + " " + route.getPath());
                return route.callMethod(request);
            }
        }
        throw new NoRoutingException();
    }
}
