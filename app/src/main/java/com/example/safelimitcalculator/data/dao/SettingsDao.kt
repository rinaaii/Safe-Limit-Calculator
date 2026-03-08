package com.example.safelimitcalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.safelimitcalculator.data.entity.SettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Query("SELECT * FROM settings WHERE id = 1")
    fun getSettings(): Flow<SettingsEntity?>

    @Query("SELECT * FROM settings WHERE id = 1")
    suspend fun getSettingsOnce(): SettingsEntity?

    @Query("SELECT reserve FROM settings LIMIT 1")
    suspend fun getReserve(): Float?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(settings: SettingsEntity)

    @Update
    suspend fun update(settings: SettingsEntity)
}