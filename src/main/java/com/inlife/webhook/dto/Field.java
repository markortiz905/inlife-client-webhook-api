package com.inlife.webhook.dto;

import lombok.*;

import java.util.List;

/**
 * @author mark ortiz
 */
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Field {
    private String status;
    private String type;
    private Integer field_id;
    private String label;
    private List<Value> values;
    private String external_id;
}