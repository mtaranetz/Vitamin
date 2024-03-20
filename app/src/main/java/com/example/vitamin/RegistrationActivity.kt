package com.example.vitamin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.vitamin.databinding.ActivityRegistrationBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    lateinit var launcher: ActivityResultLauncher<Intent>
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        auth = Firebase.auth
        checkAccount()
        setContentView(binding.root)


        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null){
                firebaseAuthWithGoogle(account.idToken!!)
            }
        } catch (e: ApiException) {Log.d("MyLog", "API")}}
            binding.bSign.setOnClickListener(){
                signWithGoogle()
            }
        }


    private fun getClient(): GoogleSignInClient
    {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, gso)
    }

    private fun signWithGoogle(){
        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(Token: String){
        val credential = GoogleAuthProvider.getCredential(Token, null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                Log.d("MyLog", "Done")
                checkAccount()
            }
            else{ Log.d("MyLog", "Error")}
        }

    }

    private fun checkAccount()
    {
        if (auth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}