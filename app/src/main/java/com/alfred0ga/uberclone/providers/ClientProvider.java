package com.alfred0ga.uberclone.providers;

import com.alfred0ga.uberclone.models.Client;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ClientProvider {
    private DatabaseReference mReference;

    public ClientProvider(){
        mReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Clients");
    }

    public Task<Void> create(Client client) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", client.getName());
        map.put("email", client.getEmail());

        return mReference.child(client.getId()).setValue(map);
    }

    public Task<Void> update(Client client) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", client.getName());
        map.put("image", client.getmImage());

        return mReference.child(client.getId()).updateChildren(map);
    }

    public DatabaseReference getClient(String idClient) {
        return mReference.child(idClient);
    }

}
