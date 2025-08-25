package unittest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.Before;

/**
 * 基礎測試類別，提供共用的測試設定
 */
public abstract class BaseTest {

    protected String logFilePath;
    
    /**
     * 每個測試執行前設定獨立的日誌檔案
     */
    @Before
    public void setupTestLogger() throws IOException {
        // 為每個測試類別建立獨立的日誌檔案
        String testClassName = this.getClass().getSimpleName();
        logFilePath = System.getProperty("user.dir") + "/logs/" + testClassName + ".log";
        
        // 清空日誌檔案
        clearLogFile();
        
        // 動態配置 log4j2 使用新的檔案
        configureLogger(testClassName, logFilePath);
    }
    
    /**
     * 清空日誌檔案
     */
    private void clearLogFile() throws IOException {
        Path logPath = Paths.get(logFilePath);
        
        // 確保 logs 目錄存在
        Files.createDirectories(logPath.getParent());
        
        // 清空檔案內容
        Files.write(logPath, "".getBytes(), 
                   StandardOpenOption.TRUNCATE_EXISTING, 
                   StandardOpenOption.CREATE);
    }
    
    /**
     * 動態配置 log4j2 使用指定的日誌檔案
     */
    private void configureLogger(String testName, String logFile) {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        LoggerConfig rootLogger = config.getRootLogger();
        
        // 移除所有現有的 Appenders
        rootLogger.getAppenders().forEach((name, appender) -> {
            rootLogger.removeAppender(name);
        });
        
        // 創建新的 FileAppender
        PatternLayout layout = PatternLayout.newBuilder()
                .withPattern("%msg%n")
                .build();
        
        FileAppender fileAppender = FileAppender.newBuilder()
                .setName(testName + "FileAppender")
                .withFileName(logFile)
                .withAppend(false) // 每次都覆蓋
                .setLayout(layout)
                .build();
        
        fileAppender.start();
        
        // 只添加新的 FileAppender (不要 Console)
        rootLogger.addAppender(fileAppender, null, null);
        
        // 確保不會向上傳播到父 Logger
        rootLogger.setAdditive(false);
        
        // 更新配置
        context.updateLoggers();
    }
    
    /**
     * 比較期望檔案與實際日誌檔案
     * @param expectedFilePath 期望檔案的路徑
     * @throws IOException 檔案讀取異常
     */
    protected void assertLogFileEquals(String expectedFilePath) throws IOException {
        File expectedFile = new File(expectedFilePath);
        File logFile = new File(logFilePath);
        
        // 檢查檔案是否存在
        assertTrue("Expected file does not exist: " + expectedFilePath, expectedFile.exists());
        assertTrue("Log file does not exist: " + logFilePath, logFile.exists());
        
        // 可選：使用詳細比較來除錯（如果需要的話）
        // FileComparisonHelper.compareFilesDetailed(expectedFile.toPath(), logFile.toPath());
        
        // 比較檔案內容
        assertTrue("Log file content does not match expected content", 
                  Files.readAllLines(expectedFile.toPath()).equals(Files.readAllLines(logFile.toPath())));
    }
}
