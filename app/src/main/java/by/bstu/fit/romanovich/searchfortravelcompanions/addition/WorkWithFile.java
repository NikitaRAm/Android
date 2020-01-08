package by.bstu.fit.romanovich.searchfortravelcompanions.addition;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class WorkWithFile {
    public static String readFile(File file) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
// readLine - он считывает строку в файле , если получилось ,то он передает ее в line , если нет , то null
            //
            while ((line = reader.readLine()) != null) {
                //добавляет line в конец строки
                builder.append(line);
                builder.append('\n');
            }
            return String.valueOf(builder);
        } catch (FileNotFoundException e) {
            Log.d("Ошибка ", "упс");
        } catch (IOException e) {
            Log.d("Ошибка ", "упс");
        }
        return "";
    }

    public static Object parseGson(File file, Type listType){
//        Type listType = new TypeToken<ArrayList<>>(){}.getType();
        return new Gson().fromJson(readFile(file), listType);
    }
    public static String toJson(Object obj){
        return new Gson().toJson(obj);
    }

    public static void serialize(String json, File file){
        try {
            FileWriter writer = new FileWriter(file, false);
            writer.append(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Log.d("WorkWithFile", "Ошибка записи в файл");
        }
    }
}

