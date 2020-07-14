package com.skcool.mypetstories.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

open class FirebaseRepository {

    var firebaseDatabase = FirebaseDatabase.getInstance()
    var firebaseAuth = FirebaseAuth.getInstance()
    var firebaseStorage = FirebaseStorage.getInstance()

    fun getReference(refName:String): DatabaseReference {
        return firebaseDatabase.getReference(refName)
    }

    fun getStorageReference(refName: String):StorageReference{
        return firebaseStorage.getReference(refName)
    }

    fun getUid():String{
        return firebaseAuth.uid.toString()
    }


    fun getAuth():FirebaseAuth{
        return firebaseAuth
    }





}