package com.benrostudios.weggi.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDAO {
    //Queries of Room
    @get:Query("SELECT * from LocHistory ORDER BY uid DESC")
    val allHistory: List<LocHistory>

    @get:Query("SELECT COUNT(*) FROM LocHistory")
    val count: Int

    @Query("DELETE FROM LocHistory WHERE uid = :title")
    fun deleteByUid(title: Int): Int

    @Insert
    fun insert(historyItem: LocHistory)

    @Delete
    fun delete(historyItem: LocHistory)

    @Query("DELETE FROM LocHistory")
    fun deleteAllHistory()
}
