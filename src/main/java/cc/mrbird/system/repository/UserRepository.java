package cc.mrbird.system.repository;

import cc.mrbird.system.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Auther:
 * @Date:
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity,String>, JpaSpecificationExecutor<UserEntity> {

//        List<User> users = jdbcTemplate.queryForList(sql.toString(), User.class, username, password);

         @Transactional
         @Query(value="select * from t_pm_user  where if(?1!='',fname = ?1, 1=1) and fisdelete = 0",nativeQuery = true)
         UserEntity  findByName(String fname);

        @Transactional
        @Query(value= "select * from t_pm_user  where if(?1 != '', fid = ?1, 1=1) and fisdelete = 0 ",nativeQuery = true)
        UserEntity  findByFid(String fid);

        @Transactional
        @Query(value = "select * from t_pm_user  where if(?1 !='',fname=?1,1=1) and if(?2 !='',fcell=?2,1=1)" + "and if(?3 !='',fisforbidden=?3,1=1) and fisdelete = 0"  ,nativeQuery = true)
        List<UserEntity> findAllParams(String fname, String fcell, String fisforbidden);
}
