package com.hl.insmanager.util;

public class JxlsUtil {
    private static final JxlsUtil me = new JxlsUtil();

    private JxlsUtil() {

    }

    /**
     * 获取工具类实例
     *
     * @return
     */
    public static JxlsUtil me() {
        return me;
    }

    /**
     * 如果字符串为{@code null}、空字符串或仅包含空白字符, 则返回false
     *
     * @param text 要进行检查的字符串
     */
    public boolean hasText(String text) {
        return !(text == null || text.length() == 0);

    }

}
