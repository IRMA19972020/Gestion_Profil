package net.simplifiedlearning.sqlitecrudexample;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by AMRI on 10/30/2019.
 */

public class ProfilAdapter extends ArrayAdapter<Profil> {

    Context mCtx;
    int listLayoutRes;
    List<Profil> prfList;
    SQLiteDatabase mDatabase;

    public ProfilAdapter(Context mCtx, int listLayoutRes, List<Profil> employeeList, SQLiteDatabase mDatabase) {
        super(mCtx, listLayoutRes, employeeList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.prfList = employeeList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        final Profil profil = prfList.get(position);


        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewDept = view.findViewById(R.id.textViewDepartment);
        TextView textViewSalary = view.findViewById(R.id.textViewSalary);
        TextView textViewJoiningDate = view.findViewById(R.id.textViewJoiningDate);


        textViewName.setText(profil.getName());
        textViewDept.setText(profil.getDept());
        textViewSalary.setText(profil.getSalary());
        textViewJoiningDate.setText(profil.getJoiningDate());


        //Button buttonDelete = view.findViewById(R.id.buttonDeleteEmployee);
        Button buttonEdit = view.findViewById(R.id.buttonEditEmployee);

        //adding a clicklistener to button
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmployee(profil);
            }
        });

        //the delete operation
        /* buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM employees WHERE id = ?";
                        mDatabase.execSQL(sql, new Integer[]{profil.getId()});
                        reloadEmployeesFromDatabase();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
*/
        return view;
    }

    private void updateEmployee(final Profil employee) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_employee, null);
        builder.setView(view);


        final EditText editTextName = view.findViewById(R.id.editTextName);
        final EditText editTextSalary = view.findViewById(R.id.editTextSalary);
        //final EditText editTextCin = view.findViewById(R.id.editTextCin);
        final Spinner spinnerDepartment = view.findViewById(R.id.spinnerDepartment);

        editTextName.setText(employee.getName());
        editTextSalary.setText(employee.getSalary());
        //editTextCin.setText(employee.getCin());

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.buttonUpdateEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String salary = editTextSalary.getText().toString().trim();
                //String cin = editTextCin.getText().toString().trim();
                String dept = spinnerDepartment.getSelectedItem().toString();

                if (name.isEmpty()) {
                    editTextName.setError("Name can't be blank");
                    editTextName.requestFocus();
                    return;
                }

                if (salary.isEmpty()) {
                    editTextSalary.setError("Your Email can't be blank");
                    editTextSalary.requestFocus();
                    return;
                }

                String sql = "UPDATE profils \n" +
                        "SET name = ?, \n" +
                        "department = ?, \n" +
                        "salary = ? \n" +
                        "WHERE id = ?;\n";

                mDatabase.execSQL(sql, new String[]{name, dept, salary, String.valueOf(employee.getId())});
                Toast.makeText(mCtx, "Profil Updated", Toast.LENGTH_SHORT).show();
                reloadEmployeesFromDatabase();

                dialog.dismiss();
            }
        });
    }

    private void reloadEmployeesFromDatabase() {
        Cursor cursorPrf = mDatabase.rawQuery("SELECT * FROM profils", null);
        if (cursorPrf.moveToFirst()) {
            prfList.clear();
            do {
                prfList.add(new Profil(
                        cursorPrf.getInt(0),
                        cursorPrf.getString(1),
                        cursorPrf.getString(2),
                        cursorPrf.getString(3),
                        cursorPrf.getString(4)

                ));
            } while (cursorPrf.moveToNext());
        }
        cursorPrf.close();
        notifyDataSetChanged();
    }

}
