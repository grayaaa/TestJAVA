package command;

import java.io.*;


/**
 * @author qmgeng
 */
public class LocalCommand {


    /**
     * 当前用户调用命令的静态方法
     *
     * @param command 命令字符串
     * @return CommandResult    命令结果对象，包含返回值、标准错误和标准输出。
     */
    public static CommandResult run(String command) {

        // 拼凑su命令字符串
        // String[] cmds = { "/bin/sh", "-c", "\"" + command.trim() + "\"" };
        String[] cmds = {"/bin/sh", "-c", command.trim()};

        String[] envp = {"LANG=C"};

        return run(cmds, envp, null);
    }

    /**
     * 当前用户调用命令的静态方法
     *
     * @param commands 命令数组
     * @return CommandResult 命令结果对象，包含返回值、标准错误和标准输出。
     */
    public static CommandResult run(String[] commands) {

        String[] envp = {"LANG=C"};

        return run(commands, envp, null);
    }

    /**
     * 在指定的环境变量envp和目录dir下，创建新的进程并执行字符串command所表示的命令
     *
     * @param command 命令字符串，如"whoami"或者"su -l userX -c whoami"
     * @param envp    指定命令执行的环境变量，如PATH
     * @param dir     命令执行时的工作目录
     * @return 返回描述命令执行结果的对象，包含三个信息（返回码、标准错误、标准输出），对象本身和内部各值域取值均不为null。
     */
    public static CommandResult run(String command, String[] envp, File dir) {

        String stdOut = null;
        String stdErr = null;
        int exitVal = 0;
        CommandResult cr = null;

        if (command == null) {
            cr = new CommandResult(-1, "", "Commands Invalid:" + command);
            return cr;
        }

        // 执行命令，并处理执行结果
        Process suProc = null;
        try {
            suProc = Runtime.getRuntime().exec(command, envp, dir);
            stdOut = loadStream(suProc);
            stdErr = loadErrorStream(suProc);
            exitVal = waitFor(suProc);
            cr = new CommandResult(exitVal, stdOut, stdErr);

        } catch (IOException e) {
            e.printStackTrace();
            cr = new CommandResult(-2, "LocalMethod.run(IOException)", e
                    .toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            cr = new CommandResult(-3, "LocalMethod.run(InterruptedException)",
                    e.toString());
        } finally {
            if (suProc != null)
                try {
                    closeStreams(suProc);
                    suProc.destroy();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return cr;
    }

    /**
     * 在指定的环境变量envp和目录dir下，创建新的进程并执行字符串command所表示的命令
     *
     * @param command 由字符串数组表示的命令
     * @param envp    指定命令执行的环境变量，如PATH
     * @param dir     命令执行时的工作目录
     * @return 返回描述命令执行结果的对象，包含三个信息（返回码、标准错误、标准输出），对象本身和内部各值域取值均不为null。
     */
    public static CommandResult run(String[] command, String[] envp, File dir) {

        String stdOut = null;
        String stdErr = null;
        int exitVal = 0;
        CommandResult cr = null;

        if (command == null || command.length == 0) {
            cr = new CommandResult(-1, "", "Commands Invalid:" + command);
            return cr;
        }

        // 执行命令，并处理执行结果
        Process suProc = null;
        try {
            suProc = Runtime.getRuntime().exec(command, envp, dir);
            stdOut = loadStream(suProc);
            stdErr = loadErrorStream(suProc);
            exitVal = waitFor(suProc);
            cr = new CommandResult(exitVal, stdOut, stdErr);

        } catch (IOException e) {
            e.printStackTrace();
            cr = new CommandResult(-2, "LocalMethod.run(IOException)", e
                    .toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            cr = new CommandResult(-3, "LocalMethod.run(InterruptedException)",
                    e.toString());
        } finally {
            if (suProc != null)
                try {
                    closeStreams(suProc);
                    suProc.destroy();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return cr;
    }

    /**
     * 切换到其它用户调用命令的静态方法
     *
     * @param username 要切换到的用户名
     * @param command  命令串
     * @return
     */
    public static CommandResult suRun(String username, String command) {

        // 拼凑su命令字符串
        //TYPE 1
        // String suStr = "su -l " + username + " -c " + "'" + command.trim() + "'";
        //TYPE 2
        // String suStr = "su -l " + username + " -c " + command.trim();
        //TYPE 3
        //String suStr = "su -l " + username + " -c " + "\"" + command.trim()+ "\"";
        //TYPE 4
        String suStr = "su -l " + username + " -c " + "\"export LANG=C;" + command.trim() + "\"";

        String[] cmds = {"/bin/sh", "-c", suStr};

        String[] envp = {"LANG=C"};

        return run(cmds, envp, null);
    }

    /**
     * 静态工具方法，读取命令执行过程中的标准输出流
     *
     * @param proc 命令进程对象
     * @return 命令的标准输出字符串
     * @throws java.io.IOException
     */
    private static String loadStream(Process proc) throws IOException {
        int ptr = 0;
        InputStream in = new BufferedInputStream(proc.getInputStream());
        StringBuffer buffer = new StringBuffer();
        Reader reader = new InputStreamReader(in, "GBK");
        BufferedReader bReader = new BufferedReader(reader);
        for (String res = ""; (res = bReader.readLine()) != null; ) {
            buffer.append(res + "\n");
        }

//		while ((ptr = in.read()) != -1) {
//			buffer.append((char) ptr);
//
//		}
        return buffer.toString();
    }

    /**
     * 等待命令进程退出的静态方法
     *
     * @param proc 命令进程对象
     * @return 命令的返回值
     * @throws InterruptedException
     */
    private static int waitFor(Process proc) throws InterruptedException {
        proc.waitFor();
        return proc.exitValue();
    }

    /**
     * 获取进程的错误输出流
     *
     * @param proc 命令进程
     * @return 命令的错误输出字符串
     * @throws java.io.IOException
     */
    private static String loadErrorStream(Process proc) throws IOException {
        int ptr = 0;
        InputStream in = new BufferedInputStream(proc.getErrorStream());
        StringBuffer buffer = new StringBuffer();
        while ((ptr = in.read()) != -1) {
            buffer.append((char) ptr);
        }
        return buffer.toString();
    }

    /**
     * 关闭进程的输入流、输出流和错误流
     *
     * @param p
     * @throws java.io.IOException
     */
    public static void closeStreams(Process p) throws IOException {
        p.getInputStream().close();
        p.getOutputStream().close();
        p.getErrorStream().close();
    }

    /**
     * 主函数，在Linux平台测试run和suRun方法
     *
     * @param args 参数数组 第一个参数args[0]不为空时，会被做为命令执行；
     *             第一个参数args[0]不为空时，为空时执行默认命令whoami
     */
    static public void main(String[] args) {

        // 拼凑命令字符串
        String cmd = "whoami";

        // 打印命令串
        if (args != null && args.length > 0) {
            cmd = args[0];
        }
        System.out.println("command:" + cmd);

        // 调用本地命令的suRun方法
        CommandResult cr = LocalCommand.suRun("root", cmd);

        // 打印命令执行结果
        System.out.println("exitvalue:" + cr.getStdErr());
        System.out.println("stdOut:" + cr.getStdOut());
        System.out.println("stdErr:" + cr.getStdErr());

    }
}
