package me.puds.server.api.text;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public TextComponent() {}

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isBolded() {
        return bolded;
    }

    public void setBolded(boolean bolded) {
        this.bolded = bolded;
    }

    public boolean isItalicized() {
        return italicized;
    }

    public void setItalicized(boolean italicized) {
        this.italicized = italicized;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }

    public boolean isStruckthrough() {
        return struckthrough;
    }

    public void setStruckthrough(boolean struckthrough) {
        this.struckthrough = struckthrough;
    }

    public boolean isObfuscated() {
        return obfuscated;
    }

    public void setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
    }

    public TextColor getColor() {
        return color;
    }

    public void setColor(TextColor color) {
        this.color = color;
    }

    public String getInsertion() {
        return insertion;
    }

    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }

    public TextClickResponse getClickEvent() {
        return clickEvent;
    }

    public void setClickEvent(TextClickResponse clickEvent) {
        this.clickEvent = clickEvent;
    }

    public TextHoverResponse getHoverEvent() {
        return hoverEvent;
    }

    public void setHoverEvent(TextHoverResponse hoverEvent) {
        this.hoverEvent = hoverEvent;
    }

    public List<TextComponent> getExtras() {
        return extras;
    }

    public void addExtra(TextComponent extra) {
        extras.add(extra);
    }
}
