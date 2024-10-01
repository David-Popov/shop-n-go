package javawizzards.shopngo.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Response {
    private LocalDateTime date;
    private String errorDescription;
    private String ResponseID;

    public Response() {
        setDate(LocalDateTime.now());
        setResponseID(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS")));
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getResponseID() {
        return ResponseID;
    }

    public void setResponseID(String responseID) {
        ResponseID = responseID;
    }
}
