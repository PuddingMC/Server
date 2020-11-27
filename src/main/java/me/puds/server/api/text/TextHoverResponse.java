package me.puds.server.api.text;

import lombok.Data;

@Data
public class TextHoverResponse {
    private final String action;
    private final Object value;

    public static TextHoverResponse showText(String text) {
        return new TextHoverResponse("show_text", text);
    }

    // TODO: showItem
    // TODO: showEntity
}
