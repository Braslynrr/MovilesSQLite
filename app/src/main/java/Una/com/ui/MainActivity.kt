package una.com.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import una.com.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login: Button= findViewById(R.id.loginBtn)
        login.setOnClickListener {
            var name:String
            var pass:String
            name=findViewById<TextView>(R.id.userTxt).text.toString()
            pass=findViewById<TextView>(R.id.passwordTxt).text.toString()
            if(name=="admin" && pass==name){
                Toast.makeText(this,"Logueando",Toast.LENGTH_LONG).show()
                val intent = Intent(this, Menu::class.java)
                startActivity(intent)
            }
        }
    }
}