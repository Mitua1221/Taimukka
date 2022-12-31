package com.arjental.taimukka.data.cash.dao

import androidx.room.*
import com.arjental.taimukka.entities.data.cash.ApplicationInfoCash
import com.arjental.taimukka.entities.data.cash.ApplicationStatsCash
import com.arjental.taimukka.entities.data.cash.ApplicationTimeMarksCash

@Dao
interface ApplicationsStatsDao {

//    @Query("UPDATE applications_stats SET is_active = 0")
//    suspend fun setAllUsersInactive()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setApplication(app: ApplicationInfoCash)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setApplications(appList: List<ApplicationInfoCash>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setApplicationTimeMarks(timeMarksList: List<ApplicationTimeMarksCash>)

    @Transaction
    @Query("SELECT * FROM applications_info")
    suspend fun getApplications(): List<ApplicationStatsCash>

//    @Update
//    suspend fun updateCashedUser(user: CashedUserEntity)
//
//    @Query("UPDATE cashed_user_entity SET is_active = 1 WHERE`key`=(:key)")
//    suspend fun setCashedUserIsCurrent(key: String)
//
//    @Query("SELECT * FROM cashed_user_entity WHERE is_user_deleted != 1")
//    fun subscribeOnCashedUsers(): LiveData<List<CashedUserEntity>>
//
//    @Query("SELECT * FROM cashed_user_entity WHERE is_user_deleted != 1")
//    fun getCashedUsersList(): List<CashedUserEntity>
//
//    @Query("SELECT * FROM cashed_user_entity WHERE is_active = 1")
//    suspend fun getCurrentCashedUser(): CashedUserEntity?
//
//    @Query("SELECT * FROM cashed_user_entity WHERE `key`=(:userKey)")
//    suspend fun getCashedUser(userKey: String): CashedUserEntity?
//
//    @Query("SELECT * FROM cashed_user_entity WHERE `server`=(:url) AND is_user_deleted != 1")
//    suspend fun getNotDeletedCashedUser(url: String): CashedUserEntity?
//
//    @Query("UPDATE cashed_user_entity SET is_user_deleted = 1, is_active = 0 WHERE `key` = :key")
//    suspend fun markCashUserDeleted(key: String)
//
//    @Query("UPDATE cashed_user_entity SET token = (:newToken), employee_id = (:employeeId), exd_client_id = (:extId), user_permissions = (:userPermissions) WHERE`key`=(:key)")
//    suspend fun updateAccountProperties(key: String, newToken: String, employeeId: String, extId: String, userPermissions: UserPermissions)
//
//    @Query("UPDATE cashed_user_entity SET is_active = 0, is_user_deleted = 1")
//    suspend fun markAllCashedUsersDeleted()
//
//    @Query("DELETE FROM cashed_user_entity WHERE `key`=(:key) AND is_active = 0 AND is_user_deleted = 1")
//    suspend fun eraseCashedUser(key: String)
//
//    @Query("UPDATE cashed_user_entity SET dss_credentials = :dssCredentials, token = (:newToken), refresh_token = (:newRefreshToken)  WHERE `key` = :key")
//    suspend fun changeDssCredentials(key: String, dssCredentials: DssCredentials, newToken: String, newRefreshToken: String)

}