package qv21.codingexercise.mapper;

import qv21.codingexercise.models.database.Owner;
import qv21.codingexercise.models.database.Tank;
import qv21.codingexercise.models.database.Well;

public class WellDataMapper {

    public Owner map(final String csvRowContent) {
        return null;
    }

    private Well mapWell(final String csvRowContent) {
        return null;
    }

    private Tank mapTank(final String csvRowContent) {
        return null;
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
}
