package org.kathmandulivinglabs.nraprogress;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    private ListAdapter adapter;
    EditText inputSearch;
    TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        emptyText = (TextView) getListView().getEmptyView();


        adapter = new ListAdapter(this);
        setListAdapter(adapter);

/*        ArrayList<ListItem> dummyData = new ArrayList<>();
        String[] districts = {"Dolakha","Nuwakot","Sindhupalchowk","Gorkha","Dhading"};
        for(int i=0;i < districts.length; i++){
            ListItem dummyItem = new ListItem(9999,districts[i],5000,2000,100,100,100,123,345,543,789);
            dummyData.add(dummyItem);
        }
        adapter.upDateEntries(dummyData);*/



        if(isNetworkAvailable()) {
            LoadData dataLoader = new LoadData(adapter);
            dataLoader.execute("");
        } else {
            emptyText.setText("No Internet Connection, Connect to Internet and Restart the App");
        }


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ListItem selectedItem = (ListItem) getListView().getItemAtPosition(position);
        int code = selectedItem.getCode();
        if (code < 100){
            adapter.clearEntries();
            if(isNetworkAvailable()) {
                LoadData dataLoader = new LoadData(adapter);
                dataLoader.execute(String.valueOf(code));
            } else {
                emptyText.setText("No Internet Connection, Connect to Internet and Restart the App");
            }

        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_display_options, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_survey:
                adapter.setDisplay(1);
                return true;
            case R.id.menu_constrution:
                adapter.setDisplay(2);
                return true;
            case R.id.menu_installment:
                adapter.setDisplay(3);
                return true;
            case R.id.menu_grant:
                adapter.setDisplay(4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
