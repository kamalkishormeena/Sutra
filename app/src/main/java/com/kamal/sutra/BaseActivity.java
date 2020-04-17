package com.kamal.sutra;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.crittercism.app.Crittercism;
import com.kamal.sutra.repository.Settings;

/**
 * Created by Ilya on 14.07.2014.
 */
public class BaseActivity extends AppCompatActivity {

    private Settings settings = SutraApplication.getInstance().getSettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Crittercism.initialize(getApplicationContext(), "53c54614466eda2cc200000d");
        super.onCreate(savedInstanceState);
    }

    protected Settings getSettings() {
        return settings;
    }

    public void setupToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            TextView head = (TextView) findViewById(R.id.action_title);
            if (head != null)
                head.setText(title);
            else
                toolbar.setTitle(title);
            setSupportActionBar(toolbar);
        }
    }

    public void enableNavigation() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

//        int id = item.getItemId();
//        if (id == R.id.action_reading) {
////            Intent intent = new Intent(this, ReadingMaterialListActivity.class);
////            startActivity(intent);
//            return true;
//        } else if (id == R.id.action_other_analytics) {
////            startActivity(new Intent(this, ProfileActivity.class));
//            return true;
//        } else if (id == R.id.action_credits) {
////            CreditsDialog dialog = new CreditsDialog();
////            dialog.show(getFragmentManager(), CreditsDialog.class.getSimpleName());
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
