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
public class ClientRequestDto {

    private Long item_id;
    private List<Field> fields;

}
