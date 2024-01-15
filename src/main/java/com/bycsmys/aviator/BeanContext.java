package com.bycsmys.aviator;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeanContext {

    private long startTime;

    private long endTime;

    private long finishTime;

    private long gapTime;

}
