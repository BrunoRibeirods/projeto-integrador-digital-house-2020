package com.example.filmly.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import com.example.filmly.R
import com.example.filmly.ui.MainActivity
import com.example.filmly.ui.login.LoginActivity
import com.example.filmly.ui.login.LoginResult
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        // [START initialize_fblogin]
        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()


        re_facebook.setOnClickListener { buttonFacebookLogin.performClick() }

        buttonFacebookLogin.setReadPermissions("email", "public_profile")
        buttonFacebookLogin.buttonFacebookLogin.registerCallback(callbackManager, object : FacebookCallback<com.facebook.login.LoginResult> {
            override fun onSuccess(loginResult: com.facebook.login.LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        })
        // [END initialize_fblogin]



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        val create = findViewById<Button>(R.id.btn_register)
        val google = findViewById<ImageView>(R.id.re_google)

        create.setOnClickListener {
            createAccount(re_username.text.toString(), re_password.text.toString())
        }

        google.setOnClickListener {
            signInWithGoogle()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Email ou senha invalidos.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

            }
        // [END create_user_with_email]
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = findViewById<EditText>(R.id.re_username)
        val password = findViewById<EditText>(R.id.re_password)
        val passwordConfirm = findViewById<EditText>(R.id.re_password_confirm)
        val name = findViewById<EditText>(R.id.re_name)


        if (TextUtils.isEmpty(email.text.toString())) {
            valid = false
            email.error = "Campo vazio"
        }else if(TextUtils.isEmpty(name.text.toString())){
            valid = false
            name.error = "Campo vazio"
        } else if (TextUtils.isEmpty(password.text.toString())) {
            valid = false
            password.error = "Campo vazio"
        }else if(TextUtils.isEmpty(passwordConfirm.text.toString())){
            valid = false
            passwordConfirm.error = "Campo vazio"
        } else if(password.text.toString() != passwordConfirm.text.toString()){
            valid = false
            passwordConfirm.error = "Senhas não batem"
        }


        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Log.i(TAG, "Nenhum usuario conectado.")
        }
    }

    //[START] Google login configuration
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    private fun firebaseAuthWithGoogle(idToken: String) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                updateUI(null)
            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
    //[END] Google login configuration


    //[START] Facebook login configuration

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

            }
    }

    companion object {
        private const val TAG = "EmailPasswordRegister"
        private const val RC_SIGN_IN = 9001
    }
}