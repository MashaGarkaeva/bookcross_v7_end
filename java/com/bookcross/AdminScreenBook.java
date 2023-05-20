package com.bookcross;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AdminScreenBook extends AppCompatActivity{//показывает список книг
    ListView listView;
    FirebaseListAdapter adapter;
    Button showBook, showUser;
    ImageView btnAdd;
    ImageView ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book);
        showBook = findViewById(R.id.showBook);
        showUser = findViewById(R.id.showUser);
        btnAdd = findViewById(R.id.btnAddAdmin);
        ic_back = findViewById(R.id.ic_back);

        showUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminScreenBook.this, AdminScreenUser.class));
            }
        });

        listView = (ListView) findViewById(R.id.listviewtxt);
        Query query = FirebaseDatabase.getInstance().getReference().child("Book");
        FirebaseListOptions<DataClass> options = new FirebaseListOptions.Builder<DataClass>()
                .setLayout(R.layout.book)
                .setQuery(query, DataClass.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView nB = v.findViewById(R.id.nameBook);
                TextView aB = v.findViewById(R.id.auhtorBook);
                TextView pB = v.findViewById(R.id.placeBook);
                TextView idB = v.findViewById(R.id.keyBook);

                DataClass bk = (DataClass) model;
                nB.setText("Название книги: " + bk.getdataName());
                aB.setText("Автор книги: " + bk.getdataAuhtor());
                pB.setText("Где книга находится: " + bk.getPlace());
                idB.setText("id: " + bk.getKey());

            }
        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog alertDialog = new AlertDialog.Builder(AdminScreenBook.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Удалить книгу")
                        .setMessage("Вы хотите удалить эту книгу?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference itemRef = adapter.getRef(position);
                                itemRef.removeValue();
                                finish();
                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(),"Ничего не произошло",Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminScreenBook.this, AddBookScreen.class));
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminScreenBook.this, ProfilScreen.class));
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}