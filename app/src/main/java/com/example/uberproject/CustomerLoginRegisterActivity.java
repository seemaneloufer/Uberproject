package com.example.uberproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class CustomerLoginRegisterActivity extends AppCompatActivity {
    private TextView CreateCustomerAccount;
    private TextView TitleCustomer;
    private Button LoginCustomerButton;
    private Button RegisterCustomerButton;
    private EditText CustomerEmail;
    private EditText CustomerPassword;
    private static final String TAG = "CustomerLoginRegisterActivity";
    private DatabaseReference driversDatabaseRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListner;

    private ProgressDialog loadingBar;

    private FirebaseUser currentUser;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_register);
        CreateCustomerAccount = (TextView) findViewById(R.id.create_driver_account);
        TitleCustomer = (TextView) findViewById(R.id.titlr_driver);
        LoginCustomerButton = (Button) findViewById(R.id.login_driver_btn);
        RegisterCustomerButton = (Button) findViewById(R.id.register_driver_btn);
        CustomerEmail = (EditText) findViewById(R.id.driver_email);
        CustomerPassword = (EditText) findViewById(R.id.driver_password);
        loadingBar = new ProgressDialog(this);


        RegisterCustomerButton.setVisibility(View.INVISIBLE);
        RegisterCustomerButton.setEnabled(false);
        mAuth = FirebaseAuth.getInstance();

        //Registering driver

        CreateCustomerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateCustomerAccount.setVisibility(View.INVISIBLE);
                LoginCustomerButton.setVisibility(View.INVISIBLE);
                TitleCustomer.setText("Customer Registration");

                RegisterCustomerButton.setVisibility(View.VISIBLE);
                RegisterCustomerButton.setEnabled(true);
            }
        });
        RegisterCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= CustomerEmail.getText().toString();
                String password=CustomerPassword.getText().toString();
                mAuth=FirebaseAuth.getInstance();
                if (email.isEmpty()){
                    CustomerEmail.setError("Enter your Email ID");
                    CustomerEmail.requestFocus();
                }
                else if(password.isEmpty()){
                    CustomerPassword.setError("Password is empty");
                    CustomerPassword.requestFocus();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()){
                                Toast.makeText(CustomerLoginRegisterActivity.this, "sign up error", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String currentUserId = mAuth.getCurrentUser().getUid();
                                DatabaseReference driversDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(currentUserId);
                                driversDatabaseRef.setValue(true);
                                startActivity(new Intent(CustomerLoginRegisterActivity.this,CustomersMapActivity.class) );

                            }
                        }
                    });
                }
            }
        });
        LoginCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = CustomerEmail.getText().toString();
                final String password = CustomerPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(CustomerLoginRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(CustomerLoginRegisterActivity.this, "sign in error", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent intent = new Intent(CustomerLoginRegisterActivity.this, CustomersMapActivity.class);
                            startActivity(intent);
                        }
                    }
                });

            }
        });


    }//Oncreate ends
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        RegisterCustomerButton.setVisibility(View.INVISIBLE);
        RegisterCustomerButton.setEnabled(false);
    }


}