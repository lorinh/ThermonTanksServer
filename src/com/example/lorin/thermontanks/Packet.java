package com.example.lorin.thermontanks;

import java.io.Serializable;

/**
 * Created by lorinhersh on 10/16/17.
 */

public class Packet implements Serializable {
    public Vector2 selfPosition;
    public Vector2 otherPositions[];

    public String ClientId;

    public Packet(Vector2 selfPosition) {
        this.selfPosition = selfPosition;
    }

    public Packet() {
        //Do nothing
    }
}
