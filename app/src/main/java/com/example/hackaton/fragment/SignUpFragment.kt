package com.example.hackaton.fragment

import MainApi
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.hackaton.LiveData.LoginViewModel
import com.example.hackaton.R
import com.example.hackaton.databinding.FragmentLoginBinding
import com.example.hackaton.databinding.FragmentSignUpBinding
import com.example.hackaton.entity.RegisterRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpFragment : Fragment() {
    private lateinit var mainapi: MainApi
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRetrofit()
        binding.apply {
            buttonSignIn.setOnClickListener{
                findNavController().navigate(R.id.action_signUpFragment_to_LoginFragment)
            }
            buttonSignUp.setOnClickListener {
                signup(
                    RegisterRequest(editTextSignUpEmail.text.toString(),
                    editTextUsername.text.toString(),
                    editTextSignUpPassword.text.toString())
                )
                }
        }}

    private fun initRetrofit() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://sanjarman.pythonanywhere.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        mainapi = retrofit.create(MainApi::class.java)
    }

    private fun signup(request: RegisterRequest){
        CoroutineScope(Dispatchers.IO).launch {
            val response = mainapi.register(request)
            val message =
            response.errorBody()?.string()?.let { JSONObject(it).getString("message") }
            requireActivity().runOnUiThread{
                if(message != null){
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
                val user = response.body()
                if (user != null){
                    Toast.makeText(
                        context,
                        "you are register",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_signUpFragment_to_navActivity)
                    viewModel.token.value = user.access_token
                }
            }
        }
    }

}
