package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

public class JoinGamePacket extends Packet {
    private int entityId;
    private boolean hardcore;
    private GameMode gameMode;
    private GameMode previousGameMode;
    // TODO: World and dimension stuff
    private Difficulty difficulty;
    private int maxPlayers;
    private int viewDistance;
    private boolean reducedDebugInfo;
    private boolean respawnScreen;
    private boolean debug;
    private boolean flat;

    public JoinGamePacket(int entityId, boolean hardcore, GameMode gameMode, GameMode previousGameMode,
                          Difficulty difficulty, int maxPlayers, int viewDistance, boolean reducedDebugInfo,
                          boolean debug, boolean flat) {
        this.entityId = entityId;
        this.hardcore = hardcore;
        this.gameMode = gameMode;
        this.previousGameMode = previousGameMode;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.viewDistance = viewDistance;
        this.reducedDebugInfo = reducedDebugInfo;
        this.debug = debug;
        this.flat = flat;
    }

    public JoinGamePacket() {
        entityId = 0;
        hardcore = false;
        gameMode = GameMode.SURVIVAL;
        previousGameMode = GameMode.NONE;
        // TODO: World and dimension stuff
        difficulty = Difficulty.PEACEFUL;
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
        } else {
            return 0x23;
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
            gameMode = GameMode.of(buffer.readUnsignedByte());
            buffer.readInt(); // TODO: Dimension
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
            buffer.writeByte(gameMode.getValue());
            buffer.writeInt(0); // TODO: Dimension
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

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getPreviousGameMode() {
        return previousGameMode;
    }

    public void setPreviousGameMode(GameMode previousGameMode) {
        this.previousGameMode = previousGameMode;
    }

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
