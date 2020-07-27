package com.alfred0ga.uberclone.providers;

import com.alfred0ga.uberclone.models.ClientBooking;
import com.alfred0ga.uberclone.models.HistoryBooking;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class HistoryBookingProvider {
    private DatabaseReference mReference;

    public HistoryBookingProvider() {
        mReference = FirebaseDatabase.getInstance().getReference().child("HistoryBooking");
    }

    public Task<Void> create(HistoryBooking historyBooking) {
        return mReference.child(historyBooking.getIdHistoryBooking()).setValue(historyBooking);
    }

    public Task<Void> updateCalificationClient(String idHistoryBooking, float calificationClient) {
        Map<String, Object> map = new HashMap<>();
        map.put("calificationClient", calificationClient);
        return mReference.child(idHistoryBooking).updateChildren(map);
    }

    public Task<Void> updateCalificationDriver(String idHistoryBooking, float calificationDriver) {
        Map<String, Object> map = new HashMap<>();
        map.put("calificationDriver", calificationDriver);
        return mReference.child(idHistoryBooking).updateChildren(map);
    }

    public DatabaseReference getHistoryBooking(String idHistoryBooking) {
        return mReference.child(idHistoryBooking);
    }
}
