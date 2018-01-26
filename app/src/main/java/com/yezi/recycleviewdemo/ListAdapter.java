package com.yezi.recycleviewdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ZQ on 2018/1/23.
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mStringList;
    private OnItemClickListener mOnItemClickListener;

    private int ITEM = 1;
    private int FOOTER = 2;

    interface OnItemClickListener{
        void onItemClick(View view,int pos);
    }

    public ListAdapter(List<String> list){
        mStringList = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == ITEM){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null);
            return new MyViewHolder(view);
        }else {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_footer,null);
             return new FootViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder)holder;
            myViewHolder.mTextView.setText(mStringList.get(position));
            if(mOnItemClickListener != null){
                myViewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(view,position);
                    }
                });
            }
        }


    }

    @Override
    public int getItemViewType(int position) {
       if(position + 1 == mStringList.size()){
           return FOOTER;
       }else {
           return  ITEM;
       }
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.title);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder{
        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }
}
