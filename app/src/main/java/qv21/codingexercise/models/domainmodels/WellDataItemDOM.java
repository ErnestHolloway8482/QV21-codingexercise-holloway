package qv21.codingexercise.models.domainmodels;

import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;

import qv21.codingexercise.models.databasemodels.WellDataDM;

public class WellDataItemDOM {
    //UUID
    public ObservableField<String> uuid = new ObservableField<>();

    //Owner Information
    public ObservableField<String> ownerName = new ObservableField<>();

    //Well Information
    public ObservableField<String> apiNumber = new ObservableField<>();
    public ObservableDouble longitude = new ObservableDouble();
    public ObservableDouble latitude = new ObservableDouble();
    public ObservableInt propertyNumber = new ObservableInt();
    public ObservableField<String> county = new ObservableField<>();
    public ObservableField<String> wellName = new ObservableField<>();
    public ObservableInt sec = new ObservableInt();
    public ObservableField<String> twp = new ObservableField<>();
    public ObservableField<String> rng = new ObservableField<>();

    //Tank Information
    public ObservableField<String> tankName = new ObservableField<>();
    public ObservableInt mid = new ObservableInt();
    public ObservableInt tankNumber = new ObservableInt();
    public ObservableFloat tankSize = new ObservableFloat();
    public ObservableFloat bblsPerInch = new ObservableFloat();

    public static WellDataItemDOM create(final WellDataDM wellDataDM) {
        return new WellDataItemDOM(wellDataDM);
    }

    private WellDataItemDOM(final WellDataDM wellDataDM) {
        //UUID
        uuid.set(wellDataDM.getUuid());

        //Owner Information
        ownerName.set(wellDataDM.getOwnerName());

        //Well Information
        apiNumber.set(wellDataDM.getApiNumber());
        longitude.set(wellDataDM.getLongitude());
        latitude.set(wellDataDM.getLatitude());
        propertyNumber.set(wellDataDM.getPropertyNumber());
        county.set(wellDataDM.getCounty());
        wellName.set(wellDataDM.getWellName());
        sec.set(wellDataDM.getSec());
        twp.set(wellDataDM.getTwp());
        rng.set(wellDataDM.getRng());

        //Tank Information
        tankName.set(wellDataDM.getTankName());
        mid.set(wellDataDM.getMid());
        tankNumber.set(wellDataDM.getTankNumber());
        tankSize.set(wellDataDM.getTankSize());
        bblsPerInch.set(wellDataDM.getBblsPerInch());

    }
}
