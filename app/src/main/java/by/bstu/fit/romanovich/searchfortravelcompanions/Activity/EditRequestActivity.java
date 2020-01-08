package by.bstu.fit.romanovich.searchfortravelcompanions.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

import by.bstu.fit.romanovich.searchfortravelcompanions.Requests.Requests;
import by.bstu.fit.ramanovich.searchfortravelcompanions.R;
import by.bstu.fit.romanovich.searchfortravelcompanions.units.Request;

public class EditRequestActivity extends AppCompatActivity {

    private EditText dateEditText;
    private EditText timeEditText;
    private EditText wherefromEditText;
    private EditText whereEditText;
    private EditText carEditText;
    private EditText placeEditText;
    private EditText priceEditText;
    Calendar dateAndTime= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_requests);

        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        wherefromEditText = findViewById(R.id.wherefromEditText);
        whereEditText = findViewById(R.id.whereEditText);
        carEditText = findViewById(R.id.carEditText);
        placeEditText = findViewById(R.id.placeEditText);
        priceEditText = findViewById(R.id.priceEditText);

        fillFields();
    }

    private void fillFields() {
        Request request = Requests.getList().
                get(getIntent().getIntExtra("id", 0));
        dateEditText.setText(request.getDate());
        timeEditText.setText(request.geTtime());
        wherefromEditText.setText(request.getWherefrom());
        whereEditText.setText(request.getWhere());
        carEditText.setText(request.getCar());
        placeEditText.setText(request.getPlace().toString());
        priceEditText.setText(request.getPrice().toString());


    }

    public void update_onClick(View view){
        Request request = Requests.getList().
                get(getIntent().getIntExtra("id", 0));

        request.setDate(dateEditText.getText().toString());
        request.setTime(timeEditText.getText().toString());
        request.setWherefrom(wherefromEditText.getText().toString());
        request.setWhere(whereEditText.getText().toString());
        request.setCar(carEditText.getText().toString());
        request.setPlace(Integer.valueOf(placeEditText.getText().toString()));
        request.setPrice(Integer.valueOf(priceEditText.getText().toString()));

        finish();
    }
}

