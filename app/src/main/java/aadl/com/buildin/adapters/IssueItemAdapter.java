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
import aadl.com.buildin.models.HomeItem;
import aadl.com.buildin.models.IssueItem;
import aadl.com.buildin.utils.Redirector;
import aadl.com.buildin.viewholders.ClickableRecyclerViewHolder;

public class IssueItemAdapter extends RecyclerView.Adapter<IssueItemAdapter.IssueItemHolder> implements OnRecyclerViewItemClick {

    private Context mContext;
    private List<IssueItem> items;


    public class IssueItemHolder extends ClickableRecyclerViewHolder {
        public TextView title;
        public TextView description;
        public ImageView image;
        public Button up;
        public Button down;


        public IssueItemHolder(@NonNull View itemView) {
            super(itemView, IssueItemAdapter.this);
            this.title = itemView.findViewById(R.id.title);
            this.description = itemView.findViewById(R.id.description);
            this.image = itemView.findViewById(R.id.card_image);
            this.up = itemView.findViewById(R.id.upvote);
            this.down = itemView.findViewById(R.id.downvote);

        }

        @Override
        protected int getItemPosition() {
            return this.getAdapterPosition();
        }
    }

    public IssueItemAdapter (Context context, List<IssueItem> items) {
        this.mContext = context;
        this.items = items;
    }


    @NonNull
    @Override
    public IssueItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_issue_item, viewGroup, false);

        return new IssueItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueItemHolder issueItemHolder, int i) {
        IssueItem item = items.get(i);

        issueItemHolder.title.setText(item.getTitle());
        issueItemHolder.description.setText(item.getDescription());

        byte[] imageByteArray = Base64.decode(item.getImage_data().get(0), Base64.DEFAULT);
        Glide.with(mContext).load(imageByteArray).into(issueItemHolder.image);

        issueItemHolder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (view).setBackground(mContext.getDrawable(R.drawable.up_c));
            }
        });

        issueItemHolder.down.setOnClickListener(new View.OnClickListener() {
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
