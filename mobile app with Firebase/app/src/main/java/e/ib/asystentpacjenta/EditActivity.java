package e.ib.asystentpacjenta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import e.ib.asystentpacjenta.businesstier.entities.Hint;
import e.ib.asystentpacjenta.businesstier.service.Fetcher;
import e.ib.asystentpacjenta.businesstier.service.HintDAO;

public class EditActivity extends Activity {

    private class SecondFetcher extends Fetcher {

        public SecondFetcher(List<Hint> list, List<Integer> position, ArrayAdapter<String> adapter) {
            super(list, position, adapter);
        }

        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            super.onComplete(task);
            id = getIntent().getLongExtra("getId", -1);
            selected = searchHint(id);
            mode = getIntent().getIntExtra("mode", -2);
            mode_tv.setText(getString(R.string.em_tv_mode) + " " + getString(mode));

            if(mode != R.string.am_btn_add) {
                if(selected != null) {
                    chosen_tv.setText(selected.getToken());
                } else {
                    i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("resp", -1);
                    finish();
                    startActivity(i);
                }
            } else chosen_tv.setText("-");

            if(mode == R.string.am_btn_modify){
                new_token.setText( getIntent().getStringExtra("getToken") );
                new_info.setText( getIntent().getStringExtra("getInfo") );
            }
        }
    }


    private Intent i;

    private final OnCompleteListener<DocumentSnapshot> RETURN_OK = task -> {
        this.finish();
        startActivity(i);
    };

    private final OnFailureListener RETURN_NOT_OK = task -> {
        this.finish();
        startActivity(i);
    };

    private EditText new_token;
    private EditText new_info;
    private TextView mode_tv;
    private TextView chosen_tv;

    private List<Hint> hints = new ArrayList<>();

    private Hint selected;

    private int mode;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        new_info = (EditText) findViewById(R.id.new_info);
        new_token = (EditText) findViewById(R.id.new_token);
        mode_tv = (TextView) findViewById(R.id.mode_tv);
        chosen_tv = (TextView) findViewById(R.id.chosen_tv);

        HintDAO.fetchAll(getApplicationContext()).addOnCompleteListener(new SecondFetcher(hints, new ArrayList<>(), null));

    }


    public void onClickExec(View view) {
        i = new Intent(getApplicationContext(), MainActivity.class);

        if(R.string.am_btn_add == mode){
            Hint h = new Hint();
            h.setId(hints.get(hints.size()-1).getId() + 1);
            h.setToken(new_token.getText().toString());
            h.setInfo(new_info.getText().toString());
            h.setModified(new Date().toString());
            HintDAO.add(getApplicationContext(), h).addOnCompleteListener(RETURN_OK).addOnFailureListener(RETURN_NOT_OK);
            HintDAO.fetchAll(getApplicationContext()).addOnCompleteListener(new Fetcher(hints, new ArrayList<>(), null));

            i.putExtra("resp", 1);
        } else if(R.string.am_btn_modify == mode) {
            Hint nh = new Hint();
            nh.setId(id);
            nh.setInfo(new_info.getText().toString());
            nh.setToken(new_token.getText().toString());
            HintDAO.modify(getApplicationContext(), selected, nh).addOnCompleteListener(RETURN_OK).addOnFailureListener(RETURN_NOT_OK);
            i.putExtra("resp", 2);
        } else if (R.string.am_btn_delete == mode) {
            Hint h = new Hint();
            h.setId(selected.getId());
            HintDAO.remove(getApplicationContext(), h).addOnCompleteListener(RETURN_OK).addOnFailureListener(RETURN_NOT_OK);
            i.putExtra("resp", 3);
        } else {
            Log.d("XDDDDDDDDDDDDDDDDDDDDDDDDDDDd", String.valueOf(-1));
            i.putExtra("resp", -1);
        }

    }

    private Hint searchHint(long id){
        Hint h = new Hint();
        h.setId(id);
        Log.d("INPUTTTTTTT", String.valueOf(id));
        for(Hint hint : hints){
            Log.d("HINTTTTTTTTTTTTTTTTTTT", String.valueOf(hint.getId()));
            if(hint.equals(h)){
                return hint;
            }
        }
        return null;
    }

}
