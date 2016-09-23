package UnitTest.task;

import com.lg.log.LogConfig;
import com.lg.log.Logs;
import com.lg.task.ITask;
import com.lg.task.ParserTaskExecutor;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public class TaskTest {
    public static void main(String[] args) {
        LogConfig.initZlog();
        LogConfig.configLogLevel(0);
        ITask iTask = ITask.task(() -> Logs.d("hello world"));
//        iTask.cancelTask();
        ParserTaskExecutor.INSTANCE.execute(iTask);
        try {
            ParserTaskExecutor.INSTANCE.printStatus();
//            Logs.i(iTask.isTaskCanceled() + "");
            Thread.sleep(1000 * 30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
