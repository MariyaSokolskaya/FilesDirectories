package com.example.admin215.filesdirectories;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    TextView text;
    Button scan;
    StringBuilder strTree = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan = (Button) findViewById(R.id.scan);
        text = (TextView) findViewById(R.id.list_files);
        strTree.append("Directories: \n");
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanDir scanDir = new ScanDir();
                scanDir.execute();
            }
        });

    }

    class ScanDir extends AsyncTask<Void, Void, Void>{

        void recoursiveCall(String path, String indent){
            File [] fileList = new File(path).listFiles();
            for(File file: fileList){
                if (file.isDirectory()){
                    strTree.append(indent).append(file.getName()).append("\n");
                    recoursiveCall(file.getAbsolutePath(), indent + "--");
                }
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            text.setText(strTree);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            recoursiveCall(Environment.getExternalStorageDirectory().getPath(), "");
            return null;
        }
    }
}
