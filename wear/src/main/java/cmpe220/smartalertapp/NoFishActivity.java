package cmpe220.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;


public class NoFishActivity extends Activity {

    static public String backScreen;
    static public String nextScreen;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_fish);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_no_fish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                    if ((x2 > x1)){
                        Intent BackIntent;
                        switch (backScreen){
                            case "emergency":
                                BackIntent = new Intent(this, AllergyActivity.class);
                                BackIntent.putExtra("backscreen", "emergency");
                                break;
                            case "home":
                                BackIntent = new Intent(this, AllergyActivity.class);
                                break;
                            case "Cow's Milk":
                                BackIntent = new Intent(this, NoMilkActivity.class);
                                break;
                            case "Fish":
                                BackIntent = new Intent(this, NoFishActivity.class);
                                break;
                            case "Eggs":
                                BackIntent = new Intent(this, NoEggActivity.class);
                                break;
                            case "Peanuts":
                                BackIntent = new Intent(this, NoPeanutActivity.class);
                                break;
                            case "Shellfish":
                                BackIntent = new Intent(this, NoShellfishActivity.class);
                                break;
                            case "Soy":
                                BackIntent = new Intent(this, NoSoyActivity.class);
                                break;
                            case "Tree Nuts":
                                BackIntent = new Intent(this, NoTreenutActivity.class);
                                break;
                            case "Wheat":
                                BackIntent = new Intent(this, NoGlutenActivity.class);
                                break;
                            default:
                                BackIntent = new Intent(this, AllergyActivity.class);
                                break;
                        }
                        startActivity(BackIntent);
                    } else {
                        Intent NextIntent;
                        switch (nextScreen){
                            case "emergency":
                                NextIntent = new Intent(this, AllergyActivity.class);
                                NextIntent.putExtra("backscreen", "emergency");
                                break;
                            case "home":
                                NextIntent = new Intent(this, AllergyActivity.class);
                                break;
                            case "Cow's Milk":
                                NextIntent = new Intent(this, NoMilkActivity.class);
                                break;
                            case "Fish":
                                NextIntent = new Intent(this, NoFishActivity.class);
                                break;
                            case "Eggs":
                                NextIntent = new Intent(this, NoEggActivity.class);
                                break;
                            case "Peanuts":
                                NextIntent = new Intent(this, NoPeanutActivity.class);
                                break;
                            case "Shellfish":
                                NextIntent = new Intent(this, NoShellfishActivity.class);
                                break;
                            case "Soy":
                                NextIntent = new Intent(this, NoSoyActivity.class);
                                break;
                            case "Tree Nuts":
                                NextIntent = new Intent(this, NoTreenutActivity.class);
                                break;
                            case "Wheat":
                                NextIntent = new Intent(this, NoGlutenActivity.class);
                                break;
                            default:
                                NextIntent = new Intent(this, AllergyActivity.class);
                                break;
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
}
