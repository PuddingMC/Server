package me.puds.server.api.protocol.server;

import me.puds.server.api.Difficulty;
import me.puds.server.api.Gamemode;
import me.puds.server.api.protocol.*;

public class GameJoinPacket extends Packet {
    private int entityId;
    private boolean hardcore;
    private Gamemode gamemode;
    private Gamemode previousGamemode;
    // TODO: World and dimension stuff
    private Difficulty difficulty;
    private int maxPlayers;
    private int viewDistance;
    private boolean reducedDebugInfo;
    private boolean respawnScreen;
    private boolean debug;
    private boolean flat;

    public GameJoinPacket() {
        entityId = 0;
        hardcore = false;
        gamemode = Gamemode.SURVIVAL;
        previousGamemode = Gamemode.NONE;
        // TODO: World and dimension stuff
        difficulty = Difficulty.PEACEFUL;
        maxPlayers = 0;
        viewDistance = 0;
        reducedDebugInfo = false;
        respawnScreen = false;
        debug = false;
        flat = false;
    }

    public GameJoinPacket(int entityId, boolean hardcore, Gamemode gamemode, Gamemode previousGamemode,
                          Difficulty difficulty, int maxPlayers, int viewDistance, boolean reducedDebugInfo,
                          boolean respawnScreen, boolean debug, boolean flat) {
        this.entityId = entityId;
        this.hardcore = hardcore;
        this.gamemode = gamemode;
        this.previousGamemode = previousGamemode;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.viewDistance = viewDistance;
        this.reducedDebugInfo = reducedDebugInfo;
        this.respawnScreen = respawnScreen;
        this.debug = debug;
        this.flat = flat;
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
            gamemode = Gamemode.of(buffer.readUnsignedByte());
            if (version.isNewerThan(ProtocolVersion.RELEASE_1_12_2)) {
                buffer.readInt(); // TODO: Dimension
            } else {
                buffer.readByte(); // TODO: Dimension
            }
            difficulty = Difficulty.of(buffer.readUnsignedByte());
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
            buffer.writeByte(gamemode.getValue());
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

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public boolean isHardcore() {
        return hardcore;
    }

    public void setHardcore(boolean hardcore) {
        this.hardcore = hardcore;
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    public Gamemode getPreviousGamemode() {
        return previousGamemode;
    }

    public void setPreviousGamemode(Gamemode previousGamemode) {
        this.previousGamemode = previousGamemode;
    }

    // TODO: World and dimension stuff

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(int viewDistance) {
        this.viewDistance = viewDistance;
    }

    public boolean isReducedDebugInfo() {
        return reducedDebugInfo;
    }

    public void setReducedDebugInfo(boolean reducedDebugInfo) {
        this.reducedDebugInfo = reducedDebugInfo;
    }

    public boolean isRespawnScreen() {
        return respawnScreen;
    }

    public void setRespawnScreen(boolean respawnScreen) {
        this.respawnScreen = respawnScreen;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isFlat() {
        return flat;
    }

    public void setFlat(boolean flat) {
        this.flat = flat;
    }
}
