package io.github.kamilszewc.simplerouter;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Request {

    private final SimpleRouter.Method method;
    private final URI uri;

    public Request(SimpleRouter.Method method, URI uri) {
        this.method = method;
        this.uri = uri;
    }

    public Request(SimpleRouter.Method method, URL url) throws URISyntaxException {
        this.method = method;
        this.uri = url.toURI();
    }

    public Request(SimpleRouter.Method method, String uri) throws URISyntaxException {
        this.method = method;
        this.uri = new URI(uri);
    }

    public SimpleRouter.Method getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }

    public String getPath() {
        return uri.getPath();
    }
}
