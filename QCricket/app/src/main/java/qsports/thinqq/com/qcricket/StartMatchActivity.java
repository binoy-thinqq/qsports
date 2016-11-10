package qsports.thinqq.com.qcricket;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class StartMatchActivity extends ActionBarActivity {

    List<String> matchList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_match);

        AutoCompleteTextView matchName = (AutoCompleteTextView)findViewById(R.id.autoCmpMatchName);
        matchName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Item Selected", Toast.LENGTH_LONG);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),"Nothing Selected", Toast.LENGTH_LONG);
            }
        });

        matchList = new ArrayList<>();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,android.R.id.text1,matchList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                String str = matchList.get(position);

                text1.setText(str);
                text2.setText("--");

                if(str != null && !str.isEmpty()){
                    String[] split = str.split("##");
                    if(split != null && split.length > 1){
                        text1.setText(split[0]);
                        text2.setText(split[1]);
                    }
                }
                return view;
            }
        };
        matchName.setAdapter(stringArrayAdapter);


        //TODO: move this to service call
        matchList.add("Weekend warriors vs Chennai rocks team ##  Friday, March 20, 2015");
        matchList.add("Mumbai Indians vs Kolkata Knight Riders ## Friday, March 20, 2015");
        matchList.add("Kinngs Eleven warriors vs Delhi Daredevils ## Friday, March 20, 2015");
        matchList.add("Rajasthan Royals vs Kochin team ## Friday, March 20, 2015");
        matchList.add("Sunrisers Hyderabad  vs warriors team ## Friday, March 20, 2015");
        matchList.add("Chennai Super Kings  warriors vs Bangalore team ## Friday, March 20, 2015");
        stringArrayAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
