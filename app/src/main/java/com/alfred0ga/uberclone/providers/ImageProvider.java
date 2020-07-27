package com.alfred0ga.uberclone.providers;

import android.content.Context;

import com.alfred0ga.uberclone.utils.CompressorImageBitmap;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class ImageProvider {
    private StorageReference mStorage;

    public ImageProvider(String ref) {
        mStorage = FirebaseStorage.getInstance().getReference().child(ref);
    }

    public UploadTask saveImage(Context context, File image, String idUser) {
        byte[] imageByte = CompressorImageBitmap.getImage(context, image.getPath(), 500, 500);
        StorageReference storage = mStorage.child(idUser + ".jpg");
        mStorage = storage;
        UploadTask uploadTask = storage.putBytes(imageByte);
        return uploadTask;
    }

    public StorageReference getStorage() {
        return mStorage;
    }
}
