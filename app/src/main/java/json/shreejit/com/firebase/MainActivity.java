package json.shreejit.com.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    Button signin;

    private int myrequestcode = 1000;
    FirebaseAuth auth;

    GoogleSignInClient msignin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        signin = (Button) findViewById(R.id.btnsign);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = msignin.getSignInIntent();

                startActivityForResult(signin,myrequestcode);
            }
        });

        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        msignin = GoogleSignIn.getClient(this, gso);


    }
    @Override
    protected  void onStart(){
        super.onStart();
        FirebaseUser currentuser=auth.getCurrentUser();
        if(currentuser != null)
        {
            Intent profile= new Intent(MainActivity.this, Profile.class);
            startActivity(profile);
        }

    }

    public void onActivityResult(int requestcode, int resultcode, Intent data)

    {
        super.onActivityResult(requestcode, resultcode, data);
        if(requestcode==myrequestcode){
            Task<GoogleSignInAccount> Task= GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account= Task.getResult(ApiException.class);
                FirebaseSignin(account);
            }
            catch (ApiException e){
                Toast.makeText(this,"Could not sign in", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void FirebaseSignin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user=auth.getCurrentUser();


                    Intent goo=new Intent(MainActivity.this,Profile.class);

                    startActivity(goo);

                    Toast.makeText(MainActivity.this, "SignIn Successful",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
