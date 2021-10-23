package net.reikeb.maxicity.misc;

public enum Version {

    v1_17_1("v1_17_R1");

    private final String s;

    private Version(String s) {
        this.s = s;
    }

    public static Version fromString(String text) {
        if (text != null)
            for (Version c : Version.values())
                if (text.equalsIgnoreCase(c.s))
                    return c;
        return null;
    }
}