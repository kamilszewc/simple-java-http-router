package io.github.kamilszewc.simplerouter;

import java.util.Map;

public interface MethodHandler {
    Object handler(Request request, Map<String, String> pathVariables);
}
