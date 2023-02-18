package com.theone.n_bbang.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.navigation.NavigationBarView
import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseActivity
import com.theone.n_bbang.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    val fragmentManager = supportFragmentManager
    override fun init() {
        binding.activity = this

        setNavigationBar()
    }


    private fun setNavigationBar() {
        binding.bottomNavigation.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_home -> {
                        val homeFragment = HomeFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, homeFragment).commit()
                        Timber.e("homeFragment")
                        return true
                    }
                    R.id.action_join_list -> {
                        val joinListFragment = JoinListFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, joinListFragment).commit()
                        Timber.e("joinListFragment")
                        return true
                    }
                    R.id.action_my_info -> {
                        val myInfoFragment = MyInfoFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, myInfoFragment).commit()
                        Timber.e("myInfoFragment")
                        return true
                    }
                }

                return false
            }

        })
        binding.bottomNavigation.selectedItemId = R.id.action_home
        binding.bottomNavigation.itemIconTintList = null
    }
}