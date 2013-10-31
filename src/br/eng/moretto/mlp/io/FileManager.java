package br.eng.moretto.mlp.io;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

public class FileManager {

    public static void read() {

        try {
            // Obtain a channel
            ReadableByteChannel channel = new FileInputStream("infile").getChannel();
        
            // Create a direct ByteBuffer; see also e158 Creating a ByteBuffer
            ByteBuffer buf = ByteBuffer.allocateDirect(10);
        
            int numRead = 0;
            while (numRead >= 0) {
                // read() places read bytes at the buffer's position so the
                // position should always be properly set before calling read()
                // This method sets the position to 0
                buf.rewind();
        
                // Read bytes from the channel
                numRead = channel.read(buf);
        
                // The read() method also moves the position so in order to
                // read the new bytes, the buffer's position must be set back to 0
                buf.rewind();
        
                // Read bytes from ByteBuffer; see also
                // e159 Getting Bytes from a ByteBuffer
                for (int i=0; i<numRead; i++) {
                    byte b = buf.get();
                }
            }
        } catch (Exception e) {
        }        
    }
}
