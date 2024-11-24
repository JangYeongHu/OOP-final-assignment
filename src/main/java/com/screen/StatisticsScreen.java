package com.screen;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.app.MainController;
import com.item.Sword;
import com.screen.interfaces.Screen;

public class StatisticsScreen extends JPanel implements Screen {

    private MainController mainController;
    private JTable statisticsTable;

    private DefaultTableModel statisticsTableModel;
    private JPanel tablePanel;
    private CardLayout tableCardLayout;
    private JScrollPane logScrollPane;
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
        generateBottomButton();
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


    private void updateScreen() {

    }


    private void generateUpperButton() {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton exitButton = new JButton("돌아가기");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Start");
            }
        });
        buttonPanel.add(exitButton);
        add(buttonPanel,BorderLayout.NORTH);
    }

    private void generateBottomButton() {
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
        JButton logButton = new JButton("로그 보기");
        JButton statisticsButton = new JButton("통계 보기");
        logButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableCardLayout.show(tablePanel,"log");
            }
        });

        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableCardLayout.show(tablePanel,"statistics");
            }
        });

        buttonPanel.add(statisticsButton);
        buttonPanel.add(logButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void generateTable() {
        tableCardLayout = new CardLayout();
        tablePanel = new JPanel(tableCardLayout);
        generateOrUpdateStatisticsTable();
        generateLogTable();
        add(tablePanel);
        tableCardLayout.show(tablePanel,"statistics");
    }
    private void generateOrUpdateStatisticsTable() {
        String[] columnNames = {"칼 이름", "성공 횟수", "실패 횟수", "총 횟수", "성공 확률"};

        // TableModel이 null인지 확인하여 새로 생성하거나 기존 테이블 갱신
        if (statisticsTableModel == null) {
            statisticsTableModel = new DefaultTableModel(columnNames, 0);
            JTable statisticsTable = new JTable(statisticsTableModel);

            // 테이블 기본 설정
            statisticsTable.setFont(new Font("Serif", Font.PLAIN, 18));
            statisticsTable.getTableHeader().setFont(new Font("Serif", Font.BOLD, 20));
            // 2. 행 높이 설정
            statisticsTable.setRowHeight(30);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < statisticsTable.getColumnCount(); i++) {
                statisticsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // 스크롤 팬 생성 및 패널에 추가
            statisticsScrollPane = new JScrollPane(statisticsTable);
            tablePanel.add(statisticsScrollPane, "statistics");
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
                    String.format("%.3f", sword.getSuccessRate())+"%"
            });
        }

        // 테이블 갱신 (옵션 - 이미 DefaultTableModel로 갱신됨)
        statisticsTableModel.fireTableDataChanged();
    }

    private void generateLogTable() {
        String[] columnNames = {"성공 여부", "검", "날짜", "시간"};
        DefaultTableModel logTableModel = new DefaultTableModel(columnNames, 0);
        JTable logTable = new JTable(logTableModel);
        //로그는 어쩔지 좀 더 고민 중입니다.
        /*for (Sword sword : GameScreen.Slist) {
            logTableModel.addRow(new Object[]{
                    sword.getName(),
                    sword.SuccessCount(),
                    sword.getFailureCount(),
                    sword.getTotalAttempts(),
                    //String.format("%.2f", sword.getSuccessRate())
            });
*/
        for (int i = 0; i < 30; i++) {
            logTableModel.addRow(new Object[]{
                    "짱센검",
                    1,
                    2,
                    3
            });
        }
        logTable.setIntercellSpacing(new Dimension(1, 2));
        logTable.setGridColor(Color.lightGray);

        logScrollPane = new JScrollPane(logTable);

        tablePanel.add(logScrollPane, "log");
    }
}
