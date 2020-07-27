package com.alfred0ga.uberclone.activities.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alfred0ga.uberclone.R;
import com.alfred0ga.uberclone.models.HistoryBooking;
import com.alfred0ga.uberclone.providers.ClientProvider;
import com.alfred0ga.uberclone.providers.DriverProvider;
import com.alfred0ga.uberclone.providers.HistoryBookingProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryBookingDetailDriverActivity extends AppCompatActivity {
    private TextView mTvName;
    private TextView mTvOrigin;
    private TextView mTvDestination;
    private TextView mTvYourCalification;

    private RatingBar mRatingBar;
    private CircleImageView mCircleImage;
    private CircleImageView mCircleImageBack;

    private String mExtraId;
    private HistoryBookingProvider mHistoryBookingProvider;
    private ClientProvider mClientProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_booking_detail_driver);

        mTvName = findViewById(R.id.tv_nameBookingDetail);
        mTvOrigin = findViewById(R.id.tv_originHistoryBookingDetail);
        mTvDestination = findViewById(R.id.tv_destinationHistoryBookingDetail);
        mTvYourCalification = findViewById(R.id.tv_calificationHistoryBookingDetail);

        mRatingBar = findViewById(R.id.rb_HistoryBookingDetail);
        mCircleImage = findViewById(R.id.civ_historyBookingDetail);
        mCircleImageBack = findViewById(R.id.civ_back);

        mClientProvider = new ClientProvider();
        mExtraId = getIntent().getStringExtra("idHistoryBooking");
        mHistoryBookingProvider = new HistoryBookingProvider();
        getHistoryBooking();

        mCircleImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getHistoryBooking() {
        mHistoryBookingProvider.getHistoryBooking(mExtraId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HistoryBooking historyBooking = snapshot.getValue(HistoryBooking.class);
                    mTvOrigin.setText(historyBooking.getOrigin());
                    mTvDestination.setText(historyBooking.getDestination());
                    mTvYourCalification.setText("Tu calificación: " + historyBooking.getCalificationDriver());

                    if (snapshot.hasChild("calificationClient")) {
                        mRatingBar.setRating((float) historyBooking.getCalificationClient());
                    }

                    mClientProvider.getClient(historyBooking.getIdClient()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String name = snapshot.child("name").getValue().toString();
                                mTvName.setText(name.toUpperCase());
                                if (snapshot.hasChild("image")) {
                                    String image = snapshot.child("image").getValue().toString();
                                    Picasso.get().load(image).into(mCircleImage);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}