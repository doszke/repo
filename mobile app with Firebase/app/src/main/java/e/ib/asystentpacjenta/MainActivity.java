package e.ib.asystentpacjenta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import e.ib.asystentpacjenta.businesstier.entities.Hint;
import e.ib.asystentpacjenta.businesstier.service.Fetcher;
import e.ib.asystentpacjenta.businesstier.service.HintDAO;

import static android.content.ContentValues.TAG;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private List<Hint> hints = new ArrayList<>();
    private int selected = 0;


    private TextView text_info;
    private EditText text_token;
    private Spinner hintSpinner;

    private ArrayAdapter<String> adapter;
    private List<Integer> position = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        int resp = getIntent().getIntExtra("resp", -2);

        if (resp == 1) {
            Toast.makeText(getApplicationContext(), "Dodano wskazówkę", Toast.LENGTH_SHORT).show();
        } else if (resp == 2) {
            Toast.makeText(getApplicationContext(), "Zaedytowano wskazówkę", Toast.LENGTH_SHORT).show();
        } else if (resp == 3) {
            Toast.makeText(getApplicationContext(), "Usunięto wskazówkę", Toast.LENGTH_SHORT).show();
        } else if (resp == -1) {
            Toast.makeText(getApplicationContext(), "Coś poszło nie tak... Spróbuj ponownie", Toast.LENGTH_SHORT).show();
        }


        HintDAO.fetchAll(getApplicationContext()).addOnCompleteListener(new Fetcher(hints, position, adapter));

        text_info = findViewById(R.id.text_info);
        text_token = findViewById(R.id.token_info);
        hintSpinner = findViewById(R.id.searchList);


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);

        hintSpinner.setAdapter(adapter);
        hintSpinner.setOnItemSelectedListener(this);


        text_token.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.clear();
                position.clear();
                Log.d("DEBUG", text_token.getText().toString());
                String inputs = text_token.getText().toString().toLowerCase();


                /*
                Wyszukiwarka ma na celu przesortowanie wszystkich wskazówek
                i wybranie tylko tych, w których występują wszystkie wpisane
                przez użytkownika słowa.
                 */
                String[] words = inputs.trim().split(" ");
                List<String> help = new ArrayList<>();
                List<Integer> helpPos = new ArrayList<>();
                boolean isFirst = true;

                //każde słowo wyszukane osobno
                for (String input : words) {
                    List<String> actual = new ArrayList<>();
                    List<Integer> actualPos = new ArrayList<>();

                    Pattern pattern = Pattern.compile(input.trim());
                    Matcher matcher;
                    for (int i = 0; i < hints.size(); i++) {
                        Hint h = hints.get(i);
                        matcher = pattern.matcher(h.getToken().toLowerCase());
                        if (matcher.find()) { //szukam wystąpienia
                            if (isFirst) { //pierwszy obrót dodaje wszystko, by potem to przesiewać
                                actual.add(h.getToken());
                                actualPos.add(i);
                            } else { //każdy kolejny doda tylko wtedy, kiedy dana wskazówka została wybrana we wcześniejszym obrocie
                                if (help.contains(h.getToken())) {
                                    actual.add(h.getToken());
                                    actualPos.add(i);
                                }
                            }
                        }
                    }
                    isFirst = false; //każdy kolejny obrót nie jest pierwszym
                    help = actual; //ustawiam pomocnicze listy
                    helpPos = actualPos;
                }
                //ustawiam główne listy
                adapter.addAll(help);
                position = helpPos;
            }

            @Override
            public void afterTextChanged(Editable s) {
                hintSpinner.setSelection(0);
                if (!position.isEmpty()) {
                    text_info.setText(hints.get(position.get(0)).getInfo());
                }
            }
        });

        //init spinnera na start
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = this.position.get(position);
        text_info.setText(hints.get(selected).getInfo());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        text_info.setText("");
    }

    //btn listeners
    public void onClickAdd(View view) {
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("mode", R.string.am_btn_add);
        startActivity(intent);
    }

    public void onClickEdit(View view) {
        Hint h = hints.get(position.get(selected));
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("mode", R.string.am_btn_modify);
        intent.putExtra("getId", h.getId());
        intent.putExtra("getToken", h.getToken());
        intent.putExtra("getInfo", h.getInfo());
        //intent.putExtra("selected", selected);
        startActivity(intent);
    }

    public void onClickDelete(View view) {
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("mode", R.string.am_btn_delete);
        Log.d("sometag2", String.valueOf(hints.get(position.get((int) hintSpinner.getSelectedItemId())).getId()));
        intent.putExtra("getId", hints.get(position.get((int) hintSpinner.getSelectedItemId())).getId());
        //intent.putExtra("selected", selected);
        startActivity(intent);
    }


}
