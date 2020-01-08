package by.bstu.fit.romanovich.searchfortravelcompanions.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import java.util.Calendar;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import by.bstu.fit.romanovich.searchfortravelcompanions.Requests.Requests;
import by.bstu.fit.ramanovich.searchfortravelcompanions.R;
import by.bstu.fit.romanovich.searchfortravelcompanions.units.Сompanion;
import by.bstu.fit.romanovich.searchfortravelcompanions.units.Driver;


public class AddRequestActivity extends AppCompatActivity {


    private RadioButton studentRadioButton;
    private EditText pathToFileEditText;
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
        setContentView(R.layout.activity_add_requests);

        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        wherefromEditText = findViewById(R.id.wherefromEditText);
        whereEditText = findViewById(R.id.whereEditText);
        carEditText = findViewById(R.id.carEditText);
        placeEditText = findViewById(R.id.placeEditText);
        priceEditText = findViewById(R.id.priceEditText);
        pathToFileEditText =findViewById(R.id.pathToFileEditText);
        studentRadioButton = findViewById(R.id.studentRadioButton);
        setInitialDate();
        setInitialTime();
    }

    public void create_onClick(View view){
        if(studentRadioButton.isChecked())
            Requests.add(new Driver( dateEditText.getText().toString(),
                    timeEditText.getText().toString(),
                    wherefromEditText.getText().toString(),
                    whereEditText.getText().toString(),
                    carEditText.getText().toString(),
                    Integer.valueOf(placeEditText.getText().toString()),
                    Integer.valueOf(priceEditText.getText().toString()),
                    pathToFileEditText.getText().toString(),
                    0));
        else
            Requests.add(new Сompanion( dateEditText.getText().toString(),
                    timeEditText.getText().toString(),
                    wherefromEditText.getText().toString(),
                    whereEditText.getText().toString(),
                    carEditText.getText().toString(),
                    Integer.valueOf(placeEditText.getText().toString()),
                    Integer.valueOf(priceEditText.getText().toString()),
                    pathToFileEditText.getText().toString(),
                    0));

        finish();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_.png";
            File file = new File(super.getFilesDir(),  imageFileName);

            try (FileOutputStream out = new FileOutputStream(file)) {
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                pathToFileEditText.setText(file.getAbsolutePath());
            } catch (IOException e) {
                Log.d("CreateNew", e.getMessage());
            }
        }
    }


    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(AddRequestActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(AddRequestActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDate() {

        dateEditText.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }
    private void setInitialTime() {

        timeEditText.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };




}
