package aadl.com.buildin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import aadl.com.buildin.R;
import aadl.com.buildin.listeners.OnRecyclerViewItemClick;
import aadl.com.buildin.models.HomeItem;
import aadl.com.buildin.utils.Redirector;
import aadl.com.buildin.viewholders.ClickableRecyclerViewHolder;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.HomeItemHolder> implements OnRecyclerViewItemClick {

    private Context mContext;
    private List<HomeItem> items;


    public class HomeItemHolder extends ClickableRecyclerViewHolder {
        public TextView title;
        public ImageView image;

        public HomeItemHolder(@NonNull View itemView) {
            super(itemView, HomeItemAdapter.this);
            this.title = itemView.findViewById(R.id.title);
            this.image = itemView.findViewById(R.id.home_item_card_image);

        }

        @Override
        protected int getItemPosition() {
            return this.getAdapterPosition();
        }
    }

    public HomeItemAdapter (Context context, List<HomeItem> items) {
        this.mContext = context;
        this.items = items;
    }


    @NonNull
    @Override
    public HomeItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_home_item, viewGroup, false);

        return new HomeItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemHolder homeItemHolder, int i) {
        HomeItem item = items.get(i);

        homeItemHolder.title.setText(item.getTitle());

        Glide.with(mContext).load(item.getThumbnail()).into(homeItemHolder.image);


    }

    @Override
    public void onClick(View view, int position) {
        HomeItem item = items.get(position);

        Redirector.from(mContext).to(item.getOnClickClass()).withAnimation().go();
    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }


}
