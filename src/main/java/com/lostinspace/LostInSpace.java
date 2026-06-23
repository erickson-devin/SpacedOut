package com.lostinspace;

import com.lostinspace.view.StartProgramView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Application entry point.
 *
 * No more static global state — I/O handles are created here and
 * injected into the view layer via constructor parameters.
 *
 * Run with:  mvn exec:java
 *   or:      java -jar target/lost-in-space-2.0.0.jar
 */
public class LostInSpace {

    public static void main(String[] args) {

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter    console  = new PrintWriter(System.out, true);

        try {
            StartProgramView start = new StartProgramView(keyboard, console);
            start.display();
        } catch (Throwable e) {
            console.println("\n[FATAL ERROR] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            e.printStackTrace(console);
        } finally {
            try {
                keyboard.close();
            } catch (Exception ignored) {}
            console.flush();
            console.close();
        }
    }
}
