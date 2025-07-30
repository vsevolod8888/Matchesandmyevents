package data.room

import screens.main.item.Event

data class BigItemDataClass(
    val startTimeTimeStamp: Long = -1L,
    val endTimeTimeStamp: Long = -1L,
    val listItems:List<Event>
)
