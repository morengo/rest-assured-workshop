package logger;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class ToLoggerPrintStream {

        private Logger myLog;
        private PrintStream myPrintStream;

        public ToLoggerPrintStream(Logger aLogger) {
            myLog = aLogger;
        }

        public PrintStream getPrintStream() {
            if (myPrintStream == null) {
                OutputStream output = new OutputStream() {
                    private StringBuilder myStringBuilder = new StringBuilder();

                    @Override
                    public void write(int b) throws IOException {
                        this.myStringBuilder.append((char) b);
                    }

                    @Override
                    public void flush() {
                        myLog.debug(this.myStringBuilder.toString());
                        myStringBuilder = new StringBuilder();
                    }
                };

                myPrintStream = new PrintStream(output, true);
            }
            return myPrintStream;
        }


}
