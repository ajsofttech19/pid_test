package com.en.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class Main extends Activity {
    public static String portName = "USB:";
    public static int portSettings = 0;
    boolean Into = false;
    int LoopTimes;
    Button button_StartLoop;
    Button button_StopLoop;
    Button button_send;
    /* access modifiers changed from: private */
    public Context context;
    EditText editText_LoopTimes;
    EditText editText_msg;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    Spinner spinner1;
    /* access modifiers changed from: private */
    public Runnable updateTimer = new Runnable() {
        public void run() {
            if (((PowerManager) Main.this.getSystemService(Context.POWER_SERVICE)).isScreenOn()) {
                if (Main.this.Into) {
                    Main.this.Into = false;
                    try {
                        Thread.sleep(4000);
                    } catch (Exception e) {
                    }
                }
                switch (Main.this.spinner1.getSelectedItemPosition()) {
                    case 0:
                        PDFunctions.myOpenPort(1, 0);
                        break;
                    case 1:
                        PDFunctions.myOpenPort(1, 1);
                        break;
                    case 2:
                        PDFunctions.myOpenPort(1, 2);
                        break;
                    case 3:
                        PDFunctions.myOpenPort(1, 3);
                        break;
                    case 4:
                        PDFunctions.myOpenPort(2, 0);
                        break;
                    case 5:
                        PDFunctions.myOpenPort(2, 1);
                        break;
                    case 6:
                        PDFunctions.myOpenPort(2, 2);
                        break;
                    case 7:
                        PDFunctions.myOpenPort(2, 10);
                        break;
                    case 8:
                        PDFunctions.myOpenPort(2, 11);
                        break;
                    case 9:
                        PDFunctions.myOpenPort(2, 12);
                        break;
                    case 10:
                        PDFunctions.myOpenPort(2, 13);
                        break;
                    case 11:
                        PDFunctions.myOpenPort(3, 0);
                        break;
                }
                TextView time = (TextView) Main.this.findViewById(R.id.textView_times);
                int intCount = Integer.parseInt(Main.this.ReadFile(Main.this.context, "tmpCount"));
                if (intCount > 0 && intCount <= Main.this.LoopTimes) {
                    time.setText("Times : " + intCount);
                    PDFunctions.ShowString(" " + intCount + ":" + Main.this.editText_msg.getText().toString());
                    Main.this.WriteFile(Main.this.context, "tmpCount", String.valueOf(intCount + 1));
                    Main.this.handler.postDelayed(this, 1000);
                    return;
                }
                return;
            }
            Main.this.Into = true;
            Main.this.handler.postDelayed(this, 1000);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.context = this;
        this.editText_msg = (EditText) findViewById(R.id.editText_msg);
        this.editText_LoopTimes = (EditText) findViewById(R.id.editText_LoopTimes);
        this.spinner1 = (Spinner) findViewById(R.id.spinner1);




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.portName, 17367048);
        adapter.setDropDownViewResource(17367049);

        this.spinner1.setAdapter(adapter);
        this.spinner1.setSelection(4);
        this.button_send = (Button) findViewById(R.id.button_send);
        this.button_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (Main.this.spinner1.getSelectedItemPosition()) {
                    case 0:
                        PDFunctions.myOpenPort(1, 0);
                        break;
                    case 1:
                        PDFunctions.myOpenPort(1, 1);
                        break;
                    case 2:
                        PDFunctions.myOpenPort(1, 2);
                        break;
                    case 3:
                        PDFunctions.myOpenPort(1, 3);
                        break;
                    case 4:
                        PDFunctions.myOpenPort(2, 0);
                        break;
                    case 5:
                        PDFunctions.myOpenPort(2, 1);
                        break;
                    case 6:
                        PDFunctions.myOpenPort(2, 2);
                        break;
                    case 7:
                        PDFunctions.myOpenPort(2, 10);
                        break;
                    case 8:
                        PDFunctions.myOpenPort(2, 11);
                        break;
                    case 9:
                        PDFunctions.myOpenPort(2, 12);
                        break;
                    case 10:
                        PDFunctions.myOpenPort(2, 13);
                        break;
                    case 11:
                        PDFunctions.myOpenPort(3, 0);
                        break;
                }
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                PDFunctions.ShowString(Main.this.editText_msg.getText().toString());
            }
        });
        this.button_StartLoop = (Button) findViewById(R.id.button_StartLoop);
        this.button_StartLoop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Main.this.LoopTimes = Integer.parseInt(Main.this.editText_LoopTimes.getText().toString());
                Main.this.WriteFile(Main.this.context, "tmpCount", "1");
                Main.this.handler.removeCallbacks(Main.this.updateTimer);
                Main.this.handler.postDelayed(Main.this.updateTimer, 1000);
            }
        });
        this.button_StopLoop = (Button) findViewById(R.id.button_StopLoop);
        this.button_StopLoop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Main.this.WriteFile(Main.this.context, "tmpCount", "0");
            }
        });
        this.Into = false;
    }

    public int myDialog(String Title, String Messages) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(Title);
        dialog.setMessage(Messages);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
        return 0;
    }

    public boolean WriteFile(Context context2, String name, String data) {
        try {
            FileOutputStream out = context2.openFileOutput(name, 0);
            out.write(data.getBytes());
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String ReadFile(Context context2, String name) {
        String data = "";
        try {
            FileInputStream in = context2.openFileInput(name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    in.close();
                    return data;
                }
                data = String.valueOf(data) + line;
            }
        } catch (Exception e) {
            return "";
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }

static {
    try {
        Log.d("JNILoad", "Trying to load libPosPDdrv.so");
        String arch = System.getProperty("os.arch");

        System.loadLibrary("PosPDdrv");
    } catch (UnsatisfiedLinkError e) {
        Log.e("JNILoad", "WARNING: Could not load libPosPDdrv.so:::"+e.getMessage());
    }
}
}
