package UnitTest;

import com.lg.log.LogConfig;
import com.lg.log.Logs;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public class LogTest {
    public static void main(String[] args) {
        LogConfig.initZlog();
//        LogConfig.defaultLog();
        LogConfig.configLogLevel(1);
        Logs.w("hello world");
        Logs.i("fadfafa");
    }
}
