package cmpe.smartalertapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cmpe220.smartalertapp.R;

public class Password extends Activity {

    EditText passwordInput, passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);

        passwordInput = (EditText) findViewById(R.id.passwordInput);
        passwordConfirm = (EditText)findViewById(R.id.passwordInputAgain);
        final Button nextButton = (Button) findViewById(R.id.nextButton);
        final Intent ProceedToProfileIntent = new Intent(this, ProceedToProfile.class);

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                passwordInput = (EditText) findViewById(R.id.passwordInput);
                passwordConfirm = (EditText) findViewById(R.id.passwordInputAgain);

                if (passwordInput.getText().toString().trim().length() == 0) {
                    CharSequence text = "Please enter your password";
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else if (passwordConfirm.getText().toString().trim().length() == 0) {
                    CharSequence text = "Please confirm your password";
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else if (!passwordInput.getText().toString().equals(passwordConfirm.getText().toString())) {
                    CharSequence text = "Password does not match\nPlease confirm your password";
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    MainActivity.userData.put(MainActivity.currentUserName, passwordInput.getText().toString());
                    MainActivity.userData.put("password", passwordInput.getText().toString()); //FIX THIS
                    startActivity(ProceedToProfileIntent);
                }
            }
        });
    }
}
