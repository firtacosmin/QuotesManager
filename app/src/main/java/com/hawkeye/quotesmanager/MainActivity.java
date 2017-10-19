package com.hawkeye.quotesmanager;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hawkeye.quotesmanager.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by CRM on 7/31/2017.
 */

public class MainActivity extends AppCompatActivity {

    private int READ_REQUEST_CODE = 3;
    private String TAG = "MainActivity";
    private File path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding dinbing = DataBindingUtil.setContentView(this,R.layout.activity_main);
        dinbing.managerLayout.imgeButtonImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
//                // browser.
//                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//
//                // Filter to only show results that can be "opened", such as a
//                // file (as opposed to a list of contacts or timezones)
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//                // Filter to show only images, using the image MIME data type.
//                // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
//                // To search for all documents available via installed storage providers,
//                // it would be "*/*".
//                intent.setType("*/*");
//
//                startActivityForResult(intent, READ_REQUEST_CODE);


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(
                            Intent.createChooser(intent, "Select a File to Upload"),
                            READ_REQUEST_CODE);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(MainActivity.this, "Please install a File Manager.",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                try {
                    parseFile(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void parseFile(Uri fileUri) throws FileNotFoundException, UnsupportedEncodingException, IOException {

        Log.i(TAG, "Uri: " + fileUri.toString());
        if (fileUri.toString().contains(".csv")) {
            File file = new File(fileUri.getPath());
            if (file.isFile() && file.getAbsolutePath().endsWith(".csv")) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                try {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = br.readLine();
                    }
                    String everything = sb.toString();
                    Log.d(TAG,"The parse mesage is : " + everything);
                } finally {
                    br.close();
                }

            }
        }
    }

}
