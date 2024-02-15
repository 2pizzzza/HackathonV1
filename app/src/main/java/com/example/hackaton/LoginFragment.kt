package com.example.hackaton

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
import com.example.hackaton.databinding.FragmentLoginBinding
import com.example.hackaton.entity.AuthRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {
    private lateinit var mainapi: MainApi
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRetrofit()
        binding.apply {

            bNext.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                viewModel.token.value = "kajhsdkjsajsgbfdj"
            }

            SignIn.setOnClickListener {
                auth(
                    AuthRequest(
                        login.text.toString(),
                        password.text.toString()
                    )
                )
            }
        }
    }

    private fun initRetrofit(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create()).build()

         mainapi = retrofit.create(MainApi::class.java)
    }

    private fun auth(authRequest: AuthRequest){
        CoroutineScope(Dispatchers.IO).launch {
            val response = mainapi.auth(authRequest)
            val message = response.errorBody()?.string()?.let { JSONObject(it).getString("message") }
            requireActivity().runOnUiThread {
                binding.error.text = message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                val user = response.body()
                if(user != null){
                    Toast.makeText(context, "you are register, please click next", Toast.LENGTH_SHORT).show()
                    viewModel.token.value = user.token
                    viewModel.email.value = user.email
                    viewModel.nickname.value = user.firstName
                    binding.bNext.visibility = View.VISIBLE
                }
            }

        }
    }
}