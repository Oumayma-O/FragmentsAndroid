package com.example.fragments

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.Switch

class MainActivity : AppCompatActivity(), ActionMode.Callback {
    private lateinit var toolbar: Toolbar
    private lateinit var actionMode: ActionMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, FragmentClock(), null)
            .addToBackStack(null)
            .commit()

        findViewById<View>(R.id.button).setOnLongClickListener {
            actionMode = this@MainActivity.startActionMode(this@MainActivity)!!
            return@setOnLongClickListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.file1, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_switch) {
            findViewById<Switch>(R.id.switch1).isChecked = !findViewById<Switch>(R.id.switch1).isChecked
            setTime(null)
        }
        return super.onOptionsItemSelected(item)
    }

    fun setTime(view: View?) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val fragmentClock = FragmentClock()
        val bundle = Bundle()
        bundle.putBoolean("digitalOK", findViewById<Switch>(R.id.switch1).isChecked)
        fragmentClock.arguments = bundle
        transaction.replace(R.id.fragment, fragmentClock)
        transaction.commit()
    }

    override fun onCreateActionMode(actionMode: ActionMode, menu: Menu?): Boolean {
        val inflater: MenuInflater = actionMode.menuInflater
        inflater.inflate(R.menu.context_mode_menu, menu)
        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menuItem: MenuItem?): Boolean {
        return when (menuItem?.itemId) {
            R.id.action_color -> {
                findViewById<View>(R.id.button).setBackgroundColor(
                    resources.getColor(
                        R.color.black
                    )
                )
                actionMode?.finish()
                true
            }
            else -> false
        }
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
    }
}
