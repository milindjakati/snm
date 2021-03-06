package incidents.warriors.com.incidentsver10;



        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.AsyncTask;
        import android.support.v7.app.ActionBarActivity;

        import android.os.Bundle;
        import android.util.Base64;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.io.IOException;
        import java.net.MalformedURLException;
        import java.net.URL;

public class ViewImage extends ActionBarActivity {


    private ImageView imageView;
    private TextView ttitle,tdesc,tcontact,tlocation;

    private PostUserClass pu;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewimage);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String id = extras.getString("id");
        String title = extras.getString("title");
        String desc = extras.getString("desc");
        String location = extras.getString("location");
        String contact = extras.getString("contact");


        ttitle=(TextView)findViewById(R.id.dtitle);
        tdesc=(TextView)findViewById(R.id.ddesc);
        tcontact=(TextView)findViewById(R.id.dcontact);
        tlocation=(TextView)findViewById(R.id.dlocation);

        ttitle.setText(title);
        tdesc.setText(desc);
        tcontact.setText(contact);
        tlocation.setText(location);






            getImage(id);


        imageView = (ImageView) findViewById(R.id.imageViewShow);

        pu = new PostUserClass();


    }




    private void getImage(String id) {

        class GetImage extends AsyncTask<String,Void,Bitmap>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewImage.this, "Loading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                loading.dismiss();
                imageView.setImageBitmap(b);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String id = params[0];
                String add = "http://incidents.site50.net/download.php?id="+id;
                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }
        }

        GetImage gi = new GetImage();
        gi.execute(id);
    }
}
