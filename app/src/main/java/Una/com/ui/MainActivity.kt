package Una.com.ui

import Una.com.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

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
            }
        }
    }
}