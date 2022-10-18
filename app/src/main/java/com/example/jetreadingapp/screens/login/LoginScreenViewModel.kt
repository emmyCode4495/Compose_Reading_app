package com.example.jetreadingapp.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetreadingapp.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {
   // val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailPassword(email: String, password: String, home: () -> Unit)
    = viewModelScope.launch{
        try {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        Log.d("FB","SignInWithEmailAndPassword: lOGGED IN ${task.result}")
                        //TODO("Go to home screen")
                       home()
                    }else{
                        Log.d("FB","signInWithEmailPassword: ${task.result}")
                    }
                }

        }catch (ex: Exception){
            Log.d("FB","signInWithEmailPassword: ${ex.message}")
        }

    }


    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        login: () -> Unit
    ){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        val displayName = task.result?.user?.email?.split("@")?.get(0)
                        CreateUser(displayName)
                        login()
                    }else{
                        Log.d("FB","createUserWithEmailAndPassword: ${task.result}")
                    }
                    _loading.value = false
                }
        }

    }

    private fun CreateUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = MUser(userId = userId.toString(),
        displayName = displayName.toString(),
        avatarUrl = "",
        quote = "Life is great",
        profession = "Android developer",
        id = null).toMap()


        FirebaseFirestore.getInstance().collection("users")
            .add(user)

    }

}