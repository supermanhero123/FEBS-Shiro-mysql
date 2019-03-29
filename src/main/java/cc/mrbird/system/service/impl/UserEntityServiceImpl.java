package cc.mrbird.system.service.impl;

import cc.mrbird.common.util.CompareUtils;
import cc.mrbird.common.util.DifferenceAttr;
import cc.mrbird.common.util.MD5Utils;
import cc.mrbird.system.domain.UserEntity;
import cc.mrbird.system.repository.UserRepository;
import cc.mrbird.system.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Auther:
 * @Date:
 */
@Service
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<UserEntity> findAllByParams(UserEntity userEntity) {
        return userRepository.findAllParams(userEntity.getFname(), userEntity.getFcell(), userEntity.getFisforbidden());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUserEntity(UserEntity userEntity) throws Exception {
        //设置id
        userEntity.setFid(UUID.randomUUID().toString().replace("-", ""));
        userEntity.setFpassword(MD5Utils.md5(userEntity.getPassWord()));
        userEntity.setFcreatetime(new Date());
        userEntity.setFisdelete(0);
        userRepository.save(userEntity);
    }

    @Modifying
    @Transactional
    public void updateUserEntity(UserEntity userEntity) throws Exception {
        UserEntity user = userRepository.findByFid(userEntity.getFid());
        //替换不同的属性值
        CompareUtils attrCompare = new CompareUtils();
        userEntity.setFlastupdatetime(new Date());
        DifferenceAttr attr = attrCompare.compareValue(user, userEntity);
        System.out.println(user.toString());
    }

    // 该处删除为逻辑删除 更改状态 为1
    @Override
    @Modifying
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserEntity(String fid) throws Exception {
        List<String> list = Arrays.asList(fid.split(","));
        for(String  id : list) {
            UserEntity userEntity = userRepository.findByFid(id);
            userEntity.setFisdelete(1);
        }
    }

    @Override
    public UserEntity findByName(String fname) {
        return userRepository.findByName(fname);
    }

    @Override
    public UserEntity findById(String fid) {
        return userRepository.findByFid(fid);
    }


    @Transactional
    public void registUser(UserEntity userEntity) {
        UserEntity user = new UserEntity();
        user.setFnumber("管理员");  //角色名称
        user.setFpassword(MD5Utils.md5(userEntity.getPassWord()));
        user.setCuflag(0);  //0为添加  1为修改
        user.setFisdelete(0); //删除标记    0 未删除   1删除
        user.setFpersonid("null");// 关联职员id    null 表示为 关联
        user.setFdeforgunitid("null");   //缺省组织ID
        user.setFomsflag(0);//OMS提取标志 0 为未 提取  1为已提取
        user.setFcrmflag(0); //CRM提取标志 0 为 未提取 1未已提取
        user.setFisforbidden(UserEntity.FISFORBIDDEN_VALID);
        userRepository.save(user);
    }
}
