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

public class EmailActivity extends Activity {
    public static String userName; //Why?
    private EditText emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
        userName = null;

        emailInput = (EditText) findViewById(R.id.emailInput);
        final Button nextButton = (Button) findViewById(R.id.nextButton);
        final Intent PasswordIntent = new Intent(this, Password.class);

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String inputEmail = emailInput.getText().toString();
                //MainActivity.currentUserName = emailInput.getText().toString();
                userName = emailInput.getText().toString();
                char[] valid = userName.toCharArray();
                boolean checkValid = false;

                for (int i = 0; i < valid.length; i++) {
                    if (valid[i] == '@') {
                        checkValid = true;
                    }
                }

                if (userName.trim().length() == 0) {
                    CharSequence text = "Please enter your email";
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    if (!checkValid) {
                        CharSequence text = "Invalid Email Address!";
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (MainActivity.userData.containsKey(emailInput.getText().toString())){
                        CharSequence text = "This Email has been registered already!";
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        MainActivity.userData.put("email", emailInput.getText().toString()); //FIX THIS
                        MainActivity.currentUserName = inputEmail;
                        startActivity(PasswordIntent);
                    }
                }
            }
        });
    }
}
