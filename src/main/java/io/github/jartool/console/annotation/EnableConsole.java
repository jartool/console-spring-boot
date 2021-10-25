package io.github.jartool.console.annotation;

import io.github.jartool.console.config.ConsoleConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用控制台日志
 *
 * @author jartool
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ConsoleConfig.class)
@Documented
public @interface EnableConsole {

}
