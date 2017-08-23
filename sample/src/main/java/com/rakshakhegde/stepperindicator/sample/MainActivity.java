package com.rakshakhegde.stepperindicator.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.google.android.libraries.remixer.Remixer;
import com.google.android.libraries.remixer.annotation.RangeVariableMethod;
import com.google.android.libraries.remixer.annotation.RemixerBinder;
import com.google.android.libraries.remixer.storage.LocalStorage;
import com.google.android.libraries.remixer.ui.RemixerInitialization;
import com.google.android.libraries.remixer.ui.view.RemixerFragment;
import com.rakshakhegde.stepperindicator.StepperIndicator;

public class MainActivity extends AppCompatActivity {

    StepperIndicator indicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager pager = findViewById(R.id.pager);
        assert pager != null;
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        indicator = findViewById(R.id.stepper_indicator);
        // We keep last page for a "finishing" page
        indicator.setViewPager(pager, true);

        indicator.addOnStepClickListener(new StepperIndicator.OnStepClickListener() {
            @Override
            public void onStepClicked(int step) {
                pager.setCurrentItem(step, true);
            }
        });

        RemixerInitialization.initRemixer(getApplication());
        Remixer.getInstance().setSynchronizationMechanism(new LocalStorage(getApplicationContext()));

        RemixerBinder.bind(this);

        final FloatingActionButton remixerButton = findViewById(R.id.remixerButton);
        RemixerFragment.newInstance().attachToFab(this, remixerButton);
    }

    @RangeVariableMethod(minValue = 6, maxValue = 70, initialValue = 20)
    public void setLabelSize(Float fontSize) {
        indicator.setLabelSize(fontSize);
    }

}
