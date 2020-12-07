package com.example.skilltracker.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.skilltracker.database.entity.*

// TODO: Add updates and deletes as needed
/**
 * adding suspend to functions that need to run asynchronously
 */
@Dao
interface SkillDao {

    /* QUERIES */
    /**
     * Return all SkillSet data from database
     * @return LiveData with list of SkillSets
     */
    @Query("SELECT * FROM SkillSet")
    fun getAll(): LiveData<List<SkillSet>>

    /**
     * Deletes all data in SkillSet table
     */
    @Query("DELETE FROM SkillSet")
    suspend fun nukeTable()

    @Query("DELETE FROM Skill")
    suspend fun nukeSkillTable()

    @Query("DELETE FROM Task")
    suspend fun nukeTaskTable()

    @Query("DELETE FROM SkillSetSkillCrossRef")
    suspend fun nukeSkillSetSkillCrossRefTable()

    @Query("DELETE FROM SkillTaskCrossRef")
    suspend fun nukeSkillTaskCrossRefTable()
    /**
     * Return all Skills data from database
     * @return LiveData with list of Skills
     */
    @Query("SELECT * FROM Skill")
    fun getAllSkills(): LiveData<List<Skill>>

    /**
     * Return all Task data from database
     * @return LiveData with list of Tasks
     */
    @Query("SELECT * FROM Task")
    fun getAllTasks(): LiveData<List<Task>>

    /**
     * Return Join data between SkillSet and Skill tables from database
     * @return LiveData with list of SkillSet with related Skill
     */
    @Transaction
    @Query("SELECT * FROM SkillSet")
    fun getAllSkillSetWithSkills(): LiveData<List<SkillSetWithSkills>>

    /**
     * Return Join data between SkillSet and Skill tables from database with specific
     * skillSetId
     * @return LiveData with a SkillSet with List of Skills
     */
    @Transaction
    @Query("SELECT * FROM SkillSet WHERE skillSetId=:skillSetId")
    fun getSpecificSkillSetWithSkills(skillSetId: Long): LiveData<List<SkillSetWithSkills>>


    @Transaction
    @Query("SELECT * FROM Skill WHERE skillId=:skillId")
    fun getSpecificSkillWithTasks(skillId: Long): LiveData<List<SkillWithTasks>>

    /**
     * Return Join data between Skill and Task tables from database
     * @return LiveData with list of Skill with related Tasks
     */
    @Transaction
    @Query("SELECT * FROM Skill")
    fun getAllSkillWithTasks(): LiveData<List<SkillWithTasks>>

    @Transaction
    @Query("SELECT * FROM Skill s JOIN SkillSetSkillCrossRef ssXRef ON ssXRef.skillId = s.skillId WHERE ssXRef.skillSetId = :skillSetId")
    fun getSkillsFromJoin(skillSetId: Long): LiveData<List<Skill>>


    /* INSERTS */
    /**
     * Insert SkillSet data into database
     * @param skillSet SkillSets to be added to SkillSet table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg skillSet: SkillSet): List<Long>

    /**
     * Insert Skill data into database
     * @param skill Skills to be added to Skill table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg skill: Skill): List<Long>

    /**
     * Insert Task data into database
     * @param task Task to be added to Task table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg task: Task): List<Long>

    /**
     * Insert join data between Skillset and Skill into database
     * @param join data be added to SkillSetSkillCrossRef table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg join: SkillSetSkillCrossRef)

    /**
     * Insert SkillSet data into database
     * @param join data to be added to SkillTaskCrossRef table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg join: SkillTaskCrossRef)

    /* DELETES */
    /**
     * Delete SkillSet data from database
     * @param skillSet SkillSets to be removed from SkillSet table
     */
    @Delete
    suspend fun delete(skillSet: SkillSet)

    @Delete
    suspend fun deleteSkillSetSkillCrossRef(vararg skillSetSkillCrossRef: SkillSetSkillCrossRef)

    /* UPDATES */
    /**
     * Update SkillSet data in database
     * @param skillSets SkillSet rows to be updated
     */
    @Update
    suspend fun update(vararg skillSets: SkillSet)

    @Update
    suspend fun updateSkill(vararg skills: Skill)

    @Update
    suspend fun updateTask(vararg task: Task)
}