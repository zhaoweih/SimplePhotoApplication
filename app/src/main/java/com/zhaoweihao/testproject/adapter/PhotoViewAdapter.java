package com.zhaoweihao.testproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.GsonUtils;
import com.bumptech.glide.Glide;
import com.zhaoweihao.testproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片的View适配器
 * @author zhaoweihao
 */
public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.RecyclerHolder> {

    private static final String TAG = "PhotoViewAdapter";

    //上下文
    private Context mContext;

    //图片List
    private List<String> dataList = new ArrayList<>();

    public PhotoViewAdapter(RecyclerView recyclerView) {
        this.mContext = recyclerView.getContext();
    }

    //设置图片数据
    public void setData(List<String> dataList) {

        Log.d(TAG, GsonUtils.toJson(dataList));

        if (null != dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);

            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.picture_item, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        Log.d(TAG, dataList.get(position));
        //将图片设置到Imageview
        Glide.with(mContext).load(dataList.get(position)).into(holder.photoImageViewItem);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        ImageView photoImageViewItem;

        private RecyclerHolder(View itemView) {
            super(itemView);
            photoImageViewItem = itemView.findViewById(R.id.iv_item);
        }
    }
}