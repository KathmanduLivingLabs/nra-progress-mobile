package org.kathmandulivinglabs.nraprogress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by nirab on 3/26/17.
 */

public class DetailsActivity extends AppCompatActivity {

    private TextView editTextData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateils);
        editTextData = (TextView) findViewById(R.id.details_data);
    }
}
