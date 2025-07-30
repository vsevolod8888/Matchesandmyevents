package data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GameEntity::class,EventEntity::class], version = 1)
abstract class AppDatabasee : RoomDatabase(){
    abstract fun getDao(): TodoDao
    abstract fun getEventDao(): EventDao
}


