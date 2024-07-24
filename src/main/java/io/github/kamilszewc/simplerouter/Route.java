package io.github.kamilszewc.simplerouter;

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

    public Object callMethod(Request request) {
        return methodHandler.handler(request);
    }

    public boolean allowsRouting(Request request) {
        // First check if method fits
        var requestMethod = request.getMethod();
        if (method != requestMethod) {
            return false;
        }

        // Get request path
        var requestPath = request.getPath();

        // Compare requestPath and path one to one
        if (path.equals(requestPath)) {
            return true;
        }

        // Extract path elements
        var requestPathElements = requestPath.split("/");
        var pathElements = path.split("/");

        return false;
    }
}
