package javawizzards.shopngo.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Request {
    private LocalDateTime date;
    private String RequestId;

    public Request() {
    }

    public Request(LocalDateTime date, String requestId) {
        this.date = date;
        RequestId = requestId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }
}
