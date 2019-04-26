package cmpe220.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ViewMedicalActivity extends SwipeActivity{

    private String backScreen;
    private TextView MedicalInfo;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.medical_info);
//
//        if (!WatchActivity.UserData.containsKey("personalData")){
//            WatchActivity.UserData.put(
//                    "personalData",
//                    new String[] {"","","","","","","",""});
//        }
//        if (!WatchActivity.UserData.containsKey("allergies")){
//            WatchActivity.UserData.put(
//                    "allergies",
//                    new String[] {""});
//        }
//        if (!WatchActivity.UserData.containsKey("contacts")){
//            WatchActivity.UserData.put(
//                    "contacts",
//                    new String[] {""});
//        }
//
//        HashMap UserDataCopy = WatchActivity.UserData;
//        String[] allergiesArray = (String[]) UserDataCopy.get("allergies");
//        String allergiesList = "";
//        boolean first = true;
//        for (String allergy : allergiesArray){
//            if (first){
//                allergiesList = allergy;
//            } else {
//                allergiesList = allergiesList+", "+allergy;
//            }
//        }
//
//        MedicalInfo = (TextView) findViewById(R.id.profile_data);
////        MedicalInfo.setText(
////                "Name: "+((String[]) UserDataCopy.get("personalData"))[0]+ "\n"+
////                "Birthday: "+((String[]) UserDataCopy.get("personalData"))[1]+ "\n"+
////                "Height: "+((String[]) UserDataCopy.get("personalData"))[2]+ "\n"+
////                "Weight: "+((String[]) UserDataCopy.get("personalData"))[3]+ "\n"+
////                "Blood Type: "+((String[]) UserDataCopy.get("personalData"))[4]+ "\n"+
////                "Medical ID: "+((String[]) UserDataCopy.get("personalData"))[5]+ "\n"+
////                "Health Care Prover: "+((String[]) UserDataCopy.get("personalData"))[6]+ "\n"+
////                "Driver's License: "+((String[]) UserDataCopy.get("personalData"))[0]+ "\n"+
////                "Allergies: "+allergiesList);
//
//        MedicalInfo.setText(
//                "Name: Zach\n"+
//                        "Birthday: 3/28/1995\n"+
//                        "Height: 5\"5'\n"+
//                        "Weight: 200lbs\n"+
//                        "Blood Type: A+\n"+
//                        "Medical ID: ID Number\n"+
//                        "Health Care Provider: Insurance Company\n"+
//                        "Driver's License: License Number\n"+
//                        "Allergies: None!"+allergiesList+
//                        "\n\n\n"
//        );
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_info);
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
        if (!WatchActivity.UserData.containsKey("personalData")){
            WatchActivity.UserData.put(
                    "personalData",
                    new String[] {""});
        }

        HashMap UserDataCopy = WatchActivity.UserData;

        String[] personalDataArray = (String[]) UserDataCopy.get("personalData");

        MedicalInfo = (TextView) findViewById(R.id.profile_data);
        boolean visible = false;
        if (UserDataCopy.containsKey("visibility")){
            String[] visibilitiesCopy = (String[]) UserDataCopy.get("visibility");
            for (String v : visibilitiesCopy){
                if (v.equals("t")){
                    visible = true;
                }
            }
        }

        if (personalDataArray.length == 1) {
            MedicalInfo.setText("Profile not found");
        } else if (!UserDataCopy.containsKey("visibility") || !visible){
            Log.i("Visibility: ", "Not found");
            MedicalInfo.setText("Personal data not made visible"+"\n\n"+
                                "To change profile visibility, go to Personal Data under Edit Health Profile in the mobile app"+
                                "\n\n\n");
        } else {
            Log.i("Visibility: ", "Found");
            String[] visibilities = (String[]) UserDataCopy.get("visibility");
            Log.i("Visibility: ", "["+visibilities[0]+" "
                    +visibilities[1]+" "
                    +visibilities[2]+" "
                    +visibilities[3]+" "
                    +visibilities[4]+" "
                    +visibilities[5]+" "
                    +visibilities[6]+" "
                    +visibilities[7]+"]");
            String infoText = "";
            if (visibilities[0].equals("t")){
                infoText = infoText+"Name: " + personalDataArray[0] + "\n";
                Log.i("infoText1: ", infoText);
            }
            if (visibilities[1].equals("t")){
                infoText = infoText+"Birthday: " + personalDataArray[1] + "\n";
                Log.i("infoText2: ", infoText);
            }
            if (visibilities[2].equals("t")){
                infoText = infoText+"Height: " + personalDataArray[2] + "\n";
                Log.i("infoText3: ", infoText);
            }
            if (visibilities[3].equals("t")){
                infoText = infoText+"Weight: " + personalDataArray[3] + "\n";
                Log.i("infoText4: ", infoText);
            }
            if (visibilities[4].equals("t")){
                infoText = infoText+"Blood Type: " + personalDataArray[4] + "\n";
                Log.i("infoText5: ", infoText);
            }
            if (visibilities[5].equals("t")){
                infoText = infoText+"Medical ID: " + personalDataArray[5] + "\n";
                Log.i("infoText6: ", infoText);
            }
            if (visibilities[6].equals("t")){
                infoText = infoText+"Health Care Provider: " + personalDataArray[6] + "\n";
                Log.i("infoText7: ", infoText);
            }
            if (visibilities[7].equals("t")){
                infoText = infoText+"Driver's License: " + personalDataArray[7] + "\n";
                Log.i("infoText8: ", infoText);
            }
            infoText = infoText+"\n\n";
            MedicalInfo.setText(infoText);

//            MedicalInfo.setText(
//                    "Name: " + personalDataArray[0] + "\n" +
//                            "Birthday: " + personalDataArray[1] + "\n" +
//                            "Height: " + personalDataArray[2] + "\n" +
//                            "Weight: " + personalDataArray[3] + "\n" +
//                            "Blood Type: " + personalDataArray[4] + "\n" +
//                            "Medical ID: " + personalDataArray[5] + "\n" +
//                            "Health Care Provider: " + personalDataArray[6] + "\n" +
//                            "Driver's License: " + personalDataArray[7] + "\n\n\n");
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
                    Intent emergencyIntent = new Intent(this, EmergencyOptionsActivity.class);
                    if (backScreen.equals("emergency")) {
                        emergencyIntent.putExtra("backscreen", "emergency");
                        startActivity(emergencyIntent);
                    } else {
                        startActivity(emergencyIntent);
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
        Intent emergencyIntent = new Intent(this, EmergencyOptionsActivity.class);
        if (backScreen.equals("emergency")) {
            emergencyIntent.putExtra("backscreen", "emergency");
            startActivity(emergencyIntent);
        } else {
            startActivity(emergencyIntent);
        }
    }
}
