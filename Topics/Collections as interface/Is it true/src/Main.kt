fun main() {
    val nameList = readln().split(" ").toMutableList()
    val nameSet = nameList.toMutableSet()
    val name = readln()

    println(checkElements(nameList, name))
    println(checkElements(nameSet, name))
}

// Write here function checkElements()
fun checkElements( a: MutableCollection<String>, b: String):Boolean{
return a.contains(b)

}