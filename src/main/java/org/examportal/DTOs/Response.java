package org.examportal.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> extends BaseResponseDto<T> {
    private T data;
    private int length;
    private boolean isEmpty;

    public Response(T data) {
        this.data = data;
    }

    public Response(T data, int length) {
        this.data = data;
        this.length = length;
    }

}
