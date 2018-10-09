package qv21.codingexercise.bindings;

import android.databinding.InverseMethod;

public class Converter {
    @InverseMethod("convertIntegerToString")
    public static int convertStringToInteger(final String value){
        try{
            return Integer.parseInt(value);
        } catch(NumberFormatException e){
            return getDefaultValue();
        }
    }

    public static String convertIntegerToString(final int value){
        return Integer.toString(value);
    }

    @InverseMethod("convertFloatToString")
    public static float convertStringToFloat(final String value){
        try{
            return Float.parseFloat(value);
        } catch(NumberFormatException e){
            return getDefaultValue();
        }
    }

    public static String convertFloatToString(final float value){
        return Float.toString(value);
    }

    @InverseMethod("convertDoubleToString")
    public static double convertStringToDouble(final String value){
        try{
            return Double.parseDouble(value);
        } catch(NumberFormatException e){
            return getDefaultValue();
        }
    }

    public static String convertDoubleToString(final double value){
        return Double.toString(value);
    }

    private static int getDefaultValue(){
        return 0;
    }
}
