package io.github.kamilszewc.simplerouter;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple Java Http Router class
 */
public class SimpleRouter {

    /**
     * Supported http methods
     */
    public enum Method {
        GET,
        POST,
        PUT,
        PATCH,
        DELETE,
        ANY
    }

    private List<Route> routes = new ArrayList<>();

    /**
     * Default constructor
     */
    public SimpleRouter() {}

    /**
     * Add routing path
     * @param route Route object
     */
    public void addRoute(Route route) {
        routes.add(route);
    }

    /**
     * Add routing path
     * @param method Http method
     * @param path Path (URI)
     * @param methodHandler Method to be called
     */
    public void addRoute(Method method, String path, MethodHandler methodHandler) {
        Route route = new Route(method, path, methodHandler);
        routes.add(route);
    }

    /**
     * Add routing path
     * @param method Http method
     * @param path Path (URI)
     * @param methodHandler Method to be called
     */
    public void addRoute(String method, String path, MethodHandler methodHandler) {
        addRoute(Method.valueOf(method.toUpperCase()), path, methodHandler);
    }

    /**
     * Process routing
     * @param request Request object
     * @return Return object of the called java method
     * @throws NoRoutingException Risen when no routing path is found
     */
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
