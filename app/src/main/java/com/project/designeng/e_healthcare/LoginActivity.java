package com.project.designeng.e_healthcare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
/*import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;*/
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;


public class LoginActivity extends AppCompatActivity {

    Button signInButton,signUpButton;
    CheckBox patientCheckBox;
    TextInputEditText emailEditText,passwordEditText;
    private FirebaseAuth mAuth;
    String email,password;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean loggedIn = preferences.getBoolean("loggedIn",false);
        boolean asAPatient = preferences.getBoolean("asAPatient",false);

        if(loggedIn){
            if(asAPatient)
                startActivity(new Intent(LoginActivity.this, ForPatientActivity.class));
            else startActivity(new Intent(LoginActivity.this, ForPersonActivity.class));
        }
        mAuth = FirebaseAuth.getInstance();
        //getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);  for Night mode
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        patientCheckBox = findViewById(R.id.check_a_patient);
        signUpButton = findViewById(R.id.email_sign_up_button);
        signUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

            }
        });
        signInButton = findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag= true;
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    flag = false;
                    emailEditText.setError("Enter valid email");
                }
                if(password.length() < 6){
                    passwordEditText.setError("password can't be less than 6");
                    flag = false;
                }
                if(isConnected(LoginActivity.this) && flag)
                    signIn(email, password);
                else Toast.makeText(LoginActivity.this, "Please connect to internet first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Sign in status", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent;
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("loggedIn",true);
                            if(patientCheckBox.isChecked()) {
                                editor.putBoolean("asAPatient",true);
                                intent = new Intent(LoginActivity.this, ForPatientActivity.class);
                            }
                            else {
                                editor.putBoolean("asAPatient",true);
                                intent = new Intent(LoginActivity.this, ForPersonActivity.class);
                            }
                            editor.apply();
                            startActivity(intent);
                        } else {
                            Log.w("Sign in status", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static boolean isConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert cm != null;
            NetworkInfo netinfo = cm.getActiveNetworkInfo();

            if (netinfo != null && netinfo.isConnectedOrConnecting()) {
                android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
            } else
                return false;
        }catch (NullPointerException e){ Log.e("Null object", Objects.requireNonNull(e.getMessage()));
        }
        return false;
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}

    /*private void insertDataToDB(final String email, final String password) {
        class InsertDataTask extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... strings) {
                List<NameValuePair> data = new ArrayList<>();

                data.add(new BasicNameValuePair("userName",email));
                data.add(new BasicNameValuePair("password",password));

                String result = "Data is not here";
                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://192.168.43.168/DesignEng/insertData.php");
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    InputStream stream = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    result = reader.readLine();
                }catch(Exception e){
                    Log.e("error",e.getMessage());
                }

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(LoginActivity.this, "Result:"+s, Toast.LENGTH_SHORT).show();
                super.onPostExecute(s);
            }
        }

        new InsertDataTask().execute(email, password);
    }*/

