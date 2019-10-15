package aadl.com.buildin.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import aadl.com.buildin.R;
import aadl.com.buildin.listeners.OnRecyclerViewItemClick;
import aadl.com.buildin.models.GuardItem;
import aadl.com.buildin.viewholders.ClickableRecyclerViewHolder;

public class GuardItemAdapter extends RecyclerView.Adapter<GuardItemAdapter.GuardItemHolder> implements OnRecyclerViewItemClick {

    private Context mContext;
    private List<GuardItem> items;


    public class GuardItemHolder extends ClickableRecyclerViewHolder {
        public TextView name;
        public TextView contact;
        public TextView shift;


        public GuardItemHolder(@NonNull View itemView) {
            super(itemView, GuardItemAdapter.this);
            this.name = itemView.findViewById(R.id.name);
            this.contact = itemView.findViewById(R.id.phone);
            this.shift = itemView.findViewById(R.id.shift);

        }

        @Override
        protected int getItemPosition() {
            return this.getAdapterPosition();
        }
    }

    public GuardItemAdapter (Context context, List<GuardItem> items) {
        this.mContext = context;
        this.items = items;
    }


    @NonNull
    @Override
    public GuardItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_guard_item, viewGroup, false);

        return new GuardItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuardItemHolder GuardItemHolder, int i) {
        GuardItem item = items.get(i);

        GuardItemHolder.name.setText(item.getName());
        GuardItemHolder.contact.setText(item.getContact());
        GuardItemHolder.shift.setText(item.getShift());

    }

    @Override
    public void onClick(View view, int position) {
        GuardItem item = items.get(position);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + item.getContact().replace(" ","")));
        try {

            this.mContext.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(mContext, "Please give permission to call.", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }


}
