/// source - https://docs.oracle.com/en/java/javase/18/core/checksum-nio-example.html
package org.example.pr2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Task3 {
    // Compute a 16-bit checksum for all the remaining bytes
    // in the given byte buffer

    private static int sum(ByteBuffer bb) {
        int sum = 0;
        while (bb.hasRemaining()) {
            if ((sum & 1) != 0)
                sum = (sum >> 1) + 0x8000;
            else
                sum >>= 1;
            sum += bb.get() & 0xff;
            sum &= 0xffff;
        }
        return sum;
    }

    // Compute and print a checksum for the given file

    private static void sum(File f) throws IOException {

        // Open the file and then get a channel from the stream
        try (
                FileInputStream fis = new FileInputStream(f);
                FileChannel fc = fis.getChannel()) {

            // Get the file's size and then map it into memory
            int sz = (int) fc.size();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

            // Compute and print the checksum
            int sum = sum(bb);
            int kb = (sz + 1023) / 1024;
            System.out.printf("sum: 0x%X\tsize: %d kb\tfile: %s\n", sum, kb, f);
        }
    }

    public static void main(String[] args) {
        File f = new File("./text.txt");
        try {
            sum(f);
        } catch (IOException e) {
            System.err.println(f + ": " + e);
        }
    }
}