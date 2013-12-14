package com.headsup.glass;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

class InputTask extends AsyncTask<String, Void, Void> {

        PipedOutputStream mPOut;
        PipedInputStream mPIn;
        LineNumberReader mReader;
        Process mProcess;
        @Override
        protected void onPreExecute() {
            mPOut = new PipedOutputStream();
            try {
                mPIn = new PipedInputStream(mPOut);
                mReader = new LineNumberReader(new InputStreamReader(mPIn));
            } catch (IOException e) {
                cancel(true);
            }

        }

        public void stop() {
            Process p = mProcess;
            if (p != null) {
                p.destroy();
            }
            cancel(true);
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                mProcess = new ProcessBuilder()
                    .command("/system/bin/input", "keyevent " + params[0])
                    .redirectErrorStream(true)
                    .start();


                try {
                    mProcess.waitFor();
//                    InputStream in = mProcess.getInputStream();
//                    OutputStream out = mProcess.getOutputStream();
//                    byte[] buffer = new byte[1024];
//                    int count;
//
//                    while ((count = in.read(buffer)) != -1) {
//                        mPOut.write(buffer, 0, count);
//                        publishProgress();
//                    }
//                    out.close();
//                    in.close();
//                    mPOut.close();
//                    mPIn.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (mProcess != null) {
                        mProcess.destroy();
                    }
                    mProcess = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}