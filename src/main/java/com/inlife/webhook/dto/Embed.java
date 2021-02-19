package com.inlife.webhook.dto;

import lombok.*;

/**
 * @author mark ortiz
 */
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Embed {
    private int embed_id;
    private Object embed_html;
    private Object description;
    private String original_url;
    private String url;
    private String hostname;
    private Object embed_height;
    private String resolved_url;
    private String title;
    private String type;
    private Object embed_width;
}