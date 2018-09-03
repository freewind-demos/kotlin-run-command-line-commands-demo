package example

import java.io.File
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    runCommand(File("."), "./gradlew compileKotlin")
}

private fun runCommand(workingDir: File, command: String, captureOutput: Boolean = false): String? {
    val parts = command.split("\\s+".toRegex())
    val process = run {
        val redirectTo = if (captureOutput) ProcessBuilder.Redirect.PIPE else ProcessBuilder.Redirect.INHERIT
        ProcessBuilder(*parts.toTypedArray())
                .directory(workingDir)
                .redirectOutput(redirectTo)
                .redirectError(redirectTo)
                .start()
    }
    process.waitFor(60, TimeUnit.MINUTES)
    return if (captureOutput) {
        process.inputStream.bufferedReader().readText()
    } else {
        null
    }
}