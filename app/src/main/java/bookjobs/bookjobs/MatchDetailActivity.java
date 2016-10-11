package bookjobs.bookjobs;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Aliasgar on 9/10/16.
 */
public class MatchDetailActivity extends AppCompatActivity {


    public Match match;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_match);

        Intent intent = getIntent();
        match = (Match) intent.getSerializableExtra("match");


        init();

    }

    private void init() {
        //TODO initilaize all views from match object
    }
}
