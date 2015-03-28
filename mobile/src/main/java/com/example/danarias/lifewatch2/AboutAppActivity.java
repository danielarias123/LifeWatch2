package com.example.danarias.lifewatch2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;


public class AboutAppActivity extends ActionBarActivity implements View.OnClickListener {

    Button backtoMenuButton;
    TextView aboutDescTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        aboutDescTextView = (TextView) findViewById(R.id.appDiscTextView);
        aboutDescTextView.setMovementMethod(new ScrollingMovementMethod());

        backtoMenuButton = (Button) findViewById(R.id.backtoMenuButton);
        backtoMenuButton.setOnClickListener(this);
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.backtoMenuButton:
                Intent i = new Intent(AboutAppActivity.this, MobileMainActivity.class);
                startActivity(i);
                finish();
                break;




        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_app, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AboutAppActivity.this, MobileMainActivity.class);
        startActivity(i);
        finish();
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
}
