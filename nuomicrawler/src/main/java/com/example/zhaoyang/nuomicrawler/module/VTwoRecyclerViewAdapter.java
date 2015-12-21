package com.example.zhaoyang.nuomicrawler.module;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhaoyang.nuomicrawler.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhaoyang on 16-8-21.
 */

public class VTwoRecyclerViewAdapter extends RecyclerView.Adapter<VTwoRecyclerViewAdapter.VTwoRecyclerViewHolder> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<VTwoData> mContents;

    public VTwoRecyclerViewAdapter(Context mCtx, List<VTwoData> contents) {
        mLayoutInflater = LayoutInflater.from(mCtx);
        mContents = (ArrayList<VTwoData>) contents;
    }

    @Override
    public VTwoRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VTwoRecyclerViewHolder(mLayoutInflater.inflate(R.layout.item_vtwo_recyclerview, null));
    }

    @Override
    public void onBindViewHolder(VTwoRecyclerViewHolder holder, int position) {
        holder.tv_content.setText(mContents.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    public static class VTwoRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content;

        public VTwoRecyclerViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
