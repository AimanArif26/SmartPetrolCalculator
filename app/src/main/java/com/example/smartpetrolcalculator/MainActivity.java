package com.example.smartpetrolcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    Spinner spPetrol;
    EditText etPrice, etUsage;
    RadioButton rbYes;
    TextView tvCost, tvRebate, tvFinal;
    Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Material Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spPetrol = findViewById(R.id.spPetrol);
        etPrice = findViewById(R.id.etPrice);
        etUsage = findViewById(R.id.etUsage);
        rbYes = findViewById(R.id.rbYes);
        tvCost = findViewById(R.id.tvCost);
        tvRebate = findViewById(R.id.tvRebate);
        tvFinal = findViewById(R.id.tvFinal);
        btnCalculate = findViewById(R.id.btnCalculate);

        // Setup Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.petrol_types,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPetrol.setAdapter(adapter);

        btnCalculate.setOnClickListener(v -> calculate());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            return true; // Already on Home
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void calculate() {
        String priceStr = etPrice.getText().toString().trim();
        String usageStr = etUsage.getText().toString().trim();

        // Check for empty fields
        if (priceStr.isEmpty() || usageStr.isEmpty()) {
            tvCost.setText("Please fill in all fields.");
            tvRebate.setText("");
            tvFinal.setText("");
            return;
        }

        // Check for valid numbers
        double price, usage;
        try {
            price = Double.parseDouble(priceStr);
            usage = Double.parseDouble(usageStr);
        } catch (NumberFormatException e) {
            tvCost.setText("Please enter valid numbers.");
            tvRebate.setText("");
            tvFinal.setText("");
            return;
        }

        // Check for zero or negative values
        if (price <= 0 || usage <= 0) {
            tvCost.setText("Please enter values greater than zero.");
            tvRebate.setText("");
            tvFinal.setText("");
            return;
        }

        String petrol = spPetrol.getSelectedItem().toString();

        double totalCost = price * usage;
        double rebate = 0;

        // Calculate BUDI rebate only for RON95 and eligible users
        if (petrol.equals("RON95") && rbYes.isChecked()) {
            rebate = usage * 1.99;
            tvRebate.setText("BUDI Rebate: RM " + String.format("%.2f", rebate));
        } else {
            tvRebate.setText("BUDI Rebate: Not applicable");
        }

        double totalSaving = totalCost - rebate;

        tvCost.setText("Total Petrol Cost: RM " + String.format("%.2f", totalCost));
        tvFinal.setText("Total Saving: RM " + String.format("%.2f", totalSaving));
    }
}