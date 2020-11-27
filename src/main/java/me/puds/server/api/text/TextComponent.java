package me.puds.server.api.text;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
public class TextComponent {
    private String text = "";

    private boolean bolded = false;
    private boolean italicized = false;
    private boolean underlined = false;
    private boolean struckthrough = false;
    private boolean obfuscated = false;
    private TextColor color = TextColor.RESET;

    private String insertion = null;
    private TextClickResponse clickEvent = null;
    private TextHoverResponse hoverEvent = null;

    private final List<TextComponent> extras = new ArrayList<>();

    public TextComponent(String text) {
        this.text = text;
    }

    public TextComponent(String text, boolean bolded, boolean italicized, boolean underlined, boolean struckthrough,
                         boolean obfuscated) {
        this.text = text;
        this.bolded = bolded;
        this.italicized = italicized;
        this.underlined = underlined;
        this.struckthrough = struckthrough;
        this.obfuscated = obfuscated;
    }

    public TextComponent(String text, TextColor color) {
        this.text = text;
        this.color = color;
    }

    public TextComponent(String text, TextColor color, boolean bolded, boolean italicized, boolean underlined,
                         boolean struckthrough, boolean obfuscated) {
        this.text = text;
        this.color = color;
        this.bolded = bolded;
        this.italicized = italicized;
        this.underlined = underlined;
        this.struckthrough = struckthrough;
        this.obfuscated = obfuscated;
    }

    public TextComponent(JSONObject json) {
        // TODO: Implement this
    }

    public JSONObject toJson() {
        Map<String, Object> map = new HashMap<>(Map.of(
                "text", text,
                "bold", bolded,
                "italic", italicized,
                "underlined", underlined,
                "strikethrough", struckthrough,
                "obfuscated", obfuscated,
                "color", color.name().toLowerCase()
        ));

        if (insertion != null) {
            map.put("insertion", insertion);
        }
        if (clickEvent != null) {
            map.put("clickEvent", Map.of(
                    "action", clickEvent.getAction(),
                    "value", clickEvent.getValue()
            ));
        }
        if (hoverEvent != null) {
            map.put("hoverEvent", Map.of(
                    "action", hoverEvent.getAction(),
                    "value", hoverEvent.getValue()
            ));
        }

        if (!extras.isEmpty()) {
            List<JSONObject> extraList = new ArrayList<>();
            for (TextComponent extra : extras) {
                extraList.add(extra.toJson());
            }
            map.put("extra", extraList);
        }

        return new JSONObject(map);
    }

    // TODO: toLegacyText
    // TODO: fromLegacyText

    public TextBuilder getBuilder() {
        return TextBuilder.builder(this);
    }

    public void addExtra(TextComponent extra) {
        extras.add(extra);
    }
}
