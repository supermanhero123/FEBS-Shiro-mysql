package cc.mrbird.system.controller;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.util.FileUtil;
import cc.mrbird.common.util.MD5Utils;
import cc.mrbird.system.domain.User;
import cc.mrbird.system.domain.UserEntity;
import cc.mrbird.system.service.UserEntityService;
import cc.mrbird.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller

public class UserEntityController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private UserEntityService userEntityService;

    private static final String ON = "on";

    @RequestMapping("userEntity")
    @RequiresPermissions("userEntity:list")
    public String index(Model model) {
        User user = super.getCurrentUser();
        model.addAttribute("user", user);
        return "system/userEntity/userEntity";
    }

    @RequestMapping("userEntity/checkUserName")
    @ResponseBody
    public boolean checkUserName(String fname, String oldusername) {
        if (StringUtils.isNotBlank(oldusername) && fname.equalsIgnoreCase(oldusername)) {
            return true;
        }
        UserEntity result = userEntityService.findByName(fname);

        return result == null;
    }

    @RequestMapping("userEntity/getUser")
    @ResponseBody
    public ResponseBo getUser(String fid) {
        try {
            UserEntity user = userEntityService.findById(fid);
            return ResponseBo.ok(user);
        } catch (Exception e) {
            log.error("获取用户失败", e);
            return ResponseBo.error("获取用户失败，请联系网站管理员！");
        }
    }

    @Log("获取用户信息")
    @RequestMapping("userEntity/list")
    @RequiresPermissions("userEntity:list")
    @ResponseBody
    public Map<String, Object> userList(QueryRequest request, UserEntity userEntity) {
        List<UserEntity> list = userEntityService.findAllByParams(userEntity);
        return super.selectByPageNumSize(request, () -> userEntityService.findAllByParams(userEntity));
    }

    @RequestMapping("userEntity/regist")
    @ResponseBody
    public ResponseBo regist(UserEntity userEntity) {
        try {
            UserEntity oldUserEntity = userEntityService.findByName(userEntity.getFname());


            if (oldUserEntity != null) {
                return ResponseBo.warn("该用户名已被使用！");
            }
            userEntityService.registUser(userEntity);
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("注册失败", e);
            return ResponseBo.error("注册失败，请联系网站管理员！");
        }
    }

    @Log("更换主题")
    @RequestMapping("userEntity/theme")
    @ResponseBody
    public ResponseBo updateTheme(User user) {
        try {
            this.userService.updateTheme(user.getTheme(), user.getUsername());
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("修改主题失败", e);
            return ResponseBo.error();
        }
    }

    @Log("新增用户")
    @RequiresPermissions("userEntity:add")
    @RequestMapping("userEntity/add")
    @ResponseBody
    public ResponseBo addUser(UserEntity userEntity, Long[] roles) {
        try {
            if (ON.equalsIgnoreCase(userEntity.getFisforbidden()))
                userEntity.setFisforbidden(UserEntity.FISFORBIDDEN_VALID);
            else
                userEntity.setFisforbidden(UserEntity.FISFORBIDDEN_LOCK);
                userEntityService.saveUserEntity(userEntity);
            return ResponseBo.ok("新增用户成功！");
        } catch (Exception e) {
            log.error("新增用户失败", e);
            return ResponseBo.error("新增用户失败，请联系网站管理员！");
        }
    }

    @Log("修改用户")
    @RequiresPermissions("userEntity:update")
    @RequestMapping("userEntity/update")
    @ResponseBody
    public ResponseBo updateUser(UserEntity userEntity, Long[] rolesSelect) {
        try {
            if (ON.equalsIgnoreCase(userEntity.getFisforbidden()))
                userEntity.setFisforbidden(UserEntity.FISFORBIDDEN_VALID);
            else
                userEntity.setFisforbidden(UserEntity.FISFORBIDDEN_LOCK);
                userEntityService.updateUserEntity(userEntity);
            return ResponseBo.ok("修改用户成功！");
        } catch (Exception e) {
            log.error("修改用户失败", e);
            return ResponseBo.error("修改用户失败，请联系网站管理员！");
        }
    }

    @Log("删除用户")
   @RequiresPermissions("userEntity:delete")
    @RequestMapping("userEntity/delete")
    @ResponseBody
    public ResponseBo deleteUsers(String ids) {
        try {
            userEntityService.deleteUserEntity(ids);
            return ResponseBo.ok("删除用户成功！");
        } catch (Exception e) {
            log.error("删除用户失败", e);
            return ResponseBo.error("删除用户失败，请联系网站管理员！");
        }
    }

    @RequestMapping("userEntity/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        User user = getCurrentUser();
        String encrypt = MD5Utils.encrypt(user.getUsername().toLowerCase(), password);

        return user.getPassword().equals(encrypt);
    }

    @RequestMapping("userEntity/updatePassword")
    @ResponseBody
    public ResponseBo updatePassword(String newPassword) {
        try {
            this.userService.updatePassword(newPassword);
            return ResponseBo.ok("更改密码成功！");
        } catch (Exception e) {
            log.error("修改密码失败", e);
            return ResponseBo.error("更改密码失败，请联系网站管理员！");
        }
    }

    @RequestMapping("userEntity/profile")
    public String profileIndex(Model model) {
        User user = super.getCurrentUser();
        user = this.userService.findUserProfile(user);
        String ssex = user.getSsex();
        if (User.SEX_MALE.equals(ssex)) {
            user.setSsex("性别：男");
        } else if (User.SEX_FEMALE.equals(ssex)) {
            user.setSsex("性别：女");
        } else {
            user.setSsex("性别：保密");
        }
        model.addAttribute("user", user);
        return "system/user/profile";
    }

    @RequestMapping("userEntity/getUserProfile")
    @ResponseBody
    public ResponseBo getUserProfile(Long userId) {
        try {
            User user = new User();
            user.setUserId(userId);
            return ResponseBo.ok(this.userService.findUserProfile(user));
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            return ResponseBo.error("获取用户信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("userEntity/updateUserProfile")
    @ResponseBody
    public ResponseBo updateUserProfile(User user) {
        try {
            this.userService.updateUserProfile(user);
            return ResponseBo.ok("更新个人信息成功！");
        } catch (Exception e) {
            log.error("更新用户信息失败", e);
            return ResponseBo.error("更新用户信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("userEntity/changeAvatar")
    @ResponseBody
    public ResponseBo changeAvatar(String imgName) {
        try {
            String[] img = imgName.split("/");
            String realImgName = img[img.length - 1];
            User user = getCurrentUser();
            user.setAvatar(realImgName);
            this.userService.updateNotNull(user);
            return ResponseBo.ok("更新头像成功！");
        } catch (Exception e) {
            log.error("更换头像失败", e);
            return ResponseBo.error("更新头像失败，请联系网站管理员！");
        }
    }
}
