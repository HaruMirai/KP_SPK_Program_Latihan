package com.example.kprekomendasilatihan;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class managedatabase extends AppCompatActivity {
    EditText nomor, b1, b2, b3, b4, nomoralter, persentase, intens, repetisi, leanbm;
    Button simpanbobot, tampilbobot, hapusbobot, simpanalter, tampilalter, hapusalter, home;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_managedatabase);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nomor = findViewById(R.id.nomor);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        nomoralter = findViewById(R.id.nomoralter);
        persentase = findViewById(R.id.persentase);
        intens = findViewById(R.id.intens);
        repetisi = findViewById(R.id.repetisi);
        leanbm = findViewById(R.id.leanbm);
        simpanbobot = findViewById(R.id.btnsimpanbobot);
        tampilbobot = findViewById(R.id.btntampilbobot);
        hapusbobot = findViewById(R.id.btnhampusbobot);
        simpanalter = findViewById(R.id.btnsimpanalter);
        tampilalter = findViewById(R.id.btntampilalter);
        hapusalter = findViewById(R.id.btnhampusalter);
        home = findViewById(R.id.btnhome);
        db = new DBHelper(this);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(managedatabase.this, MainActivity.class));
            }
        });
        simpanbobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isinomor = nomor.getText().toString();
                String isib1 = b1.getText().toString();
                String isib2 = b2.getText().toString();
                String isib3 = b3.getText().toString();
                String isib4 = b4.getText().toString();

                if (TextUtils.isEmpty(isinomor) || TextUtils.isEmpty(isib1) || TextUtils.isEmpty(isib2)
                        || TextUtils.isEmpty(isib3) || TextUtils.isEmpty(isib4)) {
                    Toast.makeText(managedatabase.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                } else {
                    Boolean checkkode = db.checknomor(isinomor);
                    if (checkkode == false) {
                        Boolean insert = db.insertBobotData(isinomor, isib1, isib2, isib3, isib4);
                        if (insert == true) {
                            Toast.makeText(managedatabase.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), managedatabase.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(managedatabase.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(managedatabase.this, "Data Bobot Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        tampilbobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilDataBobot();
                if (res.getCount() == 0) {
                    Toast.makeText(managedatabase.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append(("Nomor : " + res.getString(0) + "\n"));
                    buffer.append(("b1 : " + res.getString(1) + "\n"));
                    buffer.append(("b2 : " + res.getString(2) + "\n"));
                    buffer.append(("b3 : " + res.getString(3) + "\n"));
                    buffer.append(("b4 : " + res.getString(4) + "\n\n\n"));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(managedatabase.this);
                builder.setCancelable(true);
                builder.setTitle("Data Bobot");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });
        hapusbobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kb = nomor.getText().toString();
                Boolean cekHapusData = db.hapusDataBobot(kb);
                if (cekHapusData == true)
                    Toast.makeText(managedatabase.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(managedatabase.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });
        simpanalter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isinomoralter = nomoralter.getText().toString();
                String isipersentase = persentase.getText().toString();
                String isiintens = intens.getText().toString();
                String isirepetisi = repetisi.getText().toString();
                String isileanbm = leanbm.getText().toString();

                if (TextUtils.isEmpty(isinomoralter) || TextUtils.isEmpty(isipersentase) || TextUtils.isEmpty(isiintens)
                        || TextUtils.isEmpty(isirepetisi) || TextUtils.isEmpty(isileanbm)) {
                    Toast.makeText(managedatabase.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                } else {
                    Boolean checkkode = db.checknomoralternatif(isinomoralter);
                    if (checkkode == false) {
                        Boolean insert = db.insertAlternatifData(isinomoralter, isipersentase, isiintens, isirepetisi, isileanbm);
                        if (insert == true) {
                            Toast.makeText(managedatabase.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), managedatabase.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(managedatabase.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(managedatabase.this, "Data Alternatif Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        tampilalter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilDataAlternatif();
                if (res.getCount() == 0) {
                    Toast.makeText(managedatabase.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append(("Nomoralter : " + res.getString(0) + "\n"));
                    buffer.append(("persentase : " + res.getString(1) + "\n"));
                    buffer.append(("intens : " + res.getString(2) + "\n"));
                    buffer.append(("repetisi : " + res.getString(3) + "\n"));
                    buffer.append(("leanbm : " + res.getString(4) + "\n\n\n"));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(managedatabase.this);
                builder.setCancelable(true);
                builder.setTitle("Data Alternatif");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
        hapusalter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kb = nomoralter.getText().toString();
                Boolean cekHapusData = db.hapusDataAlternatif(kb);
                if (cekHapusData == true)
                    Toast.makeText(managedatabase.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(managedatabase.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();


            }
        });

    }
}