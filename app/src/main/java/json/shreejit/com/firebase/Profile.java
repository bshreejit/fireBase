package json.shreejit.com.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    ImageView userimg;
    TextView username;
    TextView usermail;
    Button signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userimg= (ImageView) findViewById(R.id.userimg);
        username= (TextView) findViewById(R.id.username);
        usermail= (TextView) findViewById(R.id.usermail);
        signout=findViewById(R.id.btnlog);


        username.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        usermail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        userimg.setImageURI(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());

        Picasso.with(getBaseContext()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                .into(userimg);


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });



    }
}
