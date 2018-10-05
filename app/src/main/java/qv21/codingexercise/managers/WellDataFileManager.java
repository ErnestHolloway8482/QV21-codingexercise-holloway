package qv21.codingexercise.managers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WellDataFileManager {
    private static final String CSV_ROW_HEADER = "Owner,API #,Longitude,Latitude,Property #,Lease / Well Name,Tank MID,Tank Name,Tank Nbr,Tank Size,BBLS Per Inch,SEC,TWP,RNG,COUNTY";

    public List<String> getWellData(final String fileNameAndPath) {
        try {
            List<String> wellData = new ArrayList<>();

            Stream<String> stream = Files.lines(Paths.get(fileNameAndPath));

            wellData = stream
                    .filter(readLine -> !readLine.startsWith(CSV_ROW_HEADER))
                    .collect(Collectors.toList());

            stream.close();

            return wellData;

        } catch (IOException e) {
            return null;
        }
    }
}
