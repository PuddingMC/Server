package me.puds.server.api.text;

public class TextHoverResponse {
    private final String action;
    private final Object value;

    public TextHoverResponse(String action, Object value) {
        this.action = action;
        this.value = value;
    }

    public String getAction() {
        return action;
    }

    public Object getValue() {
        return value;
    }

    public static TextHoverResponse showText(String text) {
        return new TextHoverResponse("show_text", text);
    }

    // TODO: showItem
    // TODO: showEntity
}
