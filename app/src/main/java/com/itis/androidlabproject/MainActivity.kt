package com.itis.androidlabproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import com.itis.androidlabproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        supportFragmentManager.beginTransaction().run {
            add(R.id.frame_layout, HomeFragment())
            addToBackStack("home")
            commit()
        }

        with(binding) {
            navbarBtnHome.setOnClickListener {
                replaceFragment(HomeFragment())
            }
            navbarBtnSearch.setOnClickListener {
                replaceFragment(SearchFragment())
            }
            navbarBtnReels.setOnClickListener {
                replaceFragment(ReelsFragment())
            }
            navbarBtnLike.setOnClickListener {
                replaceFragment(LikeFragment())
            }
            navbarBtnProfile.setOnClickListener {
                replaceFragment(ProfileFragment())
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            addToBackStack(fragment.javaClass.toString())
            setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
            commit()
            replace(R.id.frame_layout, fragment)
        }
    }
}
