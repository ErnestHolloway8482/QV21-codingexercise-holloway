package qv21.codingexercise.managers;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

/**
 * A {@link Singleton} file manager that will read the contents of the .csv file containing the raw well data content and parse it into {@link List<String>}
 */
@Singleton
public class WellDataFileManager {
    private static final String CSV_ROW_HEADER = "Owner,API #,Longitude,Latitude,Property #,Lease / Well Name,Tank MID,Tank Name,Tank Nbr,Tank Size,BBLS Per Inch,SEC,TWP,RNG,COUNTY";

    public List<String> readWellData(final String fileNameAndPath) {
        try {
            return readStringLinesFromFile(fileNameAndPath);
        } catch (IOException e) {
            return null;
        }
    }

    public List<String> readWellData(final InputStream inputStream) {
        try {
            return readStringLinesFromFile(inputStream);
        } catch (IOException e) {
            return null;
        }
    }

    @NonNull
    private List<String> readStringLinesFromFile(final String fileNameAndPath) throws IOException {
        File file = new File(fileNameAndPath);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        return parseCSVRowsToStringList(bufferedReader);
    }

    @NonNull
    private List<String> readStringLinesFromFile(final InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        return parseCSVRowsToStringList(bufferedReader);
    }

    private List<String> parseCSVRowsToStringList(BufferedReader bufferedReader) throws IOException {
        List<String> wellData = new ArrayList<>();
        String csvRowContent;

        do {
            csvRowContent = bufferedReader.readLine();

            if (csvRowContent != null && !csvRowContent.contains(CSV_ROW_HEADER)) {
                wellData.add(csvRowContent);
            }
        } while (csvRowContent != null);

        bufferedReader.close();

        return wellData;
    }
}
