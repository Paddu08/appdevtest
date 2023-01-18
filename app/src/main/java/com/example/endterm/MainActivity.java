package com.example.endterm;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private static EditText etName510, etPhone510, etIdNumber510;
    private static RadioButton rbAadhar510, rbDL510;
    private static DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName510 = findViewById(R.id.etName);
        etPhone510 = findViewById(R.id.etPhone);
        etIdNumber510 = findViewById(R.id.etIdNumber);
        rbAadhar510 = findViewById(R.id.rbAadhar);
        rbDL510 = findViewById(R.id.rbDL);

        dbHelper = new DbHelper(this);
    }

    public void onSaveClick(View view) {
        String name = etName510.getText().toString();
        String phone = etPhone510.getText().toString();
        String idNumber = etIdNumber510.getText().toString();
        String idType = "";

        if (rbAadhar510.isChecked()) {
            idType = "Aadhar";
        } else if (rbDL510.isChecked()) {
            idType = "Driving License";
        }

        if (name.trim().isEmpty() || phone.trim().isEmpty() || idNumber.trim().isEmpty() || idType.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        long id = dbHelper.insertIdentity(name, phone, idNumber, idType);

        if (id != -1) {
            Toast.makeText(this, "Identity saved with ID: " + id, Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Error saving identity", Toast.LENGTH_SHORT).show();
        }
    }



    public void onShowAllClick(View view) {
        Cursor cursor = dbHelper.getAllIdentities();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No identities found", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            builder.append("ID: " + cursor.getInt(0) + "\n");
            builder.append("Name: " + cursor.getString(1) + "\n");
            builder.append("Phone: " + cursor.getString(2) + "\n");
            builder.append("ID Number: " + cursor.getString(3) + "\n");
            builder.append("ID Type: " + cursor.getString(4) + "\n\n");
        }

        Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show();
    }


    public void onCountClick(View view) {
        int count = dbHelper.getTotalUsers();
        Toast.makeText(this, "Total registered users: " + count, Toast.LENGTH_SHORT).show();
    }

    public void onSearchClick(View view) {
        String aadhar = etIdNumber510.getText().toString();
        if (aadhar.trim().isEmpty()) {
            Toast.makeText(this, "Please enter an Aadhar number", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = dbHelper.getIdentityByAadhar(aadhar);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No records found for the given Aadhar number", Toast.LENGTH_SHORT).show();
            return;
        }

        cursor.moveToFirst();
        String name = cursor.getString(0);
        String phone = cursor.getString(1);
        Toast.makeText(this, "Name: " + name + "\nPhone: " + phone, Toast.LENGTH_SHORT).show();
    }
    public void onShowDLClick(View view) {
        Cursor cursor = dbHelper.getDLIdentities();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No driving licenses found", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            builder.append("ID: " + cursor.getInt(0) + "\n");
            builder.append("Name: " + cursor.getString(1) + "\n");
            builder.append("Phone: " + cursor.getString(2) + "\n");
            builder.append("ID Number: " + cursor.getString(3) + "\n");
            builder.append("ID Type: " + cursor.getString(4) + "\n\n");
        }

        Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show();
    }

    private void clearFields() {
        etName510.setText("");
        etPhone510.setText("");
        etIdNumber510.setText("");
        rbAadhar510.setChecked(false);
        rbDL510.setChecked(false);
    }
}