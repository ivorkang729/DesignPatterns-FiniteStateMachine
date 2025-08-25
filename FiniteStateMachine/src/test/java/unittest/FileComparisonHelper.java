package unittest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileComparisonHelper {
    
    public static void compareFilesDetailed(Path expectedPath, Path actualPath) throws IOException {
        List<String> expectedLines = Files.readAllLines(expectedPath);
        List<String> actualLines = Files.readAllLines(actualPath);
        
        System.out.println("=== 檔案比較詳細資訊 ===");
        System.out.println("Expected file: " + expectedPath);
        System.out.println("Actual file: " + actualPath);
        System.out.println("Expected lines count: " + expectedLines.size());
        System.out.println("Actual lines count: " + actualLines.size());
        
        System.out.println("\n=== Expected content ===");
        for (int i = 0; i < expectedLines.size(); i++) {
            String line = expectedLines.get(i);
            System.out.println(String.format("Line %d: [%s] (length: %d)", i+1, line, line.length()));
            printCharCodes(line);
        }
        
        System.out.println("\n=== Actual content ===");
        for (int i = 0; i < actualLines.size(); i++) {
            String line = actualLines.get(i);
            System.out.println(String.format("Line %d: [%s] (length: %d)", i+1, line, line.length()));
            printCharCodes(line);
        }
        
        System.out.println("\n=== Line by line comparison ===");
        int maxLines = Math.max(expectedLines.size(), actualLines.size());
        for (int i = 0; i < maxLines; i++) {
            String expectedLine = i < expectedLines.size() ? expectedLines.get(i) : "<MISSING>";
            String actualLine = i < actualLines.size() ? actualLines.get(i) : "<MISSING>";
            
            boolean match = expectedLine.equals(actualLine);
            System.out.println(String.format("Line %d: %s", i+1, match ? "✓ MATCH" : "✗ DIFFER"));
            if (!match) {
                System.out.println("  Expected: [" + expectedLine + "]");
                System.out.println("  Actual:   [" + actualLine + "]");
            }
        }
    }
    
    private static void printCharCodes(String str) {
        StringBuilder sb = new StringBuilder("  Char codes: ");
        for (char c : str.toCharArray()) {
            sb.append((int)c).append(" ");
        }
        System.out.println(sb.toString());
    }
}
