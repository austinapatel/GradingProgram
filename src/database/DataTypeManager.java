package database;

import java.util.ArrayList;

/**
 * Manages conversions between data types
 */
public class DataTypeManager {

    public DataTypeManager() {
    }

    public static ArrayList<String> toStringArrayList(ArrayList<Object> input) {
        ArrayList<String> output = new ArrayList<>();

        for (Object o : input)
            output.add(o.toString());

        return output;
    }

    public static ArrayList<Integer> toIntegerArrayList(ArrayList<Object> input) {
        ArrayList<Integer> output = new ArrayList<>();

        for (Object o : input)
            output.add(Integer.parseInt(o.toString()));

        return output;
    }

    public static ArrayList<Double> toDoubleArrayList(ArrayList<Object> input) {
        ArrayList<Double> output = new ArrayList<>();

        for (Object o : input)
            output.add(Double.parseDouble(o.toString()));

        return output;
    }

}
