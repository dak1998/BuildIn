package aadl.com.buildin.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import aadl.com.buildin.listeners.OnRecyclerViewItemClick;

public abstract class ClickableRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnRecyclerViewItemClick listener;

    public ClickableRecyclerViewHolder(@NonNull View itemView, OnRecyclerViewItemClick listener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        this.listener.onClick(this.itemView, getItemPosition());
    }

    protected abstract int getItemPosition();
}
