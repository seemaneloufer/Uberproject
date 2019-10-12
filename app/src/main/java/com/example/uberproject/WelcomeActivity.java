package com.example.uberproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    private Button DriverWelcomeButton;
    private Button CustomerWelcomeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        DriverWelcomeButton= (Button)findViewById(R.id.driver_welcome_btn);
        CustomerWelcomeButton=(Button)findViewById(R.id.customer_welcome_btn);

        CustomerWelcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CustomerIntent = new Intent(WelcomeActivity.this, CustomerLoginRegisterActivity.class);
                startActivity(CustomerIntent);
            }
        });
        DriverWelcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent DriverIntent = new Intent(WelcomeActivity.this, DriverLoginRegisterActivity.class);
                startActivity(DriverIntent);
            }
        });
    }
}
