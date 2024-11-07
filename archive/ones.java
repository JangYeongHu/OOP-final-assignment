package archive;//그냥연습용으로 하나만들어봤습니다

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ones extends JFrame {
	Random rand = new Random();

	static JLabel scoreLabel;
	static JLabel posLabel;
	static JLabel Sname;
	String[] SNlist = { "건물주", "나무검", "돌검", "금검", "치킨 닭다리", "철검", "다이아몬드검", "네더라이트검", "마법부여검", "마법부여네더라이트검" };// 검종류

	static int money = 1000;
	static int point = 1;

	void mymain() {
		JPanel top = new JPanel(new GridLayout(1, 3));
		JPanel sword = new JPanel();
		Container pane = getContentPane();
		Sname = new JLabel(SNlist[0]);
		Sname.setFont(Sname.getFont().deriveFont(28.0f));
		sword.add(Sname);
		pane.add(sword, BorderLayout.CENTER);

		setTitle("검 강화하기");// 타이틀이름

		top.setPreferredSize(new Dimension(1000, 30));
		setSize(1000, 1200);
		setVisible(true);
		JLabel topLabel = new JLabel("검 강화하기");
		topLabel.setFont(topLabel.getFont().deriveFont(24.0f));

		scoreLabel = new JLabel(" 돈 : " + money);
		scoreLabel.setFont(scoreLabel.getFont().deriveFont(24.0f));
		JPanel posPane = new JPanel(new BorderLayout());
		posLabel = new JLabel("강화확률 " + upgrade_probability(point));// 첫강화는 100%
		posLabel.setFont(posLabel.getFont().deriveFont(28.0f));

		top.add(scoreLabel, BorderLayout.LINE_START);
		top.add(topLabel, BorderLayout.CENTER);

		posPane.add(posLabel, BorderLayout.LINE_END);
		top.add(posPane, BorderLayout.LINE_END);
		pane.add(top, BorderLayout.PAGE_START);
		MakeButton();
	}

	String upgrade_probability(int U) {// 현재 강화율과 비교해서 강화확률조정
		/*
		 * 확률은1 = 100 2 = 90 3 = 80 4 = 70 5 = 60 6 = 50 7 = 40 8 = 30 9 = 20 10 = 10
		 * 임시로 정한 확률입니다.
		 */
		int num = 100 - U * 10;// 성공확률계산
		int n = rand.nextInt(1, 101);// 성공
		System.out.println(n);
		if (num > n) {
			Sname.setText(SNlist[point]);
			return num + "";
		}
		point = 1;
		Sname.setText(SNlist[point]);
		return 100 + "";
	}

	void MakeButton() {// 추후에 클래스로나누기
		// 강화하기, 상점, 판매
		JPanel bottom = new JPanel(new GridLayout(1, 3));
		Button upgrade = new Button("강화하기");
		upgrade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				posLabel.setText("강화확률 " + upgrade_probability(point++));
			}
		});
		Button shop = new Button("상점");
		Button sell = new Button("팔기");
		upgrade.setPreferredSize(new Dimension(500, 100));
		bottom.add(shop);
		bottom.add(upgrade);
		bottom.add(sell);
		add(bottom, BorderLayout.PAGE_END);
	}

	public static void main(String[] args) {
		ones m = new ones();
		m.mymain();
	}
}
