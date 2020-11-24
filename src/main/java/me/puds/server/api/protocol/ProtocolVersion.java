package me.puds.server.api.protocol;

import java.util.Arrays;

public enum ProtocolVersion {
    UNSUPPORTED(-1, "unsupported", ProtocolConstants.STATUS_ONLY),
    RELEASE_1_8(47, "1.8", ProtocolConstants.V47), // All 1.8.x versions use the same protocol version
    RELEASE_1_12_2(340, "1.12.2", ProtocolConstants.V340),
    RELEASE_1_16(735, "1.16", ProtocolConstants.V735),
    RELEASE_1_16_1(736, "1.16.1", ProtocolConstants.V736),
    SNAPSHOT_20W27A(738, "20w27a", ProtocolConstants.V738), // 1.16.2
    SNAPSHOT_20W28A(740, "20w28a", ProtocolConstants.V740), // 1.16.2
    SNAPSHOT_20W29A(741, "20w29a", ProtocolConstants.V741), // 1.16.2
    SNAPSHOT_20W30A(743, "20w30a", ProtocolConstants.V743), // 1.16.2
    SNAPSHOT_1_16_2_PRE1(744, "1.16.2-pre1", ProtocolConstants.V744),
    SNAPSHOT_1_16_2_PRE2(746, "1.16.2-pre2", ProtocolConstants.V746),
    SNAPSHOT_1_16_2_PRE3(748, "1.16.2-pre3", ProtocolConstants.V748),
    SNAPSHOT_1_16_2_RC1(749, "1.16.2-rc1", ProtocolConstants.V749),
    SNAPSHOT_1_16_2_RC2(750, "1.16.2-rc2", ProtocolConstants.V750),
    RELEASE_1_16_2(751, "1.16.2", ProtocolConstants.V751),
    SNAPSHOT_1_16_3_RC1(752, "1.16.3-rc1", ProtocolConstants.V752),
    RELEASE_1_16_3(753, "1.16.3", ProtocolConstants.V753),
    SNAPSHOT_1_16_4_PRE1(1073741825, "1.16.4-pre1", ProtocolConstants.V1073741825),
    SNAPSHOT_1_16_4_PRE2(1073741826, "1.16.4-pre2", ProtocolConstants.V1073741826),
    SNAPSHOT_1_16_4_RC1(1073741827, "1.16.4-rc1", ProtocolConstants.V1073741827),
    RELEASE_1_16_4(754, "1.16.4", ProtocolConstants.V754),
    SNAPSHOT_20W45A(1073741829, "20w45a", ProtocolConstants.V1073741829), // 1.17
    SNAPSHOT_20W46A(1073741830, "20w46a", ProtocolConstants.V1073741830); // 1.17

    private final int version;
    private final String displayName;
    private final Protocol protocol;

    ProtocolVersion(int version, String displayName, Protocol protocol) {
        this.version = version;
        this.displayName = displayName;
        this.protocol = protocol;
    }

    public boolean isNewerThan(ProtocolVersion other) {
        return ordinal() >= other.ordinal();
    }

    public int getVersion() {
        return version;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public static ProtocolVersion of(int version) {
        return Arrays.stream(values())
                .filter(v -> v.getVersion() == version)
                .findFirst()
                .orElse(UNSUPPORTED);
    }
}
