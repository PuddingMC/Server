package me.puds.server.api.protocol.packet.client;

import me.puds.server.api.protocol.*;

public class InteractEntityPacket extends Packet {
    private int entityId;
    private EntityInteraction type;
    private float targetX;
    private float targetY;
    private float targetZ;
    private Hand hand;
    private boolean sneaking;

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0x0e;
    }

    @Override
    public ConnectionState getState() {
        return ConnectionState.PLAY;
    }

    @Override
    public PacketSender getSender() {
        return PacketSender.CLIENT;
    }

    @Override
    public void fromBuffer(ProtocolVersion version, PacketBuffer buffer) {
        this.entityId = buffer.readInt();
        this.type = EntityInteraction.of(buffer.readVarInt());
        if (type == EntityInteraction.INTERACT_AT) {
            this.targetX = buffer.readFloat();
            this.targetY = buffer.readFloat();
            this.targetZ = buffer.readFloat();
        }
        if (type == EntityInteraction.INTERACT || type == EntityInteraction.INTERACT_AT) {
            this.hand = Hand.of(buffer.readVarInt());
        }
        this.sneaking = buffer.readBoolean();
    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeInt(entityId);
        buffer.writeVarInt(type.getValue());
        if (type == EntityInteraction.INTERACT_AT) {
            buffer.writeFloat(targetX);
            buffer.writeFloat(targetY);
            buffer.writeFloat(targetZ);
        }
        if (type == EntityInteraction.INTERACT || type == EntityInteraction.INTERACT_AT) {
            buffer.writeVarInt(hand.getValue());
        }
        buffer.writeBoolean(sneaking);
        return buffer;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public EntityInteraction getType() {
        return type;
    }

    public void setType(EntityInteraction type) {
        this.type = type;
    }

    public float getTargetX() {
        return targetX;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
    }

    public float getTargetZ() {
        return targetZ;
    }

    public void setTargetZ(float targetZ) {
        this.targetZ = targetZ;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }
}
