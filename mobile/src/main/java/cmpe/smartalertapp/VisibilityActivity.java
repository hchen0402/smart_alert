package cmpe.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import cmpe220.smartalertapp.R;


public class VisibilityActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visibility);
        CheckBox name = (CheckBox)findViewById(R.id.Name);
        CheckBox birthday = (CheckBox)findViewById(R.id.Birthday);
        CheckBox height = (CheckBox)findViewById(R.id.Height);
        CheckBox weight = (CheckBox)findViewById(R.id.Weight);
        CheckBox bloodType = (CheckBox)findViewById(R.id.BloodType);
        CheckBox medicalID = (CheckBox)findViewById(R.id.MedicalID);
        CheckBox healthOption = (CheckBox)findViewById(R.id.HealthCareProvider);
        CheckBox driversLisence = (CheckBox)findViewById(R.id.DriversLicense);
        final Intent personData = new Intent(this, PersonalDataActivity.class);

//        if (MainActivity.visibilityDataBase.containsKey(MainActivity.currentUserName)) {
//            String[] checked = MainActivity.visibilityDataBase.get(MainActivity.currentUserName);
//            if (checked[0].equals("t")) name.setChecked(true);
//            if (checked[1].equals("t")) birthday.setChecked(true);
//            if (checked[2].equals("t")) height.setChecked(true);
//            if (checked[3].equals("t")) weight.setChecked(true);
//            if (checked[4].equals("t")) bloodType.setChecked(true);
//            if (checked[5].equals("t")) medicalID.setChecked(true);
//            if (checked[6].equals("t")) healthOption.setChecked(true);
//            if (checked[7].equals("t")) driversLisence.setChecked(true);
//        }

        if (PersonalDataActivity.visibilities != null) {
            String[] checked = PersonalDataActivity.visibilities;
            if (checked[0].equals("t")) name.setChecked(true);
            if (checked[1].equals("t")) birthday.setChecked(true);
            if (checked[2].equals("t")) height.setChecked(true);
            if (checked[3].equals("t")) weight.setChecked(true);
            if (checked[4].equals("t")) bloodType.setChecked(true);
            if (checked[5].equals("t")) medicalID.setChecked(true);
            if (checked[6].equals("t")) healthOption.setChecked(true);
            if (checked[7].equals("t")) driversLisence.setChecked(true);
        }

        Button okButton = (Button)findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox name = (CheckBox)findViewById(R.id.Name);
                CheckBox birthday = (CheckBox)findViewById(R.id.Birthday);
                CheckBox height = (CheckBox)findViewById(R.id.Height);
                CheckBox weight = (CheckBox)findViewById(R.id.Weight);
                CheckBox bloodType = (CheckBox)findViewById(R.id.BloodType);
                CheckBox medicalID = (CheckBox)findViewById(R.id.MedicalID);
                CheckBox healthOption = (CheckBox)findViewById(R.id.HealthCareProvider);
                CheckBox driversLisence = (CheckBox)findViewById(R.id.DriversLicense);

                String[] checked = new String[8];
                if (name.isChecked())
                    checked[0] = "t";
                else
                    checked[0] = "f";

                if (birthday.isChecked())
                    checked[1] = "t";
                else
                    checked[1] = "f";

                if (height.isChecked())
                    checked[2] = "t";
                else
                    checked[2] = "f";

                if (weight.isChecked())
                    checked[3] = "t";
                else
                    checked[3] = "f";

                if (bloodType.isChecked())
                    checked[4] = "t";
                else
                    checked[4] = "f";

                if (medicalID.isChecked())
                    checked[5] = "t";
                else
                    checked[5] = "f";

                if (healthOption.isChecked())
                    checked[6] = "t";
                else
                    checked[6] = "f";

                if (driversLisence.isChecked())
                    checked[7] = "t";
                else
                    checked[7] = "f";

                //MainActivity.visibilityDataBase.put(MainActivity.currentUserName, checked);
                PersonalDataActivity.visibilitiesCopy = checked;
                startActivity(personData);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent BackIntent = new Intent(getApplicationContext(), PersonalDataActivity.class);
        startActivity(BackIntent);
    }
}
