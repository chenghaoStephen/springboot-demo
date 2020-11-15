package com.abc.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomSessionDao extends AbstractSessionDAO {

    @Autowired
    private RedisConfig redisConfig;

    //自定义key的前缀，方面后面查询
    private final String SHIRO_SESSION_PREFIX = "shiro-session:";

    private byte[] getkey(String key){
        //返回key
        return (SHIRO_SESSION_PREFIX + key).getBytes();
    }

    private void saveSession(Session session){
        //保存session方法
        if(session != null || session.getId() != null){
            byte[] key = getkey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            redisConfig.set(key, value);
            redisConfig.expire(key,1800);
        }
    }

    /**
     * 初次访问时调用
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId =generateSessionId(session);
        assignSessionId(session, sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("read session");
        if (sessionId == null) {
            return null;
        }
        byte[] key = getkey(sessionId.toString());
        byte[] value = redisConfig.get(key);
        return (Session) SerializationUtils.deserialize(value);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if(session == null || session.getId() == null){
            return;
        }
        byte[] key = getkey(session.getId().toString());
        redisConfig.del(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys = redisConfig.keys(SHIRO_SESSION_PREFIX);
        Set<Session> sessions = new HashSet<>();
        if(CollectionUtils.isEmpty(keys)){
            //如果为空的集合就直接返回
            return sessions;
        }
        //如果不是就遍历
        for(byte[] key : keys){
            Session session = (Session) SerializationUtils.deserialize(key);
            sessions.add(session);
        }
        return sessions;
    }
}
