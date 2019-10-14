package aadl.com.buildin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import aadl.com.buildin.R;
import aadl.com.buildin.listeners.OnRecyclerViewItemClick;
import aadl.com.buildin.models.EventItem;
import aadl.com.buildin.models.HomeItem;
import aadl.com.buildin.models.IssueItem;
import aadl.com.buildin.utils.Redirector;
import aadl.com.buildin.viewholders.ClickableRecyclerViewHolder;

public class EventItemAdapter extends RecyclerView.Adapter<EventItemAdapter.EventItemHolder> implements OnRecyclerViewItemClick {

    private Context mContext;
    private List<EventItem> items;


    public class EventItemHolder extends ClickableRecyclerViewHolder {
        public TextView title;
        public TextView description;
        public TextView date;
        public Button up;
        public Button down;


        public EventItemHolder(@NonNull View itemView) {
            super(itemView, EventItemAdapter.this);
            this.title = itemView.findViewById(R.id.title);
            this.description = itemView.findViewById(R.id.description);
            this.date = itemView.findViewById(R.id.date);
            this.up = itemView.findViewById(R.id.upvote);
            this.down = itemView.findViewById(R.id.downvote);

        }

        @Override
        protected int getItemPosition() {
            return this.getAdapterPosition();
        }
    }

    public EventItemAdapter (Context context, List<EventItem> items) {
        this.mContext = context;
        this.items = items;
    }


    @NonNull
    @Override
    public EventItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_event_item, viewGroup, false);

        return new EventItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventItemHolder eventItemHolder, int i) {
        EventItem item = items.get(i);

        eventItemHolder.title.setText(item.getTitle());
        eventItemHolder.description.setText(item.getDescription());
        eventItemHolder.date.setText(item.getDate());

//        byte[] imageByteArray = Base64.decode(item.getImage_data().get(0), Base64.DEFAULT);
//        Glide.with(mContext).load(imageByteArray).into(issueItemHolder.image);

        eventItemHolder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (view).setBackground(mContext.getDrawable(R.drawable.up_c));
            }
        });

        eventItemHolder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (view).setBackground(mContext.getDrawable(R.drawable.down_c));
            }
        });


    }

    @Override
    public void onClick(View view, int position) {
//        HomeItem item = items.get(position);

//        Redirector.from(mContext).to(item.getOnClickClass()).withAnimation().go();
    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }


}
