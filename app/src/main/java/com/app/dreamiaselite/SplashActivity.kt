package com.app.dreamiaselite

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.dreamiaselite.ui.theme.DreamIasEliteTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // Android 12+ system splash
        super.onCreate(savedInstanceState)

        setContent {
            DreamIasEliteTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = androidx.compose.material3.MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher),
                        contentDescription = "App icon",
                        modifier = Modifier.size(200.dp)
                    )
                }

                LaunchedEffect(Unit) {
                    // Small delay so the icon stays visible briefly
                    delay(500)
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}
