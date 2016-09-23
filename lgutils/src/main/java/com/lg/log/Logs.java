package com.lg.log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public class Logs {
    //    public static final int D = 0; // 调试
//    public static final int I = 1; // 基本流程信息
//    public static final int W = 2; // 特殊位置
//    public static final int L = 3; // 日志输出
    public final static int V = 0;
    public final static int D = 1;
    public final static int I = 2;
    public final static int W = 3;
    public final static int E = 4;
    public final static int L = 5;
    public final static int P = 6;

    public static void configLevel(int level) {
        LEVEL = level;
    }

    public static int getLEVEL() {
        return LEVEL;
    }

    private static int LEVEL = -1;

    private static ILog logs;

    public static void setLogProxy(ILog logProxy) {
        logs = logProxy;
    }


    private static void checkLogProxy() {
        if (logs == null) throw new RuntimeException("should call LogConfig initLog first");
    }

    public static void i(String tag, Object msg, Object... args) {
        checkLogProxy();
        if (LEVEL <= I) {
            logs.i(tag, msg, args);
        }
    }

    public static void d(String tag, Object msg, Object... args) {
        checkLogProxy();
        if (LEVEL <= D) {
            logs.d(tag, msg, args);
        }
    }

    public static void w(String tag, Object msg, Object... args) {
        checkLogProxy();
        if (LEVEL <= W) {
            logs.w(tag, msg, args);
        }
    }

    public static void e(String tag, Object msg, Object... args) {
        checkLogProxy();
        if (LEVEL <= E) {
            logs.e(tag, msg, args);
        }
    }

    public static void l(String tag, Object msg, Object... args) {
        checkLogProxy();
        if (LEVEL <= L) {
            logs.l(tag, msg, args);
        }
    }

    public static void p(String tag, Object msg, Object... args) {
        checkLogProxy();
        if (LEVEL <= P) {
            logs.p(tag, msg, args);
        }
    }


    public static void i(Object msg, Object... args) {
        checkLogProxy();
        if (LEVEL <= I) {
            logs.i(tag(), msg, args);
        }
    }

    public static void d(Object msg, Object... args) {
        checkLogProxy();
        if (LEVEL <= D) {
            logs.d(tag(), msg, args);
        }

    }

    public static void w(Object msg, Object... args) {
        checkLogProxy();
        if (LEVEL <= W) {
            logs.w(tag(), msg, args);
        }
    }

    public static void e(Object msg, Object... args) {
        checkLogProxy();
        if (LEVEL <= E) {
            logs.e(tag(), msg, args);
        }
    }

    public static void l(Object msg, Object... args) {
        checkLogProxy();
        logs.l(tag(), msg, args);
        saveLog(msg, args);
    }

    public static void p(Object msg, Object... args) {
        checkLogProxy();
        logs.p(tag(), msg, args);
    }

    public static void saveLog(Object msg, Object... args) {

    }

    private final static Pattern ANONYMOUS_CLASS = Pattern.compile("\\$\\d+$");
    private final static int STACK_DEPTH = 3;
    private final static Map<String, String> mTags = new HashMap<>();

    private static String tag() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length < STACK_DEPTH) {
            throw new IllegalStateException
                    ("Synthetic stacktrace didn't have enough elements: are you using proguard?");
        }
        String className = stackTrace[STACK_DEPTH - 1].getClassName();
        String tag = mTags.get(className);
        if (tag != null) {
            return tag;
        }

        try {
            Class<?> c = Class.forName(className);
            for (String f : mUseTags) {
                try {
                    Field field = c.getDeclaredField(f);
                    if (field != null) {
                        field.setAccessible(true);
                        Object value = field.get(null);
                        if (value instanceof String) {
                            mTags.put(className, (String) value);
                            return (String) value;
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException |
                        IllegalStateException | NullPointerException e) {
                    //Ignore
                }
            }
        } catch (ClassNotFoundException e) { /* Ignore */ }

        // Check class field useTag, if exists - return it, otherwise - return the generated tag
        Matcher m = ANONYMOUS_CLASS.matcher(className);
        if (m.find()) {
            className = m.replaceAll("");
        }
        return className.substring(className.lastIndexOf('.') + 1);
    }

    private static String[] mUseTags = new String[]{"tag", "TAG"};

    public static synchronized Log useTags(String[] tags) {
        mUseTags = tags;
        return null;
    }
}
