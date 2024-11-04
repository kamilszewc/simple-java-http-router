package io.github.kamilszewc.simplerouter;

public class HeaderRoute implements Route {

    private final SimpleRouter.Method method;
    private final String header;
    private final MethodHandler methodHandler;

    public HeaderRoute(SimpleRouter.Method method, String header, MethodHandler methodHandler) {
        this.method = method;
        this.header = header;
        this.methodHandler = methodHandler;
    }

    public String getHeader() {
        return header;
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

        // Get headers
        var requestHeaders = request.getHeaders();

        if (!requestHeaders.containsKey(header)) {
            throw new NoRoutingException("Header " + header + "does not match request headers ");
        }

        return new RoutingContext(request, null, requestHeaders);
    }

    @Override
    public String toString() {
        return "HeaderRoute(" +
                "method=" + method +
                " header=" + header + ")";
    }
}
