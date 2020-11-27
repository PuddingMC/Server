package me.puds.server.api.protocol.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.puds.server.api.protocol.NetworkDifficulty;
import me.puds.server.api.protocol.NetworkGameMode;
import me.puds.server.api.protocol.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class GameJoinPacket extends Packet {
    private int entityId;
    private boolean hardcore;
    private NetworkGameMode gameMode;
    private NetworkGameMode previousGameMode;
    // TODO: World and dimension stuff
    private NetworkDifficulty difficulty;
    private int maxPlayers;
    private int viewDistance;
    private boolean reducedDebugInfo;
    private boolean respawnScreen;
    private boolean debug;
    private boolean flat;

    public GameJoinPacket() {
        entityId = 0;
        hardcore = false;
        gameMode = NetworkGameMode.SURVIVAL;
        previousGameMode = NetworkGameMode.NONE;
        // TODO: World and dimension stuff
        difficulty = NetworkDifficulty.PEACEFUL;
        maxPlayers = 0;
        viewDistance = 0;
        reducedDebugInfo = false;
        respawnScreen = false;
        debug = false;
        flat = false;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            return 0x24;
        } else if (version.isNewerThan(ProtocolVersion.RELEASE_1_12_2)) {
            return 0x23;
        } else {
            return 0x01;
        }
    }

    @Override
    public ConnectionState getState() {
        return ConnectionState.PLAY;
    }

    @Override
    public PacketSender getSender() {
        return PacketSender.SERVER;
    }

    @Override
    public void fromBuffer(ProtocolVersion version, PacketBuffer buffer) {
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            // TODO: 1.16 implementation
        } else {
            entityId = buffer.readInt();
            gameMode = NetworkGameMode.of(buffer.readUnsignedByte());
            if (version.isNewerThan(ProtocolVersion.RELEASE_1_12_2)) {
                buffer.readInt(); // TODO: Dimension
            } else {
                buffer.readByte(); // TODO: Dimension
            }
            difficulty = NetworkDifficulty.of(buffer.readUnsignedByte());
            maxPlayers = buffer.readUnsignedByte();
            buffer.readString(); // TODO: Level type
            reducedDebugInfo = buffer.readBoolean();
        }
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            // TODO: 1.16 implementation
        } else {
            buffer.writeInt(entityId);
            buffer.writeByte(gameMode.getValue());
            if (version.isNewerThan(ProtocolVersion.RELEASE_1_12_2)) {
                buffer.writeInt(0); // TODO: Dimension
            } else {
                buffer.writeByte(0); // TODO: Dimension
            }
            buffer.writeByte(difficulty.getValue());
            buffer.writeByte(maxPlayers);
            buffer.writeString("default"); // TODO: Level type
            buffer.writeBoolean(reducedDebugInfo);
        }
        return buffer;
    }
}
