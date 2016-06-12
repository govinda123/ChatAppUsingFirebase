package com.gdgnd.fire1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class Login extends AppCompatActivity {

    private Firebase mFirebaseRef;

    EditText editText, emaileditext;
    static String mUsername;
    Button button;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Firebase.setAndroidContext(this);

        mFirebaseRef = new Firebase("https://fire123chat.firebaseio.com/");

        editText = (EditText) findViewById(R.id.editText);
        emaileditext = (EditText) findViewById(R.id.emaileditext);




button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgressDialog = new ProgressDialog(Login.this);
                mProgressDialog.show();

                final String email = ((EditText)findViewById(R.id.emaileditext)).getText().toString();
                final String password =((TextView)findViewById(R.id.editText)).getText().toString();

                System.out.println("user--"+email);
                System.out.println("pswrd--" + password);

                mFirebaseRef.createUser(email,password, new Firebase.ResultHandler() {


                    @Override
                    public void onSuccess() {
                        mFirebaseRef.authWithPassword(email,password, null);
                        System.out.println("aya kya yaha pe");
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        System.out.println(firebaseError.getMessage());
                        mFirebaseRef.authWithPassword(email, password, null);
                    }
                });


            }
        });




        mFirebaseRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    mUsername = ((String) authData.getProviderData().get("email"));

                    final Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    mUsername = null;

                }
            }
        });


    }


}
