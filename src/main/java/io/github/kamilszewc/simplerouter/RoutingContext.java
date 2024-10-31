package io.github.kamilszewc.simplerouter;

import java.util.HashMap;
import java.util.Map;

public class RoutingContext {
    private final Request request;
    private Map<String, String> pathVariables = new HashMap<>();

    public RoutingContext(Request request, Map<String, String> pathVariables) {
        this.request = request;
        this.pathVariables = pathVariables;
    }

    public Request getRequest() {
        return request;
    }

    public Map<String, String> getPathVariables() {
        return pathVariables;
    }
}
