package com.lg.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public class ZLog implements ILog {
    public final static int V = 0;
    public final static int D = 1;
    public final static int I = 2;
    public final static int W = 3;
    public final static int E = 4;
    public final static int L = 5;
    public final static int P = 6;

    public ZLog() {
    }

    public interface Printer {
        public void print(int level, String tag, String msg);
    }

    private static class SystemOutPrinter implements Printer {
        private final static String[] LEVELS = new String[]{"V", "D", "I", "W", "E", "L", "P"};

        public void print(int level, String tag, String msg) {
            System.out.println(LEVELS[level] + "/" + tag + ": " + msg);
        }
    }

    private static class AndroidPrinter implements Printer {

        private final static String[] METHOD_NAMES = new String[]{"v", "d", "i", "w", "e"};

        private final Class<?> mLogClass;
        private final Method[] mLogMethods;
        private final boolean mLoaded;

        public AndroidPrinter() {
            Class logClass = null;
            boolean loaded = false;
            mLogMethods = new Method[METHOD_NAMES.length];
            try {
                logClass = Class.forName("android.util.Log");
                for (int i = 0; i < METHOD_NAMES.length; i++) {
                    mLogMethods[i] = logClass.getMethod(METHOD_NAMES[i], String.class, String.class);
                }
                loaded = true;
            } catch (NoSuchMethodException | ClassNotFoundException e) {
                // Ignore
            }
            mLogClass = logClass;
            mLoaded = loaded;
        }

        public void print(int level, String tag, String msg) {
            try {
                if (mLoaded) {
                    mLogMethods[level].invoke(null, tag, msg);
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                // Ignore
            }
        }
    }

    public final static SystemOutPrinter SYSTEM = new SystemOutPrinter();
    public final static AndroidPrinter ANDROID = new AndroidPrinter();


    private static boolean mUseFormat = false;
    private static int mMinLevel = V;

    private static Set<Printer> mPrinters = new HashSet<>();

    static {
        if (ANDROID.mLoaded) {
            usePrinter(ANDROID, true);
        } else {
            usePrinter(SYSTEM, true);
        }
    }


    public static synchronized Log level(int minLevel) {
        mMinLevel = minLevel;
        return null;
    }

    public static synchronized Log useFormat(boolean yes) {
        mUseFormat = yes;
        return null;
    }

    public static synchronized Log usePrinter(Printer p, boolean on) {
        if (on) {
            mPrinters.add(p);
        } else {
            mPrinters.remove(p);
        }
        return null;
    }


    public synchronized void v(String tag, Object msg, Object... args) {
        log(V, tag, mUseFormat, msg, args);
    }

    @Override
    public synchronized void d(String tag, Object msg, Object... args) {
        log(D, tag, mUseFormat, msg, args);
    }

    @Override
    public synchronized void i(String tag, Object msg, Object... args) {
        log(I, tag, mUseFormat, msg, args);
    }

    @Override
    public synchronized void w(String tag, Object msg, Object... args) {
        log(W, tag, mUseFormat, msg, args);
    }

    @Override
    public synchronized void l(String tag, Object msg, Object... args) {
        log(W, tag, mUseFormat, msg, args);
    }

    @Override
    public synchronized void p(String tag, Object msg, Object... args) {
        log(W, tag, mUseFormat, msg, args);
    }

    public synchronized void e(String tag, Object msg, Object... args) {
        log(E, tag, mUseFormat, msg, args);
    }


    private static void log(int level, String tag, boolean fmt, Object msg, Object... args) {
//        if (level < mMinLevel) {
//            return;
//        }
//        if (mUseTags.length > 0 && tag.equals(msg)) {
//            if (args.length > 1) {
//                print(level, tag, format(fmt, args[0], Arrays.copyOfRange(args, 1, args.length)));
//            } else {
//                print(level, tag, format(fmt, (args.length > 0 ? args[0] : "")));
//            }
//        } else {
        print(level, tag, format(fmt, msg, args));
//        }
    }

    private static String format(boolean fmt, Object msg, Object... args) {
        Throwable t = null;
        if (args == null) {
            // Null array is not supposed to be passed into this method, so it must
            // be a single null argument
            args = new Object[]{null};
        }
        if (args.length > 0 && args[args.length - 1] instanceof Throwable) {
            t = (Throwable) args[args.length - 1];
            args = Arrays.copyOfRange(args, 0, args.length - 1);
        }
        if (fmt && msg instanceof String) {
            String head = (String) msg;
            if (head.indexOf('%') != -1) {
                return String.format(head, args);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(msg == null ? "null" : msg.toString());
        for (Object arg : args) {
            sb.append("\t");
            sb.append(arg == null ? "null" : arg.toString());
        }
        if (t != null) {
            sb.append("\n");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            sb.append(sw.toString());
        }
        return sb.toString();
    }

    final static int MAX_LOG_LINE_LENGTH = 4000;

    private static void print(int level, String tag, String msg) {
        for (String line : msg.split("\\n")) {
            do {
                int splitPos = Math.min(MAX_LOG_LINE_LENGTH, line.length());
                for (int i = splitPos - 1; line.length() > MAX_LOG_LINE_LENGTH && i >= 0; i--) {
                    if (" \t,.;:?!{}()[]/\\".indexOf(line.charAt(i)) != -1) {
                        splitPos = i;
                        break;
                    }
                }
                splitPos = Math.min(splitPos + 1, line.length());
                String part = line.substring(0, splitPos);
                line = line.substring(splitPos);

                for (Printer p : mPrinters) {
                    p.print(level, tag, part);
                }
            } while (line.length() > 0);
        }
    }
}
