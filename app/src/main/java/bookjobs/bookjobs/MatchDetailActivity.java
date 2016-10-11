package bookjobs.bookjobs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Aliasgar on 9/10/16.
 */
public class MatchDetailActivity extends AppCompatActivity {


    public Match match;
    public ImageView cover;
    public TextView requester_name, requester_address, requester_location, requester_distance, requester_contact, requester_bio;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_match);

        Intent intent = getIntent();
        match = (Match) intent.getSerializableExtra("match");
        cover = (ImageView) findViewById(R.id.cover);
        requester_name = (TextView) findViewById(R.id.requester_name);
        requester_address = (TextView) findViewById(R.id.requester_address);
        requester_location = (TextView) findViewById(R.id.requester_map);
        requester_distance = (TextView) findViewById(R.id.distance);
        requester_contact = (TextView) findViewById(R.id.requester_contact);
        requester_bio = (TextView) findViewById(R.id.requester_bio);

        requester_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setType("plain/text");
                sendIntent.setData(Uri.parse(match.getUserWants().getEmail()));
                sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { match.getUserWants().getEmail() });
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "[BookJob] Book Owner Response");
                startActivity(sendIntent);
            }
        });
        requester_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creates an Intent that will load a map
                Uri gmmIntentUri = Uri.parse("geo:"+match.getUserWants().getLocation().getLatitude()+","+match.getUserWants().getLocation().getLatitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        init();

    }

    private void init() {
        //TODO init cover image from server/ db


        requester_name.setText(match.getUserWants().getName());
        requester_address.setText(match.getUserWants().getAddress());
        requester_distance.setText(""+match.getDistance());
        requester_bio.setText(match.getUserWants().getBio());


    }
}
