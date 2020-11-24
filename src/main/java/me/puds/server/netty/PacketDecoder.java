package me.puds.server.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.puds.server.api.protocol.PacketBuffer;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 2) {
            return;
        }

        PacketBuffer buffer = new PacketBuffer(in);

        in.markReaderIndex();
        int length = buffer.readVarInt();

        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }

        out.add(in.readBytes(length));
    }
}
