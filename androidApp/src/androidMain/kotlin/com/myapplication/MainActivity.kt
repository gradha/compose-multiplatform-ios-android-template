package com.myapplication

import FirstScreen
import MainView
import ProxyNavigator
import SecondScreen
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navigator = object : ProxyNavigator {
            override fun openNext() {
                open()
            }
        }

        setContent {
            MainView(navigator)
        }
    }

    private fun open() {
        startActivity(Intent(this, FirstActivity::class.java))
    }
}

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navigator = object : ProxyNavigator {
            override fun openNext() {
                open()
            }
        }

        setContent {
            FirstScreen(navigator)
        }
    }

    private fun open() {
        startActivity(Intent(this, SecondActivity::class.java))
    }
}

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecondScreen()
        }
    }
}
