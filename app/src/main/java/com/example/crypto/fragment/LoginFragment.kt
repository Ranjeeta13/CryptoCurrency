package com.example.crypto.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.crypto.R
import com.example.crypto.databinding.FragmentLoginBinding
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.googleBtn.setOnClickListener {
            Log.d(TAG, "Google Button Clicked")
            signInWithGoogle()
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            Log.d(TAG, "User already logged in, navigating...")
            navigateToHome()
        }
    }

    private fun signInWithGoogle() {
        val credentialManager = CredentialManager.create(requireContext())

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(getString(R.string.default_web_client_id))
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = requireContext(),
                )
                
                val credential = result.credential
                Log.d(TAG, "Credential type: ${credential.type}")

                when {
                    credential is GoogleIdTokenCredential -> {
                        Log.d(TAG, "Received GoogleIdTokenCredential")
                        firebaseAuthWithGoogle(credential.idToken)
                    }
                    credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
                        Log.d(TAG, "Received wrapped CustomCredential")
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                    }
                    else -> {
                        Log.e(TAG, "Unexpected credential type: ${credential.type}")
                        Toast.makeText(context, "Unexpected login type", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Google sign-in failed", e)
                Toast.makeText(context, "Sign-In Failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Firebase Auth with Google Successful")
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                    navigateToHome()
                } else {
                    Log.w(TAG, "Firebase Auth failed", task.exception)
                    Toast.makeText(context, "Auth Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun navigateToHome() {
        if (!isAdded) return
        try {
            val navController = findNavController()
            if (navController.currentDestination?.id == R.id.loginFragment) {
                navController.navigate(R.id.action_loginFragment_to_homeFragment)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Navigation failed", e)
        }
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}
