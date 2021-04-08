package com.example.testppe.ui.dashboard;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog.Builder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testppe.AjoutItem;
import com.example.testppe.Avis;
import com.example.testppe.BDD.DBHelper;
import com.example.testppe.BDD.DBHelper_Produit;
import com.example.testppe.R;
import com.example.testppe.SelectItem;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

//classe scanner de code barre
public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    //This class provides methods to play DTMF tones
    private ToneGenerator toneGen1;
    private TextView barcodeText;
    private String barcodeData;
    private DBHelper mydb ;
    private DBHelper_Produit proddb;
    AlertDialog dialog;
    Builder builder;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        //initialisation des BDD
        mydb = new DBHelper(DashboardFragment.this.getContext());
        proddb = new DBHelper_Produit(DashboardFragment.this.getContext());

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                //textView.setText(s);
            }
        });

        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC,     100);
        surfaceView = root.findViewById(R.id.surface_view);
        barcodeText = root.findViewById(R.id.barcode_text);

        initialiseDetectorsAndSources();

        return root;
    }

    private void initialiseDetectorsAndSources() {



        barcodeDetector = new BarcodeDetector.Builder(this.getContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this.getContext(), barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(DashboardFragment.this.getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(DashboardFragment.this.getActivity(), new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    barcodeText.post(new Runnable() {

                        @Override
                        public void run() {
                            //dans une nouvelle thread on recupert les données trouver sur le barre code
                            if (barcodes.valueAt(0).email != null) {
                                barcodeText.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                                barcodeText.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                            } else {

                                barcodeData = barcodes.valueAt(0).displayValue;
                                barcodeText.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

                            }

                            if(!barcodeData.toString().equals("Barcode Text"))//si un barcode a été reconnu
                            {

                                String id = proddb.getidfromcode(barcodeData.toString());//recup le nom de l'objet

                                if(!id.equals("unknown"))//si l'objet fait partie de notr BDD Shorter
                                {
                                    //insertion
                                    mydb.insertrecherche(id);
                                    Intent intent1 = new Intent(DashboardFragment.this.getActivity(), SelectItem.class);
                                    intent1.putExtra("numero", id);

                                    DashboardFragment.this.getActivity().startActivity(intent1);
                                }else //autrement on fait rien
                                {
                                    Toast.makeText(DashboardFragment.this.getContext(), "Objet Inconnu", Toast.LENGTH_SHORT).show();


                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            DashboardFragment.this.getContext());
                                    // set title
                                    alertDialogBuilder.setTitle("AJOUTER");
                                    alertDialogBuilder.setCancelable(true);
                                    // set dialog message
                                    alertDialogBuilder
                                            .setMessage("Objet inconnu, voulez vous l'ajouter ?")
                                            .setCancelable(true)
                                            .setPositiveButton( "Yes",new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,int id) {
                                                    try {
                                                        //si l'utilisateur veut l'ajouter ouverture de la page ajouter
                                                        Intent intent = new Intent(DashboardFragment.this.getActivity(), AjoutItem.class);
                                                        intent.putExtra("EXTRA_SESSION_ID", barcodeData);//on envoie le barecode
                                                        startActivity(intent);//lance l'activité
                                                        dialog.dismiss();//ferme la fenetre de dialogue
                                                        //so some work
                                                    } catch (Exception e) {
                                                        //Exception
                                                    }
                                                }
                                            })
                                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,int id) {
                                                    //si bouton non : fermeture de la fenetre de dialogue
                                                    dialog.cancel();
                                                }
                                            });

                                    // create alert dialog
                                    AlertDialog alertDialog = alertDialogBuilder.create();

                                    // show it
                                    alertDialog.show();
                                }
                            }
                        }
                    });

                }
            }
        });


    }
    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {

            dialog.dismiss();
            Intent intent = new Intent(DashboardFragment.this.getActivity(), AjoutItem.class);
            intent.putExtra("EXTRA_SESSION_ID", barcodeData);
            startActivity(intent);
        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
          //  AlertExampleActivity.this.finish();
            dialog.dismiss();
            DashboardFragment.this.getActivity().finish();
        }
    }



}