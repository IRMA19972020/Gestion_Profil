package net.simplifiedlearning.sqlitecrudexample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ProfilActivity extends AppCompatActivity {

    List<Profil> profilList;
    SQLiteDatabase mDatabase;
    ListView listViewPrf;
    ProfilAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        listViewPrf = findViewById(R.id.listViewEmployees);
        profilList = new ArrayList<>();

        //opening the database
        mDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);

        //this method will display the employees in the list
        showEmployeesFromDatabase();
    }

    private void showEmployeesFromDatabase() {
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorPrf = mDatabase.rawQuery("SELECT * FROM profils", null);

        //if the cursor has some data
        if (cursorPrf.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                profilList.add(new Profil(
                        cursorPrf.getInt(0),
                        cursorPrf.getString(1),
                        cursorPrf.getString(2),
                        cursorPrf.getString(3),
                        cursorPrf.getString(4)

                ));
            } while (cursorPrf.moveToNext());
        }
        //closing the cursor
        cursorPrf.close();

        //creating the adapter object
        adapter = new ProfilAdapter(this, R.layout.list_layout_employee, profilList, mDatabase);

        //adding the adapter to listview
        listViewPrf.setAdapter(adapter);
    }

}
