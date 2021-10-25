package io.github.jartool.console.util;

import cn.hutool.crypto.SecureUtil;

/**
 * SecurityUtil
 *
 * @author jartool
 */
public class SecurityUtil {

    public static String encrypt(String text) {
        StringBuilder sb = new StringBuilder();
        text.chars().forEach(c -> sb.append(SecureUtil.md5(String.valueOf(c))));
        return SecureUtil.md5(sb.toString());
    }
}
