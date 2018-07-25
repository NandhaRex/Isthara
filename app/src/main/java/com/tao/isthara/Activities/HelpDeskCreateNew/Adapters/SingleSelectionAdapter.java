package com.tao.isthara.Activities.HelpDeskCreateNew.Adapters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.tao.isthara.Model.ItemModel;
import com.tao.isthara.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class SingleSelectionAdapter extends RecyclerView.Adapter {

    private List itemModels;
    private static Context context;
    private Activity mActivity;
    private int lastCheckedPosition = -1;

    public SingleSelectionAdapter(Context context, List itemModels, Activity activity) {
        this.itemModels = itemModels;
        this.context = context;
        this.mActivity = activity;
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_single, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemModel model = (ItemModel) itemModels.get(position);
        initializeViews(model, holder, position);
    }


    private void initializeViews(final ItemModel model, final RecyclerView.ViewHolder holder, final int position) {
        ((ItemViewHolder)holder).name.setText(model.getName());
        if (model.getId() == lastCheckedPosition){
            ((ItemViewHolder)holder).radioButton.setChecked(true);
        }else{
            ((ItemViewHolder)holder).radioButton.setChecked(false);
        }

        ((ItemViewHolder)holder).radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = position;
                notifyItemRangeChanged(0, itemModels.size());

                selectedItem();

            }
        });

        ((ItemViewHolder)holder).row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = position;
                notifyItemRangeChanged(0, itemModels.size());

                selectedItem();

            }
        });

    }

    private void selectedItem(){


        ItemModel itemModel = getSelectedItem();
        int selectedId = itemModel.getId();
        String selectedName = itemModel.getName();

        Intent intent = new Intent();
        intent.putExtra("SELECTION_ID", selectedId);
        intent.putExtra("SELECTION_NAME", selectedName);
        mActivity.setResult(RESULT_OK, intent);
        mActivity.finish();
    }

    public ItemModel getSelectedItem(){
        ItemModel model = (ItemModel) itemModels.get(lastCheckedPosition);
        return model;
    }
    public int selectedPosition(){
        return lastCheckedPosition;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {


        TextView name;
        RadioButton radioButton;
        RelativeLayout row;

        public ItemViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            //ButterKnife.bind(this, itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            radioButton =(RadioButton) itemView.findViewById(R.id.radio);

            row =(RelativeLayout) itemView.findViewById(R.id.row);

        }
    }
}