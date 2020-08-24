package e.ib.asystentpacjenta.businesstier.service;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import e.ib.asystentpacjenta.businesstier.entities.Hint;

import static android.content.ContentValues.TAG;

public class Fetcher implements OnCompleteListener<DocumentSnapshot> {

    private final List<Hint> hints;
    private final List<Integer> position;
    private final ArrayAdapter<String> adapter;

    public Fetcher(List<Hint> list, List<Integer> position, ArrayAdapter<String> adapter) {
        this.hints = list;
        this.position = position;
        this.adapter = adapter;
    }

    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        hints.clear();
        if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            System.out.println(document);
            if (document.exists()) {
                Map<String, Object> map = document.getData();
                for (String key : map.keySet()) {
                    Map<String, Object> hintMap = (HashMap<String, Object>) map.get(key);
                    Hint h = new Hint();
                    h.setId((Long) hintMap.get("id"));
                    h.setToken((String) hintMap.get("token"));
                    h.setModified((String) hintMap.get("modified"));
                    h.setInfo((String) hintMap.get("info"));
                    hints.add(h);
                }

            }
        } else {
            Log.d(TAG, "get failed with ", task.getException());
        }
        hints.sort(new Hint.Comparator());
        List<String> tokens = new ArrayList<>(hints.size());
        int x = 0;
        for (Hint h : hints) {
            position.add(Math.toIntExact(x++));
            tokens.add(h.getToken());
        }

        if(adapter != null)
            adapter.addAll(tokens);
    }
}
