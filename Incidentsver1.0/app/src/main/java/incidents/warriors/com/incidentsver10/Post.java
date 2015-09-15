package incidents.warriors.com.incidentsver10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Post extends ActionBarActivity implements View.OnClickListener {

    private EditText editTitle;
    private EditText editDesc;
private EditText editLocation;
    private EditText editContact;


    private Button buttonPost;




    private static final String POST_URL="http://incidents.site50.net/post.php";

    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";
    private int PICK_IMAGE_REQUEST = 1;


    private ImageView imageView;
    private Button buttonChoose;

    private Button viewImage;

    private Bitmap bitmap;

    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        editTitle =(EditText)findViewById(R.id.title);
        editDesc=(EditText)findViewById(R.id.desc);
        buttonPost =(Button)findViewById(R.id.post);

        buttonChoose =(Button)findViewById(R.id.buttonChoose);

        imageView = (ImageView)findViewById(R.id.imageView);

        editLocation = (EditText)findViewById(R.id.location);
        editContact =(EditText)findViewById(R.id.contact);





buttonChoose.setOnClickListener(this);
        buttonPost.setOnClickListener(this);







    }

    public void onClick(View v)
    {
         if(v== buttonPost)
            {
                postuser();
            }

        if(v==buttonChoose) {

        showFileChooser();
        }

        if(v==viewImage)
        {
            startActivity(new Intent(this, ViewImage.class));
        }

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    private void postuser()
    {
        String title = editTitle.getText().toString();
        String desc = editDesc.getText().toString();
        String location =editLocation.getText().toString();
        String contact =editContact.getText().toString();
String uploadImage =getStringImage(bitmap);

        post(title,desc,uploadImage,location,contact);
    }

    private void post(String title,String desc,String uploadImage,String location,String contact)
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
                data.put("image",params[2]);
                data.put("location",params[3]);
                data.put("contact",params[4]);

                String result = puc.sendPostRequest(POST_URL,data);
                return result;

            }
        }
        PostUser pu = new PostUser();
        pu.execute(title,desc,uploadImage,location,contact);


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
