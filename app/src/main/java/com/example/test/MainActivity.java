package com.example.test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText time;
    private TextView finalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time = findViewById(R.id.in_time);
        button =  findViewById(R.id.btn_run);
        finalResult =  findViewById(R.id.tv_result);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskrun runner=new taskrun();
                String sleeptime=time.getText().toString();
                runner.execute(sleeptime);

            }
        });


    }

    public  class taskrun extends AsyncTask<String,String,String>{
        private String reps;
        ProgressDialog progressDialog;
        private  static final String TAG="MainActivity";
        @Override
        protected String doInBackground(String... strings) {
            publishProgress("sleeping");
            int time=Integer.parseInt(strings[0]);
            progressDialog.setMax(time);
            try {
                for(int i=1;i<=time;i++){
                    Thread.sleep(1000);
                    progressDialog.setProgress(i);
                    Log.i(TAG,"execute"+i);

                }
                reps="Slept for"+strings[0]+" second";

            }
            catch (Exception e)
            {
                reps=e.getMessage();
            }

            return reps;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            finalResult.setText(s);
        }

        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("wait for "+time.getText().toString()+" second");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    cancel(true);
                }
            });

            progressDialog.show();

        }

        @Override
        protected void onProgressUpdate(String... values) {
            finalResult.setText(values[0]);

        }
    }

}