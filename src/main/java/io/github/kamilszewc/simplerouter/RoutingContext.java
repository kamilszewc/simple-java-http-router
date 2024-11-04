package io.github.kamilszewc.simplerouter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.HashMap;
import java.util.Map;

public class RoutingContext {
    private final Request request;
    private Map<String, String> pathVariables = new HashMap<>();
    private Multimap<String, String> headers = ArrayListMultimap.create();

    public RoutingContext(Request request, Map<String, String> pathVariables, Multimap<String, String> headers) {
        this.request = request;
        this.pathVariables = pathVariables;
        this.headers = headers;
    }

    public Request getRequest() {
        return request;
    }

    public Map<String, String> getPathVariables() {
        return pathVariables;
    }

    public Multimap<String, String> getHeaders() {
        return headers;
    }
}
