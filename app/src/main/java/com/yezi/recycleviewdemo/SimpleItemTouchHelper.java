package com.yezi.recycleviewdemo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by ZQ on 2018/1/25.
 */

public class SimpleItemTouchHelper extends ItemTouchHelper.Callback {

    private ListAdapter mListAdapter;
    private List<String> mData;
    public SimpleItemTouchHelper(ListAdapter adapter,List<String> data) {
       mData = data;
       mListAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
        return  makeMovementFlags(dragFlag,swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
       int from = viewHolder.getAdapterPosition();
       int to = target.getAdapterPosition();
       mListAdapter.notifyItemMoved(from,to);
       Collections.swap(mData,from,to);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        mData.remove(pos);
        mListAdapter.notifyItemRemoved(pos);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            ListAdapter.MyViewHolder holder = (ListAdapter.MyViewHolder)viewHolder;
            holder.itemView.setBackgroundColor(0xffbcbcbc); //设置拖拽和侧滑时的背景色
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        ListAdapter.MyViewHolder holder = (ListAdapter.MyViewHolder)viewHolder;
        holder.itemView.setBackgroundColor(0xffeeeeee); //背景色还原
    }
}
