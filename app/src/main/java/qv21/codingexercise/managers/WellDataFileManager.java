package qv21.codingexercise.managers;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

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

    @NonNull
    private List<String> readStringLinesFromFile(final String fileNameAndPath) throws IOException {
        List<String> wellData = new ArrayList<>();
        String csvRowContent;

        File file = new File(fileNameAndPath);
        FileReader fileReader = new FileReader(file);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        do {
            csvRowContent = bufferedReader.readLine();

            if (csvRowContent != null && !csvRowContent.contains(CSV_ROW_HEADER)) {
                wellData.add(csvRowContent);
            }
        } while (csvRowContent != null);

        bufferedReader.close();
        fileReader.close();

        return wellData;
    }
}
