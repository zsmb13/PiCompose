package co.zsmb.picompose.screens

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
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import co.zsmb.picompose.R
import co.zsmb.picompose.data.ScoreKeeper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun Home(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Pi") },
            actions = {
                IconButton(onClick = { navController.navigate("highscores") }) {
                    Icon(
                        Icons.Default.Star,
                        stringResource(R.string.content_description_high_scores)
                    )
                }
            }
        )
    }) {
        PiPad()
    }
}

@Composable
private fun PiPad() {
    val pi = stringResource(R.string.pi)

    val scope = rememberCoroutineScope()

    var input by remember { mutableStateOf("3.") }
    val scrollState = rememberScrollState()

    var showDialog by remember { mutableStateOf(false) }
    var lastInput by remember { mutableStateOf('-') }

    fun reset() {
        input = "3."
        lastInput = '-'
        showDialog = false
    }

    KeyPad(
        input = input,
        scrollState = scrollState,
        onClick = { digit ->
            val indexToTest = input.length
            val correctDigit = pi[indexToTest]

            lastInput = digit

            if (digit == correctDigit) {
                input += digit.toString()
                scope.launch {
                    delay(100)
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
            } else {
                val decimalReached = input.length - 2
                ScoreKeeper.logScore(decimalReached)

                showDialog = true
            }
        }
    )

    if (showDialog) {
        val length = input.length

        EndOfGameDialog(onDismiss = { reset() }) {
            Column {
                Text(stringResource(R.string.dialog_decimal_reached, length - 2))
                Text(stringResource(R.string.dialog_digit_info, lastInput, pi[length]))
                Text(stringResource(R.string.dialog_next_digits, pi.substring(length, length + 10)))
            }
        }
    }
}

@Composable
private fun KeyPad(
    input: String,
    scrollState: ScrollState,
    onClick: (digit: Char) -> Unit,
) {
    val progress = input.length - 2
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = input,
            fontSize = 32.sp,
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
                text = stringResource(id = R.string.current_progress, progress),
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
            )
        }
    }
}

@Composable
private fun NumberButton(
    number: Int,
    onClick: (Char) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = {
            @OptIn(ExperimentalStdlibApi::class)
            onClick(number.digitToChar())
        },
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

@Composable
private fun EndOfGameDialog(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.dialog_game_over_title))
        },
        text = content,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.dialog_ok))
            }
        },
    )
}
