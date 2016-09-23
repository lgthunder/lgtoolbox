package com.lg.log;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public class LogConfig {


    public static void defaultLog() {
        initLog(new ConsoleLog());
    }

    public static void initZlog(){
        initLog(new ZLog());
    }

    public static void initLog(ILog iLog) {
        Logs.setLogProxy(iLog);
    }


    public static void configLogLevel(int level) {
        Logs.configLevel(level);
    }

}
