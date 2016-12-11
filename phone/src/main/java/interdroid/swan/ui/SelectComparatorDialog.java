package interdroid.swan.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import interdroid.swan.R;
import interdroid.swancore.swansong.Comparator;

public class SelectComparatorDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);

        setContentView(R.layout.expression_builder_select_typed_value_dialog);

        ((ListView) findViewById(R.id.typed_value_list))
                .setAdapter(new ArrayAdapter<Comparator>(this,
                        android.R.layout.simple_list_item_1, Comparator
                        .values()));

        ((ListView) findViewById(R.id.typed_value_list))
                .setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent result = new Intent();
                        result.putExtra("Comparator",
                                Comparator.values()[position].toParseString());
                        setResult(RESULT_OK, result);
                        finish();
                    }
                });
    }
}
