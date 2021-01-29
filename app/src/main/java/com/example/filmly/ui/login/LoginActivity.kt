package com.example.filmly.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.filmly.ui.MainActivity
import com.example.filmly.R
import com.example.filmly.ui.lostpassword.LostPasswordActivity
import com.example.filmly.ui.register.RegisterActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*

class LoginActivity : AppCompatActivity() {

    //private lateinit var loginViewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // [START initialize_fblogin]
        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()


        facebook.setOnClickListener { buttonFacebookLogin_init.performClick() }

        buttonFacebookLogin_init.setReadPermissions("email", "public_profile")
        buttonFacebookLogin_init.buttonFacebookLogin_init.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
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


        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        google.setOnClickListener {
            signInWithGoogle()
        }


        twitter.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            signIn(username.text.toString(), password.text.toString())
            loading.visibility = View.GONE
        }

        newAccount.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        lost_password.setOnClickListener {
            val intent = Intent(this@LoginActivity, LostPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    // [START sign_in_with_email]

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Log.i(TAG, "Nenhum usuario conectado.")
        }
    }


    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }


        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Email ou senha invalidos.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)

                }


            }

    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        if (TextUtils.isEmpty(email.text.toString())) {
            valid = false
            email.error = "Campo vazio"
        }

        if (TextUtils.isEmpty(password.text.toString())) {
            valid = false
            password.error = "Campo vazio"
        }


        return valid
    }
    // [END sign_in_with_email]

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
        private const val TAG = "EmailPassword"
        private const val RC_SIGN_IN = 9001
    }





}



