package cmpe.smartalertapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cmpe220.smartalertapp.R;

public class TranslateActivity extends Activity {

    private Spinner spinner;
    private TextView translate_result;
    private AlertDialog alert;
    private String[] allergenList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        final Button btn1 = (Button) findViewById(R.id.btnChoose);
        spinner = (Spinner) findViewById(R.id.translate_from);
        List<String> list = new ArrayList<String>();
        list.add("中文"); list.add("Français"); list.add("English");
        list.add("日本語"); list.add("Español");
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String from = String.valueOf(spinner.getSelectedItem());
                translate_result = (TextView) findViewById(R.id.translate_result);
                translate_result.setText("                       ");
                switch (from) {
                    case "English":
                        allergenList = MainActivity.allergyDataBase.get(MainActivity.currentUserName);
                        String allergens = "None";
                        if(allergenList != null) {
                             allergens = detectAllergens(allergenList);
                        }
                        translate_result.setText("(25) Satay Beef\n" +
                                "(26) X.O Beef\n" +
                                "(27) Satay Beef Pot\n" +
                                "(28)Melon & Beef\n" +
                                "(29) Spicy Beef\n" +
                                "(30)Black Pepper Beef\n\n\n" +
                                "Possible allergens: \n" + allergens);
                        break;
                    case "中文":
                        translate_result.setText("中文");
                        break;
                    case "Français":
                        translate_result.setText("Français");
                        break;
                    case "日本語":
                        translate_result.setText("日本語");
                        break;
                    case "Español":
                        translate_result.setText("Español");
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent BackIntent = new Intent(getApplicationContext(), ScanActivity.class);
        startActivity(BackIntent);
    }

    private String detectAllergens(String[] input) {
        alert = new AlertDialog.Builder(this).create();
        alert.setTitle("STOP! Allergens detected!");
        alert.setButton("Got it, I won't touch it!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setIcon(R.drawable.er_launcher_icon);
        String message = "";
        for(String allergen : input) {
            if (allergen.equals("Eggs")) {
                message += "Eggs ";
            }
            if (allergen.equals("Peanuts")) {
                message += "Peanuts ";
            }
            if (allergen.equals("Soy")) {
                message += "Soy";
            }
        }
        if (!message.equals("")) {
            alert.setMessage("This menu may contains: " + message);
            alert.show();
        }
        return message;
    }
}

