package com.example.testppe;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int util_id=0;
    public MainActivity()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            util_id= getIntent().getIntExtra("EXTRA_SESSION_ID",0);
            //The key argument here must match that used in the other activity
        }


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_recherche,R.id.navigation_stat)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.night_mode:

                Toast.makeText(MainActivity.this, "night mode ",Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(this, Settings.class);

                startActivity(intent1);
                break;

            case R.id.loggout:
                finish();
                break;

            case R.id.info:
                Toast.makeText(MainActivity.this, "INFORMATIONS ",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, Informations.class);
                startActivity(intent);

                 break;
            case R.id.avis :
                Toast.makeText(MainActivity.this, "AVIS ",Toast.LENGTH_LONG).show();

                Intent intent2 = new Intent(this, Avis.class);
                intent2.putExtra("EXTRA_SESSION_ID", util_id);
                startActivity(intent2);

                break;

            case R.id.Localisation :
                Toast.makeText(MainActivity.this, "LOCALISATION",Toast.LENGTH_LONG).show();

                Intent intent3= new Intent(this, MapsActivity.class);
                //intent3.putExtra("Nom", textView1.getText().toString());
                intent3.putExtra("EXTRA_SESSION_ID", util_id);
                startActivity(intent3);

                break;

            default:
                Toast.makeText(MainActivity.this, "default command ",Toast.LENGTH_LONG).show();


        }

        item.setChecked(true);
        setTitle(item.getTitle());




        return super.onOptionsItemSelected(item);
    }


}