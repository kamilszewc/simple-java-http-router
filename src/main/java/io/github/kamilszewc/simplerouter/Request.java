package io.github.kamilszewc.simplerouter;

import java.net.MalformedURLException;
import java.net.URL;

public class Request {

    private final SimpleRouter.Method method;
    private final URL url;

    public Request(SimpleRouter.Method method, URL url) {
        this.method = method;
        this.url = url;
    }

    public Request(SimpleRouter.Method method, String url) throws MalformedURLException {
        this.method = method;
        this.url = new URL(url);
    }

    public SimpleRouter.Method getMethod() {
        return method;
    }

    public URL getUrl() {
        return url;
    }

    public String getPath() {
        return url.getPath();
    }
}
