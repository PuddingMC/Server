package me.puds.server.common.text;

public class TextBuilder {
    private final TextComponent component;

    private TextBuilder() {
        component = new TextComponent();
    }

    private TextBuilder(TextComponent component) {
        this.component = component;
    }

    public TextBuilder add(TextComponent component) {
        this.component.addExtra(component);
        return this;
    }

    public TextBuilder add(String text) {
        add(new TextComponent(text));
        return this;
    }

    public TextBuilder add(String text, boolean bolded, boolean italicized, boolean underlined, boolean struckthrough,
                    boolean obfuscated) {
        add(new TextComponent(text, bolded, italicized, underlined, struckthrough, obfuscated));
        return this;
    }

    public TextBuilder add(String text, TextColor color) {
        add(new TextComponent(text, color));
        return this;
    }

    public TextBuilder add(String text, TextColor color, boolean bolded, boolean italicized, boolean underlined,
                    boolean struckthrough, boolean obfuscated) {
        add(new TextComponent(text, color, bolded, italicized, underlined, struckthrough, obfuscated));
        return this;
    }

    public TextBuilder add() {
        add(new TextComponent());
        return this;
    }

    public TextBuilder text(String text) {
        getLastExtra().setText(text);
        return this;
    }

    public TextBuilder bolded(boolean bolded) {
        getLastExtra().setBolded(bolded);
        return this;
    }

    public TextBuilder italicized(boolean italicized) {
        getLastExtra().setItalicized(italicized);
        return this;
    }

    public TextBuilder underlined(boolean underlined) {
        getLastExtra().setUnderlined(underlined);
        return this;
    }

    public TextBuilder struckthrough(boolean struckthrough) {
        getLastExtra().setStruckthrough(struckthrough);
        return this;
    }

    public TextBuilder obfuscated(boolean obfuscated) {
        getLastExtra().setObfuscated(obfuscated);
        return this;
    }

    public TextBuilder color(TextColor color) {
        getLastExtra().setColor(color);
        return this;
    }

    public TextBuilder insertion(String insertion) {
        getLastExtra().setInsertion(insertion);
        return this;
    }

    public TextBuilder clickEvent(TextClickResponse clickEvent) {
        getLastExtra().setClickEvent(clickEvent);
        return this;
    }

    public TextBuilder clickEvent(String action, Object value) {
        clickEvent(new TextClickResponse(action, value));
        return this;
    }

    public TextBuilder hoverEvent(TextHoverResponse hoverEvent) {
        getLastExtra().setHoverEvent(hoverEvent);
        return this;
    }

    public TextBuilder hoverEvent(String action, Object value) {
        hoverEvent(new TextHoverResponse(action, value));
        return this;
    }

    public TextBuilder reset() {
        bolded(false);
        italicized(false);
        underlined(false);
        struckthrough(false);
        obfuscated(false);
        color(TextColor.RESET);
        insertion(null);
        clickEvent(null);
        hoverEvent(null);
        return this;
    }

    public TextBuilder space() {
        add(" ");
        return this;
    }

    public TextBuilder newline() {
        add("\n");
        return this;
    }

    public TextComponent build() {
        return component;
    }

    public static TextBuilder builder(TextComponent component) {
        return new TextBuilder(component);
    }

    public static TextBuilder builder() {
        return new TextBuilder();
    }

    private TextComponent getLastExtra() {
        int index = component.getExtras().size() - 1;
        return component.getExtras().get(index);
    }
}
