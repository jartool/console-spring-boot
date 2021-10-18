package com.github.jartool.console.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ClassUtil;
import com.github.jartool.console.common.Constants;
import com.github.jartool.console.queue.ConcurrentEvictingQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * LogFilter
 * @author jartool
 * @version 1.0
 * @date 2021/8/3 13:22
 */
@Slf4j
@Component
public class LogFilter extends Filter<ILoggingEvent> implements InitializingBean, ApplicationContextAware {

    private static ConcurrentEvictingQueue<String> queue;
    @Resource
    public void setQueue(ConcurrentEvictingQueue<String> queue) {
        LogFilter.queue = queue;
    }

    private ApplicationContext applicationContext;
    private static String basePackageName;

    @Override
    public FilterReply decide(ILoggingEvent event) {
        String message = event.getFormattedMessage();
        if (CharSequenceUtil.isNotBlank(message) && message.indexOf(Constants.Ignore.IGNORE_MQ_OK) < 0) {
            queue.push(CharSequenceUtil.format(Constants.Log.WS_LOG,
                    LocalDateTimeUtil.format(LocalDateTimeUtil.of(event.getTimeStamp()),Constants.DateFormatter.DATA_YMD_HH24MS_SSS),
                    CharSequenceUtil.format(Constants.Log.WS_LOG_FONT, Constants.Color.HEX_56952A, event.getLevel().levelStr),
                    event.getThreadName(),
                    CharSequenceUtil.format(Constants.Log.WS_LOG_FONT, Constants.Color.HEX_139CA2, ClassUtil.getShortClassName(event.getLoggerName())),
                    message));
            IThrowableProxy throwable = event.getThrowableProxy();
            if (throwable != null) {
                queue.push(CharSequenceUtil.format(Constants.Log.WS_LOG_THROWABLE, throwable.getClassName(), throwable.getMessage()));
                for (StackTraceElementProxy stackTrace : throwable.getStackTraceElementProxyArray()) {
                    String stackTraceString = stackTrace.getSTEAsString();
                    StackTraceElement stackTraceElement = stackTrace.getStackTraceElement();
                    String className = stackTraceElement.getClassName();
                    if (className.indexOf(basePackageName) > -1) {
                        String local = stackTraceElement.getFileName() + StrPool.COLON + stackTraceElement.getLineNumber();
                        queue.push(StrPool.TAB + stackTraceString.replace(local, CharSequenceUtil
                                .format(Constants.Log.WS_LOG_FONT, Constants.Color.HEX_287ADD, local)));
                    } else {
                        queue.push(StrPool.TAB + stackTraceString);
                    }
                }
            }
        }
        return FilterReply.NEUTRAL;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Object> annotatedBeans = applicationContext.getBeansWithAnnotation(SpringBootApplication.class);
        LogFilter.basePackageName = ClassUtils.getPackageName(annotatedBeans.values().toArray()[0].getClass());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
