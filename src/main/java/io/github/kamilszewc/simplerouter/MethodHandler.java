package io.github.kamilszewc.simplerouter;

public interface MethodHandler {
    Object handler(RoutingContext routingContext);
}
