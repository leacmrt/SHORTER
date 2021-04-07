package com.example.testppe;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

//classe pour les paramètres
public class Settings extends AppCompatActivity {

    Context thiscontext;
    Switch Landscape =null;
    Switch Mode =null;
    Switch Lang = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button back = findViewById(R.id.button2);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        Landscape =(Switch) findViewById(R.id.switchLandscape);
        Mode =(Switch) findViewById(R.id.switchDark);
        Lang =(Switch) findViewById(R.id.switchEnglish);

        int la = getResources().getConfiguration().orientation;

        if(la==1)
        {
            Landscape.setChecked(false);
            Landscape.setTextColor(Color.BLUE);
            Landscape.setText("OFF");

        }else if(la==2)
        {
            Landscape.setChecked(true);
            Landscape.setTextColor(Color.GREEN);
            Landscape.setText("ON");
        }


        Landscape.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(Settings.this, String.valueOf(la),Toast.LENGTH_LONG).show();
                if(Landscape.isChecked())//passage en mode paysage
                {

                    Landscape.setTextColor(Color.GREEN);
                    Landscape.setText("ON");
                    Settings.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                }else //passage en mode portrait
                {
                    Landscape.setTextColor(Color.BLUE);
                    Landscape.setText("OFF");
                    Settings.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);}
            }
        });

        Drawable modet = (Drawable) ((ConstraintLayout)findViewById(R.id.test)).getBackground();
        int mode = AppCompatDelegate.getDefaultNightMode();

        if(mode==1)
        {
            Mode.setChecked(false);
            Mode.setTextColor(Color.BLUE);
            Mode.setText("OFF");

        }else if(mode==2)
        {
            Mode.setChecked(true);
            Mode.setTextColor(Color.GREEN);
            Mode.setText("ON");
        }


        Mode.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Toast.makeText(Settings.this, modet.toString(),Toast.LENGTH_LONG).show();

                if(Mode.isChecked())
                {

                    Mode.setTextColor(Color.GREEN);//passage en mode nuit
                    Mode.setText("ON");
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_YES);
                    // FragmentSetting.this.getActivity().recreate();

                }else if(!Mode.isChecked())//passage en mode nuit
                {
                    Mode.setTextColor(Color.BLUE);
                    Mode.setText("OFF");
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_NO);
                    //  FragmentSetting.this.getActivity().recreate();
                }
            }

        });

        String langt = (String) ((TextView)findViewById(R.id.textView)).getText();
        //Toast.makeText(MainActivity.this, blankFragment.getTag(),Toast.LENGTH_LONG).show();
        if(langt.equals("French"))
        {
            Lang.setChecked(false);
            Lang.setTextColor(Color.BLUE);
            Lang.setText("OFF");

        }else if(langt.equals("Anglais"))
        {
            Lang.setChecked(true);
            Lang.setTextColor(Color.GREEN);
            Lang.setText("ON");
        }


        Lang.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {


                if(Lang.isChecked())//mode anglais
                {


                    Lang.setTextColor(Color.GREEN);
                    Lang.setText("ON");
                    // LocaleHelper.setLocale(thiscontext,"fr"); //for french;
                    LocaleHelper.setLocale(Settings.this.getBaseContext(),"fr");
                    Settings.this.recreate();
                    // LocaleHelper.setLocale(la.getContext(),"fr");

                }else if(!Lang.isChecked())//mode français
                {
                    Lang.setTextColor(Color.BLUE);
                    Lang.setText("OFF");
                    LocaleHelper.setLocale(Settings.this.getBaseContext(),"en");
                    Settings.this.recreate();
                }
            }

        });







    }
    public static class LocaleHelper {

        private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

        public static void onCreate(Context context) {

            String lang;
            if(getLanguage(context).isEmpty()){
                lang = getPersistedData(context, Locale.getDefault().getLanguage());
            }else {
                lang = getLanguage(context);
            }

            setLocale(context, lang);
        }

        public static void onCreate(Context context, String defaultLanguage) {
            String lang = getPersistedData(context, defaultLanguage);
            setLocale(context, lang);
        }

        public static String getLanguage(Context context) {
            return getPersistedData(context, Locale.getDefault().getLanguage());
        }

        public static void setLocale(Context context, String language) {
            persist(context, language);
            updateResources(context, language);
        }

        private static String getPersistedData(Context context, String defaultLanguage) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
        }

        private static void persist(Context context, String language) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString(SELECTED_LANGUAGE, language);
            editor.apply();
        }

        private static void updateResources(Context context, String language) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Resources resources = context.getResources();

            Configuration configuration = resources.getConfiguration();
            configuration.locale = locale;

            resources.updateConfiguration(configuration, resources.getDisplayMetrics());


        }
    }

}