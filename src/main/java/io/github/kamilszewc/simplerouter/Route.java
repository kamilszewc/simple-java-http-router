package io.github.kamilszewc.simplerouter;

import java.util.HashMap;
import java.util.Map;

public class Route {

    private final SimpleRouter.Method method;
    private final String path;
    private final MethodHandler methodHandler;

    public Route(SimpleRouter.Method method, String path, MethodHandler methodHandler) {
        this.method = method;
        this.path = path;
        this.methodHandler = methodHandler;
    }

    public String getPath() {
        return path;
    }

    public SimpleRouter.Method getMethod() {
        return method;
    }

    public static class RoutingContext {
        private Request request;
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

    public Object callMethod(RoutingContext routingContext) {
        return methodHandler.handler(routingContext.getRequest(), routingContext.getPathVariables());
    }

    public RoutingContext checkRouting(Request request) throws NoRoutingException {
        // First check if method fits
        var requestMethod = request.getMethod();
        if (method != requestMethod) {
            throw new NoRoutingException();
        }

        // Get request path
        var requestPath = request.getPath();

        // Extract path elements
        var requestPathElements = requestPath.split("/");
        var pathElements = path.split("/");

        Map<String, String> pathVariables = new HashMap<>();

        for (int i=0; i<pathElements.length; i++) {
            try {
                String pathElement = pathElements[i];
                String requestPathElement = requestPathElements[i];

                if (pathElement.startsWith("*")) continue;
                if (pathElement.startsWith(":")) {
                    String key = pathElement.substring(1);
                    pathVariables.put(key, requestPathElement);
                    continue;
                }

                if (!pathElement.equals(requestPathElement)) {
                    throw new NoRoutingException();
                }


            } catch (Exception ex) {
                throw new NoRoutingException();
            }
        }

        return new RoutingContext(request, pathVariables);
    }
}
