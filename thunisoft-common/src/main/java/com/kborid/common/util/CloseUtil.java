package com.kborid.common.util;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil {
    /**
     * Closes a closeable while suppressing any {@code IOException} that occurs.
     *
     * @param closeable the socket to close
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException ex) {
            assert true;  // avoid an empty catch
        }
    }
}
