package io.github.jartool.console.common;

/**
 * Constants
 *
 * @author jartool
 */
public class Constants {

    public interface View {
        public static final String REDIRECT = "redirect:";
        public static final String VIEW_CONSOLE = "terminal/console.html";
    }

    public interface DateFormatter {
        public static final String DATA_YMD_H24MS = "yyyy-MM-dd HH:mm:ss";
        public static final String DATA_YMD_HH24MISS = "yyyy-mm-dd hh24:mi:ss";
        public static final String DATA_YMD_HH24MS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    }

    public interface Log {
        public static final String WS_CONSOLE = "Console[{}]:{}";
        public static final String WS_LOG = "{}  {} --- [{}] {} : {}";
        public static final String WS_LOG_THROWABLE = "{}: {}";
        public static final String WS_LOG_FONT = "<font color='{}'>{}</font>";
        public static final String WS_SESSION_CLOSE = "ws-session-close";
    }

    public interface Code {
        public static final String ON = "on";
        public static final String OFF = "off";
    }

    public interface Error {
        public static final String WS_ERROR = "error-ws";
        public static final String WS_ERROR_SEND = "error-ws-send";
        public static final String WS_ERROR_SESSION_CLOSE = "error-session-close";
    }

    public interface Color {
        public static final String HEX_56952A = "#56952A";
        public static final String HEX_139CA2 = "#139CA2";
        public static final String HEX_287ADD = "#287ADD";
        public static final String HEX_06DE08 = "#06DE08";
    }

    public interface Rep {
        public static final String CODE = "code";
        public static final String SECRET = "secret";
    }

    public interface Attribute {
        public static final String AUTH_URL = "authUrl";
        public static final String AUTH_KEY = "authKey";
        public static final String AUTH_ENABLE = "authEnable";
    }
}
