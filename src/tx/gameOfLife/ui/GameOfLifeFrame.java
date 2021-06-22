package tx.gameOfLife.ui;

import tx.gameOfLife.entity.GameMap;
import tx.gameOfLife.util.Utils;
import tx.gameOfLife.casegenerator.InitCase;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

/**
 * show 生命游戏界面
 * @author tzy&xcj
 * @since 2021-5-11
 */
public class GameOfLifeFrame extends JFrame {

    private static final int DEFAULT_DURATION = 200;    // 动画间隔默认时长（单位/ms）
    private static final int DEFAULT_TRANSFORM_TIMES = 0;

    private int duration = DEFAULT_DURATION;    // 动画间隔变量定义&初始化
    private int transformTimes = DEFAULT_TRANSFORM_TIMES;

    private JButton startGameBtn = new JButton("开始游戏");
    private JTextField durationTextField = new JTextField();

    private boolean isStart = false;    // 游戏开始标识

    private boolean stop = false;       // 游戏结束标识

    private GameMap gameMap;
    private JPanel gridPanel = new JPanel();
    JLabel transformTimesLabel = new JLabel("迭代次数："+ DEFAULT_TRANSFORM_TIMES);
    private JTextField[][] textMatrix;



    public GameOfLifeFrame() {
        setTitle("生命游戏");
        // 随机生成按钮初始化
        JButton randomBtn = new JButton("随机生成");
        randomBtn.addActionListener(new RandomCreate());
        // 选择文件按钮初始化
        JButton openFileBtn = new JButton("选择文件");
        openFileBtn.addActionListener(new OpenFileActionListener());
        startGameBtn.addActionListener(new StartGameActionListener());
        // 添加按钮
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        buttonPanel.add(randomBtn);
        buttonPanel.add(openFileBtn);
        buttonPanel.add(startGameBtn);
        // 初始化动画间隔标签提示
        JLabel durationPromtLabel = new JLabel("动画间隔设置(ms为单位)");
        buttonPanel.add(durationPromtLabel);
        buttonPanel.add(durationTextField);
        // tzy自定义标签

        buttonPanel.add(transformTimesLabel);
        buttonPanel.setBackground(Color.WHITE);

        getContentPane().add("North", buttonPanel);
        // 设置窗口大小、可见属性、默认操作
        this.setSize(1000, 1200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * show 随机生成并直接显示
     * @author xcj
     * @since 2021-5-16
     */
    private class RandomCreate implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            InitCase aCase = new InitCase();
            gameMap = Utils.initMatrixFromFile(aCase.filename);
            initGridLayout();
            showMatrix();
            gridPanel.updateUI();
        }
    }

    /**
     * show 打开文件，显示矩阵信息
     * @author programmed by tzy, guided by xcj, modified by xcj
     * @since 2021-5-11
     */
    private class OpenFileActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jFileChooser = new JFileChooser(".");
            // 设置.txt文件过滤器
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "TXTFiles", "txt");
            jFileChooser.setFileFilter(filter);
            jFileChooser.setDialogTitle("请选择初始配置文件");
            int returnVal = jFileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                // 选定.txt文件后，初始化游戏网格界面，初始化游戏状态为暂停
                isStart = false;
                stop = true;
                startGameBtn.setText("开始游戏");

                String filepath = jFileChooser.getSelectedFile().getPath();
                gameMap = Utils.initMatrixFromFile(filepath);
                initGridLayout();
                showMatrix();
                gridPanel.updateUI();
            }
        }
    }

    /**
     * show 创建显示的网格布局
     * @param
     * @return
     * */
    private void initGridLayout() {
        int rows = gameMap.getLength();
        int cols = gameMap.getWidth();
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, cols));
        textMatrix = new JTextField[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                JTextField text = new JTextField();
                textMatrix[y][x] = text;
                gridPanel.add(text);
            }
        }
        add("Center", gridPanel);
    }


    /**
     * show 按细胞存活状态设置网格颜色
     * @param
     * @return
     */
    private void showMatrix() {
        int[][] matrix = gameMap.getMatrix();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == 1) {
                    // 活细胞置黑
                    textMatrix[y][x].setBackground(Color.BLACK);
                } else {
                    textMatrix[y][x].setBackground(Color.WHITE);
                }
            }
        }
    }

    public void updateTransformTimes() {
        transformTimesLabel.setText ("迭代次数："+ gameMap.getTransformTimes());
    }

    /**
     * show 开始游戏事件监听类
     * @author programmed by xcj, guided by tzy
     * @since 2021-5-11
     */
    private class StartGameActionListener implements ActionListener {
        /**
         * 重载事件动作
         * 单击开始游戏后，获取时间间隔、将按钮Text修改为暂停
         * @param e ActionEvent类对象
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // 如果单击开始游戏按钮时游戏处于停止状态，则开始游戏并将按钮文字修改为“暂停游戏”
            if (!isStart) {
                // 获取时间间隔
                try {
                    duration = Integer.parseInt(durationTextField.getText().trim());
                } catch (NumberFormatException e1) {
                    // 默认时间间隔
                    duration = DEFAULT_DURATION;
                }

                new Thread(new RunLifeGame()).start();
                isStart = true;
                stop = false;
                startGameBtn.setText("暂停游戏");
            }
            // 如果游戏正在运行，则执行暂停并将按钮文字修改为“开始游戏”
            else {
                stop = true;
                isStart = false;
                startGameBtn.setText("开始游戏");
            }
        }
    }

    /**
     * 游戏进程，持续运行并显示信息
     * 重载Runnable.run()方法，当游戏正在运行时，更新细胞矩阵的存活状态并显示至方格。
     * @author programmed by tzy,guided by xcj
     * @since 2021-5-11
     */
    private class RunLifeGame implements Runnable {
        @Override
        public void run() {
            while (!stop) {
                gameMap.transform();
                showMatrix();
                updateTransformTimes();
                // 设置刷新间隔
                try {
                    TimeUnit.MILLISECONDS.sleep(duration);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
}
