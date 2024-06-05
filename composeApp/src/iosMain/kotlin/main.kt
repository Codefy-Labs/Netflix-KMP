import androidx.compose.ui.window.ComposeUIViewController
import com.codefylabs.netflix.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
