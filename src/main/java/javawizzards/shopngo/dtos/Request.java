package javawizzards.shopngo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
public class Request<T> implements Serializable {
    private String requestId;
    private LocalDateTime timestamp;
    private T data;
}
