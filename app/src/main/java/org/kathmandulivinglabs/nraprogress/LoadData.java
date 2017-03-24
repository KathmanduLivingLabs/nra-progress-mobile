package org.kathmandulivinglabs.nraprogress;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by nirab on 3/19/17.
 */

public class LoadData extends AsyncTask<String, Void, ArrayList<ListItem>> {

    private final String mUrl = "http://139.59.227.15:8848/api/v1/mis/region/records?ns=true&district=";

    private final ListAdapter mAdapter;

    public LoadData(ListAdapter adapter) {
        mAdapter = adapter;
    }

    private InputStream retrieveStream(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = null;
        httpGet = new HttpGet(url);

        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(httpGet);
            HttpEntity getResponseEntity = httpResponse.getEntity();
            return getResponseEntity.getContent();
        } catch (IOException e) {
            httpGet.abort();
        }
        return null;
    }

    @Override
    protected ArrayList<ListItem> doInBackground(String... strings) {

        ArrayList<ListItem> list = new ArrayList<ListItem>();
        InputStream source = retrieveStream(mUrl+strings[0]);
        BufferedReader r = new BufferedReader(new InputStreamReader(source));
        StringBuilder responseString = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                responseString.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(String.valueOf(responseString));
            Log.wtf("Data",jsonObj.toString());
            JSONObject stats = jsonObj.getJSONObject("stats");
            JSONObject survey_status = stats.getJSONObject("survey_status");
            int obft = survey_status.getInt("beneficiaries");
            int obfs = survey_status.getInt("surveys");
            JSONObject construction_status = stats.getJSONObject("construction_status");
            int occ = construction_status.getInt("Completed");
            int ocip = construction_status.getInt("In Progress");
            int ocns = construction_status.getInt("Not Started");
            JSONObject grant_status = stats.getJSONObject("grant_status");
            int ogr = grant_status.getInt("Received");
            int ognr = grant_status.getInt("Not Received");
            JSONObject installment_status = stats.getJSONObject("installment_status");
            int oia = installment_status.getInt("Applied");
            int oina = installment_status.getInt("Not Applied");
            ListItem overallItem = new ListItem(99999,"Overall",obft,obfs,occ,ocip,ocns,ogr,ognr,oia,oina);
            list.add(overallItem);

            JSONObject numericalStats = jsonObj.getJSONObject("numericalStats");
            Iterator<String> keys= numericalStats.keys();
            while (keys.hasNext())
            {
                String keyValue = (String)keys.next();
                String[] splitKeyValue = keyValue.split("[$]");
                String name = splitKeyValue[0].substring(0, 1).toUpperCase() + splitKeyValue[0].substring(1);
                int code = Integer.parseInt(splitKeyValue[1]);
                JSONObject districtStats = numericalStats.getJSONObject(keyValue);
                JSONObject beneficiaries = districtStats.getJSONObject("beneficiaries");
                int bft = beneficiaries.getInt("total");
                int bfs = beneficiaries.getInt("surveyed");
                JSONObject construction = districtStats.getJSONObject("construction");
                int cc = construction.getInt("completed");
                int cip = construction.getInt("inprogress");
                int cns = construction.getInt("not_started");
                JSONObject grant = districtStats.getJSONObject("grant");
                int gr = grant.getInt("received");
                int gnr = grant.getInt("not_received");
                JSONObject second_installment = districtStats.getJSONObject("second_installment");
                int ia = second_installment.getInt("applied");
                int ina = second_installment.getInt("not_applied");
                ListItem item = new ListItem(code,name,bft,bfs,cc,cip,cns,gr,gnr,ia,ina);
                list.add(item);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    protected void onPostExecute(ArrayList<ListItem> entries) {
        mAdapter.upDateEntries(entries);
    }
}

