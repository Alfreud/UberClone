package com.alfred0ga.uberclone.providers;

import com.alfred0ga.uberclone.models.Client;
import com.alfred0ga.uberclone.models.Driver;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DriverProvider {
    private DatabaseReference mReference;

    public DriverProvider(){
        mReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");
    }

    public Task<Void> create(Driver driver) {
        return mReference.child(driver.getId()).setValue(driver);
    }

    public DatabaseReference getDriver(String idDriver) {
        return mReference.child(idDriver);
    }

    public Task<Void> update(Driver driver) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", driver.getName());
        map.put("image", driver.getmImage());
        map.put("brand", driver.getBrand());
        map.put("plate", driver.getPlate());

        return mReference.child(driver.getId()).updateChildren(map);
    }
}
