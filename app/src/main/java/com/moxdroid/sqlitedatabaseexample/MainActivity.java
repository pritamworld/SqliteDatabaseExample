package com.moxdroid.sqlitedatabaseexample;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.moxdroid.sqlitedatabaseexample.models.Student;

public class MainActivity extends AppCompatActivity {

    Student dbStudent;
    EditText edtSid, edtSnm, edtMarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSid = (EditText)findViewById(R.id.edtSno);
        edtSnm = (EditText)findViewById(R.id.edtSnm);
        edtMarks = (EditText)findViewById(R.id.edtMarks);

        getSupportActionBar().setTitle("Student");
        dbStudent = new Student(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.db_menu, menu);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            final SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            SearchView search = (SearchView) menu.findItem(R.id.mnuSearch).getActionView();

            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {

                    Student mStudent = dbStudent.getStudentById(Integer.parseInt(query));
                    if(mStudent!=null)
                    {
                        edtSid.setText(String.valueOf(mStudent.getStudentId()));
                        edtSnm.setText(mStudent.getStudentName());
                        edtMarks.setText(String.valueOf(mStudent.getTotalMarks()));
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No Record Found...", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    return true;

                }

            });

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Student tempStudent;
        switch (item.getItemId())
        {
            case R.id.mnuAdd:
                tempStudent = new Student();
                tempStudent.setStudentName(edtSnm.getText().toString());
                tempStudent.setTotalMarks(Double.parseDouble(edtMarks.getText().toString()));
                int sid = dbStudent.insertStudent(tempStudent);
                edtSid.setText(String.valueOf(sid));
                break;

            case R.id.mnuUpdate:
                tempStudent = new Student();
                tempStudent.setStudentId(Integer.parseInt(edtSid.getText().toString()));
                tempStudent.setStudentName(edtSnm.getText().toString());
                tempStudent.setTotalMarks(Double.parseDouble(edtMarks.getText().toString()));
                dbStudent.updateStudent(tempStudent);
                break;

            case R.id.mnuDelete:
                tempStudent = new Student();
                tempStudent.setStudentId(Integer.parseInt(edtSid.getText().toString()));
                dbStudent.deleteStudent(tempStudent);
                break;

            case R.id.mnuList:
                Toast.makeText(this,"Move to List Activity",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
