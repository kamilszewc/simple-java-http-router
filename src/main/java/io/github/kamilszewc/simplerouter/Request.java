package io.github.kamilszewc.simplerouter;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Request class - describes the request that reaches router
 */
public class Request {

    private final SimpleRouter.Method method;
    private final URI uri;
    private Map<String, String> parameters = new HashMap<>();

    /**
     * Constructor
     * @param method Http method
     * @param uri Http path (URI)
     */
    public Request(SimpleRouter.Method method, URI uri) {
        this.method = method;
        this.uri = uri;
    }

    /**
     * Constructor
     * @param method Http method
     * @param url URL
     * @throws URISyntaxException Risen when URL can not be converted to URI
     */
    public Request(SimpleRouter.Method method, URL url) throws URISyntaxException {
        this.method = method;
        this.uri = url.toURI();
    }

    /**
     * Constructor
     * @param method Http method
     * @param path Path
     * @throws URISyntaxException Risen when path can not be converted to URI
     */
    public Request(SimpleRouter.Method method, String path) throws URISyntaxException {
        this.method = method;
        this.uri = new URI(path);
    }

    /**
     * Constructor
     * @param method Http method
     * @param path Path
     * @throws URISyntaxException Risen when path can not be converted to URI
     */
    public Request(String method, String path) throws URISyntaxException {
        this.method = SimpleRouter.Method.valueOf(method.toUpperCase());
        this.uri = new URI(path);
    }

    /**
     * Constructor
     * @param method Http method
     * @param path Path
     * @param parameters Path parameters map
     * @throws URISyntaxException Risen when path can not be converted to URI
     */
    public Request(String method, String path, Map<String, String> parameters) throws URISyntaxException {
        this.method = SimpleRouter.Method.valueOf(method.toUpperCase());
        this.uri = new URI(path);
        this.parameters = parameters;
    }

    /**
     * Constructor
     * @param method Http method
     * @param path Path
     * @param parameters Path parameters map
     * @throws URISyntaxException Risen when path can not be converted to URI
     */
    public Request(SimpleRouter.Method method, String path, Map<String, String> parameters) throws URISyntaxException {
        this.method = method;
        this.uri = new URI(path);
        this.parameters = parameters;
    }

    /**
     * Returns http method of request
     * @return Http method
     */
    public SimpleRouter.Method getMethod() {
        return method;
    }

    /**
     * Returns http uri of request
     * @return URI
     */
    public URI getUri() {
        return uri;
    }

    public String getPath() {
        return uri.getPath();
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
