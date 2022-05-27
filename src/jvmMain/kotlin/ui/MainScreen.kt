package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.Item
import domain.LoadingTask
import domain.Storage


@Composable
fun MainScreen() {
//    val items = listOf(
//        Item("гитара", 1500, 1),
//        Item("бензопила", 3000, 4),
//        Item("ноутбук", 2000, 3)
//    )
//
//    val task = LoadingTask(space = 4, items = items)
//    task.process()
//    val text = remember { mutableStateOf(" ") }
//    text.value = task.toString()


//    Column(modifier = Modifier.fillMaxSize()) {
//        task.processTable.forEach {
//            Row {
//                it.forEach {
//                    Text(it.price.toString())
//                    Spacer(modifier = Modifier.width(5.dp))
//                }
//            }
//            Spacer(modifier = Modifier.height(5.dp))
//        }
//    }


    val itemNameField = remember { mutableStateOf("") }
    val itemCostField = remember { mutableStateOf("") }
    val itemWeightField = remember { mutableStateOf("") }
    val itemSpaceField = remember { mutableStateOf("") }
    val chosenItem = remember { mutableStateOf(-1) }
    val items = remember { mutableStateOf(emptyList<Item>()) }
    val space = remember { mutableStateOf<Int?>(null) }
    var result = remember { mutableStateOf<Storage?>(null) }


    Row(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight().padding(5.dp),
            shape = RoundedCornerShape(size = 8.dp),
            border = BorderStroke(width = 1.dp, color = Color.Gray)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Предметы")
                }
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(modifier = Modifier.fillMaxHeight(0.7f).fillMaxWidth()) {
                    itemsIndexed(items.value) { index, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clickable {
                                    chosenItem.value = index
                                },
                            backgroundColor = if (index == chosenItem.value) {
                                Color.Gray
                            } else {
                                Color.White
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize().padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("name: ${item.name}, price: ${item.price}, weight: ${item.weight}")
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Divider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        Text("Название")
                        OutlinedTextField(
                            value = itemNameField.value,
                            onValueChange = {
                                itemNameField.value = it
                            },
                            modifier = Modifier.fillMaxWidth(0.5f),
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("Цена")
                        OutlinedTextField(
                            value = itemCostField.value,
                            onValueChange = {
                                itemCostField.value = it
                            },
                            modifier = Modifier.fillMaxWidth(0.5f)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("Вес")
                        OutlinedTextField(
                            value = itemWeightField.value,
                            onValueChange = {
                                itemWeightField.value = it
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            try {
                                items.value = items.value.toMutableList().apply {
                                    add(
                                        Item(
                                            name = itemNameField.value,
                                            price = itemCostField.value.toInt(),
                                            weight = itemWeightField.value.toInt()
                                        )
                                    )
                                }
                            } catch (er: java.lang.NumberFormatException) {
                                //todo
                            }
                        }
                    ) {
                        Text("Добавить предмет")
                    }

                    TextButton(
                        onClick = {
                            try {
                                items.value = items.value.toMutableList().apply {
                                    removeAt(chosenItem.value)
                                    chosenItem.value = -1
                                }
                            } catch (ex: java.lang.IndexOutOfBoundsException) {
                                //todo
                            }
                        }
                    ) {
                        Text("Удалить выбранный предмет")
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxSize().padding(5.dp),
            shape = RoundedCornerShape(size = 8.dp),
            border = BorderStroke(width = 1.dp, color = Color.Gray)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
                Text(
                    text = if (space.value == null) {
                        "Введите максимальный допустимый вес"
                    } else {
                        "Максимальный вес: ${space.value}"
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Text("Максимальный вес")
                    OutlinedTextField(
                        value = itemSpaceField.value,
                        onValueChange = {
                            itemSpaceField.value = it
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextButton(
                        onClick = {
                            try {
                                space.value = itemSpaceField.value.toInt()
                                result.value = null
                            } catch (ex: NumberFormatException) {
                                //todo
                            }
                        }
                    ) {
                        Text(
                            if (space.value == null) {
                                "Ввести макс. вес"
                            } else {
                                "Изменить макс. вес"
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                TextButton(
                    onClick = {
                        val curSpace = space.value
                        if (items.value.isNotEmpty() && curSpace != null) {
                            result.value = LoadingTask(space = curSpace, items = items.value).process()
                        } else {
                            //todo
                        }
                    }
                ) {
                    Text(
                        "Решить задачу"
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                val curResult = result.value
                if (curResult != null) {
                    Text("Результат")
                    LazyColumn(modifier = Modifier.fillMaxHeight(0.7f).fillMaxWidth()) {
                        itemsIndexed(curResult.items) { index, item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                backgroundColor = Color.White
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize().padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("name: ${item.name}, price: ${item.price}, weight: ${item.weight}")
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                    Text("Общая сумма: ${curResult.price}")
                }
            }
        }
    }
}