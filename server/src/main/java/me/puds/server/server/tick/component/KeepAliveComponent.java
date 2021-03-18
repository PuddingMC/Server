package me.puds.server.server.tick.component;

import me.puds.server.api.Player;
import me.puds.server.api.Server;
import me.puds.server.api.text.TextColor;
import me.puds.server.api.text.TextComponent;
import me.puds.server.api.protocol.packet.server.ServerKeepAlivePacket;
import me.puds.server.server.tick.TickComponent;

public class KeepAliveComponent implements TickComponent {
    @Override
    public void start() {}

    @Override
    public void stop() {}

    @Override
    public void tick() {
        for (Player player : Server.getOnlinePlayers()) {
            if (System.currentTimeMillis() >= player.getLastKeepAlive() + 30000) {
                player.disconnect(new TextComponent("Timed out", TextColor.RED)); // TODO: Better timeout message
            }

            ServerKeepAlivePacket packet = new ServerKeepAlivePacket(System.currentTimeMillis());
            player.sendPacket(packet);
        }
    }
}
