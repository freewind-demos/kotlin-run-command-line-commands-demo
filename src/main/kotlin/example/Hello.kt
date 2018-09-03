package example

import java.io.File
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val output = runCommand(File("."), "./gradlew compileKotlin")
    println(output)
}

private fun runCommand(workingDir: File, command: String): String {
    val parts = command.split("\\s+".toRegex())
    val proc = ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

    proc.waitFor(60, TimeUnit.MINUTES)
    return proc.inputStream.bufferedReader().readText()
}