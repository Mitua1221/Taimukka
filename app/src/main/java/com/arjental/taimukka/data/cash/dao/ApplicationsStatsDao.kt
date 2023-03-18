package com.arjental.taimukka.data.cash.dao

import androidx.room.*
import com.arjental.taimukka.entities.data.cash.ApplicationForegroundMarksCash
import com.arjental.taimukka.entities.data.cash.ApplicationInfoCash
import com.arjental.taimukka.entities.data.cash.ApplicationNotificationsMarksCash
import com.arjental.taimukka.entities.data.cash.ApplicationStatsCash

@Dao
interface ApplicationsStatsDao {

//    @Query("UPDATE applications_stats SET is_active = 0")
//    suspend fun setAllUsersInactive()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setApplication(app: ApplicationInfoCash)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setApplications(appList: List<ApplicationInfoCash>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setApplicationForegroundMarks(timeMarksList: List<ApplicationForegroundMarksCash>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setApplicationNotificationsMarks(notificationMarks: List<ApplicationNotificationsMarksCash>)

    @Transaction
    @Query("SELECT * FROM applications_info")
    suspend fun getApplications(): List<ApplicationStatsCash>

//    @Transaction
//    @Query("SELECT DISTINCT * FROM applications_info INNER JOIN applications_time_marks " +
//            "ON applications_info.app_package = applications_time_marks.app_package_sync " +
//            "WHERE applications_time_marks.`from` >= (:from) " +
//            "AND  applications_time_marks.`to` <= (:to) ")
//    suspend fun getApplications(from: Long, to: Long): List<ApplicationStatsCash>

    @Query("DELETE FROM applications_info")
    suspend fun clear()

    @Query("SELECT * FROM applications_info WHERE app_package = :appPackage")
    suspend fun getApplication(appPackage: String): ApplicationStatsCash?

//    @Update
//    suspend fun updateCashedUser(user: CashedUserEntity)
//
//    @Query("UPDATE cashed_user_entity SET is_active = 1 WHERE`key`=(:key)")
//    suspend fun setCashedUserIsCurrent(key: String)
//
//    @Query("SELECT * FROM cashed_user_entity WHERE is_user_deleted != 1")
//    fun subscribeOnCashedUsers(): LiveData<List<CashedUserEntity>>
//

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