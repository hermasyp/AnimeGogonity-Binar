package com.catnip.animecommunity.presentation.ui.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.catnip.animecommunity.BuildConfig
import com.catnip.animecommunity.base.BaseViewModelActivity
import com.catnip.animecommunity.base.wrapper.Resource
import com.catnip.animecommunity.databinding.ActivityAuthBinding
import com.catnip.animecommunity.presentation.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.koin.androidx.viewmodel.ext.android.viewModel


class AuthActivity :
    BaseViewModelActivity<ActivityAuthBinding, AuthViewModel>(ActivityAuthBinding::inflate) {

    private val TAG = AuthActivity::class.java.simpleName

    override val viewModel: AuthViewModel by viewModel()

    private val googleSignInClient: GoogleSignInClient by lazy{
        GoogleSignIn.getClient(
            this, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.FIREBASE_WEB_CLIENT_ID)
                .requestEmail()
                .build()
        )
    }

    // Google SSO with Firebase
    // Google Auth -> Token Session -> Register To Firebase -> Firebase User
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    account.idToken?.let { firebaseAuthWithGoogle(it) }
                } catch (e: ApiException) {
                    Log.w(TAG, "Google sign in failed", e)
                }
            }
        }

    override fun observeData() {
        viewModel.loginResult.observe(this) {
            when (it) {
                is Resource.Empty -> {
                    //nothing
                }
                is Resource.Error -> {
                    Toast.makeText(
                        this,
                        "Failed to Login : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    showLoadingState(false)
                }
                is Resource.Loading -> {
                    showLoadingState(true)
                }
                is Resource.Success -> {
                    showLoadingState(false)
                    if (it.payload?.second != null) {
                        navigateToHome()
                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun showLoadingState(isShow: Boolean) {
        binding.pbLogin.isVisible = isShow
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        viewModel.authenticateGoogleLogin(idToken)
    }

    override fun initView() {
        super.initView()
        supportActionBar?.hide()
        setLoginAction()
    }

    private fun setLoginAction() {
        binding.btnSignInGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }


}