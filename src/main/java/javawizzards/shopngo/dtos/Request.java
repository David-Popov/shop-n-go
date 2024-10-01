package javawizzards.shopngo.dtos;

import java.time.LocalDate;

public class Request {
    private LocalDate date;
    private String RequestId;

    public Request() {
    }

    public Request(LocalDate date, String requestId) {
        this.date = date;
        RequestId = requestId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }
}
