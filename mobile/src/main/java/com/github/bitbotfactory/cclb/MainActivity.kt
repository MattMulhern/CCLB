package com.github.bitbotfactory.cclb

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.github.kittinunf.result.Result

import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.string
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.content_main.*
import android.widget.ArrayAdapter




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        updateView()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Refreshing Data", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

        }

    }

    fun updateView() {
        setContentView(R.layout.activity_main)


        var url = "https://mulhernhex.duckdns.org/botlog/"
        url.httpGet().responseString { request, response, result ->
            //do something with response
            when (result) {
                is Result.Failure -> {
                    //error = result.getAs()
                }
                is Result.Success -> {
                    println("result: ${result}")
                    var dataJson = str2json(result.get()) as JsonObject

                    var lastStatus = dataJson.string("last_status")
                    var lastStatusTxt = findViewById<TextView>(R.id.last_status)
                    lastStatusTxt.setText(lastStatus)

                    var lastUpdate = dataJson.string("last_update")
                    var lastUpdateTxt = findViewById<TextView>(R.id.last_update)
                    lastUpdateTxt.setText(lastUpdate)

                }
            }
        }

    }


//    fun jsonList2str (input JsonArray) {
//        for
//
//    }
    fun str2json(inStr: String) : JsonObject? {
        val parser: Parser = Parser()
        val stringBuilder: StringBuilder = StringBuilder(inStr)
        val parsed = parser.parse(stringBuilder) as JsonObject

        return parsed
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
