package io.ylab.intensive.lesson05.eventsourcing;

public class RequestToDB {
    private String method;
    private String body;

    public RequestToDB() {
    }

    public RequestToDB(String method, String body) {
        this.method = method;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
