package me.puds.server.api.protocol.packet.server;

import me.puds.server.api.protocol.*;
import me.puds.server.api.nbt.NbtCompound;

import java.util.List;

public class ChunkDataPacket extends Packet {
    private int chunkX;
    private int chunkY;
    private boolean fullChunk;
    private int primaryBitMask;
    private NbtCompound heightmaps;
    private int biomesLength;
    private List<Integer> biomes;
    private int size;
    // private byte[] data;
    private int numberOfBlockEntities;
    private List<NbtCompound> blockEntities;

    @Override
    public int getPacketId(ProtocolVersion version) {
        return 0;
    }

    @Override
    public ConnectionState getState() {
        return null;
    }

    @Override
    public PacketSender getSender() {
        return null;
    }

    @Override
    public void fromBuffer(ProtocolVersion version, PacketBuffer buffer) {

    }

    @Override
    public PacketBuffer toBuffer(ProtocolVersion version) {
        return null;
    }
}
