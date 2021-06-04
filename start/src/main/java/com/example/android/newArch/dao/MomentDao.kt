package com.example.android.newArch.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.newArch.bean.DataBaseBean

/**
 * The Data Access Object for the DataBaseBean class.
 */
@Dao
interface MomentDao {
    @Query("select * from moment limit 5 offset :offSet")
    fun getSomeMoments(offSet: Int): LiveData<List<DataBaseBean>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<DataBaseBean>?): List<Long>
}
