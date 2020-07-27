package com.alfred0ga.uberclone.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alfred0ga.uberclone.R;
import com.alfred0ga.uberclone.activities.client.HistoryBookingDetailClientActivity;
import com.alfred0ga.uberclone.activities.driver.HistoryBookingDetailDriverActivity;
import com.alfred0ga.uberclone.models.HistoryBooking;
import com.alfred0ga.uberclone.providers.ClientProvider;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HistoryBookingDriverAdapter extends FirebaseRecyclerAdapter<HistoryBooking, HistoryBookingDriverAdapter.ViewHolder> {
    private ClientProvider mClientProvider;
    private Context mContext;

    public HistoryBookingDriverAdapter(@NonNull FirebaseRecyclerOptions<HistoryBooking> options, Context context) {
        super(options);
        mClientProvider = new ClientProvider();
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull HistoryBooking model) {
        final String id = getRef(position).getKey();

        holder.mTvOrigin.setText(model.getOrigin());
        holder.mTvDestination.setText(model.getDestination());
        holder.mTvCalification.setText(String.valueOf(model.getCalificationDriver()));
        mClientProvider.getClient(model.getIdDriver()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    holder.mTvName.setText(name);
                    if (snapshot.hasChild("image")) {
                        String image = snapshot.child("name").getValue().toString();
                        Picasso.get().load(image).into(holder.mIvHistoryBooking);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HistoryBookingDetailDriverActivity.class);
                intent.putExtra("idHistoryBooking", id);
                mContext.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_history_booking, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvOrigin;
        private TextView mTvDestination;
        private TextView mTvCalification;
        private ImageView mIvHistoryBooking;
        private View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            mTvName = itemView.findViewById(R.id.tv_name);
            mTvOrigin = itemView.findViewById(R.id.tv_origin);
            mTvDestination = itemView.findViewById(R.id.tv_destination);
            mTvCalification = itemView.findViewById(R.id.tv_calification);
            mIvHistoryBooking = itemView.findViewById(R.id.iv_historyBooking);
        }
    }
}
