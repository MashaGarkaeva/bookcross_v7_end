package com.bookcross;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AvtorizScreen extends AppCompatActivity {

    Button enter;
    EditText loginUsername;
    EditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avtoriz);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        enter = findViewById(R.id.enter1);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginUsername.getText().toString().equals("admin") &&//авторизация для админа
                loginPassword.getText().toString().equals("yfcnz1")){
                    startActivity(new Intent(AvtorizScreen.this, AdminScreenBook.class));
                }
                else if(!validateUserName() | ! validatePassword()){

                } else {
                    Toast.makeText(AvtorizScreen.this, "Вы не администратор, выйдите в главное меню, пожалуйста", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public Boolean validateUserName(){
        String val = loginUsername.getText().toString();
        if (val.isEmpty()){
            loginUsername.setError("Введите логин");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()){
            loginPassword.setError("Введите пароль");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

}