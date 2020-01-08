package by.bstu.fit.romanovich.searchfortravelcompanions.addition;

import android.content.Context;

import java.io.File;

import by.bstu.fit.romanovich.searchfortravelcompanions.Requests.Requests;

public class HelpForSave { public static void saveAll(Context context){
    WorkWithFile.serialize(WorkWithFile.toJson(Requests.drivers),
            new File(context.getFilesDir(), "drivers.json"));
    WorkWithFile.serialize(WorkWithFile.toJson(Requests.сompanions),
            new File(context.getFilesDir(), "сompanions.json"));
}
}
