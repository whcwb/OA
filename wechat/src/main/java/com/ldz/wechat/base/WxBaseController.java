package com.ldz.wechat.base;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.model.SysYh;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.model.CoachManagement;
import com.ldz.wechat.model.TraineeInformation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**  abstract class BaseController<T, PK extends Serializable>
 * Created by Administrator on 2018/9/16.
 */
public abstract class WxBaseController<T, PK extends Serializable> extends BaseController <T,PK> {
    /**
     * 获取当前登录学员用户信息
     * @return
     */
    public static TraineeInformation getTraineeCurrentUser(boolean require) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        TraineeInformation userInfo = (TraineeInformation) request.getAttribute("wechatTraineeUserInfo");
        RuntimeCheck.ifTrue(require && userInfo == null, "当前登录用户未空！");
        return userInfo;
    }

    /**
     * 获取当前登录教练员的用户信息
     * @param require
     * @return
     */
    public static CoachManagement getCoachCurrentUser(boolean require) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        CoachManagement userInfo = (CoachManagement) request.getAttribute("wechatCoachUserInfo");
        RuntimeCheck.ifTrue(require && userInfo == null, "当前登录用户未空！");
        return userInfo;
    }

    public static SysYh getBossCurrentUser(boolean require) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysYh userInfo = (SysYh) request.getAttribute("wechatUserInfo");
        RuntimeCheck.ifTrue(require && userInfo == null, "当前登录用户未空！");
        return userInfo;
    }

}
