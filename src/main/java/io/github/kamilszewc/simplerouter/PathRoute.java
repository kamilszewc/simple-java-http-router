package io.github.kamilszewc.simplerouter;

import java.util.HashMap;
import java.util.Map;

public class PathRoute implements Route {

    private final SimpleRouter.Method method;
    private final String path;
    private final MethodHandler methodHandler;

    public PathRoute(SimpleRouter.Method method, String path, MethodHandler methodHandler) {
        this.method = method;
        this.path = path;
        this.methodHandler = methodHandler;
    }

    public String getPath() {
        return path;
    }

    @Override
    public SimpleRouter.Method getMethod() {
        return method;
    }

    @Override
    public Object callMethod(RoutingContext routingContext) {
        return methodHandler.handler(routingContext);
    }

    @Override
    public RoutingContext checkRouting(Request request) throws NoRoutingException {
        // First check if method fits
        if (method != SimpleRouter.Method.ANY) {
            var requestMethod = request.getMethod();
            if (method != requestMethod) {
                throw new NoRoutingException("Method " + method + "does not match request method " + requestMethod);
            }
        }

        // Get request path
        var requestPath = request.getPath();

        // Extract path elements
        var requestPathElements = requestPath.split("/");
        var pathElements = path.split("/");

        Map<String, String> pathVariables = new HashMap<>();

        int i;
        for (i=0; i<pathElements.length; i++) {
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
                    throw new NoRoutingException("Path element " + pathElement + " does not match request path element: " + requestPathElement);
                }

            } catch (Exception ex) {
                throw new NoRoutingException("Exception while checking patch element: " + ex.getMessage());
            }
        }

        if (i < requestPathElements.length) {
            throw new NoRoutingException("Request path is longer than path");
        }

        return new RoutingContext(request, pathVariables, null);
    }
}
