package com.example.huntergo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.huntergo.CRUD.ArmaCRUD;
import com.example.huntergo.CRUD.ArmaduraCRUD;
import com.example.huntergo.CRUD.ConsumivelCRUD;
import com.example.huntergo.CRUD.InventarioCRUD;
import com.example.huntergo.CRUD.ItensEquipadorsCRUD;
import com.example.huntergo.CRUD.JogadorCRUD;
import com.example.huntergo.CRUD.MonstroCRUD;
import com.example.huntergo.Classes.Consumivel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9000;
    private ArmaCRUD armaCRUD;
    private ArmaduraCRUD armaduraCRUD;
    private ConsumivelCRUD consumivelCRUDCRUD;
    private InventarioCRUD inventarioCRUD;
    private ItensEquipadorsCRUD itensEquipadorsCRUD;
    private JogadorCRUD jogadorCRUD;
    private MonstroCRUD monstroCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        armaCRUD = ArmaCRUD.getINSTANCE();
        armaduraCRUD = ArmaduraCRUD.getINSTANCE();
        consumivelCRUDCRUD = ConsumivelCRUD.getINSTANCE();
        inventarioCRUD = InventarioCRUD.getINSTANCE();
        itensEquipadorsCRUD = ItensEquipadorsCRUD.getINSTANCE();
        jogadorCRUD = JogadorCRUD.getINSTANCE();
        monstroCRUD = MonstroCRUD.getINSTANCE();

        Log.d("teste", "OnCreate");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        Log.d("teste", "gso");
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        Log.d("teste", "mauth");
    }

    public void onClickLogin(View view){
        Log.d("teste", "Click");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("teste", "result");
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Erro", "Google sign in failed", e);
                // ...
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser, false);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("Firebase", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Firebase", "signInWithCredential:success");
                            boolean novoUsuario;

                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                novoUsuario = true;
                            } else{
                                novoUsuario= false;
                            }

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user, novoUsuario);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Erro", "signInWithCredential:failure", task.getException());
                            updateUI(null, false);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser, boolean novo) {
        if (currentUser != null) {

            Log.d("UPDATE_USER>\n", "UID: " + currentUser.getUid() +
                    "\nProviderId: " + currentUser.getProviderId() +
                    "\nName: " + currentUser.getDisplayName() +
                    "\nEmail: " + currentUser.getEmail());
            jogadorCRUD.IniciarListeners(FirebaseAuth.getInstance().getCurrentUser().getUid());
            itensEquipadorsCRUD.IniciarListeners(FirebaseAuth.getInstance().getCurrentUser().getUid());

            if(novo){
                startActivity(new Intent(this, ClasseActivity.class));
            } else {
                startActivity(new Intent(this, MapsActivity.class));
            }
        } else{
            mGoogleSignInClient.signOut();
        }
    }


}
