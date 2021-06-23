package com.lupesoft.mvcpattern.model.dataAccess.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lupesoft.mvcpattern.model.dataAccess.entities.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie")
    fun getAllMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<MovieEntity>)
}