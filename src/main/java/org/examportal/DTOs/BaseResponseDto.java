package org.examportal.DTOs;

import lombok.*;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class BaseResponseDto<T> {
    private String message;
    private int responseCode;
    private boolean status;
    private boolean toast;
    private T data;
}
