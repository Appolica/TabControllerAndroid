package com.appolica.sample.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appolica.sample.R;
import com.appolica.sample.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ItemViewHolder> {

    private List<Class<? extends Activity>> data = new ArrayList<>();

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding binding;

        public ItemViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item, parent, false);

        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.binding.listItemTextView.setText(data.get(position).getSimpleName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();

                context.startActivity(new Intent(context, data.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<Class<? extends Activity>> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }
}
