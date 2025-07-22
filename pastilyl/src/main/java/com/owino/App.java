package com.owino;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class App {
    public static void main(String[] args) throws IOException {
        /*
         * Tasks
         * 1. Read a .sql file
         * 2. Replace all uuids on the file
         * 3. Write back contents to the file with new uuids
         *
         */
        var file = args[0];
        if (!Files.exists(Path.of(file))){
            Files.createFile(Path.of(file));
        }
        var original = readLines(file);
        var updated = replaceUuids(original);
        overwriteFile(updated,file);
    }
    public static List<String> readLines(String fileName) throws IOException {
        var path = Path.of(fileName);
        return Files.readAllLines(path);
    }
    public static List<String> replaceUuids(List<String> lines) {
        List<String> update = new ArrayList<>();
        for (String line : lines) {
            var uuidRegex = "(?i)[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";
            var newUuid = UUID.randomUUID().toString();
            var updatedLine = line.replaceAll(uuidRegex, newUuid);
            update.add(updatedLine);
        }
        return update;
    }
    public static void overwriteFile(List<String> lines, String fileName) throws IOException{
        var path = Path.of(fileName);
        Files.write(path,lines);
    }
}
