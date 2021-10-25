package io.github.jartool.console.websocket;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.github.jartool.console.common.Constants;
import io.github.jartool.console.queue.ConcurrentEvictingQueue;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketServer
 *
 * @author jartool
 */
@Component
@ServerEndpoint("/console")
public class WebSocketServer implements DisposableBean {

    private static final Log log = LogFactory.get();

    private static ConcurrentEvictingQueue<String> queue;
    @Resource
    public void setQueue(ConcurrentEvictingQueue<String> queue) {
        WebSocketServer.queue = queue;
    }

    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        sessionMap.put(session.getId(), session);
        ThreadUtil.execute(() -> {
            log.info(CharSequenceUtil.format(Constants.Log.WS_CONSOLE), session.getId(), Constants.Code.ON);
            while (sessionMap.get(session.getId()) != null) {
                if (!queue.isEmpty()) {
                    sendMessage(session, queue.poll());
                }
            }
            log.info(CharSequenceUtil.format(Constants.Log.WS_CONSOLE), session.getId(), Constants.Code.OFF);
        });
    }

    @OnClose
    public void onClose(Session session) {
        sessionMap.remove(session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error(Constants.Error.WS_ERROR, error);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info(message);
    }

    /**
     * 发送消息
     * @param session session
     * @param message message
     */
    private void sendMessage(Session session, String message) {
        try {
            if (session != null && session.isOpen() && message != null) {
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error(Constants.Error.WS_ERROR_SEND, e);
        }
    }

    @Override
    public void destroy() throws Exception {
        sessionMap.values().stream().forEach(session -> {
            if (session.isOpen()) {
                try {
                    log.info(Constants.Log.WS_SESSION_CLOSE);
                    session.close();
                } catch (IOException e) {
                    log.error(Constants.Error.WS_ERROR_SESSION_CLOSE, e);
                }
            }
        });
    }
}
