package io.github.kamilszewc.simplerouter;

public interface Route {
    SimpleRouter.Method getMethod();
    Object callMethod(RoutingContext routingContext);
    RoutingContext checkRouting(Request request) throws NoRoutingException;
}
