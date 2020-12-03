package com.simple.shell.common;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.guzhandong.springframework.boot.ssh2.pool.RemoteExecConnectionPool;
import org.apache.commons.pool2.ObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * RemoteShellFileExecutor
 *
 * @author wuji
 * @date 2020-12-03 22:28
 */
@Component
public class RemoteShellFileExecutor {


    private final ObjectPool<Connection> connectionPool;

    @Autowired
    public RemoteShellFileExecutor(RemoteExecConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    public boolean exec(String src, String local) throws Exception {
        Connection conn = connectionPool.borrowObject();
        Session session = conn.openSession();
        SCPClient client = conn.createSCPClient();
        download(src, local, session, client);
        // FIXME

        return false;
    }

    /**
     * @param srcFile  要下载的文件
     * @param saveFile 保存目录，必须是目录
     * @param session  session
     * @param client   client
     * @return boolean
     */
    public static void download(String srcFile, String saveFile, Session session, SCPClient client) {
        //String cdmTar="tar czf /var/ftp/upload/ruku/ruku.tar.gz –directory=/var/ftp/upload/ruku ruku";
        boolean flag = false;
        // 是文件,直接下载
        String filename = srcFile.substring(srcFile.lastIndexOf("/") + 1);
        if (filename.contains(".")) {
            try {
                client.get(srcFile, saveFile);
            } catch (Exception ignored) {
                // ignored
            }
        }
        //是文件夹，打包下载
//                    String src = srcFile.substring(0, srcFile.lastIndexOf("/"));
//                    String cmdGet = "tar -zcvf " + srcFile + ".tar.gz " + filename;
//                    //String cmdGet="tar czf /home/"+filename+".tar.gz –directory="+src+"/"+filename+".";
//                    // 执行压缩命令
//                    session.execCommand("cd " + src + ";" + cmdGet);
//                    InputStream stdout = new StreamGobbler(session.getStdout());
//                    BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
//                    // cmriots!@#
//                    while (true) {
//                        String line = br.readLine();
//                        if (line == null)
//                            break;
//                        System.out.println(line);
//                    }
//                    System.out.println("ExitCode: " + session.getExitStatus());
//                    // 下载文件
//                    client.get(src + "/" + filename + ".tar.gz", saveFile);
//                    flag = true;
//                    e.printStackTrace();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
        //   }
        // return false;
    }


}
