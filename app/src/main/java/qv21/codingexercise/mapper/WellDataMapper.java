package qv21.codingexercise.mapper;

import qv21.codingexercise.models.database.WellData;

public class WellDataMapper {
    public WellData mapWellData(final String csvRowContent) {
        String[] rowValues = splitCsvDataBySeparator(csvRowContent, ",");

        if (rowValues == null) {
            return null;
        } else {
            //"Owner,API #,Longitude,Latitude,Property #,Lease / Well Name,Tank MID,Tank Name,Tank Nbr,Tank Size,BBLS Per Inch,SEC,TWP,RNG,COUNTY";
            WellData wellData = new WellData();
            wellData.setOwnerName(rowValues[0]);
            wellData.setApiNumber(rowValues[1]);
            wellData.setLongitude(parseDouble(rowValues[2]));
            wellData.setLatitude(parseDouble(rowValues[3]));
            wellData.setPropertyNumber(parseInteger(rowValues[4]));
            wellData.setWellName(rowValues[5]);
            wellData.setMid(parseInteger(rowValues[6]));
            wellData.setTankName(rowValues[7]);
            wellData.setTankNumber(parseInteger(rowValues[8]));
            wellData.setTankSize(parseFloat(rowValues[9]));
            wellData.setBblsPerInch(parseFloat(rowValues[10]));
            wellData.setSec(parseInteger(rowValues[11]));
            wellData.setTwp(rowValues[12]);
            wellData.setRng(rowValues[13]);
            wellData.setCounty(rowValues[14]);

            return wellData;
        }
    }

    private float parseFloat(final String floatValue) {
        try {
            return Float.parseFloat(floatValue);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int parseInteger(final String integerValue) {
        try {
            return Integer.parseInt(integerValue);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double parseDouble(final String doubleValue) {
        try {
            return Double.parseDouble(doubleValue);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String[] splitCsvDataBySeparator(final String csvRowContent, final String separatorValue) {
        if (isStringEmpty(csvRowContent) || isStringEmpty(separatorValue)) {
            return null;
        }

        String[] rowValues = csvRowContent.split(separatorValue);

        if (rowValues == null || rowValues.length != 15) {
            return null;
        } else {
            return rowValues;
        }
    }

    private boolean isStringEmpty(final String value) {
        if (value == null || value.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
