package com.doodhbhandaar.deliverydoodhbhandaar;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseInstanse {
    private static FirebaseDatabase INSTANCE;
    public static FirebaseDatabase getDatabaseInstance() {

        if (INSTANCE == null) {
            INSTANCE = FirebaseDatabase.getInstance();
            INSTANCE.setPersistenceEnabled(true);
        }
        return INSTANCE;
    }
}
