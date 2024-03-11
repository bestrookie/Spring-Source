package a;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 统计java文件的代码行数
 */
public class CodeCount {
    /**
     * 文件路径列表
     */
    static List<String> fileList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // 项目路径
        String projectPath = "/Users/bestrookie/workProject/Citizenins/citizenIns-smb-service/src";

        // 用递归循环搜索某一个目录下的所有文件（文件和文件夹）
        traverse(new File(projectPath));

        // 统计代码行数，result[0]是总代码行数，result[1]是过滤后的代码行数
        int[] result = codeLineCount(fileList);

        System.out.println("总代码行数：" + result[0] + "行");
        System.out.println("去掉空行、注释、导包行、包名行后的代码行数：" + result[1] + "行");

    }

    /**
     * 用递归循环搜索某一个目录下的所有文件（文件和文件夹）
     *
     * @param dir
     */
    public static void traverse(File dir) {

        if (dir == null && !dir.exists() || dir.isFile()) {
            return;
        }

        // 读取出该目录下的所有文件
        File[] files = dir.listFiles();

        for (File file : files) {
            // 如果是文件，加入到文件集合中否则加入到文件夹集合中
            if (file.isFile()) {
                fileList.add(file.toString());
            }
            // 如果是文件夹，加入到文件夹集合中，并且再次调用traverse方法
            if (file.isDirectory()) {
                traverse(file);
            }
        }
    }

    /**
     * 统计代码行数
     *
     * @param fileList 文件列表
     * @return
     * @throws IOException
     */
    public static int[] codeLineCount(List<String> fileList) throws IOException {
        // 定义一个行数数组，lines[0]是总代码行数，lines[1]是过滤后的代码行数
        int[] lines = new int[2];

        // 遍历文件列表
        for (int i = 0; i < fileList.size(); i++) {

            // 如果是.java文件
            if (fileList.get(i).endsWith(".java")) {

                // 读取
                BufferedReader br = new BufferedReader(new FileReader(fileList.get(i)));

                // 定义行数，用于统计该文件内的代码行数
                int line = 0;

                // 按行循环遍历
                while ((br.readLine()) != null) {
                    line += 1;
                }

                // 关闭通道
                br.close();

                // 总代码行数为当前文件的行数累加
                lines[0] += line;

                // 过滤后的代码行数为文件过滤后的代码行数累加
                lines[1] += codeFiltrate(fileList.get(i), line);
            }
        }
        return lines;
    }

    /**
     * java文件代码行数过滤
     * @param filePath 文件路径
     * @param lines 该文件的总代码行数
     * @return
     * @throws IOException
     */
    public static int codeFiltrate(String filePath, int lines) throws IOException {
        // 读取该文件
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        // 空行统计
        int blankCount = 0;

        // 单行注释
        int commentCount = 0;

        // 多行注释
        int commentsCount = 0;

        // 多行注释临时统计变量
        int commentsTempCount = 0;

        // 多行注释判断标志
        boolean flag = false;

        // 包名行
        int packageCount = 0;

        // 导包行
        int importCount = 0;

        // 当前行内容
        String line;

        // 按行循环读取
        while ((line = br.readLine()) != null) {

            // 空行
            if ("".equals(line.trim())) {
                blankCount += 1;
            }

            // 单行注释
            if (line.trim().startsWith("//")) {
                commentCount += 1;
            }

            // 多行注释标志
            if (flag) {
                commentsTempCount += 1;
            }

            // 多行注释判断
            if (line.trim().startsWith("/**")) {
                commentsTempCount = 1;
                flag = true;
            }

            // 多行注释结束判断
            if (line.trim().startsWith("*/")) {
                commentsCount += commentsTempCount;
                flag = false;
                commentsTempCount = 0;
            }

            // 导包行
            if (line.trim().startsWith("import")) {
                importCount += 1;
            }

            // 包名行
            if (line.trim().startsWith("package")) {
                packageCount += 1;
            }
        }

        //关闭流
        br.close();

        // 总行数 - 所有其他内容行
        return lines - blankCount - commentCount - commentsCount - packageCount - importCount;
    }
}

