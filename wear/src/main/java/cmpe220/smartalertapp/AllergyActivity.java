package cmpe220.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;

public class AllergyActivity extends SwipeActivity {

    private String backScreen;
    private String nextScreen;
    private TextView AllergyText;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    private String[] allergiesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allergies_display);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            backScreen = extras.getString("backscreen");
        }
        else
        {
            backScreen = "home";
        }
    }

    @Override
    protected void onStart() {
        if (!WatchActivity.UserData.containsKey("allergies") || WatchActivity.UserData.get("allergies") == null || ((String[]) WatchActivity.UserData.get("allergies")).length == 0){
            WatchActivity.UserData.put(
                    "allergies",
                    new String[] {"null"});
        }

        HashMap UserDataCopy = WatchActivity.UserData;

        allergiesArray = (String[]) UserDataCopy.get("allergies");

        String allergiesList = "";
        for (String allergy : allergiesArray){
            allergiesList = allergiesList+"\n"+allergy;
        }

        AllergyText = (TextView) findViewById(R.id.allergies_text);

        if (allergiesArray[0] == "null"){
            AllergyText.setText("Allergies not found");
        } else {
            AllergyText.setText(
                    allergiesList + "\n\n\n");
            for (int i = 0; i < allergiesArray.length; i++){
                String allergyScreen = allergiesArray[i];
                if ((i - 1) >= 0){
                    switch (allergyScreen){
                        case "Cow's Milk":
                            NoMilkActivity.backScreen = allergiesArray[i-1];
                            break;
                        case "Eggs":
                            NoEggActivity.backScreen = allergiesArray[i-1];
                            break;
                        case "Peanuts":
                            NoPeanutActivity.backScreen = allergiesArray[i-1];
                            break;
                        case "Fish":
                            NoFishActivity.backScreen = allergiesArray[i-1];
                            break;
                        case "Shellfish":
                            NoShellfishActivity.backScreen = allergiesArray[i-1];
                            break;
                        case "Tree Nuts":
                            NoTreenutActivity.backScreen = allergiesArray[i-1];
                            break;
                        case "Wheat":
                            NoGlutenActivity.backScreen = allergiesArray[i-1];
                            break;
                        case "Soy":
                            NoSoyActivity.backScreen = allergiesArray[i-1];
                            break;
                    }

                } else {
                    switch (allergyScreen){
                        case "Cow's Milk":
                            NoMilkActivity.backScreen = backScreen;
                            break;
                        case "Eggs":
                            NoEggActivity.backScreen = backScreen;
                            break;
                        case "Peanuts":
                            NoPeanutActivity.backScreen = backScreen;
                            break;
                        case "Fish":
                            NoFishActivity.backScreen = backScreen;
                            break;
                        case "Shellfish":
                            NoShellfishActivity.backScreen = backScreen;
                            break;
                        case "Tree Nuts":
                            NoTreenutActivity.backScreen = backScreen;
                            break;
                        case "Wheat":
                            NoGlutenActivity.backScreen = backScreen;
                            break;
                        case "Soy":
                            NoSoyActivity.backScreen = backScreen;
                            break;
                    }
                }
                if ((i + 1) < allergiesArray.length){
                    switch (allergyScreen){
                        case "Cow's Milk":
                            NoMilkActivity.nextScreen = allergiesArray[i+1];
                            break;
                        case "Eggs":
                            NoEggActivity.nextScreen = allergiesArray[i+1];
                            break;
                        case "Peanuts":
                            NoPeanutActivity.nextScreen = allergiesArray[i+1];
                            break;
                        case "Fish":
                            NoFishActivity.nextScreen = allergiesArray[i+1];
                            break;
                        case "Shellfish":
                            NoShellfishActivity.nextScreen = allergiesArray[i+1];
                            break;
                        case "Tree Nuts":
                            NoTreenutActivity.nextScreen = allergiesArray[i+1];
                            break;
                        case "Wheat":
                            NoGlutenActivity.nextScreen = allergiesArray[i+1];
                            break;
                        case "Soy":
                            NoSoyActivity.nextScreen = allergiesArray[i+1];
                            break;
                    }

                } else {

                    switch (allergyScreen){
                        case "Cow's Milk":
                            NoMilkActivity.nextScreen = backScreen;
                            break;
                        case "Eggs":
                            NoEggActivity.nextScreen = backScreen;
                            break;
                        case "Peanuts":
                            NoPeanutActivity.nextScreen = backScreen;
                            break;
                        case "Fish":
                            NoFishActivity.nextScreen = backScreen;
                            break;
                        case "Shellfish":
                            NoShellfishActivity.nextScreen = backScreen;
                            break;
                        case "Tree Nuts":
                            NoTreenutActivity.nextScreen = backScreen;
                            break;
                        case "Wheat":
                            NoGlutenActivity.nextScreen = backScreen;
                            break;
                        case "Soy":
                            NoSoyActivity.nextScreen = backScreen;
                            break;
                        default:
                            if (allergiesArray.length > 1){
                                String prev = allergiesArray[i-1];
                                switch (prev) {
                                    case "Cow's Milk":
                                        NoMilkActivity.nextScreen = backScreen;
                                        break;
                                    case "Eggs":
                                        NoEggActivity.nextScreen = backScreen;
                                        break;
                                    case "Peanuts":
                                        NoPeanutActivity.nextScreen = backScreen;
                                        break;
                                    case "Fish":
                                        NoFishActivity.nextScreen = backScreen;
                                        break;
                                    case "Shellfish":
                                        NoShellfishActivity.nextScreen = backScreen;
                                        break;
                                    case "Tree Nuts":
                                        NoTreenutActivity.nextScreen = backScreen;
                                        break;
                                    case "Wheat":
                                        NoGlutenActivity.nextScreen = backScreen;
                                        break;
                                    case "Soy":
                                        NoSoyActivity.nextScreen = backScreen;
                                        break;

                                }

                            }
                    }
                }
            }
        }

        super.onStart();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    if ((x2 > x1) || (allergiesArray[0] == "null")){
                        Intent emergencyIntent = new Intent(this, EmergencyOptionsActivity.class);
                        if (backScreen.equals("emergency")) {
                            emergencyIntent.putExtra("backscreen", "emergency");

                            startActivity(emergencyIntent);

                        } else {

                            startActivity(emergencyIntent);
                        }
                    } else {
                        Intent NextIntent;
                        nextScreen = allergiesArray[0];
                        switch (nextScreen){
                            case "Cow's Milk":
                                NextIntent = new Intent(getApplicationContext(), NoMilkActivity.class);
                                break;
                            case "Eggs":
                                NextIntent = new Intent(getApplicationContext(), NoEggActivity.class);
                                break;
                            case "Peanuts":
                                NextIntent = new Intent(getApplicationContext(), NoPeanutActivity.class);
                                break;
                            case "Fish":
                                NextIntent = new Intent(getApplicationContext(), NoFishActivity.class);
                                break;
                            case "Shellfish":
                                NextIntent = new Intent(getApplicationContext(), NoShellfishActivity.class);
                                break;
                            case "Tree Nuts":
                                NextIntent = new Intent(getApplicationContext(), NoTreenutActivity.class);
                                break;
                            case "Wheat":
                                NextIntent = new Intent(getApplicationContext(), NoGlutenActivity.class);
                                break;
                            case "Soy":
                                NextIntent = new Intent(getApplicationContext(), NoSoyActivity.class);
                                break;
                            default:
                                NextIntent = new Intent(this, EmergencyOptionsActivity.class);
                                if (backScreen.equals("emergency")) {
                                    NextIntent.putExtra("backscreen", "emergency");
                                    startActivity(NextIntent);

                                } else {
                                    startActivity(NextIntent);
                                }
                        }
                        startActivity(NextIntent);
                    }
                } else {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void previous() {
        Intent emergencyIntent = new Intent(this, EmergencyOptionsActivity.class);
        if (backScreen.equals("emergency")) {
            emergencyIntent.putExtra("backscreen", "emergency");
            startActivity(emergencyIntent);
        } else {
            startActivity(emergencyIntent);
        }
    }

    @Override
    protected void next() {
        Intent NextIntent;
        nextScreen = allergiesArray[0];
        switch (nextScreen){
            case "Cow's Milk":
                NextIntent = new Intent(getApplicationContext(), NoMilkActivity.class);
                break;
            case "Eggs":
                NextIntent = new Intent(getApplicationContext(), NoEggActivity.class);
                break;
            case "Peanuts":
                NextIntent = new Intent(getApplicationContext(), NoPeanutActivity.class);
                break;
            case "Fish":
                NextIntent = new Intent(getApplicationContext(), NoFishActivity.class);
                break;
            case "Shellfish":
                NextIntent = new Intent(getApplicationContext(), NoShellfishActivity.class);
                break;
            case "Tree Nuts":
                NextIntent = new Intent(getApplicationContext(), NoTreenutActivity.class);
                break;
            case "Wheat":
                NextIntent = new Intent(getApplicationContext(), NoGlutenActivity.class);
                break;
            case "Soy":
                NextIntent = new Intent(getApplicationContext(), NoSoyActivity.class);
                break;
            default:
                NextIntent = new Intent(this, EmergencyOptionsActivity.class);
                if (backScreen.equals("emergency")) {
                    NextIntent.putExtra("backscreen", "emergency");
                    startActivity(NextIntent);
                } else {
                    startActivity(NextIntent);
                }
        }
        startActivity(NextIntent);

    }
}
