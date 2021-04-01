package co.zsmb.picompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.zsmb.picompose.ui.theme.PiPracticeComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PiPracticeComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text("Pi")
                                },
                                actions = {
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(Icons.Default.Star, null)
                                    }
                                }
                            )
                        }
                    ) {
                        PiApp()
                    }
                }
            }
        }
    }
}

@Composable
fun PiApp() {
    val pi = stringResource(R.string.pi)

    val scope = rememberCoroutineScope()

    var input by remember { mutableStateOf("3.") }
    val scrollState = rememberScrollState()

    var showDialog by remember { mutableStateOf(false) }
    var lastInput by remember { mutableStateOf(0) }

    KeyPad(
        input = input,
        scrollState = scrollState,
        onClick = { digit ->
            val indexToTest = input.length
            val correctDigit = pi[indexToTest]

            lastInput = digit

            @OptIn(ExperimentalStdlibApi::class)
            if (digit.digitToChar() == correctDigit) {
                input += digit.toString()
                scope.launch {
                    delay(100)
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
            } else {
                showDialog = true
            }
        }
    )

    if (showDialog) {
        val length = input.length
        val message = buildString {
            appendLine("Reached decimal place ${length - 2}")
            appendLine("Pressed $lastInput, correct digit was ${pi[length]}")
            appendLine("Next ten digits were ${pi.substring(length, length + 10)}")
        }
        EndOfGameDialog(
            message = message,
            onDismiss = { showDialog = false },
        )
    }
}

@Composable
private fun EndOfGameDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Game over")
        },
        text = {
            Text(message)
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        },
    )
}

@Composable
fun KeyPad(
    input: String,
    scrollState: ScrollState,
    onClick: (digit: Int) -> Unit,
) {
    val progress = input.length - 2
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = input,
            fontSize = 50.sp,
            modifier = Modifier
                .verticalScroll(scrollState)
                .weight(1f)
                .padding(4.dp),
        )
        Row(Modifier.fillMaxWidth()) {
            NumberButton(number = 1, onClick, Modifier.weight(1f))
            NumberButton(number = 2, onClick, Modifier.weight(1f))
            NumberButton(number = 3, onClick, Modifier.weight(1f))
        }
        Row(Modifier.fillMaxWidth()) {
            NumberButton(number = 4, onClick, Modifier.weight(1f))
            NumberButton(number = 5, onClick, Modifier.weight(1f))
            NumberButton(number = 6, onClick, Modifier.weight(1f))
        }
        Row(Modifier.fillMaxWidth()) {
            NumberButton(number = 7, onClick, Modifier.weight(1f))
            NumberButton(number = 8, onClick, Modifier.weight(1f))
            NumberButton(number = 9, onClick, Modifier.weight(1f))
        }
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            NumberButton(number = 0, onClick, Modifier.weight(1f))
            Text(
                text = "Progress: $progress",
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun NumberButton(
    number: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = { onClick(number) },
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp),
        border = ButtonDefaults.outlinedBorder.copy(
            brush = SolidColor(MaterialTheme.colors.primary)
        )
    ) {
        Text(
            text = number.toString(),
            fontSize = 30.sp,
            color = MaterialTheme.colors.primary,
        )
    }
}
