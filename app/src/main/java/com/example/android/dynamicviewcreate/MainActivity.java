package com.example.android.dynamicviewcreate;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.avocarrot.json2view.DynamicView;
import com.avocarrot.json2view.DynamicViewId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JSONObject jsonObject;

        try {

            jsonObject = new JSONObject(readFile("sample.json", this));

        } catch (JSONException je) {
            je.printStackTrace();
            jsonObject = null;
        }

        if (jsonObject != null) {

            /* create dynamic view and return the view with the holder class attached as tag */
            View sampleView = DynamicView.createView(this, jsonObject, SampleViewHolder.class);
            /* get the view with id "testClick" and attach the onClickListener */
            //((SampleViewHolder) sampleView.getTag()).clickableView.setOnClickListener(this);

            /* add Layout Parameters in just created view and set as the contentView of the activity */
            sampleView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
            setContentView(sampleView);

        } else {
            Log.e("Json2View", "Could not load valid json file");
        }

    }

    /**
     * Helper function to load file from assets
     */
    private String readFile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open(fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line;
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null) isr.close();
                if (fIn != null) fIn.close();
                if (input != null) input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
    }

    /**
     * Holder class that keep UI Component from the Dynamic View
     */
    static public class SampleViewHolder {
        //@DynamicViewId(id = "click")
        //public View clickableView;

        public SampleViewHolder() {
        }
    }

}
