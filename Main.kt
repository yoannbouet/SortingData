package sorting

import java.io.File
import java.io.IOException
import java.util.Scanner
import kotlin.math.round
import kotlin.math.roundToLong
import kotlin.system.exitProcess

class Sorting(args: Array<String>) {
    private var sortType = Constants.EMPTY
    private var inputFile = if (Constants.INPUT_FILE in args) {
        args.getOrNull(args.indexOf(Constants.INPUT_FILE) + 1).toString()
    } else Constants.EMPTY
    private var outputFile = if (Constants.OUTPUT_FILE in args) {
        args.getOrNull(args.indexOf(Constants.OUTPUT_FILE) + 1).toString()
    } else Constants.EMPTY

    init {
        if (Constants.INPUT_FILE in args) {
            try {
                File(inputFile).readText()
            } catch (e: IOException) {
                Messages.UNREADABLE_FILE.show(inputFile)
            }
        }
        if (Constants.OUTPUT_FILE in args) {
            try {
                File(outputFile).writeText(Constants.EMPTY)
            } catch (e: IOException) {
                Messages.UNREADABLE_FILE.show(outputFile)
            }
        }

        if (Constants.SORT_TYPE in args) {
            when(args.getOrNull(args.indexOf(Constants.SORT_TYPE) + 1)) {
                Constants.NATURAL -> sortType = Constants.NATURAL
                Constants.COUNT -> sortType = Constants.COUNT
                else -> Messages.NO_SORTING_ARGUMENT.show()
            }
        } else sortType = Constants.NATURAL
        if (Constants.DATA_TYPE in args) {
            when(args.getOrNull(args.indexOf(Constants.DATA_TYPE) + 1)) {
                Constants.LONG -> long(sortType)
                Constants.WORD -> word(sortType)
                Constants.LINE -> line(sortType)
                else -> Messages.NO_DATA_TYPE.show()
            }
        }

        for (arg in args) if (arg !in constantValues && arg != inputFile && arg != outputFile) {
            Messages.UNKNOWN_ARG.show(arg)
        }
    }

    private fun word(sort: String) {
        val list = mutableListOf<String>()
        if (inputFile.isNotEmpty()) {
            for (line in File(inputFile).readLines()) {
                val matches = Regex("[\\S-]+").findAll(line)
                for (match in matches) list.add(match.value)
            }
        } else {
            val scanner = Scanner(System.`in`)
            do {
                val matches = Regex("[\\S-]+").findAll(scanner.nextLine())
                for (match in matches) list.add(match.value)
            } while (scanner.hasNext())
            scanner.close()
        }

        list.toTypedArray().let {
            listOfWords = it
            naturalSort(it, Constants.WORD)
        }
        val counted: MutableSet<Pair<Int, String>> = mutableSetOf()
        listOfWords.forEach { str -> counted.add(Pair(listOfWords.count() { it == str }, str)) }

        Messages.TotalWords.show(list.size.toString())
        if (sort == Constants.NATURAL) {
            Messages.SORT_NATURAL.show(listOfWords.joinToString(Constants.BLANK))
        } else {
            counted.sortedBy { it.first }.forEach { Pair ->
                Messages.SORT_COUNT.show(
                    Pair.second,
                    Pair.first.toString(),
                    (round(Pair.first * 100.0 / listOfWords.size)).roundToLong().toString()
                )
            }
        }
    }

    private fun long(sort: String) {
        val list = mutableListOf<String>()
        if (inputFile.isNotEmpty()) {
            for (line in File(inputFile).readLines()) {
                val matches = Regex("[\\S-d]+").findAll(line)
                for (match in matches) list.add(match.value)
            }
        } else {
            val scanner = Scanner(System.`in`)
            do {
                val matches = Regex("[\\S-d]+").findAll(scanner.nextLine())
                for (match in matches) list.add(match.value)
            } while (scanner.hasNext())
            scanner.close()
        }

        // REMOVE NOT LONG
        for (str in list) {
            if (!Regex("[-]?[\\d]+").matches(str)) {
                Messages.NOT_LONG.show(str)
                list.remove(str)
            }
        }

        list.toTypedArray().let {
            listOfLong = it
            naturalSort(it, Constants.LONG)
        }
        val counted: MutableSet<Pair<Int, String>> = mutableSetOf()
        listOfLong.forEach { str -> counted.add(Pair(listOfLong.count() { it == str }, str)) }

        Messages.TotalNumbers.show(listOfLong.size.toString())
        if (sort == Constants.NATURAL) {
            Messages.SORT_NATURAL.show(listOfLong.joinToString(Constants.BLANK))
        } else {
            counted.sortedBy { it.first }.forEach { Pair ->
                Messages.SORT_COUNT.show(
                    Pair.second,
                    Pair.first.toString(),
                    (round(Pair.first * 100.0 / listOfLong.size)).roundToLong().toString()
                )
            }
        }
    }

    private fun line(sort: String) {
        val list = mutableListOf<String>()
        if (inputFile.isNotEmpty()) {
            for (line in File(inputFile).readLines()) {
                list.add(line)
            }
        } else {
            val scanner = Scanner(System.`in`)
            do {
                list.add(scanner.nextLine())
            } while (scanner.hasNext())
            scanner.close()
        }

        list.toTypedArray().let {
            listOfLines = it
            naturalSort(it, Constants.LINE)
        }
        val counted: MutableSet<Pair<Int, String>> = mutableSetOf()
        listOfLines.forEach { str -> counted.add(Pair(listOfLines.count() { it == str }, str)) }

        Messages.TotalLines.show("${list.size}")
        if (sort == Constants.NATURAL) {
            Messages.SORT_NATURAL.show(listOfLines.joinToString(Constants.BLANK))
        } else {
            counted.sortedBy { it.first }.forEach { Pair ->
                Messages.SORT_COUNT.show(
                    Pair.second,
                    Pair.first.toString(),
                    (round(Pair.first * 100.0 / listOfLines.size)).roundToLong().toString()
                )
            }
        }
    }

    private fun Messages.show(str1: String = Constants.EMPTY, str2: String = Constants.EMPTY, str3: String = Constants.EMPTY) {
        val exitList = listOf(Messages.NO_SORTING_ARGUMENT, Messages.NO_DATA_TYPE, Messages.UNREADABLE_FILE)
        if (this in exitList) {
            println(this.msg.format(str1, str2, str3))
            exitProcess(0)
        }
        val errorsList = exitList + Messages.UNKNOWN_ARG + Messages.NOT_LONG
        if (outputFile.isNotEmpty() && this !in errorsList) {
            File(outputFile).appendText(this.msg.format(str1, str2, str3) + "\n")
        } else println(this.msg.format(str1, str2, str3))
    }

    companion object {
        var listOfLong = arrayOf<String>()
        var listOfWords = arrayOf<String>()
        var listOfLines = arrayOf<String>()
        val constantValues = listOf(
            Constants.COUNT, Constants.NATURAL, Constants.LINE, Constants.LONG, Constants.OUTPUT_FILE,
            Constants.WORD, Constants.DATA_TYPE, Constants.SORT_TYPE, Constants.INPUT_FILE
        )
    }
}

fun main(args: Array<String>) {
    Sorting(args)
}
