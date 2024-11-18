package com.screen;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.app.MainController;
import com.item.Sword;
import com.screen.interfaces.Screen;

public class StatisticsScreen extends JPanel implements Screen {

    private MainController mainController;
    private JTable logTable;
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
        setVisible(true);
    }

    @Override
    public void hideScreen() {
        setVisible(false);
    }



    private void generateUpperButton() {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton exitButton = new JButton("돌아가기");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Setting");
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
        generateStatisticsTable();
        generateLogTable();
        add(tablePanel);
        tableCardLayout.show(tablePanel,"statistics");
    }
    private void generateStatisticsTable() {
        String[] columnNames = {"칼 이름", "성공 횟수", "실패 횟수", "총 횟수", "성공 확률 (%)"};
        DefaultTableModel statisticsTableModel = new DefaultTableModel(columnNames, 0);
        JTable statisticsTable = new JTable(statisticsTableModel);
        for (Sword sword : GameScreen.Slist) {
            statisticsTableModel.addRow(new Object[]{
                    sword.getName(),
                    sword.SuccessCount(),
                    sword.getFailureCount(),
                    sword.getTotalAttempts(),

            });
        }
        statisticsTable.setIntercellSpacing(new Dimension(1,2));
        statisticsTable.setGridColor(Color.lightGray);

        statisticsScrollPane = new JScrollPane(statisticsTable);

        tablePanel.add(statisticsScrollPane,"statistics");
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
}