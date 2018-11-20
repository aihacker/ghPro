package pub.tool;

import pub.functions.TypeFuncs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-6-19
 */
public class Console {

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private Console() {
    }

    public static Console instance = new Console();

    public String readNonEmptyLine(String prompt) {
        String line = readLine(prompt);
        while(line.length() == 0) {
            line = readLine(prompt);
        }
        return line;
    }

    public String readLine(String prompt) {
        return readLine(prompt, true);
    }

    public String readLine(String prompt, boolean trim) {
        System.out.print(prompt);
        String line = null;
        try {
            line = reader.readLine();
            if(trim) {
                line = line.trim();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public int readInt(String prompt) {
        String line;
        while(!TypeFuncs.isInt((line = readNonEmptyLine(prompt)))) {
            System.out.println("invalid integer number");
        }
        return Integer.parseInt(line);
    }

}
