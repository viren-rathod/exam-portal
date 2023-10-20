package org.examportal.DTOs;

import lombok.*;
import org.examportal.Constants.Status;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class BaseResponseDto<T> {
    private String message;
    private int responseCode;
    private Status status;
    private boolean toast;
    private T data;
}
