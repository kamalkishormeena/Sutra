package com.kamal.sutra;

import android.os.Bundle;

import com.kamal.sutra.domain.MeditationDay;
import com.kamal.sutra.domain.Meditations;

import java.util.Collections;
import java.util.List;

public class ProfileActivity extends ChartsBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupToolbar("");
        enableNavigation();
    }

    @Override
    public String getAssetsFileName() {
        return "other_analysis.html";
    }

    @Override
    public Object[] getData() {
        Meditations meditations = getSettings().getMeditations();
        List<Integer> daysNumberInRow = meditations.getMeditatingDaysNumberInRow(meditations.prepareChartData());
        int max = Collections.max(daysNumberInRow);
        int latest = daysNumberInRow.get(daysNumberInRow.size() - 1);
        return new Object[]{generateTextForMeditatingTime(), max, latest};
    }

    private String generateTextForMeditatingTime() {
        Meditations meditations = getSettings().getMeditations();
        List<MeditationDay> days = meditations.prepareChartData();
        StringBuilder dataBuilder = new StringBuilder();
        for (int i = 0, n = days.size(); i < n; i++) {
            dataBuilder.append("['");
            dataBuilder.append(days.get(i).getDate());
            dataBuilder.append("', ");
            dataBuilder.append(days.get(i).getLength() / 1000 / 60);
            if (i == n - 1) {
                dataBuilder.append("]\r\n");
            } else {
                dataBuilder.append("],\r\n");
            }
        }
        return dataBuilder.toString();
    }
}
