package com.bookcross;

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

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ProfilScreen extends AppCompatActivity implements View.OnClickListener {

    Button see_place_books;
    ImageView icon_add;
    ImageView icon_back;
    ImageView ic_setting;
    ImageView icon_chat;
    ListView listView;
    FirebaseListAdapter adapter;
    private static final String EXTRA_CURRENT_USER_ID = "currentUser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        icon_add = findViewById(R.id.icon_add);
        see_place_books = findViewById(R.id.see_place_books);
        icon_back = findViewById(R.id.icon_back);
        icon_chat = findViewById(R.id.icon_chat);
        ic_setting = findViewById(R.id.ic_setting);

        see_place_books.setOnClickListener(this);
        icon_add.setOnClickListener(this);
        icon_back.setOnClickListener(this);
        icon_chat.setOnClickListener(this);
        ic_setting.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.listview);
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
            }
        };

        listView.setAdapter(adapter);

    }

    public void onClick(View view) {

        switch (view.getId()){

            case R.id.see_place_books:
                Intent intent = new Intent(ProfilScreen.this, MapsScreen.class);
                ProfilScreen.this.startActivity(intent);
                break;
            case R.id.icon_add:
                Intent intent1 = new Intent(ProfilScreen.this, AddBookScreen.class);
                ProfilScreen.this.startActivity(intent1);
                break;
            case R.id.icon_back:
                Intent intent2 = new Intent(ProfilScreen.this, MainScreen.class);
                ProfilScreen.this.startActivity(intent2);
                break;
            case R.id.icon_chat:
                Intent intent4 = new Intent(ProfilScreen.this, UsersActivity.class);
                ProfilScreen.this.startActivity(intent4);
                break;
            case R.id.ic_setting:
                startActivity(new Intent(ProfilScreen.this, AvtorizScreen.class));
        }
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
