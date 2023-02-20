package sorting

enum class Messages(val msg: String) {
    TotalNumbers("Total numbers: %s."),
    TotalLines("Total lines: %s."),
    TotalWords("Total words: %s."),
    SORT_COUNT("%s: %s time(s), %s%%"),
    SORT_NATURAL("Sorted data: %s"),
    NO_SORTING_ARGUMENT("No sorting type defined!"),
    NO_DATA_TYPE("No data type defined!"),
    UNKNOWN_ARG("%s is not a valid parameter. It will be skipped."),
    NOT_LONG("%s is not a long. It will be skipped."),
    UNREADABLE_FILE("The %s file cannot be read.")
}