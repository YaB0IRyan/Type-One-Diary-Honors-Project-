package com.example.typeonediary;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReaderWriter {
    public List<Entry> readEntries(File file) {
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(file.toPath());

            // convert JSON array to list of users
            List<Entry> entries = new Gson().fromJson(reader, new TypeToken<List<Entry>>() {}.getType());

            if (!(entries == null)){
                System.out.println("Number of entries: " + entries.size());

                for (int i = 0; i < entries.size(); i++) {
                    System.out.println(entries.get(i).getEntry());
                }
            } else {
                System.out.println("File is Empty");
            }


            // close reader
            reader.close();

            return entries;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void writeEntries(File file, List<Entry> entries) {

        try {
       // create writer
            Writer writer = new FileWriter(file.getPath());

            // convert users list to JSON file
            new Gson().toJson(entries, writer);

            // close writer
            writer.close();

            System.out.println("The following list has been exported:");
            for (int i = 0; i < entries.size(); i++) {
                System.out.println(entries.get(i).getEntry());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
