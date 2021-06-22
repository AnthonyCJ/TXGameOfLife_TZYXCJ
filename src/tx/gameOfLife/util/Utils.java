package tx.gameOfLife.util;

import tx.gameOfLife.entity.GameMap;

import java.io.*;

/**
 * show 工具方法类
 * </p>show
 * @author created by tzy, commented by xcj
 * @since 2021-5-11
 */
public class Utils {

    /**
     * 从文件路径初始化CellMatrix对象
     * @param path
     * @return
     */
    public static GameMap initMatrixFromFile(String path) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line = reader.readLine();
            // 读取第一行的配置信息
            String[] array = line.split(" ");
            int width = Integer.parseInt(array[0]);     // 读取列数
            int length = Integer.parseInt(array[1]);    // 读取行数
            int duration = Integer.parseInt(array[2]);  // 读取刷新间隔
            int transformNum = Integer.parseInt(array[3]);  // 读取变化次数
            // 读取细胞的状态矩阵
            int[][] matrix = new int[length][width];
            for (int i = 0; i < length; i++) {
                line = reader.readLine();
                array = line.split(" ");
                for (int j = 0; j < array.length; j++) {
                    matrix[i][j] = Integer.parseInt(array[j]);
                }
            }
            // 根据读取的信息初始化细胞矩阵
            return new GameMap(length, width, duration, transformNum, matrix);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
