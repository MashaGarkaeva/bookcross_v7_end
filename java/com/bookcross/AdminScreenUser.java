package com.bookcross;

import androidx.appcompat.app.AppCompatActivity;

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

public class AdminScreenUser extends AppCompatActivity {//показывает список пользователей
    ListView listView;
    FirebaseListAdapter adapter;
    Button showBook, showUser;
    ImageView ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);
        showBook = findViewById(R.id.showBook);
        showUser = findViewById(R.id.showUser);
        ic_back = findViewById(R.id.ic_back);

        showBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminScreenUser.this, AdminScreenBook.class));
            }
        });


        listView = (ListView) findViewById(R.id.listviewtxt);
        Query query = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bookcross-377713-default-rtdb.firebaseio.com/Users");
        FirebaseListOptions<User> options = new FirebaseListOptions.Builder<User>()
                .setLayout(R.layout.book)
                .setQuery(query, User.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView nUs = v.findViewById(R.id.nameUser);
                TextView idUs = v.findViewById(R.id.userId);
                TextView emUs = v.findViewById(R.id.userEmail);

                User us = (User) model;
                nUs.setText(us.getName().toString());
                idUs.setText(us.getId().toString());
                emUs.setText(us.getEmail().toString());

            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

                AlertDialog alertDialog = new AlertDialog.Builder(AdminScreenUser.this)//окно удаления элемента списка
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Удалить пользователя")
                        .setMessage("Вы хотите удалить этого пользователя?")
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

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminScreenUser.this, ProfilScreen.class));
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