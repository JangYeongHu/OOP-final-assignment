package src.screen;

import src.item.Sword;
import src.screen.interfaces.Screen;
import src.main.MainController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsScreen extends JPanel implements Screen {

    private MainController mainController;

    private JButton logButton;
    private JButton statisticsButton;
    private JTable logTable;


    private DefaultTableModel statisticsTableModel;
    private JTable statisticsTable;
    private JPanel buttonPanel;

    public StatisticsScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    @Override
    public void initialize() {
        buttonPanel = new JPanel(new BorderLayout());
        logButton = new JButton("로그 보기");
        statisticsButton = new JButton("통계 보기");

        generateStatisticsTable();
        logButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logTable.setVisible(true);
            }
        });
        buttonPanel.add(logButton);
        buttonPanel.add(statisticsButton);
        add(statisticsTable,BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    @Override
    public void showScreen() {
        setVisible(true);
    }

    @Override
    public void hideScreen() {
        setVisible(false);
    }

    private void generateStatisticsTable() {
        String[] columnNames = {"칼 이름", "성공 횟수", "실패 횟수", "총 횟수", "성공 확률 (%)"};
        statisticsTableModel = new DefaultTableModel(columnNames, 0);
        statisticsTable = new JTable(statisticsTableModel);
        //기능 추가해주셔야 합니다..
        /*for (Sword sword : swords) {
            tableModel.addRow(new Object[]{
                    sword.getName(),
                    sword.getSuccessCount(),
                    sword.getFailureCount(),
                    sword.getTotalAttempts(),
                    String.format("%.2f", sword.getSuccessRate())
            }); */
        add(statisticsTable);
    }
}