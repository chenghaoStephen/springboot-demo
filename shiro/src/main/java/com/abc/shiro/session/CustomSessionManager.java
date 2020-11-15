package com.abc.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * 自定义SessionManager，避免每次访问接口认证时多次从redis获取数据，第一次取出数据后就放在request中
 */
public class CustomSessionManager extends DefaultWebSessionManager {
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey)sessionKey).getServletRequest();
        }
        if (request != null && sessionId != null) {
            // 从request中获取session
            Session session = (Session)request.getAttribute(sessionId.toString());
            if (session != null) {
                return session;
            }
        }
        // request中没有session，获取并赋值
        Session session = super.retrieveSession(sessionKey);
        if (request != null && sessionId != null) {
            request.setAttribute(sessionId.toString(), session);
        }
        return session;
    }
}
