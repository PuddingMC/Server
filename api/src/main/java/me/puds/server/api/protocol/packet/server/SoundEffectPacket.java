package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;

public class SoundEffectPacket extends Packet {
    private int soundId;
    private SoundCategory soundCategory;
    private int effectPositionX;
    private int effectPositionY;
    private int effectPositionZ;
    private float volume;
    private float pitch;

    public SoundEffectPacket(int soundId, SoundCategory soundCategory, int effectPositionX, int effectPositionY,
                             int effectPositionZ, float volume, float pitch) {
        this.soundId = soundId;
        this.soundCategory = soundCategory;
        this.effectPositionX = effectPositionX;
        this.effectPositionY = effectPositionY;
        this.effectPositionZ = effectPositionZ;
        this.volume = volume;
        this.pitch = pitch;
    }

    public SoundEffectPacket() {
        this.soundId = 0;
        this.soundCategory = SoundCategory.MASTER;
        this.effectPositionX = 0;
        this.effectPositionY = 0;
        this.effectPositionZ = 0;
        this.volume = 0;
        this.pitch = 0;
    }

    @Override
    public int getPacketId(ProtocolVersion version) {
        if (version.isNewerThan(ProtocolVersion.RELEASE_1_16)) {
            return 0x51;
        } else {
            return 0x49;
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
        soundId = buffer.readVarInt();
        soundCategory = SoundCategory.values()[buffer.readVarInt()];
        effectPositionX = buffer.readInt();
        effectPositionY = buffer.readInt();
        effectPositionZ = buffer.readInt();
        volume = buffer.readFloat();
        pitch = buffer.readFloat();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(soundId);
        buffer.writeVarInt(soundCategory.ordinal());
        buffer.writeInt(effectPositionX);
        buffer.writeInt(effectPositionY);
        buffer.writeInt(effectPositionZ);
        buffer.writeFloat(volume);
        buffer.writeFloat(pitch);
        return buffer;
    }

    public int getSoundId() {
        return soundId;
    }

    public SoundCategory getSoundCategory() {
        return soundCategory;
    }

    public int getEffectPositionX() {
        return effectPositionX;
    }

    public int getEffectPositionY() {
        return effectPositionY;
    }

    public int getEffectPositionZ() {
        return effectPositionZ;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public void setSoundCategory(SoundCategory soundCategory) {
        this.soundCategory = soundCategory;
    }

    public void setEffectPositionX(int effectPositionX) {
        this.effectPositionX = effectPositionX;
    }

    public void setEffectPositionY(int effectPositionY) {
        this.effectPositionY = effectPositionY;
    }

    public void setEffectPositionZ(int effectPositionZ) {
        this.effectPositionZ = effectPositionZ;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
