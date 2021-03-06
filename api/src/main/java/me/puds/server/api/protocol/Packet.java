package me.puds.server.api.protocol;

import me.puds.server.api.event.Listenable;

public abstract class Packet implements Listenable {
    private boolean canceled = false;

    public abstract int getPacketId(ProtocolVersion version);
    public abstract ConnectionState getState();
    public abstract PacketSender getSender();

    public abstract void fromBuffer(ProtocolVersion version, PacketBuffer buffer);
    public abstract PacketBuffer toBuffer(ProtocolVersion version);

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
