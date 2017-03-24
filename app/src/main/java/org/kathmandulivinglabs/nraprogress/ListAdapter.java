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

import java.util.ArrayList;

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
        if (convertView == null) {
                itemView = (LinearLayout) mLayoutInflater.inflate(
                        R.layout.list_item, parent, false);
        } else {
            itemView = (LinearLayout) convertView;
        }

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

        String detailTotalBeneficiaries = "Total Beneficiaries: " + String.valueOf(mEntries.get(position).getBeneficiariesTotal());
        String detailSurveyCompleted = "Surveys Completed: " + String.valueOf(mEntries.get(position).getBeneficiariesSurveyed());
        String detailConstructionComplete = "Construction Complete: " + String.valueOf(mEntries.get(position).getConstructionComplete());
        String detailConstructionInProgress = "Construction In Progress: " + String.valueOf(mEntries.get(position).getConstructionInProgress());
        String detailConstructionNotStarted = "Construction Not Started: " + String.valueOf(mEntries.get(position).getConstructionNotStarted());
        String detailsSecondInstallmentApplied = "Second Installment Applied: " + String.valueOf(mEntries.get(position).getInstallmentApplied());
        String detailsSecondInstallmentNotApplied = "Second Installment Not Applied: " + String.valueOf(mEntries.get(position).getInstallmentNotApplied());
        String detailGrantRecived = "Grant Recived: " + String.valueOf(mEntries.get(position).getGrantReceived());
        String detailGrantNotRecived = "Grant Not Recived: " + String.valueOf(mEntries.get(position).getGrantNotRecieved());


        String title = mEntries.get(position).getName();
        titleText.setText(title);
        switch (displayStyle) {
            case 1:
                detailTextOne.setText(detailTotalBeneficiaries);
                detailTextTwo.setText(detailSurveyCompleted);
                detailTextThree.setVisibility(View.GONE);
                detailTextfour.setVisibility(View.GONE);
                break;
            case 2:
                detailTextOne.setText(detailSurveyCompleted);
                detailTextTwo.setText(detailConstructionComplete);
                detailTextThree.setVisibility(View.VISIBLE);
                detailTextThree.setText(detailConstructionInProgress);
                detailTextfour.setVisibility(View.VISIBLE);
                detailTextfour.setText(detailConstructionNotStarted);
                break;
            case 3:
                detailTextOne.setText(detailSurveyCompleted);
                detailTextTwo.setText(detailsSecondInstallmentApplied);
                detailTextThree.setVisibility(View.VISIBLE);
                detailTextThree.setText(detailsSecondInstallmentNotApplied);
                detailTextfour.setVisibility(View.GONE);
                break;
            case 4:
                detailTextOne.setText(detailSurveyCompleted);
                detailTextTwo.setText(detailGrantRecived);
                detailTextThree.setVisibility(View.VISIBLE);
                detailTextThree.setText(detailGrantNotRecived);
                detailTextfour.setVisibility(View.GONE);
                break;
            default:
                detailTextOne.setText(detailTotalBeneficiaries);
                detailTextTwo.setText(detailSurveyCompleted);
                detailTextThree.setVisibility(View.GONE);
                detailTextfour.setVisibility(View.GONE);
        }


        return itemView;
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

    public void setDisplay(int style){
        displayStyle = style;
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


