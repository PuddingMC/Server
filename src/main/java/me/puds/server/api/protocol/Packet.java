package me.puds.server.api.protocol;

import lombok.Getter;
import lombok.Setter;
import me.puds.server.api.event.Listenable;

@Getter
@Setter
public abstract class Packet implements Listenable {
    private boolean canceled = false;

    public abstract int getPacketId(ProtocolVersion version);
    public abstract ConnectionState getState();
    public abstract PacketSender getSender();

    public abstract void fromBuffer(ProtocolVersion version, PacketBuffer buffer);
    public abstract PacketBuffer toBuffer(ProtocolVersion version);
}
