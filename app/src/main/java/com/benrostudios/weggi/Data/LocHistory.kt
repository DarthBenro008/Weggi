package com.benrostudios.weggi.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LocHistory")
class LocHistory(
    //All the columns of room
    @PrimaryKey(autoGenerate = true)
    var uid: Int?=0,

    @field:ColumnInfo(name = "Location")
    var Location: String,
    @field:ColumnInfo(name = "Time")
    var dateo: String)
{
    constructor():this(null,"","")
}