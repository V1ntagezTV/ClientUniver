package com.example.testings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import com.example.testings.webview.WebViewActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.nav_news
        bottomNav.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.nav_news -> {
                    navController.navigate(R.id.nav_news)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_events -> {
                    navController.navigate(R.id.nav_events)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_shedule -> {
                    navController.navigate(R.id.nav_shedule)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false

        }
        this.drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_news,
                R.id.nav_events,
                R.id.nav_shedule,
                R.id.nav_unstudents,
                R.id.nav_setting,
                R.id.nav_about,
                R.id.nav_info,
                R.id.nav_faculties,
                R.id.nav_services
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        fun openWebViewActivity(urls: String, context: Context) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("link", urls)
            startActivity(intent, Bundle())
        }
        when (item.itemId){
            R.id.action_site -> {
                openWebViewActivity("http://sibsu.ru/", this.baseContext)
            }
            R.id.action_group_vk -> {
                openWebViewActivity("https://vk.com/sibsu_ru", this.baseContext)
            }
            R.id.action_user_cab -> {
                openWebViewActivity("https://cabinet.sibsu.ru/", this.baseContext)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }

    }
}
