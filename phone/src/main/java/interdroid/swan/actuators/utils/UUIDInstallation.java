package interdroid.swan.actuators.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * Created by rm on 09/10/2016.
 */

public class UUIDInstallation {

        private static String sID;
        private static final String INSTALLATION = "SWAN-INSTALLATION";

        public synchronized static String id(Context context) {
            if (sID == null) {
                final File installation = new File(context.getFilesDir(), INSTALLATION);
                try {
                    if (!installation.exists()) {
                        writeInstallationFile(installation);
                    }
                    sID = readInstallationFile(installation);
                } catch (IOException e) {
                    //    Log.w(ACRA.LOG_TAG, "Couldn't retrieve InstallationId for " + context.getPackageName(), e);
                    return "Couldn't retrieve InstallationId";
                } catch (RuntimeException e) {
                    //      Log.w(ACRA.LOG_TAG, "Couldn't retrieve InstallationId for " + context.getPackageName(), e);
                    return "Couldn't retrieve InstallationId";
                }
            }
            return sID;
        }

        private static String readInstallationFile(File installation) throws IOException {
            final RandomAccessFile f = new RandomAccessFile(installation, "r");
            final byte[] bytes = new byte[(int) f.length()];
            try {
                f.readFully(bytes);
            } finally {
                f.close();
            }
            return new String(bytes);
        }

        private static void writeInstallationFile(File installation) throws IOException {
            final FileOutputStream out = new FileOutputStream(installation);
            try {
                final String id = UUID.randomUUID().toString();
                out.write(id.getBytes());
            } finally {
                out.close();
            }
        }
    }

