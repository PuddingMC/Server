package me.puds.server.api.protocol;

import java.lang.reflect.Field;
import java.util.Arrays;

public enum ProtocolVersion {
    UNSUPPORTED(-1, "unsupported"),
    RELEASE_1_12_2(340, "1.12.2"),
    RELEASE_1_16(735, "1.16"),
    RELEASE_1_16_1(736, "1.16.1"),
    SNAPSHOT_20W27A(738, "20w27a"), // 1.16.2
    SNAPSHOT_20W28A(740, "20w28a"), // 1.16.2
    SNAPSHOT_20W29A(741, "20w29a"), // 1.16.2
    SNAPSHOT_20W30A(743, "20w30a"), // 1.16.2
    SNAPSHOT_1_16_2_PRE1(744, "1.16.2-pre1"),
    SNAPSHOT_1_16_2_PRE2(746, "1.16.2-pre2"),
    SNAPSHOT_1_16_2_PRE3(748, "1.16.2-pre3"),
    SNAPSHOT_1_16_2_RC1(749, "1.16.2-rc1"),
    SNAPSHOT_1_16_2_RC2(750, "1.16.2-rc2"),
    RELEASE_1_16_2(751, "1.16.2"),
    SNAPSHOT_1_16_3_RC1(752, "1.16.3-rc1"),
    RELEASE_1_16_3(753, "1.16.3"),
    SNAPSHOT_1_16_4_PRE1(1073741825, "1.16.4-pre1"),
    SNAPSHOT_1_16_4_PRE2(1073741826, "1.16.4-pre2"),
    SNAPSHOT_1_16_4_RC1(1073741827, "1.16.4-rc1"),
    RELEASE_1_16_4(754, "1.16.4"),
    SNAPSHOT_20W45A(1073741829, "20w45a"), // 1.17
    SNAPSHOT_20W46A(1073741830, "20w46a"), // 1.17
    SNAPSHOT_20W48A(1073741831, "20w48a"), // 1.17
    SNAPSHOT_20W49A(1073741832, "20w49a"); // 1.17

    private final int version;
    private final String displayName;

    ProtocolVersion(int version, String displayName) {
        this.version = version;
        this.displayName = displayName;
    }

    public boolean isNewerThan(ProtocolVersion other) {
        return ordinal() >= other.ordinal();
    }

    public static ProtocolVersion of(int version) {
        return Arrays.stream(values())
                .filter(v -> v.getVersion() == version)
                .findFirst()
                .orElse(UNSUPPORTED);
    }

    public Protocol getProtocol() {
        try {
            Field field = ProtocolConstants.class.getDeclaredField("V" + version);
            Object object = field.get(null);
            if (object instanceof Protocol) {
                return (Protocol) object;
            }
            return ProtocolConstants.STATUS_ONLY;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return ProtocolConstants.STATUS_ONLY;
        }
    }

    public int getVersion() {
        return version;
    }

    public String getDisplayName() {
        return displayName;
    }
}
