package com.lg.log;

import com.lg.date.IDateFormat;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public class Log {
    public Log() {
    }

    public Log(String task, String message) {
        this.task = task;
        this.message = message;
        this.currentThread = Thread.currentThread().getName();
        this.time = System.currentTimeMillis() + "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCurrentThread() {
        return currentThread;
    }

    public void setCurrentThread(String currentThread) {
        this.currentThread = currentThread;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    private String message;
    private String time;
    private String currentThread;
    private String task;

    private String hasHandle;

    private String sql = "CREATE TABLE `mysql`.`<logs>` (\n" +
            "\t`id` int NOT NULL AUTO_INCREMENT,\n" +
            "\t`message` text NOT NULL,\n" +
            "\t`time` text,\n" +
            "\t`currentThread` text,\n" +
            "\t`task` text,\n" +
            "\tPRIMARY KEY (`id`)\n" +
            ") COMMENT='';";

    public void print() {
        Logs.p("<========================================================>");
        Logs.p("<============log time:" + IDateFormat.formatDate(this.getTime(), IDateFormat.YYMMDD_HHMMSS));
        Logs.p("<============thread:" + this.getCurrentThread());
        Logs.p("<============task:" + this.getTask());
        Logs.p("<============message:" + this.getMessage());
        Logs.p("<========================================================>");

    }

    public String getHasHandle() {
        return hasHandle;
    }

    public void setHasHandle(String hasHandle) {
        this.hasHandle = hasHandle;
    }
}
