package tx.gameOfLife.entity;

import java.util.Arrays;
import tx.gameOfLife.globalvariable.CellStatus;
import tx.gameOfLife.globalvariable.TransformFlag;
/**
 * 生命游戏地图类
 * 包括地图行数、列数、刷新间隔、状态转移函数
 * @author created by tzy, announted by xcj
 * @since 2021-5-11
 */
public class GameMap {
    // 矩阵长度（行）
    private int length;

    // 矩阵宽度（列）
    private int width;

    // 动画速度，每两个状态之间的毫秒数
    private int duration;

    // 总的变化次数
    private int transformTimes;

    // 矩阵状态，1表示活，0表示死
    private int[][] matrix;

    /**
     * 含参构造方法
     * @param length 细胞矩阵长
     * @param width 细胞矩阵宽
     * @param duration 迭代间隔
     * @param transformTimes 迭代次数
     * @param matrix 细胞矩阵初始状态
     */
    public GameMap(int length, int width, int duration, int transformTimes, int[]... matrix) {
        this.length = length;
        this.width = width;
        this.duration = duration;
        this.transformTimes = transformTimes;
        this.matrix = matrix;
    }


    /**
     * 细胞矩阵状态转移函数
     * 细胞状态为活包括两种情况：1.如果周围细胞数量为3，则当前位置细胞下一状态状态置活
     * 2.如果当前位置细胞原状态为活，且周围活细胞数量为2，则当前位置细胞下一状态为活
     * 其余情况细胞下一状态均为死
     */
    public void transform(){
        int[][] nextMatrix = new int[length][width];  // 新建下一状态矩阵

        // 对每一个细胞求周围活细胞数量
        for (int y = 0; y < this.length; y++)  {
            for (int x = 0; x < this.width; x++) {
                // 求细胞周围活细胞个数
                final int nearNum = findLivedNum(y,x);
                // 等于3，则下一状态总是活
                if(nearNum == TransformFlag.BECOME_ALIVE){
                    nextMatrix[y][x] = CellStatus.ALIVE;
                }
                // 等于2，则与上一状态一样
                else if(nearNum == TransformFlag.REMAIN_STATUS){
                    nextMatrix[y][x] = matrix[y][x];
                }
                // 其它情况均为死亡
                else {
                    nextMatrix[y][x] = CellStatus.DEAD;
                }
            }
        }
        // 更新状态矩阵
        matrix = nextMatrix;
        // 更新迭代次数
        this.transformTimes++;
    }


    /**
     * 统计坐标(x, y)细胞周围活细胞个数
     * 求坐标为(x, y)的细胞周围八个位置的活细胞数（自己、越界位置不计）
     * @param y 纵坐标
     * @param x 横坐标
     * @return num 周围活细胞数
     */
    public int findLivedNum(int y, int x){
        int num = 0;

        for(int j = y - 1; j <= y + 1; j++){
            for(int i = x - 1; i <= x + 1; i++){
                // 自己不参与计数
                if(i == x && j == y) {

                }
                //数组越界不参与计数
                else if( j < 0 || j >= length || i < 0 || i >= width){

                }
                else {
                    num += matrix[j][i];
                }
            }
        }
        return num;
    }

    // 状态矩阵转为字符串
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] ints : matrix) {
            sb.append(Arrays.toString(ints)).append("\n");
        }
        return sb.toString();
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setTransformTimes(int transformTimes) {
        this.transformTimes = transformTimes;
    }

    public int getTransformTimes() {
        return transformTimes;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}
