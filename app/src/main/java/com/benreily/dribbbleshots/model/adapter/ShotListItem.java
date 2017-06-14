package com.benreily.zzzonked.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.benreily.zzzonked.R;
import com.benreily.zzzonked.model.pojo.DribbbleShotModel;
import com.benreily.zzzonked.model.pojo.ImagesModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShotListItem extends AbstractItem<ShotListItem, ShotListItem.ViewHolder> {

    private String title;
    private String description;
    private ImagesModel images;


    public ShotListItem(DribbbleShotModel shotModel) {
        this.title = shotModel.getTitle();
        this.description = shotModel.getDescriptionNoHtml();
        this.images = shotModel.getImages();
    }


    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.item_shot_root_layout;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_dribbble_shot;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);

        holder.tvTitle.setText(title);

        if (description.length() > 0) {
            holder.tvDescription.setText(description);
        }

        WeakReference<Context> contextWeakReference = new WeakReference<>(holder.itemView.getContext());
        Context context = contextWeakReference.get();

        if (images.getHidpi() != null) {
            Glide.with(context)
                    .load(images.getHidpi())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.shotImage);
        } else {
            Glide.with(context)
                    .load(images.getNormal())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.shotImage);
        }

        context = null;

    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.tvTitle.setText(null);
        holder.tvDescription.setText(null);
        holder.shotImage.setImageDrawable(null);
        Glide.clear(holder.shotImage);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_description)
        TextView tvDescription;

        @BindView(R.id.iv_shot)
        ImageView shotImage;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
