package incidents.warriors.com.incidentsver10;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Post extends ActionBarActivity implements View.OnClickListener {

    private EditText editTitle;
    private EditText editDesc;

    private Button buttonPost;

    private static final String POST_URL="http://incidents.esy.es/post.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        editTitle =(EditText)findViewById(R.id.title);
        editDesc=(EditText)findViewById(R.id.desc);
        buttonPost =(Button)findViewById(R.id.post);

        buttonPost.setOnClickListener(this);




    }

    public void onClick(View v)
    {
         if(v== buttonPost)
            {
                postuser();
            }
    }

    private void postuser()
    {
        String title = editTitle.getText().toString();
        String desc = editDesc.getText().toString();


        post(title,desc);
    }

    private void post(String title,String desc)
    {
        class PostUser extends AsyncTask<String,Void,String>
        {
            ProgressDialog loading;
            PostUserClass puc = new PostUserClass();

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading =ProgressDialog.show(Post.this,"Wait A Sec...",null,true,true);

            }

            @Override

            protected void onPostExecute(String s)
            {

                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override

            protected String doInBackground(String ... params)
            {
                HashMap<String,String> data = new HashMap<String,String>();
                data.put("title",params[0]);
                data.put("desc",params[1]);

                String result = puc.sendPostRequest(POST_URL,data);
                return result;

            }
        }
        PostUser pu = new PostUser();
        pu.execute(title,desc);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
