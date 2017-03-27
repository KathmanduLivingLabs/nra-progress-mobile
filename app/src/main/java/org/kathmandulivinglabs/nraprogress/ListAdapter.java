package org.kathmandulivinglabs.nraprogress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListAdapter extends BaseAdapter implements Filterable{

    private Context mContext;
    private NameFilter nameFilter;
    private int displayStyle = 2;

    private LayoutInflater mLayoutInflater;

    private ArrayList<ListItem> mEntries = new ArrayList<>();
    private ArrayList<ListItem> fetchedData = new ArrayList<>();


    public ListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        LinearLayout itemView;
            if (convertView == null){
                itemView = (LinearLayout) mLayoutInflater.inflate(
                        R.layout.list_item, parent, false);
                return renderTextview(itemView, position);
            }
            else {
                return renderTextview((LinearLayout) convertView, position);
            }
    }


    private LinearLayout renderTextview(LinearLayout itemView, int position){

        TextView titleText = (TextView)
                itemView.findViewById(R.id.listTitle);
        TextView detailTextOne = (TextView)
                itemView.findViewById(R.id.listDetailOne);
        TextView detailTextTwo = (TextView)
                itemView.findViewById(R.id.listDetailTwo);
        TextView detailTextThree = (TextView)
                itemView.findViewById(R.id.listDetailThree);
        TextView detailTextfour = (TextView)
                itemView.findViewById(R.id.listDetailFour);
        HorizontalBarChart chart = (HorizontalBarChart) itemView.findViewById(R.id.barchart);



        ListItem currrentItem = mEntries.get(position);
        int tbf = currrentItem.getBeneficiariesTotal();
        int bfs = currrentItem.getBeneficiariesSurveyed();
        int cc = currrentItem.getConstructionComplete();
        int cip = currrentItem.getConstructionInProgress();
        int cns = currrentItem.getConstructionNotStarted();
        int sia = currrentItem.getInstallmentApplied();
        int sina = currrentItem.getInstallmentNotApplied();
        int gr = currrentItem.getGrantReceived();
        int gnr = currrentItem.getGrantNotRecieved();


        String detailTotalBeneficiaries = "Total Beneficiaries: " + String.valueOf(tbf);
        String detailSurveyCompleted = "Beneficiaries Surveyed: " + String.valueOf(bfs);
        String detailConstructionComplete = "Construction Complete: " + String.valueOf(cc);
        String detailConstructionInProgress = "Construction In Progress: " + String.valueOf(cip);
        String detailConstructionNotStarted = "Construction Not Started: " + String.valueOf(cns);
        String detailsSecondInstallmentApplied = "Second Installment Applied: " + String.valueOf(sia);
        String detailsSecondInstallmentNotApplied = "Second Installment Not Applied: " + String.valueOf(sina);
        String detailGrantRecived = "Grant Recived: " + String.valueOf(gr);
        String detailGrantNotRecived = "Grant Not Recived: " + String.valueOf(gnr);


        String title = mEntries.get(position).getName();
        sortEntries(displayStyle);
        titleText.setText(title);



        switch (displayStyle) {
            case 1:
                detailTextOne.setText(detailTotalBeneficiaries);
                detailTextTwo.setText(detailSurveyCompleted + getPercentageString(tbf, bfs));
                detailTextThree.setVisibility(View.GONE);
                detailTextfour.setVisibility(View.GONE);
                List<BarEntry> entries_sp = new ArrayList<>();
                entries_sp.add(new BarEntry(0f, new float[] {bfs, tbf-bfs}));
                BarDataSet set_sp = new BarDataSet(entries_sp,"");
                set_sp.setStackLabels(new String[]{"Completed", "Remaining"});
                set_sp.setColors(new int[]{ColorTemplate.rgb("#47ce8f"),ColorTemplate.rgb("#ce4b47")});
                BarData data_sp = new BarData(set_sp);
                chart.setData(data_sp);
                break;
            case 2:
                detailTextOne.setText(detailSurveyCompleted);
                detailTextTwo.setText(detailConstructionComplete + getPercentageString(bfs,cc));
                detailTextThree.setVisibility(View.VISIBLE);
                detailTextThree.setText(detailConstructionInProgress + getPercentageString(bfs,cip));
                detailTextfour.setVisibility(View.VISIBLE);
                detailTextfour.setText(detailConstructionNotStarted + getPercentageString(bfs,cns));
                List<BarEntry> entries_cc = new ArrayList<>();
                entries_cc.add(new BarEntry(0f, new float[] {(float)cc, (float)cip, (float)cns}));
                BarDataSet set_cc = new BarDataSet(entries_cc,"");
                set_cc.setStackLabels(new String[]{"Completed", "In Progress", "Not Started"});
                set_cc.setColors(new int[]{ColorTemplate.rgb("#47ce8f"),ColorTemplate.rgb("#4786ce"),ColorTemplate.rgb("#ce4b47")});
                BarData data_cc = new BarData(set_cc);
                chart.setData(data_cc);
                break;
            case 3:
                detailTextOne.setText(detailSurveyCompleted);
                detailTextTwo.setText(detailsSecondInstallmentApplied + getPercentageString(bfs,sia));
                detailTextThree.setVisibility(View.VISIBLE);
                detailTextThree.setText(detailsSecondInstallmentNotApplied + getPercentageString(bfs,sina));
                detailTextfour.setVisibility(View.GONE);
                List<BarEntry> entries_ia = new ArrayList<>();
                entries_ia.add(new BarEntry(0f, new float[] {sia, sina}));
                BarDataSet set_ia = new BarDataSet(entries_ia,"");
                set_ia.setStackLabels(new String[]{"Applied", "Not Applied"});
                set_ia.setColors(new int[]{ColorTemplate.rgb("#47ce8f"),ColorTemplate.rgb("#ce4b47")});
                BarData data_ia = new BarData(set_ia);
                chart.setData(data_ia);
                break;
            case 4:
                detailTextOne.setText(detailSurveyCompleted);
                detailTextTwo.setText(detailGrantRecived + getPercentageString(bfs, gr));
                detailTextThree.setVisibility(View.VISIBLE);
                detailTextThree.setText(detailGrantNotRecived + getPercentageString(bfs, gnr));
                detailTextfour.setVisibility(View.GONE);
                List<BarEntry> entries_gr = new ArrayList<>();
                entries_gr.add(new BarEntry(0f, new float[] {gr,gnr}));
                BarDataSet set_gr = new BarDataSet(entries_gr,"");
                set_gr.setStackLabels(new String[]{"Received", "Not Recieved"});
                set_gr.setColors(new int[]{ColorTemplate.rgb("#47ce8f"),ColorTemplate.rgb("#ce4b47")});
                BarData data_gr = new BarData(set_gr);
                chart.setData(data_gr);
                break;
            default:
                detailTextOne.setText(detailTotalBeneficiaries);
                detailTextTwo.setText(detailSurveyCompleted);
                detailTextThree.setVisibility(View.GONE);
                detailTextfour.setVisibility(View.GONE);
        }

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setEnabled(false);
        chart.setPinchZoom(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawValueAboveBar(false);
        chart.animateY(300);
        chart.getData().setHighlightEnabled(false);

        chart.invalidate();

        return itemView;

    }


    public String getPercentageString(int total, int number){
        DecimalFormat df = new DecimalFormat("##.##");
        if (total == 0 || number == 0){
            return " (0%)";
        }else {
            return " (" + String.valueOf(df.format(((float)number/(float)total) * 100)) + "%)";
        }

    }

    public void upDateEntries(ArrayList<ListItem> entries) {
        mEntries = entries;
        fetchedData = entries;
        notifyDataSetChanged();
    }

    public void clearEntries(){
        mEntries.clear();
        notifyDataSetChanged();
    }

    public void sortEntries(final int sortcase){
        Collections.sort(mEntries, new Comparator<ListItem>() {
            int sortStyle = sortcase;
            @Override
            public int compare(ListItem item1, ListItem item2)
            {
                switch (sortcase){
                    case 1:
                        return  item2.getBeneficiariesSurveyed() - item1.getBeneficiariesSurveyed();
                    case 2:
                        return item2.getConstructionComplete() - item1.getConstructionComplete();
                    case 3:
                        return item2.getInstallmentApplied() - item1.getInstallmentApplied();
                    case 4:
                        return item2.getGrantReceived() - item1.getGrantReceived();
                    default:
                        return item2.getBeneficiariesTotal() - item1.getBeneficiariesTotal();
                }
            }
        });

    }

    public void setDisplay(int style){
        displayStyle = style;
        sortEntries(style);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if(nameFilter == null) {
            nameFilter = new NameFilter();
        }
        return nameFilter;
    }

    private class NameFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<ListItem> filterList=new ArrayList<ListItem>();
                for(int i=0;i<fetchedData.size();i++){
                    if((fetchedData.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        ListItem item = fetchedData.get(i);
                        filterList.add(item);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=fetchedData.size();
                results.values=fetchedData;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mEntries =(ArrayList<ListItem>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}


