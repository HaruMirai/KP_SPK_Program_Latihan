package com.example.kprekomendasilatihan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class login extends AppCompatActivity {
    EditText username, password;
    Button masuk;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    username = findViewById(R.id.editusername);
    password = findViewById(R.id.editpassword);
    masuk = findViewById(R.id.btnmasuk);
    db = new DBHelper(this);

    masuk.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            if (TextUtils.isEmpty(user) || (TextUtils.isEmpty(pass)))
                Toast.makeText(login.this,"All Field Required", Toast.LENGTH_SHORT).show();
            else {
                Boolean checkuserpass = db.checkusernamepassword(user,pass);
                if (checkuserpass==true){
                    Toast.makeText(login.this, "Login sukses", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), managedatabase.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });



    }
}