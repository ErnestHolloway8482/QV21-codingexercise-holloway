package qv21.codingexercise.models.database;

import java.util.List;

public class Well {
    private String ownerName;

    private String api;
    private double longitude;
    private double latitude;
    private int property;
    private String county;
    private String wellName;
    private int sec;
    private String twp;
    private String rng;

    private List<Tank> tanks;
}
