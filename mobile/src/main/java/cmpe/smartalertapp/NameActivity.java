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


public class NameActivity extends Activity {


    EditText nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);

        final Button nextButton = (Button) findViewById(R.id.nextButton);
        nameInput = (EditText) findViewById(R.id.name_edit_box);
        final Intent EmailIntent = new Intent(this, EmailActivity.class);

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (nameInput.getText().toString().trim().length() == 0) {
                    CharSequence text = "Please enter a name";
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    MainActivity.userData.put("name", nameInput.getText().toString()); //FIX THIS
                    startActivity(EmailIntent);
                }
            }
        });
    }
}