package me.puds.server.common.text;

public enum TextColor {
    BLACK('0', 30),
    DARK_BLUE('1', 34),
    DARK_GREEN('2', 32),
    DARK_AQUA('3', 36),
    DARK_RED('4', 31),
    DARK_PURPLE('5', 35),
    GOLD('6', 33),
    GRAY('7', 37),
    DARK_GRAY('8', 90),
    BLUE('9', 94),
    GREEN('a', 92),
    AQUA('b', 96),
    RED('c', 91),
    LIGHT_PURPLE('d', 95),
    YELLOW('e', 93),
    WHITE('f', 97),
    MAGIC('k', 5),
    BOLD('l', 1),
    STRIKETHROUGH('m', 9),
    UNDERLINE('n', 4),
    ITALIC('o', 3),
    RESET('r', 0);

    private static final char COLOR_CHAR = '§';

    private final char character;
    private final int ansi;

    TextColor(char character, int ansi) {
        this.character = character;
        this.ansi = ansi;
    }

    @Override
    public String toString() {
        return COLOR_CHAR + "" + character;
    }

    public char getCharacter() {
        return character;
    }

    public int getAnsi() {
        return ansi;
    }
}
