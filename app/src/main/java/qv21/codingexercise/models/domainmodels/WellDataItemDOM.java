package qv21.codingexercise.models.domainmodels;

import qv21.codingexercise.models.databasemodels.WellDataDM;

/**
 * Domain model definition for the well data that is displayed on the screens within the app.
 */
public class WellDataItemDOM {
    //UUID
    public String uuid;

    //Owner Information
    public String ownerName;

    //Well Information
    public String apiNumber;
    public double longitude;
    public double latitude;
    public int propertyNumber;
    public String county;
    public String wellName;
    public int sec;
    public String twp;
    public String rng;

    //Tank Information
    public String tankName;
    public int mid;
    public int tankNumber;
    public float tankSize;
    public float bblsPerInch;

    public static WellDataItemDOM create(final WellDataDM wellDataDM) {
        return getInstance(wellDataDM);
    }

    public static void updateContentsOfWellData(final WellDataDM wellDataDM, final WellDataItemDOM wellDataItemDOM) {
        if (wellDataDM == null || wellDataItemDOM == null) {
            return;
        }

        //Owner Information
        wellDataDM.setOwnerName(wellDataItemDOM.ownerName);

        //Well Information
        wellDataDM.setApiNumber(wellDataItemDOM.apiNumber);
        wellDataDM.setLongitude(wellDataItemDOM.longitude);
        wellDataDM.setLongitude(wellDataItemDOM.latitude);
        wellDataDM.setPropertyNumber(wellDataItemDOM.propertyNumber);
        wellDataDM.setCounty(wellDataItemDOM.county);
        wellDataDM.setWellName(wellDataItemDOM.wellName);
        wellDataDM.setSec(wellDataItemDOM.sec);
        wellDataDM.setTwp(wellDataItemDOM.twp);
        wellDataDM.setRng(wellDataItemDOM.rng);

        //Tank Information
        wellDataDM.setTankName(wellDataItemDOM.tankName);
        wellDataDM.setMid(wellDataItemDOM.mid);
        wellDataDM.setTankNumber(wellDataItemDOM.tankNumber);
        wellDataDM.setTankSize(wellDataItemDOM.tankSize);
        wellDataDM.setBblsPerInch(wellDataItemDOM.bblsPerInch);
    }

    private static WellDataItemDOM getInstance(final WellDataDM wellDataDM) {
        if (wellDataDM == null) {
            return null;
        }

        WellDataItemDOM wellDataItemDOM = new WellDataItemDOM();

        //UUID
        wellDataItemDOM.uuid = wellDataDM.getUuid();

        //Owner Information
        wellDataItemDOM.ownerName = wellDataDM.getOwnerName();

        //Well Information
        wellDataItemDOM.apiNumber = wellDataDM.getApiNumber();
        wellDataItemDOM.longitude = wellDataDM.getLongitude();
        wellDataItemDOM.latitude = wellDataDM.getLatitude();
        wellDataItemDOM.propertyNumber = wellDataDM.getPropertyNumber();
        wellDataItemDOM.county = wellDataDM.getCounty();
        wellDataItemDOM.wellName = wellDataDM.getWellName();
        wellDataItemDOM.sec = wellDataDM.getSec();
        wellDataItemDOM.twp = wellDataDM.getTwp();
        wellDataItemDOM.rng = wellDataDM.getRng();

        //Tank Information
        wellDataItemDOM.tankName = wellDataDM.getTankName();
        wellDataItemDOM.mid = wellDataDM.getMid();
        wellDataItemDOM.tankNumber = wellDataDM.getTankNumber();
        wellDataItemDOM.tankSize = wellDataDM.getTankSize();
        wellDataItemDOM.bblsPerInch = wellDataDM.getBblsPerInch();

        return wellDataItemDOM;
    }
}
