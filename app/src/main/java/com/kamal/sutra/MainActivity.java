package com.kamal.sutra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.kamal.sutra.domain.Bin;
import com.kamal.sutra.domain.NoteType;
import com.kamal.sutra.domain.Session;
import com.kamal.sutra.repository.Settings;
import com.kamal.sutra.utils.EnglishNumberToWords;

import org.apache.commons.lang3.text.WordUtils;

import java.util.List;
import java.util.Random;

public class MainActivity extends BaseActivity {

    private RelativeLayout monasteryBtn;
    private RelativeLayout meditateBtn;
    private Button analysisBtn;
    private TextView dayNumberText;
    private TextView infoText;
    private Settings settings;
    private ImageView profile;
    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar("");

        settings = getSettings();
        profile = findViewById(R.id.profile);
        monasteryBtn = findViewById(R.id.linearLayout2);
        monasteryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monasteryActivity = new Intent(MainActivity.this, MonasteryActivity.class);
                startActivity(monasteryActivity);
            }
        });
        meditateBtn = findViewById(R.id.linearLayout3);
        meditateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent chooseTimeActivity = new Intent(MainActivity.this, ChooseTimeActivity.class);
//                Pair[] pairs = new Pair[1];
//                pairs[0] = new Pair<>(findViewById(R.id.linearLayout), "bottom");
//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, pairs);
//                startActivity(chooseTimeActivity, optionsCompat.toBundle());
                Intent chooseTimeActivity = new Intent(MainActivity.this, ChooseTimeActivity.class);
                startActivity(chooseTimeActivity);
            }
        });

        analysisBtn = (Button) findViewById(R.id.btn_analysis);
        analysisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnalysisActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent profileActivity = new Intent(MainActivity.this, ProfileActivity.class);
//                Pair[] pairs = new Pair[2];
//                pairs[0] = new Pair<>(findViewById(R.id.profile), "profile");
//                pairs[1] = new Pair<>(findViewById(R.id.linearLayout), "bottom");
//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, pairs);
//                startActivity(profileActivity, optionsCompat.toBundle());
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        dayNumberText = (TextView) findViewById(R.id.dayNumber);
        infoText = (TextView) findViewById(R.id.home_info);
    }

    @Override
    protected void onResume() {
        super.onResume();
        long day = getDayNumberValue();
        if (day < 21) {
            dayNumberText.setText(WordUtils.capitalize(getString(R.string.home_title, EnglishNumberToWords.convert(day))));
        } else {
            dayNumberText.setText(WordUtils.capitalize(getString(R.string.home_title, "" + day)));
        }

        if (settings.getLastSession() != null) {
            analysisBtn.setVisibility(View.VISIBLE);
        }

        infoText.setText(R.string.home_info);
        if (settings.getLesson6VisitCount() == 1) {
            settings.setLesson6VisitCount(2);
            infoText.setText(R.string.home_monastery_finished);
        } else if (settings.getWellDoneVisitCount() == 1) {
            settings.setWellDoneVisitCount(2);
            infoText.setText(R.string.home_meditation_finished);
        } else if (settings.getAnalyticVisitCount() == 1) {
            settings.setAnalyticVisitCount(2);
            infoText.setText(R.string.home_analysis_finished);
        } else {
            Session session = settings.getLastSession();
            if (session != null) {
                long sessionLength = session.getSessionEndTime() - session.getSessionStartTime();
                List<Bin> bins = session.calculateNotingSpeed(sessionLength + 1);
                int seeInCount = session.getCompletedNotesCount(NoteType.SEE_IN);
                int hearInCount = session.getCompletedNotesCount(NoteType.HEAR_IN);
                int feelInCount = session.getCompletedNotesCount(NoteType.FEEL_IN);
                int seeOutCount = session.getCompletedNotesCount(NoteType.SEE_OUT);
                int hearOutCount = session.getCompletedNotesCount(NoteType.HEAR_OUT);
                int feelOutCount = session.getCompletedNotesCount(NoteType.FEEL_OUT);
                int sum = seeInCount + seeOutCount + feelInCount + feelOutCount + hearInCount + hearOutCount;
                if (bins.get(0).getValue() != 0 && sessionLength / bins.get(0).getValue() < 1000) {
                    infoText.setText(R.string.home_high_noting_speed);
                } else if ((feelInCount + feelOutCount) / (double) sum > 0.8) {
                    infoText.setText(R.string.home_feel_in_out_sensations);
                } else if ((seeInCount + hearInCount) / (double) sum > 0.8) {
                    infoText.setText(R.string.home_hear_in_see_in_sensations);
                } else {
                    String[] randomText = getResources().getStringArray(R.array.home_no_analytic_text);
                    int randomTextNumber = rand.nextInt(randomText.length);
                    infoText.setText(randomText[randomTextNumber]);
                }
            }
        }
    }

    private long getDayNumberValue() {
        long now = System.currentTimeMillis() / 24 / 60 / 60 / 1000;
        long firstStart = settings.getFirstLaunchDay();
        if (firstStart == -1) {
            settings.setFirstLaunchDay(now);
            return 1;
        } else {
            long delta = now - firstStart + 1;
            if (delta <= 0) {
                delta = 1;
            }
            return delta;
        }
    }
}


