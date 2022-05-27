package domain

class LoadingTask(
    private val space: Int,
    private val items: List<Item>
) {
    val processTable: MutableList<MutableList<Storage>> =
        MutableList(size = items.size + 1) {
            MutableList(size = space + 1) {
                Storage(
                    items = mutableListOf(),
                    price = 0
                )
            }
        }

    fun process(): Storage {
        for (n in 0..items.size) {
            for (k in 0.. space) {
                if (k == 0 || n == 0) {

                } else if (n == 1) {
                    if (items[0].weight <= k) {
                        processTable[1][k] = Storage(items = mutableListOf(items[0]))
                    }
                } else {
                    if (items[n - 1].weight > k) {
                        processTable[n][k] = processTable[n - 1][k]
                    } else {
                        val newPrice = items[n - 1].price + processTable[n - 1][k - items[n - 1].weight].price
                        if (processTable[n - 1][k].price > newPrice) {
                            processTable[n][k] = processTable[n - 1][k]
                        } else {
                            val itemsList = mutableListOf(items[n - 1]) + processTable[n - 1][k - items[n - 1].weight].items
                            processTable[n][k] = Storage(itemsList.toMutableList())
                        }
                    }
                }
            }
        }

        return processTable.map { it.last() }.maxByOrNull { it.price } ?: Storage(mutableListOf())
    }
}
