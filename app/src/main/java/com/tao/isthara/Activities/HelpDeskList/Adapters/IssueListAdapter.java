package com.tao.isthara.Activities.HelpDeskList.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.tao.isthara.Model.Issue_list_item;
import com.tao.isthara.R;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class IssueListAdapter extends RecyclerView.Adapter<IssueListAdapter.ListViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    private List<Issue_list_item> issueList;

    public IssueListAdapter(Context mCtx, List<Issue_list_item> iList) {
        this.mCtx = mCtx;
        this.issueList = iList;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_issue_list_item, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Issue_list_item issues = issueList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(issues.getTitle());
        holder.textViewCategory.setText(issues.getCategory()+" - "+issues.getSubcategory());
        holder.textViewTicket.setText(String.valueOf(issues.getTicketNo()));
        holder.textViewDate.setText(String.valueOf(issues.getDate()));
        holder.textViewPropertyRoomBed.setText(String.valueOf(issues.getBedname()));



    }


    @Override
    public int getItemCount() {
        return issueList.size();
    }


    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewCategory, textViewTicket, textViewDate, textViewPropertyRoomBed;

        public ListViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewTicket = itemView.findViewById(R.id.textViewTicketNo);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewPropertyRoomBed = itemView.findViewById(R.id.textViewPropertyRoomBed);
        }
    }
}