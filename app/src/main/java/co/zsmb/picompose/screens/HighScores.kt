package co.zsmb.picompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.zsmb.picompose.R
import co.zsmb.picompose.data.ScoreKeeper


@Composable
fun HighScores(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.high_scores_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Header(Modifier.width(120.dp))

            ScoreKeeper.scores.forEachIndexed { index, score ->
                ScoreItem(index, score, Modifier.width(120.dp))
            }
        }
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Row(modifier) {
        Text(
            text = "#",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = stringResource(R.string.high_scores_score_label),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.weight(2f),
        )
    }
}

@Composable
private fun ScoreItem(
    index: Int,
    score: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Text(
            text = "${index + 1}.",
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = score.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(2f),
        )
    }
}
