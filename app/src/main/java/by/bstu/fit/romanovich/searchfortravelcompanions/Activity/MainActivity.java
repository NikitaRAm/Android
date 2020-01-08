package by.bstu.fit.romanovich.searchfortravelcompanions.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;

import by.bstu.fit.romanovich.searchfortravelcompanions.Requests.Requests;
import by.bstu.fit.ramanovich.searchfortravelcompanions.R;
import by.bstu.fit.romanovich.searchfortravelcompanions.addition.HelpForSave;
import by.bstu.fit.romanovich.searchfortravelcompanions.addition.MyAdapter;
import by.bstu.fit.romanovich.searchfortravelcompanions.addition.WorkWithFile;
import by.bstu.fit.romanovich.searchfortravelcompanions.units.Driver;
import by.bstu.fit.romanovich.searchfortravelcompanions.units.Сompanion;
import by.bstu.fit.romanovich.searchfortravelcompanions.units.Request;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private MyAdapter mAdapter;

    private FloatingActionButton fabAdd;
    private LinearLayout searchLinear;
    private EditText searchEditText;
    private EditText searchEditText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        fabAdd = findViewById(R.id.fabAdd);
        searchLinear = findViewById(R.id.searchLinear);
        searchEditText = findViewById(R.id.searchEditText);
        searchEditText2 = findViewById(R.id.searchEditText2);

        //меню контекстного действия
        listView.setOnItemClickListener(defaultItemClickOfList);
        listView.setOnItemLongClickListener((parent, view, postion, id) ->{
            startActionMode(mActionModeCallback);
            return false;
        });

        registerForContextMenu(listView);
        loadLists();
    }

    // default всплывающее меню
    AdapterView.OnItemClickListener defaultItemClickOfList;
    {
        defaultItemClickOfList = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.inflate(R.menu.menulayuot);
                popupMenu.setOnMenuItemClickListener((menu) -> {
                    switch (menu.getItemId()) {
                        case R.id.action_viewPerson:
                            Intent intent = new Intent(MainActivity.this, RequestActivity.class);
                            intent.putExtra("id", Requests.getList().indexOf(((ListView) parent).getItemAtPosition(position)));
                            startActivity(intent);
                            return true;
                        case R.id.action_remove:
                            Request request = (Request) ((ListView) parent).getItemAtPosition(position);
                            if (request.getClass() == Сompanion.class)
                                Requests.remove((Сompanion) request);
                            else
                                Requests.remove((Driver) request);

                            HelpForSave.saveAll(MainActivity.this);

                            mAdapter = new MyAdapter(MainActivity.this, R.layout.rowlayout, Requests.getList());
                            listView.setAdapter(mAdapter);
                            return true;
                        case R.id.action_editPerson:
                            Intent intent1 = new Intent(MainActivity.this, EditRequestActivity.class);
                            intent1.putExtra("id", Requests.getList().indexOf(((ListView) parent).getItemAtPosition(position)));
                            startActivity(intent1);
                            return true;
                        default:
                            return false;
                    }
                });
                popupMenu.show();
            }
        };
    }
    // множественный выбор в режиме контекстных действий
    AdapterView.OnItemClickListener myOnItemClickListener;
    {
        myOnItemClickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                mAdapter.toggleChecked(position);
            }
        };
    }

    public void search_onClick(View view){
        String str = searchEditText.getText().toString();
        String str2 = searchEditText2.getText().toString();
        if(str.length() == 0 && str2.length() == 0) {
            // выводим весь список
            mAdapter = new MyAdapter(this,
                    R.layout.rowlayout, Requests.getList());
            listView.setAdapter(mAdapter);
        }
        else{
            // выводим найденное
            ArrayList<Request> list = new ArrayList<>();

            for (Request contact : Requests.getList()){
                if(    contact.getWherefrom().contains(str) &&
                        contact.getWhere().contains(str2))
                    list.add(contact);
            }


            mAdapter = new MyAdapter(this,
                    R.layout.rowlayout, list);
            listView.setAdapter(mAdapter);
        }

    }
    //задать бар по умолчанию
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.defaultmenu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_add:
                add_onClick(null);
                return true;
            case R.id.action_search:
                if(searchLinear.getVisibility() == View.GONE)
                {
                    searchLinear.setVisibility(View.VISIBLE);
                }
                else{
                    searchEditText.setText("");
                    searchEditText2.setText("");
                    searchLinear.setVisibility(View.GONE);
                }
                return true;
            default:
                return false;
        }
    }

    // для режима контекстных действий
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.context_action, menu);

            // обновляем вид списка
            mAdapter = new MyAdapter(MainActivity.this, R.layout.rowlayout_multiple, Requests.getList());
            listView.setAdapter(mAdapter);

            // убрать всплывающее меню, включить выбор
            listView.setOnItemClickListener(myOnItemClickListener);

            fabAdd.setVisibility(View.GONE);
            return true;
        }

        //динамическое управление меню
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.contextAction_remove:
                    removePersons(mode);
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // обновляем вид списка
            mAdapter = new MyAdapter(MainActivity.this, R.layout.rowlayout, Requests.getList());
            listView.setAdapter(mAdapter);

            // вернуть всплывающее меню, убрать выбор
            listView.setOnItemClickListener(defaultItemClickOfList);

            fabAdd.setVisibility(View.VISIBLE);
        }
    };

    private void loadLists(){
        File file = new File(super.getFilesDir(), "drivers.json");
        if(file.exists())
            Requests.setListofDrivers((ArrayList<Driver>) WorkWithFile.parseGson(
                    file, new TypeToken<ArrayList<Driver>>(){}.getType()));
        else
            Requests.setListofDrivers(new ArrayList<>());

        file = new File(super.getFilesDir(), "сompanions.json");
        if(file.exists())
            Requests.setListofCompanions((ArrayList<Сompanion>) WorkWithFile.parseGson(
                    file, new TypeToken<ArrayList<Сompanion>>(){}.getType()));
        else
            Requests.setListofCompanions(new ArrayList<>());
    }
    protected void onPostResume() {
        super.onPostResume();

        HelpForSave.saveAll(this);

        mAdapter = new MyAdapter(this, R.layout.rowlayout, Requests.getList());
        listView.setAdapter(mAdapter);
    }

    public void add_onClick(View view){
        startActivity(new Intent(MainActivity.this, AddRequestActivity.class));
    }
    public void removePersons(ActionMode menu){
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("Уверен?");  // заголовок
        ad.setPositiveButton("Да", (dialog, arg1) -> {
            ArrayList<Request> list = mAdapter.getCheckedItems();

            for(Request request : list){
                if(request.getClass() == Сompanion.class)
                    Requests.remove((Сompanion) request);
                else
                    Requests.remove((Driver) request);
            }

            HelpForSave.saveAll(this);

            mAdapter = new MyAdapter(this, R.layout.rowlayout, Requests.getList());
            listView.setAdapter(mAdapter);

            menu.finish();
            fabAdd.setVisibility(View.VISIBLE);
        });
        ad.setNegativeButton("Отмена", (dialog, arg1) -> {

        });
        ad.show();
    }
}
