package by.bstu.fit.romanovich.searchfortravelcompanions.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import by.bstu.fit.romanovich.searchfortravelcompanions.Requests.Requests;
import by.bstu.fit.ramanovich.searchfortravelcompanions.R;
import by.bstu.fit.romanovich.searchfortravelcompanions.units.Request;
import by.bstu.fit.romanovich.searchfortravelcompanions.units.Driver;

public class RequestActivity extends AppCompatActivity {
    private TextView dateTextView;
    private TextView timeTextView;
    private TextView wherefromTextView;
    private TextView whereTextView;
    private TextView carTextView;
    private TextView placeTextView;
    private TextView priceTextView;
    private TextView whoIsTextView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        dateTextView=findViewById(R.id.dateTextView);
        timeTextView=findViewById(R.id.timeTextView);
        wherefromTextView=findViewById(R.id.wherefromTextView);
        whereTextView=findViewById(R.id.whereTextView);
        carTextView=findViewById(R.id.carTextView);
        placeTextView=findViewById(R.id.placeTextView);
        priceTextView=findViewById(R.id.priceTextView);
        whoIsTextView=findViewById(R.id.whoIsTextView);
        imageView = findViewById(R.id.photoView);


        fillFields();
    }

    private void fillFields() {
        Request request = Requests.getList().
                get(getIntent().getIntExtra("id", 0));

        dateTextView.setText(request.getDate());
        timeTextView.setText(request.geTtime());
        whoIsTextView.setText((request instanceof Driver ? "Водитель" : "Попутчик"));
        wherefromTextView.setText(request.getWherefrom());
        whereTextView.setText(request.getWhere());
        carTextView.setText(request.getCar());
        placeTextView.setText(request.getPlace().toString());
        priceTextView.setText(request.getPrice().toString());


        if (!request.getPathPhoto().isEmpty()) {
            File file = new File(request.getPathPhoto());

            if (file.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            }
        }
    }


}
