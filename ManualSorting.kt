package sorting

fun lexicographicOrder(str1: String, str2: String, type: String = Constants.BLANK): Boolean {
    val longestStr = if (str1.lastIndex < str2.lastIndex) str1 + Constants.BLANK else str1
    for (i in 0..longestStr.lastIndex) {
        return if (type == Constants.LINE) longestStr[i].code < str2[i].code
        else longestStr[i].code <= str2[i].code
    }
    return false
}

fun naturalSort(array: Array<String>, type: String) {
    if (array.size < 2) return

    val middle = array.size / 2
    val left = array.sliceArray(0 until middle)
    val right = array.sliceArray(middle until array.size)

    naturalSort(left, type)
    naturalSort(right, type)

    var i = 0
    var j = 0
    var k = 0

    while (i < left.size && j < right.size) {
        if (type == Constants.LONG) {
            if (left[i].toLong() < right[j].toLong()) array[k++] = left[i++]
            else array[k++] = right[j++]
        } else if (type == Constants.LINE) {
            if (lexicographicOrder(left[i], right[j], Constants.LINE)) array[k++] = left[i++]
            else array[k++] = right[j++]
        } else {
            if (lexicographicOrder(left[i], right[j])) array[k++] = left[i++]
            else array[k++] = right[j++]
        }
    }
    while (i < left.size) array[k++] = left[i++]
    while (j < right.size) array[k++] = right[j++]


    when(type) {
        Constants.LONG -> if (Sorting.listOfLong.size == array.size) Sorting.listOfLong = array
        Constants.WORD -> if (Sorting.listOfWords.size == array.size) Sorting.listOfWords = array
        Constants.LINE -> if (Sorting.listOfLines.size == array.size) Sorting.listOfLines = array
    }
}