package pers.coderaji.teez.common.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingFormatArgumentException;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * @author aji
 * @date 2024/7/28 2:34
 * @description Teez日志格式化
 */
public class TeezSimpleFormatter extends Formatter {
    @Override
    public synchronized String format(LogRecord record) {
        //格式化时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        String formattedDate = simpleDateFormat.format(new Date());
        //格式化消息内容
        String message = formatMessage(record);
        //获取报错内容
        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            printWriter.println();
            record.getThrown().printStackTrace(printWriter);
            printWriter.close();
            throwable = printWriter.toString();
        }
        //格式化日志
        String format = "%s [%s] %s %s [TEEZ] -- %s\n";
        return String.format(format,
                formattedDate,
                Thread.currentThread().getName(),
                record.getLevel().getName(),
                record.getLoggerName(),
                message,
                throwable);
    }

    public synchronized String formatMessage(LogRecord record) {
        String message = record.getMessage();
        try {
            message = message.replaceAll("\\{\\}", "%s");
            message = String.format(message, record.getParameters());
        } catch (MissingFormatArgumentException e) {
            throw new RuntimeException("placeholder {} more than argument");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return message;
    }
}