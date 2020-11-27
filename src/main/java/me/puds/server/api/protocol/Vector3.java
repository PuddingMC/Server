package me.puds.server.api.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vector3 {
    private int x = 0;
    private int y = 0;
    private int z = 0;
}
