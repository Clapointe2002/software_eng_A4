package org.example;
import org.junit.platform.console.ConsoleLauncher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class TestRunner {
    public static void main(String[] args) {
        try {
            File file = new File("test-results.txt");
            FileOutputStream fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
            System.setErr(ps);

            ConsoleLauncher.main(new String[]{
                    "--disable-ansi-colors",
                    "--details-theme=ascii",
                    "--select-class=org.example.PostTest"
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
