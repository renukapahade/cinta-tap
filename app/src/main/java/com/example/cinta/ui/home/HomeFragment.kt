package com.example.cinta.ui.home

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cinta.R
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    val TAG = "Cinta-TAP"
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

        //Progress bar animation controls
        val progressBar: ProgressBar = root.findViewById(R.id.progressBar)
        val progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 360)
        progressAnimator.duration = 3600
        progressAnimator.interpolator = LinearInterpolator()
        progressAnimator.start()

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        //Access the current user ID
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val userID = sharedPref?.getString(getString(R.string.current_user), null)
        Log.d(TAG,userID.toString())

        val db = FirebaseFirestore.getInstance()
        root.setOnClickListener {
            if(progressBar.visibility != View.GONE) {
                val progress = progressAnimator.animatedFraction * 360
                progressAnimator.end()
                textView.text = "Angle recorded = " + progress.toString() + " degrees"
                progressBar.visibility = View.GONE
                val userData = hashMapOf("angle" to progress.toString())
                userID?.let { db.collection("users").document(it) }?.set(userData)
            }
        }

        return root
    }
}
