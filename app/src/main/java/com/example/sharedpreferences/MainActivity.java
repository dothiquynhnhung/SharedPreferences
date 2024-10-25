package com.example.sharedpreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText edtCanNang, edtChieuCao;
    private TextView tvKqBMI, tvChanDoan;
    private Button btnKetQua;
    private  SharedPreferences sharedPreferences;


    // Tên tệp SharedPreferences
    private static final String SHARED_PREF_NAME = "BMIData";
    private static final String KEY_CAN_NANG = "can_nang";
    private static final String KEY_CHIEU_CAO = "chieu_cao";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCanNang = findViewById(R.id.canNang);
        edtChieuCao = findViewById(R.id.chieuCao);
        tvKqBMI = findViewById(R.id.kqBMI);
        tvChanDoan = findViewById(R.id.chanDoanTitle);
        btnKetQua = findViewById(R.id.btnKetQua);

        // Lấy dữ liệu đã lưu trữ nếu có
        loadSavedData();

        btnKetQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndSaveBMI();
            }
        });
    }

    private void calculateAndSaveBMI() {
        String canNangStr = edtCanNang.getText().toString();
        String chieuCaoStr = edtChieuCao.getText().toString();

        if (!canNangStr.isEmpty() && !chieuCaoStr.isEmpty()) {
            double canNang = Double.parseDouble(canNangStr);
            double chieuCao = Double.parseDouble(chieuCaoStr) / 100; // Đổi sang mét
            double bmi = canNang / (chieuCao * chieuCao);

            String bmiResult = String.format("%.2f", bmi);
            tvKqBMI.setText(bmiResult);

            String chanDoan = getChanDoan(bmi);
            tvChanDoan.setText(chanDoan);

            // Lưu trữ dữ liệu
            saveData(canNangStr, chieuCaoStr);
        }
    }

    private String getChanDoan(double bmi) {
        if (bmi < 18.5) {
            return "Thiếu cân";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            return "Bình thường";
        } else if (bmi >= 25 && bmi < 29.9) {
            return "Thừa cân";
        } else {
            return "Béo phì";
        }
    }

    private void saveData(String canNang, String chieuCao) {
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_CAN_NANG, canNang);
        editor.putString(KEY_CHIEU_CAO, chieuCao);
        editor.apply();
    }

    private void loadSavedData() {
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String savedCanNang = sharedPreferences.getString(KEY_CAN_NANG, "");
        String savedChieuCao = sharedPreferences.getString(KEY_CHIEU_CAO, "");

        if (!savedCanNang.isEmpty() && !savedChieuCao.isEmpty()) {
            edtCanNang.setText(savedCanNang);
            edtChieuCao.setText(savedChieuCao);
        }
    }


}