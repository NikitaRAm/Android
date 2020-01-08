package by.bstu.fit.romanovich.searchfortravelcompanions.addition;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import by.bstu.fit.ramanovich.searchfortravelcompanions.R;
import by.bstu.fit.romanovich.searchfortravelcompanions.units.Driver;
import by.bstu.fit.romanovich.searchfortravelcompanions.units.Request;


public class MyAdapter extends ArrayAdapter<Request> {
    private final ArrayList<Request> list;
    private HashMap<Integer, Boolean> checkedMap = new HashMap<>();
    private final Context context;
    private final int resource;

    public MyAdapter(@NonNull Context context, int resource, ArrayList<Request> list) {
        super(context, -1, list);
        this.context = context;
        this.list = list;
        this.resource = resource;

        for(int i = 0; i < list.size(); i++){
            checkedMap.put(i, false);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, parent, false);

        TextView nameField = (TextView) rowView.findViewById(R.id.nameField);
        ImageView photoView = (ImageView) rowView.findViewById(R.id.photoPersonImageView);
        TextView whoIsField = (TextView) rowView.findViewById(R.id.whoIsField);
        TextView whereField = (TextView) rowView.findViewById(R.id.whereField);

        Request request = list.get(position);
        nameField.setText(request.toString());
        whereField.setText(request.toWhere());
        whoIsField.setText((request instanceof Driver ? "Водитель" : "Попутчик"));
        if (!request.getPathPhoto().isEmpty()) {
            File file = new File(request.getPathPhoto());

            if (file.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                photoView.setImageBitmap(myBitmap);
            }
        }

        if(resource == R.layout.rowlayout_multiple){
            CheckedTextView checkedTextView = rowView.findViewById(R.id.checkBox);
            Boolean checked = checkedMap.get(position);
            if (checked != null) {
                checkedTextView.setChecked(checked);
            }
        }
        return rowView;
    }

    public void toggleChecked(int position) {
        if (checkedMap.get(position)) {
            checkedMap.put(position, false);
        } else {
            checkedMap.put(position, true);
        }

        notifyDataSetChanged();
    }
    public List<Integer> getCheckedItemPositions() {
        List<Integer> checkedItemPositions = new ArrayList<>();

        for (int i = 0; i < checkedMap.size(); i++) {
            if (checkedMap.get(i)) {
                (checkedItemPositions).add(i);
            }
        }

        return checkedItemPositions;
    }
    public ArrayList<Request> getCheckedItems() {
        ArrayList<Request> checkedItems = new ArrayList<>();

        for (int i = 0; i < checkedMap.size(); i++) {
            if (checkedMap.get(i)) {
                (checkedItems).add(list.get(i));
            }
        }

        return checkedItems;
    }

}
