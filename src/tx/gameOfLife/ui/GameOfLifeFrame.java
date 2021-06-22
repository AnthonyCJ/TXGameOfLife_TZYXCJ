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
 * show ������Ϸ����
 * @author tzy&xcj
 * @since 2021-5-11
 */
public class GameOfLifeFrame extends JFrame {

    private static final int DEFAULT_DURATION = 200;    // �������Ĭ��ʱ������λ/ms��
    private static final int DEFAULT_TRANSFORM_TIMES = 0;

    private int duration = DEFAULT_DURATION;    // ���������������&��ʼ��
    private int transformTimes = DEFAULT_TRANSFORM_TIMES;

    private JButton startGameBtn = new JButton("��ʼ��Ϸ");
    private JTextField durationTextField = new JTextField();

    private boolean isStart = false;    // ��Ϸ��ʼ��ʶ

    private boolean stop = false;       // ��Ϸ������ʶ

    private GameMap gameMap;
    private JPanel gridPanel = new JPanel();
    JLabel transformTimesLabel = new JLabel("����������"+ DEFAULT_TRANSFORM_TIMES);
    private JTextField[][] textMatrix;



    public GameOfLifeFrame() {
        setTitle("������Ϸ");
        // ������ɰ�ť��ʼ��
        JButton randomBtn = new JButton("�������");
        randomBtn.addActionListener(new RandomCreate());
        // ѡ���ļ���ť��ʼ��
        JButton openFileBtn = new JButton("ѡ���ļ�");
        openFileBtn.addActionListener(new OpenFileActionListener());
        startGameBtn.addActionListener(new StartGameActionListener());
        // ��Ӱ�ť
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        buttonPanel.add(randomBtn);
        buttonPanel.add(openFileBtn);
        buttonPanel.add(startGameBtn);
        // ��ʼ�����������ǩ��ʾ
        JLabel durationPromtLabel = new JLabel("�����������(msΪ��λ)");
        buttonPanel.add(durationPromtLabel);
        buttonPanel.add(durationTextField);
        // tzy�Զ����ǩ

        buttonPanel.add(transformTimesLabel);
        buttonPanel.setBackground(Color.WHITE);

        getContentPane().add("North", buttonPanel);
        // ���ô��ڴ�С���ɼ����ԡ�Ĭ�ϲ���
        this.setSize(1000, 1200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * show ������ɲ�ֱ����ʾ
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
     * show ���ļ�����ʾ������Ϣ
     * @author programmed by tzy, guided by xcj, modified by xcj
     * @since 2021-5-11
     */
    private class OpenFileActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jFileChooser = new JFileChooser(".");
            // ����.txt�ļ�������
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "TXTFiles", "txt");
            jFileChooser.setFileFilter(filter);
            jFileChooser.setDialogTitle("��ѡ���ʼ�����ļ�");
            int returnVal = jFileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                // ѡ��.txt�ļ��󣬳�ʼ����Ϸ������棬��ʼ����Ϸ״̬Ϊ��ͣ
                isStart = false;
                stop = true;
                startGameBtn.setText("��ʼ��Ϸ");

                String filepath = jFileChooser.getSelectedFile().getPath();
                gameMap = Utils.initMatrixFromFile(filepath);
                initGridLayout();
                showMatrix();
                gridPanel.updateUI();
            }
        }
    }

    /**
     * show ������ʾ�����񲼾�
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
     * show ��ϸ�����״̬����������ɫ
     * @param
     * @return
     */
    private void showMatrix() {
        int[][] matrix = gameMap.getMatrix();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == 1) {
                    // ��ϸ���ú�
                    textMatrix[y][x].setBackground(Color.BLACK);
                } else {
                    textMatrix[y][x].setBackground(Color.WHITE);
                }
            }
        }
    }

    public void updateTransformTimes() {
        transformTimesLabel.setText ("����������"+ gameMap.getTransformTimes());
    }

    /**
     * show ��ʼ��Ϸ�¼�������
     * @author programmed by xcj, guided by tzy
     * @since 2021-5-11
     */
    private class StartGameActionListener implements ActionListener {
        /**
         * �����¼�����
         * ������ʼ��Ϸ�󣬻�ȡʱ����������ťText�޸�Ϊ��ͣ
         * @param e ActionEvent�����
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // ���������ʼ��Ϸ��ťʱ��Ϸ����ֹͣ״̬����ʼ��Ϸ������ť�����޸�Ϊ����ͣ��Ϸ��
            if (!isStart) {
                // ��ȡʱ����
                try {
                    duration = Integer.parseInt(durationTextField.getText().trim());
                } catch (NumberFormatException e1) {
                    // Ĭ��ʱ����
                    duration = DEFAULT_DURATION;
                }

                new Thread(new RunLifeGame()).start();
                isStart = true;
                stop = false;
                startGameBtn.setText("��ͣ��Ϸ");
            }
            // �����Ϸ�������У���ִ����ͣ������ť�����޸�Ϊ����ʼ��Ϸ��
            else {
                stop = true;
                isStart = false;
                startGameBtn.setText("��ʼ��Ϸ");
            }
        }
    }

    /**
     * ��Ϸ���̣��������в���ʾ��Ϣ
     * ����Runnable.run()����������Ϸ��������ʱ������ϸ������Ĵ��״̬����ʾ������
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
                // ����ˢ�¼��
                try {
                    TimeUnit.MILLISECONDS.sleep(duration);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
}
