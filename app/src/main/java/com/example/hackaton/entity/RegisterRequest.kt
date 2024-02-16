package com.example.hackaton.entity

import android.provider.ContactsContract.CommonDataKinds.Email

data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String
)