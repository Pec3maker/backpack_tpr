package domain

data class Storage(
    val items: MutableList<Item>,
    val price: Int = items.sumOf { it.price }
) {
    override fun toString(): String {
        return items.joinToString(separator = "\n ") { "name: ${it.name}, price: ${it.price};" }
    }
}