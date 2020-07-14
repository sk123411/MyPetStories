package com.skcool.mypetstories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.skcool.mypetstories.databinding.ActivityMainBinding
import com.skcool.mypetstories.viewmodel.HomeFragmentViewModel

class MainActivity : AppCompatActivity() {
    var title:String = "MyPetStories"
    lateinit var binding:ActivityMainBinding

    companion object{

        var USERNAME:String?=null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val homeFragmentViewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)

        homeFragmentViewModel.user.observe(this, Observer {
            Toast.makeText(this, USERNAME,Toast.LENGTH_SHORT).show()

            USERNAME= it.toString()
        })

        val navController = findNavController(R.id.fragment)
        this.title = title


        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            title = when (destination.id) {
                R.id.loginFragment -> "Login"
                R.id.signUpFragment -> "Sign up"
                R.id.imageUploadFragment -> "Upload Picture"
                else -> "MyPetStories"

            }


        }

    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp()
    }





}