package com.example.googlemaps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.example.models.ResponseSearch;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;


public class AutoSuggestAdapter extends ArrayAdapter<ResponseSearch> implements Filterable {

    private ArrayList<ResponseSearch> mListData, mTempItem, mSuggestion;
    Activity context;

    public AutoSuggestAdapter(Activity context, int resource, int textViewResourceId, ArrayList<ResponseSearch> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        mListData = items;
        mListData = new ArrayList<>();
        mSuggestion = new ArrayList<ResponseSearch>();
    }
    public void setData(ArrayList<ResponseSearch> list){
        mListData.clear();
        mListData.addAll(list);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Nullable
    @Override
    public ResponseSearch getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        View view = convertView;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_search_auto,parent,false);
        }
        ResponseSearch search = mListData.get(position);
        if (search != null){
            TextView locationName = view.findViewById(R.id.text1);
            if (locationName != null)
                locationName.setText(search.getLocalizedName() + "," + search.getCountry().getLocalizedName());

        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String local = ((ResponseSearch) resultValue).getLocalizedName();
            return local;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null){
                mSuggestion.clear();
                for (ResponseSearch search : mTempItem){
                    String name = search.getLocalizedName();
                    if (name.toLowerCase().contains(constraint.toString().toLowerCase())){
                        mSuggestion.add(search);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mSuggestion;
                filterResults.count = mSuggestion.size();
                return filterResults;
            }else {

                return new FilterResults();
            }

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            try{
                ArrayList<ResponseSearch> responseSearches = (ArrayList<ResponseSearch>) results.values;
                if (results != null && results.count > 0){
                    clear();
                    for (ResponseSearch search : responseSearches){
                        add(search);
                        notifyDataSetChanged();
                    }
                }

            }catch (ConcurrentModificationException e){

            }
        }
    };
}
