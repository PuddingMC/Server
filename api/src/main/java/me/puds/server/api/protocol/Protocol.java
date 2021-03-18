package me.puds.server.api.protocol;

import java.util.Map;

public class Protocol {
    private final Map<ConnectionState, Map<PacketSender, Map<Integer, Packet>>> packets;

    public Protocol(Map<ConnectionState, Map<PacketSender, Map<Integer, Packet>>> packets) {
        this.packets = packets;
    }

    public boolean hasPacket(ConnectionState state, PacketSender sender, int packetId) {
        if (!packets.containsKey(state)) {
            return false;
        }

        Map<PacketSender, Map<Integer, Packet>> statePackets = packets.get(state);
        if (!statePackets.containsKey(sender)) {
            return false;
        }

        Map<Integer, Packet> senderPackets = statePackets.get(sender);
        return senderPackets.containsKey(packetId);
    }

    public Packet getPacket(ConnectionState state, PacketSender sender, int packetId) {
        if (!hasPacket(state, sender, packetId)) {
            return null;
        }
        return packets.get(state).get(sender).get(packetId);
    }

    public Map<ConnectionState, Map<PacketSender, Map<Integer, Packet>>> getPackets() {
        return packets;
    }
}
