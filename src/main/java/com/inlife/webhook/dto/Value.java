package com.inlife.webhook.dto;

import lombok.*;

/**
 * @author mark ortiz
 */
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Value {
    private Object value;
    private String start;
    private String start_date_utc;
    private Object start_time_utc;
    private Object start_time;
    private String start_utc;
    private String start_date;
    private Embed embed;
    private Object file;
}