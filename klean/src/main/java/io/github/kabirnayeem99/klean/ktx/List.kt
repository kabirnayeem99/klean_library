package io.github.kabirnayeem99.klean.ktx


fun <E> MutableList<E>.addAllIfNotExist(elements: Collection<E>) {
    for (element in elements) {
        if (!this.contains(element)) {
            this.add(element)
        }
    }
}

inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum: Long = 0
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

inline fun <T> List<T>.copy(mutatorBlock: MutableList<T>.() -> Unit): List<T> {
    return toMutableList().apply(mutatorBlock)
}

fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}

fun <T> List<T>.midElement(): T {
    if (isEmpty())
        throw NoSuchElementException("List is empty.")
    return this[size / 2]
}