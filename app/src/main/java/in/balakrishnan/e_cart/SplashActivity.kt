package `in`.balakrishnan.e_cart

import `in`.balakrishnan.e_cart.productListing.MainActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish();
//        Handler().postDelayed({
//
//        }, 2000)
    }
}
