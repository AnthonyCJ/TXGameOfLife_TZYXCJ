package tx.gameOfLife.casegenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * @author xcj&tzy
 * @since 2021-5-11
 */
public class InitCase {

    /**
     * 生成文件数量
     */
    //public static final int FILE_NUM=1;
    public String filename = createCaseFile();
    /**
     * 创建测试案例
     */
    public static String createCaseFile() {

        Random random = new Random();
        //int rows = 1 + random.nextInt(100);
        //int cols = 1 + random.nextInt(100);
        int rows = 50;  //固定行和列均为50
        int cols = 50;
        int duration = 200;
        int num = 0;
        long ramdomtime = System.nanoTime();
        File file = new File(cols + "_"+rows + "_" + ramdomtime + ".txt");

        StringBuilder filename = new StringBuilder();
        filename.append(cols).append("_").append(rows).append("_").append(ramdomtime).append(".txt");

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(file));
            StringBuilder sb = new StringBuilder(cols + " " + rows + " " + duration + " " + num);
            writer.write(sb.append("\n").toString());

            //开始逐行初始化
            for (int y = 0; y < rows; y++) {
                sb = new StringBuilder();
                for (int x = 0; x < cols; x++) {
                    //初始化细胞序列，初始化活细胞和死细胞的比例为1：2
                    if (random.nextInt(3) % 3 == 0) {
                        sb.append("1 ");
                    } else {
                        sb.append("0 ");
                    }
                }
                sb.deleteCharAt(sb.length() - 1).append("\n");
                writer.write(sb.toString());

            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return filename.toString();
    }

}
