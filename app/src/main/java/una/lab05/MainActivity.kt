package una.lab05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login: Button = findViewById(R.id.loginBtn)
        login.setOnClickListener {
            val name:String = findViewById<TextView>(R.id.userTxt).text.toString()
            val pass:String = findViewById<TextView>(R.id.passwordTxt).text.toString()
            if(name=="admin" && pass==name){
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,"Acceso Denegado", Toast.LENGTH_LONG).show()
            }
        }
    }
}