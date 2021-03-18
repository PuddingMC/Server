package me.puds.server.api.text;

public class TextClickResponse {
    private final String action;
    private final Object value;

    public TextClickResponse(String action, Object value) {
        this.action = action;
        this.value = value;
    }

    public static TextClickResponse openUrl(String url) {
        return new TextClickResponse("open_url", url);
    }

    public static TextClickResponse runCommand(String command) {
        return new TextClickResponse("run_command", command);
    }

    public static TextClickResponse suggestCommand(String command) {
        return new TextClickResponse("suggest_command", command);
    }

    public static TextClickResponse changePage(int page) {
        return new TextClickResponse("change_page", page);
    }

    public String getAction() {
        return action;
    }

    public Object getValue() {
        return value;
    }
}
