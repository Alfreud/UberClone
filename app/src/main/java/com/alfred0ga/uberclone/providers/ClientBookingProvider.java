package com.alfred0ga.uberclone.providers;

import com.alfred0ga.uberclone.models.ClientBooking;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ClientBookingProvider {
    private DatabaseReference mReference;

    public ClientBookingProvider() {
        mReference = FirebaseDatabase.getInstance().getReference().child("ClientBooking");
    }

    public Task<Void> create(ClientBooking clientBooking) {
        return mReference.child(clientBooking.getIdClient()).setValue(clientBooking);
    }

    public Task<Void> updateStatus(String idClientBooking, String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        return mReference.child(idClientBooking).updateChildren(map);
    }

    public Task<Void> updateIdHistoryBooking(String idClientBooking) {
        String idPush = mReference.push().getKey();
        Map<String, Object> map = new HashMap<>();
        map.put("idHistoryBooking", idPush);
        return mReference.child(idClientBooking).updateChildren(map);
    }

    public DatabaseReference getStatus(String idClientBookig) {
        return mReference.child(idClientBookig).child("status");
    }

    public DatabaseReference getClientBooking(String idClientBookig) {
        return mReference.child(idClientBookig);
    }

    public Task<Void> delete(String idClientBooking) {
        return mReference.child(idClientBooking).removeValue();
    }
}
