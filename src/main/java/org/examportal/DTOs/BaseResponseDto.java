package org.examportal.DTOs;

import lombok.*;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class BaseResponseDto {
    public String message;
    public HttpStatusCode responseCode;
    public boolean status;
    public boolean toast;
}
