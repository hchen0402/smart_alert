package cmpe.smartalertapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import cmpe220.smartalertapp.R;

public class ScanResultActivity extends Activity {

    private String[] allergenList;
    private AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result_activity);
        allergenList = MainActivity.allergyDataBase.get(MainActivity.currentUserName);
        String name = "";
        String brand = "";
        String ingredients = "";
        String allergens = "None";
        TextView productName = (TextView)findViewById(R.id.productName);
        TextView productBrand = (TextView)findViewById(R.id.brand);
        TextView productIndregient = (TextView)findViewById(R.id.ingredients);
        TextView productAllergens = (TextView) findViewById(R.id.allergens);
        ImageView productImage = (ImageView)findViewById(R.id.image);
        if (ScanActivity.scanFormat == null || ScanActivity.scanContent == null) {
            return;
        } else if (ScanActivity.scanFormat.equals("UPC_A") && ScanActivity.scanContent.equals("000006065966")) {
            name = "Whole Milk";
            brand = "ORGANIC VALLEY";
            ingredients = "Organic Grade A Milk. Vitamin D3.";
            productImage.setImageResource(R.drawable.milk);
        } else if (ScanActivity.scanFormat.equals("UPC_A") && ScanActivity.scanContent.equals("048121135454")) {
            name = "100% Whole Wheat Bagels";
            brand = "Thomas'";
            ingredients = "WHOLE WHEAT FLOUR, WATER, WHOLE WHEAT, SUGAR, WHEAT GLUTEN, " +
                    "YEAST, WHEAT BRAN, CORNMEAL, SALT, PRESERVATIVES (CALCIUM PROPIONATE, SORBIC ACID), " +
                    "MONO- AND DIGLYCERIDES, DATEM, CELLULOSE GUM, CITRIC ACID, MALTODEXTRIN, XANTHAN GUM, " +
                    "SOY LECITHIN, CORNSTARCH, ALGIN.";
            productImage.setImageResource(R.drawable.bagels);
        } else if (ScanActivity.scanFormat.equals("UPC_A") && ScanActivity.scanContent.equals("029000016552")) {
            name = "Crunchy Peanut Butter";
            brand = "Planters";
            ingredients = "Roasted Peanuts, Sugar, " +
                    "Contains Less Than 2% of Hydrogenated Vegetable Oil (Rapeseed, Soybean, And Cottonseed Oils), " +
                    "Salt. Contains: Peanuts..";
            productImage.setImageResource(R.drawable.peanut);
        } else {return;}
        productName.setText(name);
        productBrand.setText(brand);
        productIndregient.setText(ingredients);
        if(allergenList != null) {
            allergens = detectAllergens(ingredients, allergenList);
        }
        productAllergens.setText(allergens);
    }

    @Override
    public void onBackPressed() {
        Intent BackIntent = new Intent(getApplicationContext(), ScanActivity.class);
        startActivity(BackIntent);
    }

    private String detectAllergens(String input, String[] allergens) {
        alert = new AlertDialog.Builder(this).create();
        alert.setTitle("STOP! Allergens detected!");
        alert.setButton("Got it, I won't touch it!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setIcon(R.drawable.er_launcher_icon);
        if (input.toLowerCase().contains("milk")) {
            for(String allergen: allergens) {
                if(allergen.equals("Cow's Milk")) {
                    alert.setMessage("This product contains:" + " " + "Cow's Milk");
                    alert.show();
                    return "Cow's Milk";
                }
            }
        }
        if(input.toLowerCase().contains("wheat")) {
            for(String allergen: allergens) {
                if(allergen.equals("Wheat")) {
                    alert.setMessage("This product contains:" + " " + "Wheat");
                    alert.show();
                    return "Wheat";
                }
            }
        }
        if(input.toLowerCase().contains("peanut")) {
            for(String allergen: allergens) {
                if(allergen.equals("Peanuts")) {
                    alert.setMessage("This product contains:" + " " + "Peanuts");
                    alert.show();
                    return "Peanuts";
                }
            }
        }
        return "None";
    }
}