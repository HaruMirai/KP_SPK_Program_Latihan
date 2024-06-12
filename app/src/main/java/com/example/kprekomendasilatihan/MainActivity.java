package com.example.kprekomendasilatihan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    RadioGroup gender;
    Double persentaseakhir, lpinggang, lleher, tbadan, bbadan, lbm,
            persenwl, intenswl, repetisiwl, leanbmwl, b1, b2, b3, b4,
            persenwg, intenswg, repetisiwg, leanbmwg,
            persenmb, intensmb, repetisimb, leanbmmb,
            hasilwl, hasilwg, hasilmb, hasilwlvektor, hasilwgvektor, hasilmbvektor;

    String valuegender, tipebadan, tipelbm;
    EditText lingkarleher, tinggibadan, lingkarpinggang, beratbadan;
    Button kalkulasi, weightloss, weightgain, musclebuilding, login;
    DBHelper db;

    private void ambilDataBobotDanSetNilai(String nomor) {
        Cursor res = null;
        try {
            res = db.ambildatabobot(nomor);
            if (res != null && res.moveToFirst()) {
                b1 = Double.valueOf(res.getString(1));
                b2 = Double.valueOf(res.getString(2));
                b3 = Double.valueOf(res.getString(3));
                b4 = Double.valueOf(res.getString(4));
            } else {
                Toast.makeText(MainActivity.this, "Data tidak ditemukan untuk nomor: " + nomor, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Error mengambil data bobot: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (res != null) {
                res.close();
            }
        }
    }

    private void ambilDataAlternatifDanSetNilai(String nomoralter, String tipe) {
        Cursor res = null;
        try {
            res = db.ambildataalternatif(nomoralter);
            if (res != null && res.moveToFirst()) {
                switch (tipe) {
                    case "wl":
                        persenwl = Double.valueOf(res.getString(1));
                        intenswl = Double.valueOf(res.getString(2));
                        repetisiwl = Double.valueOf(res.getString(3));
                        leanbmwl = Double.valueOf(res.getString(4));
                        break;
                    case "wg":
                        persenwg = Double.valueOf(res.getString(1));
                        intenswg = Double.valueOf(res.getString(2));
                        repetisiwg = Double.valueOf(res.getString(3));
                        leanbmwg = Double.valueOf(res.getString(4));
                        break;
                    case "mb":
                        persenmb = Double.valueOf(res.getString(1));
                        intensmb = Double.valueOf(res.getString(2));
                        repetisimb = Double.valueOf(res.getString(3));
                        leanbmmb = Double.valueOf(res.getString(4));
                        break;
                }
            } else {
                Toast.makeText(MainActivity.this, "Data tidak ditemukan untuk alternatif: " + nomoralter, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Error mengambil data alternatif: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (res != null) {
                res.close();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gender = findViewById(R.id.radiojeniskelamin);
        beratbadan = findViewById(R.id.editberatbadan);
        lingkarleher = findViewById(R.id.editlingkarleher);
        lingkarpinggang = findViewById(R.id.editlingkarpinggang);
        tinggibadan = findViewById(R.id.edittinggibadan);
        kalkulasi = findViewById(R.id.buttondapatkanrekomendasi);
        weightloss = findViewById(R.id.buttonweightloss);
        weightgain = findViewById(R.id.buttonweightgain);
        musclebuilding = findViewById(R.id.buttonmusclebuilding);
        login = findViewById(R.id.btnlogin);
        db = new DBHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, login.class));
            }
        });
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                valuegender = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();
            }
        });


        kalkulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lpinggang = Double.valueOf(lingkarpinggang.getText().toString());
                lleher = Double.valueOf(lingkarleher.getText().toString());
                tbadan = Double.valueOf(tinggibadan.getText().toString());
                bbadan = Double.valueOf(beratbadan.getText().toString());

                if (valuegender.equals("Laki-laki")) {
                    persentaseakhir = 10.1 - 0.239 * tbadan + 0.8 * lpinggang - 0.5 * lleher;
                    if (persentaseakhir < 18) {
                        tipebadan = "lean";
                    } else {
                        tipebadan = "fat";
                    }
                    lbm = bbadan * (1 - (persentaseakhir / 100));
                    if (lbm < 65) {
                        tipelbm = "rendah";
                    } else {
                        tipelbm = "tinggi";
                    }
                } else {
                    persentaseakhir = 19.2 - 0.239 * tbadan + 0.8 * lpinggang - 0.5 * lleher;
                    if (persentaseakhir < 25) {
                        tipebadan = "lean";
                    } else {
                        tipebadan = "fat";
                    }
                    lbm = bbadan * (1 - (persentaseakhir / 100));
                    if (lbm < 50) {
                        tipelbm = "rendah";
                    } else {
                        tipelbm = "tinggi";
                    }
                }

                if (tipebadan.equals("lean") && tipelbm.equals("rendah")) {
                    ambilDataBobotDanSetNilai("2");
                } else if (tipebadan.equals("lean") && tipelbm.equals("tinggi")) {
                    ambilDataBobotDanSetNilai("3");
                } else if (tipebadan.equals("fat") && tipelbm.equals("rendah")) {
                    ambilDataBobotDanSetNilai("1");
                } else if (tipebadan.equals("fat") && tipelbm.equals("tinggi")) {
                    ambilDataBobotDanSetNilai("1");
                } else {
                    Toast.makeText(MainActivity.this, "COBA LAGI", Toast.LENGTH_LONG).show();
                }

                try {
                    ambilDataAlternatifDanSetNilai("1", "wl");
                    ambilDataAlternatifDanSetNilai("2", "wg");
                    ambilDataAlternatifDanSetNilai("3", "mb");

                    hasilwl = Math.pow(persenwl, b1) * Math.pow(intenswl, b2) * Math.pow(repetisiwl, b3) * Math.pow(leanbmwl, b4);
                    hasilwg = Math.pow(persenwg, b1) * Math.pow(intenswg, b2) * Math.pow(repetisiwg, b3) * Math.pow(leanbmwg, b4);
                    hasilmb = Math.pow(persenmb, b1) * Math.pow(intensmb, b2) * Math.pow(repetisimb, b3) * Math.pow(leanbmmb, b4);

                    hasilwlvektor = hasilwl / (hasilwl + hasilwg + hasilmb);
                    hasilwgvektor = hasilwg / (hasilwl + hasilwg + hasilmb);
                    hasilmbvektor = hasilmb / (hasilwl + hasilwg + hasilmb);

                    if (hasilwl == Math.max(hasilwl, Math.max(hasilwg, hasilmb))) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Persentase lemak = " + persentaseakhir + "%" + "\n PROGRAM YANG COCOK UNTUK ANDA ADALAH\nWEIGHT LOSS").setTitle("HASIL");
                        builder.show();
                    } else if (hasilwg == Math.max(hasilwl, Math.max(hasilwg, hasilmb))) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Persentase lemak = " + persentaseakhir + "%" + "\n PROGRAM YANG COCOK UNTUK ANDA ADALAH\nWEIGHT GAIN").setTitle("HASIL");
                        builder.show();
                    } else if (hasilmb == Math.max(hasilwl, Math.max(hasilwg, hasilmb))) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Persentase lemak = " + persentaseakhir + "%" + "\n PROGRAM YANG COCOK UNTUK ANDA ADALAH\nMUSCLE BUILDING").setTitle("HASIL");
                        builder.show();
                    } else {
                        Toast.makeText(MainActivity.this, "GAGAL GENERATE HASIL!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "GAGAL GENERATE HASIL!", Toast.LENGTH_LONG).show();
                }
            }
        });
        weightloss.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, weightloss.class));
            }
        }));
        weightgain.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, weightgain.class));
            }
        }));
        musclebuilding.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, musclebuilding.class));
            }
        }));
    }
}