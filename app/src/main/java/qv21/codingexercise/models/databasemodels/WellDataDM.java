package qv21.codingexercise.models.databasemodels;

import java.util.UUID;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

@Entity
public class WellDataDM {
    @Id
    private long id;

    @Index
    //UUID
    private final String uuid;

    //Owner Information
    private String ownerName;

    //Well Information
    private String apiNumber;
    private double longitude;
    private double latitude;
    private int propertyNumber;
    private String county;
    private String wellName;
    private int sec;
    private String twp;
    private String rng;

    //Tank Information
    private String tankName;
    private int mid;
    private int tankNumber;
    private float tankSize;
    private float bblsPerInch;

    public WellDataDM() {
        uuid = UUID.randomUUID().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }

    public String getApiNumber() {
        return apiNumber;
    }

    public void setApiNumber(final String apiNumber) {
        this.apiNumber = apiNumber;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public int getPropertyNumber() {
        return propertyNumber;
    }

    public void setPropertyNumber(final int propertyNumber) {
        this.propertyNumber = propertyNumber;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(final String county) {
        this.county = county;
    }

    public String getWellName() {
        return wellName;
    }

    public void setWellName(final String wellName) {
        this.wellName = wellName;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(final int sec) {
        this.sec = sec;
    }

    public String getTwp() {
        return twp;
    }

    public void setTwp(final String twp) {
        this.twp = twp;
    }

    public String getRng() {
        return rng;
    }

    public void setRng(final String rng) {
        this.rng = rng;
    }

    public String getTankName() {
        return tankName;
    }

    public void setTankName(final String tankName) {
        this.tankName = tankName;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(final int mid) {
        this.mid = mid;
    }

    public int getTankNumber() {
        return tankNumber;
    }

    public void setTankNumber(final int tankNumber) {
        this.tankNumber = tankNumber;
    }

    public float getTankSize() {
        return tankSize;
    }

    public void setTankSize(final float tankSize) {
        this.tankSize = tankSize;
    }

    public float getBblsPerInch() {
        return bblsPerInch;
    }

    public void setBblsPerInch(final float bblsPerInch) {
        this.bblsPerInch = bblsPerInch;
    }
}
