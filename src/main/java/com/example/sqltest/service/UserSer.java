package com.example.sqltest.service;


import com.example.sqltest.bean.*;
import com.example.sqltest.enums.UserEnum;
import com.example.sqltest.util.MyUtils;
import com.example.sqltest.vo.*;
import com.example.sqltest.dao.UserDao;
import com.example.sqltest.util.AESUtil;
import com.example.sqltest.util.MD5Util;
import com.example.sqltest.util.VerifyCodeUtils;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.io.IOException;
import java.security.Key;
import java.util.Date;

/**
 * Created by T016 on 2019/2/22.
 */
@Service
@PropertySource(value = {"classpath:/salt.properties"}, encoding = "UTF-8")
public class UserSer {

    @Value("${saltKey}")
    private String saltKey;

    private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    private UserDao userDao;

    @Autowired
    private ActivitiUserSer activitiUserSer;

    @Autowired
    private ActIdGroupSer actIdGroupSer;

    @Autowired
    private ActIdMembershipSer actIdMembershipSer;

    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterVo registerVo) throws Exception {
        if(StringUtils.isEmpty(registerVo.getUserName()) || StringUtils.isEmpty(registerVo.getPassword())){
            throw new RuntimeException("Error, please re-register!");
        }
        if(!checkUserName(registerVo.getUserName())){
            UserBean userBean = new UserBean();
            BeanUtils.copyProperties(registerVo, userBean);
            userBean.setPassword(MD5Util.md5(userBean.getPassword(),saltKey));
            activitiUserSer.register(registerVo);
            userDao.save(userBean);
        }
    }

    public boolean checkUserName(String userName){
        if(null != userDao.findByUserName(userName)){
            return true;
        }
        return false;
    }
    public String getUserName(String userId){
        return userDao.findById(userId).get().getUserName();
    }


    public Object login(LoginVo loginVo) throws Exception {
        if(StringUtils.isEmpty(loginVo.getUserName()) || StringUtils.isEmpty(loginVo.getPassword())){
            throw new RuntimeException("账号或密码为空");
        }
        // 验证码内容传输过程中使用对称加密的方式加密,判断时先用秘钥解密,再通过base64反译跟用户输入内容作对比
        if(StringUtils.isEmpty(loginVo.getVerCode()) ||
                !loginVo.getVerCode().equalsIgnoreCase(new String(Base64.decodeBase64(AESUtil.decrypt(loginVo.getImgCode(), saltKey)), "utf-8"))){
            throw new RuntimeException("验证码输入错误,请重新输入");
        }
        loginVo.setPassword(MD5Util.md5(loginVo.getPassword(),saltKey));
        UserBean userBean = new UserBean();
        BeanUtils.copyProperties(loginVo, userBean);
        Example<UserBean> example = Example.of(userBean);
        UserBean userResultBean = userDao.findOne(example).get();
        // 对admin单独进行处理
        ActIdGroupBean actIdGroupBean = actIdGroupSer.findByUserName(userResultBean.getUserName());
        if(null != actIdGroupBean){
            if(UserEnum.ADMIN.getName().equals(actIdGroupBean.getType())) {
                AdminVo adminVo = new AdminVo();
                adminVo.setAdminPassword(userResultBean.getPassword());
                return getKey(adminVo);
            }
        }
        return getKey(userResultBean.getUserid());
    }

        public String getKey(String sub){
        Date expirationDate = new Date();
        expirationDate.setTime(expirationDate.getTime()+300*60*1000);

        String jws = Jwts.builder().setSubject(sub).signWith(key).setExpiration(expirationDate).compact();
        jws = AESUtil.encrypt(jws,saltKey);

        return jws;
    }

    public AdminVo getKey(AdminVo adminVo){

        // 对admin单独处理
        return adminVo;
    }

    public void checkJjwt(String Authorization){
        try {
            Authorization = AESUtil.decrypt(Authorization,saltKey);
            Jwts.parser().setSigningKey(key).parseClaimsJws(Authorization);
        } catch (JwtException e) {
            throw new RuntimeException("登录过期,请先登录");
        }
    }

    public ImgVo getImg() throws IOException {
        ImgVo imgVo = VerifyCodeUtils.VerifyCode(120,40,4);
        imgVo.setCode(AESUtil.encrypt(imgVo.getCode(), saltKey));
        return imgVo;
    }

    public InfoVo getInfo(String userId){

        UserBean userBean = userDao.findById(userId).get();
        ActivitiUserBean activitiUserBean = activitiUserSer.findById(userBean.getUserName());
        ActIdGroupBean actIdGroupBean = actIdGroupSer.findByUserName(activitiUserBean.getUserName());
        InfoVo infoVo = new InfoVo();
        BeanUtils.copyProperties(userBean, infoVo);
        BeanUtils.copyProperties(activitiUserBean, infoVo);
        if(null != actIdGroupBean) {
            BeanUtils.copyProperties(actIdGroupBean, infoVo);
        } else {
            infoVo.setGroupName("暂无分组");
        }
        return infoVo;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(UpdateInfoVo updateInfoVo, String userId) throws Exception {
        UserBean userBean = userDao.findById(userId).get();
        if (!StringUtils.isEmpty(updateInfoVo.getGroupId())){
            ActIdMembershipBean actIdMembershipBean = new ActIdMembershipBean();
            actIdMembershipBean.setGroupId(updateInfoVo.getGroupId());
            actIdMembershipBean.setUserName(userBean.getUserName());
            actIdMembershipSer.save(actIdMembershipBean);
        }
        if (!StringUtils.isEmpty(updateInfoVo.getPassword())) {
            updateInfoVo.setPassword(MD5Util.md5(updateInfoVo.getPassword(), saltKey));
        }
        MyUtils.copyProperties(updateInfoVo, userBean, true, "userName");
        MyUtils.copyProperties(updateInfoVo, activitiUserSer.findById(userBean.getUserName()), true, "userName");
    }

    public UserBean findById(String id){
        return userDao.findById(id).get();
    }

    public static Key getKey() {
        return key;
    }

    public String getUserId(String Authorization){
        String jws = AESUtil.decrypt(Authorization,saltKey);
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jws).getBody().getSubject();
    }
}
