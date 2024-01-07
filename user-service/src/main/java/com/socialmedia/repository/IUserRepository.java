package com.socialmedia.repository;

import com.socialmedia.entity.UserProfile;
import com.socialmedia.entity.enums.EStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends MongoRepository<UserProfile,String> {


    Optional<UserProfile> findByAuthId(Long id);

    Optional<UserProfile> findByUsername(String username);

    List<UserProfile> findByStatus(EStatus status);

    //girilen username için userprofile dönsün
    // active kullanıcıları döndürelim
    // authid'si girilen değerden fazla olanları döndürelim
    // hem id'si girilen değerden fazla ve girilen status değerine göre kullanıcıları getirelim
    @Query("{'username': ?0 }")
    Optional<UserProfile> getUser(String username);

    @Query("{'status': 'ACTIVE' }")
    List<UserProfile> findallActiveUser(String username);

    @Query("{'authId': {$gt: ?0} }")
    List<UserProfile> findUserGtAuthId(Long authId);

    @Query("{$or: [{authId:  {$gt:  ?0 }}, {status:  ?1 }] }")
    List<UserProfile> findByIdAndStatus(Long authId, EStatus status);


}