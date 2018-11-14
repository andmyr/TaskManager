package com.example.homeuser.taskmanager.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.homeuser.taskmanager.R;
import com.example.homeuser.taskmanager.models.Mechanizm;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class RvSection extends StatelessSection {
    //List<String> itemList = Arrays.asList("Item1", "Item2", "Item3");

    private final String title;
    private List<? extends Mechanizm> itemList;

    public RvSection(String title, List<? extends Mechanizm> itemList) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.section_item)
                .headerResourceId(R.layout.section_header)
                .build());
        this.title = title;
        this.itemList = itemList;
    }

    @Override
    public int getContentItemsTotal() {
        return itemList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;
        itemHolder.tvItem.setText(itemList.get(position).getName());
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
        headerHolder.tvTitle.setText(title);
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvHeader);
        }
    }

    class MyItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvItem;

        MyItemViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.textView);
        }
    }
}