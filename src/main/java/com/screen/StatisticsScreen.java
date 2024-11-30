package com.screen;


import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.app.MainController;
import com.item.Sword;
import com.screen.interfaces.Screen;

public class StatisticsScreen extends JPanel implements Screen {

    private MainController mainController;
    private DefaultTableModel statisticsTableModel;
    private JPanel tablePanel;
    private CardLayout tableCardLayout;
    private JScrollPane statisticsScrollPane;


    public StatisticsScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        generateUpperButton();
        generateTable();
    }

    @Override
    public void showScreen() {
        generateOrUpdateStatisticsTable();
        setVisible(true);
    }

    @Override
    public void hideScreen() {
        setVisible(false);
    }



    private void generateUpperButton() {
        JButton exitButton = new JButton("돌아가기");
        exitButton.setForeground(new Color(214, 189, 152));
        exitButton.setFont(new Font("DungGeunMo", Font.PLAIN, 16));
        exitButton.setBackground(new Color(64, 83, 76));
        exitButton.setBounds(50,0,200,50);
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(0x677d6a), 3));;
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Start");
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(new Color(103, 125, 106));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(new Color(64, 83, 76));
            }
        });
        add(exitButton);
    }

    private void generateTable() {
        tablePanel = new JPanel(null);
        generateOrUpdateStatisticsTable();
        tablePanel.setBounds(0,0,1200,800);
        tablePanel.setBackground(new Color(64, 83, 76));
        add(tablePanel);
    }
    private void generateOrUpdateStatisticsTable() {
        String[] columnNames = {"칼 이름", "성공 횟수", "실패 횟수", "총 횟수", "성공 확률"};

        // TableModel이 null인지 확인하여 새로 생성하거나 기존 테이블 갱신
        if (statisticsTableModel == null) {
            statisticsTableModel = new DefaultTableModel(columnNames, 0);
            JTable statisticsTable = new JTable(statisticsTableModel);

            // 테이블 기본 설정
            statisticsTable.setFont(new Font("DungGeunMo", Font.PLAIN, 16));
            statisticsTable.getTableHeader().setFont(new Font("DungGeunMo", Font.BOLD, 20));
            statisticsTable.setDefaultEditor(Object.class, null);
            // 2. 행 높이 설정
            statisticsTable.setRowHeight(50);
            statisticsTable.setBackground(new Color(214, 189, 152));

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < statisticsTable.getColumnCount(); i++) {
                statisticsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // 스크롤 팬 생성 및 패널에 추가
            statisticsScrollPane = new JScrollPane(statisticsTable);
            statisticsScrollPane.setBounds(50,50,1100,750);

            class CustomScrollBarUI extends BasicScrollBarUI {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = new Color(103, 125, 106);
                    this.trackColor = new Color(214, 189, 152);
                }



                @Override
                protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                    g.setColor(trackColor);
                    g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                }

                @Override
                protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // 그림자 추가 (슬라이더 자체에 그림자 적용)
                    g2d.setColor(new Color(0, 0, 0, 120));  // 반투명한 검정 (그림자)
                    g2d.fillRoundRect(thumbBounds.x + 3, thumbBounds.y + 3, thumbBounds.width, thumbBounds.height, 10, 10);  // 그림자 위치 조정

                    // 슬라이더(thumb)에 그라디언트 효과 추가 (위에서 아래로)
                    GradientPaint gradient = new GradientPaint(
                            thumbBounds.x, thumbBounds.y, thumbColor,  // 슬라이더 위쪽 밝은 색
                            thumbBounds.x, thumbBounds.y + thumbBounds.height, new Color(64, 83, 76)  // 슬라이더 아래쪽 어두운 색
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);  // 둥근 슬라이더
                }
            }



            statisticsScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
            tablePanel.add(statisticsScrollPane);
        } else {
            // 기존 데이터를 초기화
            statisticsTableModel.setRowCount(0);
        }

        // 데이터를 추가
        for (Sword sword : MainController.swordList) {
            statisticsTableModel.addRow(new Object[]{
                    sword.getName(),
                    sword.getSuccessCount(),
                    sword.getFailureCount(),
                    sword.getTotalAttempts(),
                    String.format("%.3f", sword.getSuccessRate()*100)+"%"
            });
        }

        // 테이블 갱신 (옵션 - 이미 DefaultTableModel로 갱신됨)
        statisticsTableModel.fireTableDataChanged();
    }

}
