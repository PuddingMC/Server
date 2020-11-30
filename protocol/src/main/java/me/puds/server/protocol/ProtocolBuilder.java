package me.puds.server.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProtocolBuilder {
    private final List<Packet> packets = new ArrayList<>();
    private ProtocolVersion version;

    private ProtocolBuilder() {}

    public static ProtocolBuilder builder() {
        return new ProtocolBuilder();
    }

    public ProtocolBuilder version(ProtocolVersion version) {
        this.version = version;
        return this;
    }

    public ProtocolBuilder inherit(Protocol protocol) {
        for (Map<PacketSender, Map<Integer, Packet>> state : protocol.getPackets().values()) {
            for (Map<Integer, Packet> sender : state.values()) {
                packets.addAll(sender.values());
            }
        }
        return this;
    }

    public ProtocolBuilder packet(Packet packet) {
        packets.add(packet);
        return this;
    }

    public ProtocolBuilder remove(Packet packet) {
        packets.removeIf(currentPacket -> packet.getClass().isAssignableFrom(currentPacket.getClass()));
        return this;
    }

    public Protocol build() {
        Map<ConnectionState, Map<PacketSender, Map<Integer, Packet>>> properPackets = new HashMap<>();
        for (Packet packet : packets) {
            if (!properPackets.containsKey(packet.getState())) {
                properPackets.put(packet.getState(), new HashMap<>());
            }

            Map<PacketSender, Map<Integer, Packet>> stateMap = properPackets.get(packet.getState());
            if (!stateMap.containsKey(packet.getSender())) {
                properPackets.get(packet.getState()).put(packet.getSender(), new HashMap<>());
            }

            properPackets.get(packet.getState()).get(packet.getSender()).put(packet.getPacketId(version), packet);
        }
        return new Protocol(properPackets);
    }
}
