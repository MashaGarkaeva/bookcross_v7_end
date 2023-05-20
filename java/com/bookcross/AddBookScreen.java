package com.bookcross;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener ;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBookScreen extends AppCompatActivity{

    EditText name_book;
    EditText auhtor_book;
    Button save;
    String key_book;
    FirebaseDatabase database;
    DatabaseReference reference;
    String select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        name_book = findViewById(R.id.name_book);
        auhtor_book = findViewById(R.id.auhtor_book);
        save = findViewById(R.id.save);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);//выпадающий список

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.place, android.R.layout.simple_spinner_item);//заполнение списка
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){//обработчик нажатия
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                select = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

    }
    public void saveData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddBookScreen.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Book");

        String name = name_book.getText().toString();
        String author = auhtor_book.getText().toString();
        key_book = FirebaseDatabase.getInstance().getReference().child("Book").push().getKey();

        DataClass dataClass = new DataClass(name, author, key_book, select);
        reference.child(name).setValue(dataClass);

        Toast.makeText(AddBookScreen.this, "Книга добавлена", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddBookScreen.this, ProfilScreen.class);
        startActivity(intent);

    }

}