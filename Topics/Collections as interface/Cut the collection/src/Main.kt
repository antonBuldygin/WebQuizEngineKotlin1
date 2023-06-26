fun main() {
    val originalList = readln().split(" ")
    val originalSet = originalList.toSet()
    val word = readln()

    println(dropElements(originalList, word))
    println(dropElements(originalSet, word))
}

// Write function dropElements() here
fun dropElements(a: Collection<String>, b:String):List<String>{
    return a.filter { !it.contains(b) }
}