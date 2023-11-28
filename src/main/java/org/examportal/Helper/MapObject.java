package org.examportal.Helper;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MapObject<T, K> {
    private T T;
    private K K;
}
