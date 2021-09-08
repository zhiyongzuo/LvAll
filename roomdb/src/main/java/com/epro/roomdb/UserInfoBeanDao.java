package com.epro.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * @author zhijiang
 * @Date 2019/6/25
 */
@Dao
public interface UserInfoBeanDao {
    @Insert
    void insert(UserInfoBean userInfoBean);

    @Delete
    int deleteAll(UserInfoBean[] userInfoBeans);

    @Update
    int update(UserInfoBean userInfoBean);

    @Query("SELECT * FROM user_info WHERE csrid = :csrId")
    UserInfoBean findUserInfoBeanByCsrId(String csrId);

//    @Query("SELECT * FROM user_info")
//    Flowable<List<UserInfoBean>> findUserInfoBean();

//    @Query("select * from (select * from user_info order by logindate desc) limit 0,1")
//    Single<UserInfoBean> findUserInfoBean();
//
//    @Query("SELECT * FROM user_info WHERE csrid = :csrId")
//    Single<UserInfoBean> findUserInfoByCsrId(String csrId);
////    String findO
}
