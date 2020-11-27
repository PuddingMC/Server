package me.puds.server.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@NoArgsConstructor
@Data
public class PingResponse {
    private String versionName = "";
    private int protocol = 0;
    private int maxPlayers = 0;
    private int onlinePlayers = 0;
    private Map<String, UUID> playerSample = new LinkedHashMap<>();
    private String description = ""; // TODO: Make this a proper chat component type
    private Image icon = null;

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
}
