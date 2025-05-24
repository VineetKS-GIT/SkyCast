
package com.example.weatherapplication
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView


class launcher_activity : AppCompatActivity() {
    private lateinit var lottieAnimationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        lottieAnimationView = findViewById(R.id.lottieanimationview)


        // Define the delay time in milliseconds
        val delayMillis: Long = 4200 // 2 seconds delay

        // Handler to delay activity change
        Handler().postDelayed({
            // Start the next activity here
            lottieAnimationView.playAnimation()
            startActivity(Intent(this, MainActivity::class.java))

            // Finish this activity
            finish()
        }, delayMillis)
        setStatusBarColor(R.color.launchcolor)

    }
    private fun setStatusBarColor(colorResId: Int) {
        window.statusBarColor = resources.getColor(colorResId, theme)
    }
}




