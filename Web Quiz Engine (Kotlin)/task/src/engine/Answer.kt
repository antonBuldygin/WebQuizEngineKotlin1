package engine

data class Answer(var answer: MutableList<Int>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Answer

        return answer == other.answer
    }

    override fun hashCode(): Int {
        return answer.hashCode()
    }
}