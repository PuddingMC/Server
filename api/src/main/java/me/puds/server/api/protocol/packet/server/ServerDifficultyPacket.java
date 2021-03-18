package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

public class ServerDifficultyPacket extends Packet {
    private short difficulty;
    private boolean difficultyLocked;

    public ServerDifficultyPacket(short difficulty, boolean difficultyLocked) {
        this.difficulty = difficulty;
        this.difficultyLocked = difficultyLocked;
    }

    public ServerDifficultyPacket() {
        this.difficulty = 0;
        this.difficultyLocked = false;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x0d;
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
        difficulty = buffer.readUnsignedByte();
        difficultyLocked = buffer.readBoolean();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeByte(difficulty);
        buffer.writeBoolean(difficultyLocked);
        return buffer;
    }

    public short getDifficulty() {
        return difficulty;
    }

    public boolean isDifficultyLocked() {
        return difficultyLocked;
    }

    public void setDifficulty(short difficulty) {
        this.difficulty = difficulty;
    }

    public void setDifficultyLocked(boolean difficultyLocked) {
        this.difficultyLocked = difficultyLocked;
    }
}
