package com.simple.shell.config;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import com.simple.shell.utils.LogUtil;
import org.apache.commons.pool2.ObjectPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RemoteShellExecutor {

    private LogUtil logUtil = LogUtil.getLogger(getClass());


    private ObjectPool<Connection> connectionObjectPool;
    private String charset = "utf-8";

    //default
    private int timeOut = 1000 * 5 * 60;


    public RemoteShellExecutor(ObjectPool<Connection> connectionObjectPool) {
        this.connectionObjectPool = connectionObjectPool;
    }

    public RemoteShellExecutor(ObjectPool<Connection> connectionObjectPool, String charset, int timeOut) {
        this.connectionObjectPool = connectionObjectPool;
        this.charset = charset;
        this.timeOut = timeOut;
    }

    /**
     * 执行shell 方法
     *
     * @param cmd 命令行，示例  `ls /`
     * @return
     */
    public int exec(String cmd) throws Exception {
        return exec(cmd, null);
    }


    public int exec(String cmd, Callback callback) throws Exception {
        return exec(cmd, null, callback);
    }

    /**
     * 执行shell 方法
     *
     * @param cmd      命令行，示例  `ls /`
     * @param callback 回调接口
     * @return
     */
    public int exec(String cmd, Integer resultCode, Callback callback) throws Exception {

        Connection conn = connectionObjectPool.borrowObject();
        int result = 0;
        try {
            logUtil.info("remote exec begin,cmd : {}", cmd);
            long st = System.currentTimeMillis();
            Session session = conn.openSession();
            session.execCommand(cmd);
            if (callback != null) {
                InputStream stuOut;
                InputStream stuErr;
                String outStr;
                String outErr;
                stuOut = session.getStdout();
                stuErr = session.getStderr();
                outStr = stream2String(stuOut, this.charset);
                outErr = stream2String(stuErr, this.charset);
                callback.setStdoutString(outStr);
                callback.setStderrString(outErr);
                logUtil.info("success: {}", outStr);
                logUtil.info("error: {}", outErr);
            }
            if (resultCode != null) {
                session.waitForCondition(ChannelCondition.EXIT_STATUS, timeOut);
            }
            long et = System.currentTimeMillis();
            Integer res = session.getExitStatus();
            if (res != null) {
                result = res;
            }
            logUtil.info("remote exec complate,time :{} , result status :{}, cmd : {}", et - st, result, cmd);
            session.close();
        } catch (Exception e) {
            //login exception
            logUtil.error("remote auth error,msg :{}", e.getMessage());
            throw new RemoteExecException("远程调用脚本异常", e);
        } finally {
            connectionObjectPool.returnObject(conn);
        }
        return result;
    }

    /**
     * @param target 要下载的文件
     * @param local  保存目录
     * @return boolean
     */
    public String download(String target, String local) throws Exception {
        Connection conn = connectionObjectPool.borrowObject();
        // Session session = conn.openSession();
        SCPClient client = conn.createSCPClient();
        boolean flag = false;
        String filename = target.substring(target.lastIndexOf("/") + 1);
        if (filename.contains(".")) {
            try {
                File path = new File(local);
                if (!path.exists()) {
                    if (!path.mkdir()) {
                        throw new FileNotFoundException("文件夹创建失败!");
                    }
                }
                client.get(target, local);
                flag = true;
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        return filename;
    }

    private String stream2String(InputStream inputStream, String charset) throws Exception {
        byte[] buff = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();
        while (inputStream.read(buff) != -1) {
            stringBuilder.append(new String(buff, charset));
        }
        return stringBuilder.toString();
    }


    /**
     * 回调接口
     */
    public interface Callback {
        /**
         * 获取标准输出字符串
         *
         * @param stdoutString
         */
        void setStdoutString(String stdoutString);

        /**
         * 获取错误输出字符串
         *
         * @param stderrString
         */
        void setStderrString(String stderrString);
    }

}
