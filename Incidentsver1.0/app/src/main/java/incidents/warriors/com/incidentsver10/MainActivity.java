package incidents.warriors.com.incidentsver10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by sarveshpalav on 14/09/15.
 */
public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button postincident;
    private Button listincident;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postincident =(Button)findViewById(R.id.postincident);
        listincident =(Button)findViewById(R.id.listincident);

        postincident.setOnClickListener(this);
        listincident.setOnClickListener(this);




    }
public void onClick(View v)
{
    if(v==postincident){
        Intent post = new Intent(MainActivity.this,Post.class);
        startActivity(post);
    }
    if(v==listincident)
    {
        Intent list = new Intent(MainActivity.this,ListActivity.class);
        startActivity(list);
    }
}
}
