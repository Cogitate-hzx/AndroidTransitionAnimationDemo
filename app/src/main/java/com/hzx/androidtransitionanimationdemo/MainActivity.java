package com.hzx.androidtransitionanimationdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuizActivityWithTransition();
            }
        });
    }

    private void startQuizActivityWithTransition() {
        View translationView = findViewById(R.id.transitionView);
        // 1
        final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(this, false,
                new Pair<>(translationView, getString(R.string.transitionName)));
        // 2
        @SuppressWarnings("unchecked")
        final Bundle transitionBundle = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, pairs)
                .toBundle();

        Intent intent = new Intent(this, Main2Activity.class);
        // 3
        ActivityCompat.startActivity(this,
                intent,
                transitionBundle);
    }
}
