package cc.mrbird.system.service;

import cc.mrbird.system.domain.UserEntity;


import java.util.List;
import java.util.Optional;

/**
 * @Auther:
 * @Date: 2019/3/12 09:55
 */
public interface UserEntityService {

     List<UserEntity> findAll();

     List<UserEntity> findAllByParams(UserEntity userEntity);

     void saveUserEntity(UserEntity userEntity)throws Exception ;

     void updateUserEntity(UserEntity userEntity) throws Exception ;

     void deleteUserEntity(String fid) throws Exception;

     UserEntity findByName(String fname);

     UserEntity findById(String fid);

     void registUser(UserEntity userEntity);
}
