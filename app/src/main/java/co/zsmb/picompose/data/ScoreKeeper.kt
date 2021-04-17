package co.zsmb.picompose.data

import android.annotation.SuppressLint
import android.content.Context
import java.io.File
import java.io.PrintWriter

@SuppressLint("StaticFieldLeak")
object ScoreKeeper {
    private const val MAX_SCORES = 10
    private const val FILENAME = "scores.txt"

    private var context: Context? = null

    var scores: List<Int> = emptyList<Int>()
        private set

    fun init(appContext: Context) {
        context = appContext
        loadList()
    }

    fun logScore(score: Int) {
        scores = (scores + score).sortedDescending().take(MAX_SCORES)
        saveList()
    }

    private fun loadList() {
        scores = emptyList()

        context?.run {
            val inputFile = File(filesDir, FILENAME)
            if (!inputFile.exists()) {
                return
            }

            openFileInput(FILENAME).bufferedReader().useLines { lines ->
                scores = lines.map(String::toInt).toList()
            }
        }
    }

    private fun saveList() {
        (context ?: return)
            .openFileOutput(FILENAME, Context.MODE_PRIVATE)
            .let(::PrintWriter)
            .use { writer -> scores.forEach(writer::println) }
    }
}
