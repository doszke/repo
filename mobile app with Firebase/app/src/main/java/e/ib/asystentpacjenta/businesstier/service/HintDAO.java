package e.ib.asystentpacjenta.businesstier.service;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import e.ib.asystentpacjenta.businesstier.entities.Hint;

import static android.content.ContentValues.TAG;

public class HintDAO{

    private static final DocumentReference ref = FirebaseFirestore.getInstance().collection("asystentpacjenta").document("hints");



    public static Task<DocumentSnapshot> add(Context context, Hint h) {
        ref.update(String.valueOf(h.getId()), h);
        return ref.get();
    }


    public static Task<DocumentSnapshot> remove(Context context, Hint h) {
        Map<String, Object> m = new TreeMap<>();
        m.put(String.valueOf(h.getId()), FieldValue.delete());
        ref.update(m);
        return ref.get();
    }


    public static Task<DocumentSnapshot> modify(Context context, Hint oh, Hint nh) {
        Map<String, Object> m = new TreeMap<>();
        nh.setId(oh.getId());
        m.put(String.valueOf(oh.getId()), nh);
        ref.update(m);
        return ref.get();
    }


    public static Task<DocumentSnapshot> fetchAll(Context context) {
        return ref.get();
    }


}
