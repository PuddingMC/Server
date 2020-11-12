package me.puds.server.api.protocol;

import me.puds.server.api.protocol.client.HandshakePacket;

public class ProtocolConstants {
    public static final Protocol V47 = ProtocolBuilder.builder()
            .version(ProtocolVersion.RELEASE_1_8)
            .packet(new HandshakePacket())
            .build();

    public static final Protocol V340 = ProtocolBuilder.builder()
            .version(ProtocolVersion.RELEASE_1_12_2)
            .inherit(V47)
            .build();

    public static final Protocol V735 = ProtocolBuilder.builder()
            .version(ProtocolVersion.RELEASE_1_16)
            .inherit(V340)
            .build();

    public static final Protocol V736 = ProtocolBuilder.builder()
            .version(ProtocolVersion.RELEASE_1_16_1)
            .inherit(V735)
            .build();

    public static final Protocol V738 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_20W27A)
            .inherit(V736)
            .build();

    public static final Protocol V740 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_20W28A)
            .inherit(V738)
            .build();

    public static final Protocol V741 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_20W29A)
            .inherit(V740)
            .build();

    public static final Protocol V743 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_20W30A)
            .inherit(V741)
            .build();

    public static final Protocol V744 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_1_16_2_PRE1)
            .inherit(V743)
            .build();

    public static final Protocol V746 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_1_16_2_PRE2)
            .inherit(V744)
            .build();

    public static final Protocol V748 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_1_16_2_PRE3)
            .inherit(V746)
            .build();

    public static final Protocol V749 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_1_16_2_RC1)
            .inherit(V748)
            .build();

    public static final Protocol V750 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_1_16_2_RC2)
            .inherit(V749)
            .build();

    public static final Protocol V751 = ProtocolBuilder.builder()
            .version(ProtocolVersion.RELEASE_1_16_2)
            .inherit(V750)
            .build();

    public static final Protocol V752 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_1_16_3_RC1)
            .inherit(V751)
            .build();

    public static final Protocol V753 = ProtocolBuilder.builder()
            .version(ProtocolVersion.RELEASE_1_16_3)
            .inherit(V752)
            .build();

    public static final Protocol V1073741825 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_1_16_4_PRE1)
            .inherit(V753)
            .build();

    public static final Protocol V1073741826 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_1_16_4_PRE2)
            .inherit(V1073741825)
            .build();

    public static final Protocol V1073741827 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_1_16_4_RC1)
            .inherit(V1073741826)
            .build();

    public static final Protocol V754 = ProtocolBuilder.builder()
            .version(ProtocolVersion.RELEASE_1_16_4)
            .inherit(V1073741827)
            .build();

    public static final Protocol V1073741829 = ProtocolBuilder.builder()
            .version(ProtocolVersion.SNAPSHOT_20W45A)
            .inherit(V754)
            .build();
}
