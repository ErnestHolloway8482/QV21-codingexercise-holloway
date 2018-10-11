package qv21.codingexercise.mapper;

import com.opencsv.CSVParser;

import java.io.IOException;

import qv21.codingexercise.models.databasemodels.WellDataDM;

/**
 * This mapper class will take the string representation of a row of information from a well_data.csv file and covert it into the corresponding
 * domain model that will eventually be entered into the embedded database.
 */
public class WellDataMapper {
    private static final int NUMER_OF_EXPECTED_DATA_COLUMNS_PER_ROW = 15;

    public WellDataDM mapWellData(final String csvRowContent) {
        String[] rowValues = splitCsvDataByCommaSeparator(csvRowContent);

        if (rowValues == null) {
            return null;
        } else {
            //"Owner,API #,Longitude,Latitude,Property #,Lease / Well Name,Tank MID,Tank Name,Tank Nbr,Tank Size,BBLS Per Inch,SEC,TWP,RNG,COUNTY";
            WellDataDM wellData = new WellDataDM();
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

    private String[] splitCsvDataByCommaSeparator(final String csvRowContent) {
        if (isStringEmpty(csvRowContent)) {
            return null;
        }

        /**
         *  The CSVParser library is used to best handle the case of items that contain commas within quotes, e.g. "Continental Resources, Inc.".
         *  This library will make sure to correctly parse the value after Inc. instead of at the od of Resources and avoid having an incorrect
         *  String array entry generated.
         */
        CSVParser csvParser = new CSVParser();

        try {
            String[] rowValues = csvParser.parseLine(csvRowContent);

            if (rowValues == null || rowValues.length != NUMER_OF_EXPECTED_DATA_COLUMNS_PER_ROW) {
                return null;
            } else {
                return rowValues;
            }
        } catch (IOException e) {
            return null;
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
