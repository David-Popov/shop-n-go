package javawizzards.shopngo.dtos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Response {
    private LocalDate date;
    private String errorDescription;
    private String ResponseID;

    public Response() {
        setDate(LocalDate.now());
        setResponseID(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS")));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
