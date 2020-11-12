package me.puds.server.api;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class PingResponse {
    private String versionName;
    private int protocol;
    private int maxPlayers;
    private int onlinePlayers;
    private Map<String, UUID> playerSample;
    private String description; // TODO: Make this a proper chat component type
    private Image icon;

    public PingResponse() {
        versionName = "";
        protocol = 0;
        maxPlayers = 0;
        onlinePlayers = 0;
        playerSample = new LinkedHashMap<>();
        description = "";
        icon = null;
    }

    public PingResponse(JSONObject json) {
        JSONObject versionObject = json.getJSONObject("version");
        versionName = versionObject.getString("name");
        protocol = versionObject.getInt("protocol");

        JSONObject playersObject = json.getJSONObject("players");
        maxPlayers = playersObject.getInt("max");
        playerSample = new LinkedHashMap<>();

        JSONArray sampleArray = playersObject.getJSONArray("sample");
        for (int i = 0; i < sampleArray.length(); i++) {
            JSONObject object = sampleArray.getJSONObject(i);
            String name = object.getString("name");
            UUID uuid = UUID.fromString(object.getString("id"));
            playerSample.put(name, uuid);
        }

        JSONObject descriptionObject = json.getJSONObject("description");
        description = descriptionObject.getString("text");

        if (json.has("favicon")) {
            String base64Icon = json.getString("favicon");
            byte[] iconBytes = Base64.getDecoder().decode(base64Icon);
            try {
                icon = ImageIO.read(new ByteArrayInputStream(iconBytes));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject toJson() {
        Map<String, Object> map = new HashMap<>(Map.of(
                "version", Map.of(
                        "name", versionName,
                        "protocol", protocol
                ),
                "players", Map.of(
                        "max", maxPlayers,
                        "online", onlinePlayers,
                        "sample", playerSample.entrySet() // TODO: Does this work?
                ),
                "description", Map.of(
                        "text", description
                )
        ));

        if (icon != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try {
                ImageIO.write((RenderedImage) icon, "png", stream);
                byte[] iconBytes = stream.toByteArray();
                String base64Icon = Base64.getEncoder().encodeToString(iconBytes);
                map.put("favicon", base64Icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new JSONObject(map);
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    public Map<String, UUID> getPlayerSample() {
        return playerSample;
    }

    public void setPlayerSample(Map<String, UUID> playerSample) {
        this.playerSample = playerSample;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
}
