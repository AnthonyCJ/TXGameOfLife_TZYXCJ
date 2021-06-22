package tx.gameOfLife.entity;

import java.util.Arrays;
import tx.gameOfLife.globalvariable.CellStatus;
import tx.gameOfLife.globalvariable.TransformFlag;
/**
 * ������Ϸ��ͼ��
 * ������ͼ������������ˢ�¼����״̬ת�ƺ���
 * @author created by tzy, announted by xcj
 * @since 2021-5-11
 */
public class GameMap {
    // ���󳤶ȣ��У�
    private int length;

    // �����ȣ��У�
    private int width;

    // �����ٶȣ�ÿ����״̬֮��ĺ�����
    private int duration;

    // �ܵı仯����
    private int transformTimes;

    // ����״̬��1��ʾ�0��ʾ��
    private int[][] matrix;

    /**
     * ���ι��췽��
     * @param length ϸ������
     * @param width ϸ�������
     * @param duration �������
     * @param transformTimes ��������
     * @param matrix ϸ�������ʼ״̬
     */
    public GameMap(int length, int width, int duration, int transformTimes, int[]... matrix) {
        this.length = length;
        this.width = width;
        this.duration = duration;
        this.transformTimes = transformTimes;
        this.matrix = matrix;
    }


    /**
     * ϸ������״̬ת�ƺ���
     * ϸ��״̬Ϊ��������������1.�����Χϸ������Ϊ3����ǰλ��ϸ����һ״̬״̬�û�
     * 2.�����ǰλ��ϸ��ԭ״̬Ϊ�����Χ��ϸ������Ϊ2����ǰλ��ϸ����һ״̬Ϊ��
     * �������ϸ����һ״̬��Ϊ��
     */
    public void transform(){
        int[][] nextMatrix = new int[length][width];  // �½���һ״̬����

        // ��ÿһ��ϸ������Χ��ϸ������
        for (int y = 0; y < this.length; y++)  {
            for (int x = 0; x < this.width; x++) {
                // ��ϸ����Χ��ϸ������
                final int nearNum = findLivedNum(y,x);
                // ����3������һ״̬���ǻ�
                if(nearNum == TransformFlag.BECOME_ALIVE){
                    nextMatrix[y][x] = CellStatus.ALIVE;
                }
                // ����2��������һ״̬һ��
                else if(nearNum == TransformFlag.REMAIN_STATUS){
                    nextMatrix[y][x] = matrix[y][x];
                }
                // ���������Ϊ����
                else {
                    nextMatrix[y][x] = CellStatus.DEAD;
                }
            }
        }
        // ����״̬����
        matrix = nextMatrix;
        // ���µ�������
        this.transformTimes++;
    }


    /**
     * ͳ������(x, y)ϸ����Χ��ϸ������
     * ������Ϊ(x, y)��ϸ����Χ�˸�λ�õĻ�ϸ�������Լ���Խ��λ�ò��ƣ�
     * @param y ������
     * @param x ������
     * @return num ��Χ��ϸ����
     */
    public int findLivedNum(int y, int x){
        int num = 0;

        for(int j = y - 1; j <= y + 1; j++){
            for(int i = x - 1; i <= x + 1; i++){
                // �Լ����������
                if(i == x && j == y) {

                }
                //����Խ�粻�������
                else if( j < 0 || j >= length || i < 0 || i >= width){

                }
                else {
                    num += matrix[j][i];
                }
            }
        }
        return num;
    }

    // ״̬����תΪ�ַ���
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
