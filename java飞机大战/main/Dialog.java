package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;


public class Dialog extends JDialog {
	
	private JLabel jl01, jl02, jima01, jima02, jima03, jima04, jima05;
	private JRadioButton jr01, jr02, jr03, jr04, jr05;
	private JCheckBox jcb1, jcb2, jcb3, jcb4;

	public Dialog(JFrame j, int i) {
		super(j, true);
		setLayout(null);
		setResizable(false);
		if(i == 1)
			showFail(j);//��ʾ��սʧ��
		else if(i == 2)
			showSuccess(j);//��ʾ��ս�ɹ��Ի�
		else
			showSetting(j);//��ʾ���öԻ�
		
		setVisible(true);
		
	}
	
	
	
	private void showFail(JFrame j) {
		setTitle("��ʾ");		
		setBounds(800, 400, 500, 300);
		jl01 = new JLabel("��սʧ��");
		jl01.setFont(new Font("acefont-family", Font.BOLD, 50));
		jl01.setForeground(Color.blue);
		jl01.setBounds(65, 40, 400, 50);
		add(jl01);
		
		jl02 = new JLabel("����" + GamePanel.sum);
		jl02.setFont(new Font("acefont-family", Font.BOLD, 30));
		jl02.setForeground(Color.RED);
		jl02.setBounds(65, 120, 400, 50);
		add(jl02);
	}
	
	
	private void showSetting(JFrame j) {
		setTitle("����");
		setBounds(j.getBounds().x, j.getBounds().y+100, 800, 400);
		
		//ѡ��ս����ǩ
		jl01 = new JLabel("ѡ��ս��");
		jl01.setFont(new Font("acefont-family", Font.BOLD, 15));
		jl01.setBounds(10, 10, 80, 20);
		add(jl01);
		
		
		ImageIcon i01 = new ImageIcon(getClass().getResource("/images/Plane01.png"));
		int w01 = i01.getIconWidth(), h01 = i01.getIconHeight();
		jr01 = new JRadioButton("1");
		jr01.setBounds(10, 40, 40, 20);
		add(jr01);
		if(MyPlane.planeID == 0)
			jr01.setSelected(true);
		jr01.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyPlane.planeID = 0;
			}
		});
		
		ImageIcon i02 = new ImageIcon(getClass().getResource("/images/Plane02.png"));
		int w02 = i02.getIconWidth(), h02 = i02.getIconHeight();
		jr02 = new JRadioButton("2");
		jr02.setBounds(40+w01, 40, 40, 20);
		add(jr02);
		if(MyPlane.planeID == 1)
			jr02.setSelected(true);
		jr02.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyPlane.planeID = 1;
			}
		});
		
		ImageIcon i03 = new ImageIcon(getClass().getResource("/images/Plane03.png"));
		int w03 = i03.getIconWidth(), h03 = i03.getIconHeight();
		jr03 = new JRadioButton("3");
		jr03.setBounds(70+w01+w02, 40, 40, 20);
		add(jr03);
		if(MyPlane.planeID == 2)
			jr03.setSelected(true);
		jr03.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyPlane.planeID = 2;
			}
		});
		
		ImageIcon i04 = new ImageIcon(getClass().getResource("/images/Plane04.png"));
		int w04 = i04.getIconWidth(), h04 = i04.getIconHeight();
		jr04 = new JRadioButton("4");
		jr04.setBounds(100+w01+w02+w03, 40, 40, 20);
		add(jr04);
		if(MyPlane.planeID == 3)
			jr04.setSelected(true);
		jr04.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyPlane.planeID = 3;
			}
		});
		
		ImageIcon i05 = new ImageIcon(getClass().getResource("/images/Plane05.png"));
		int w05 = i05.getIconWidth(), h05 = i05.getIconHeight();
		jr05 = new JRadioButton("5");
		jr05.setBounds(130+w01+w02+w03+w04, 40, 40, 20);
		add(jr05);
		if(MyPlane.planeID == 4)
			jr05.setSelected(true);
		jr05.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyPlane.planeID = 4;
			}
		});
		
	   //ͼƬ��ǩ
		jima01 = new JLabel(i01);
		jima01.setBounds(10, 60, w01, h01);
		add(jima01);
				
		jima02 = new JLabel(i02);
		jima02.setBounds(40+w01, 60, w02, h02);
		add(jima02);
				
		jima03 = new JLabel(i03);
		jima03.setBounds(70+w01+w02, 60, w03, h03);
		add(jima03);
		
		jima04 = new JLabel(i04);
		jima04.setBounds(100+w01+w02+w03, 60, w04, h04);
		add(jima04);		
		
		jima05 = new JLabel(i05);
		jima05.setBounds(130+w01+w02+w03+w04, 60, w05, h05);
		add(jima05);
		
		//����ť�ӽ���ť��
		ButtonGroup bg = new ButtonGroup();
		bg.add(jr01);
		bg.add(jr02);
		bg.add(jr03);
		bg.add(jr04);
		bg.add(jr05);
		
		//��Ч��ǩ
		jl02 = new JLabel("��Ч");
		jl02.setFont(new Font("acefont-family", Font.BOLD, 15));
		jl02.setBounds(10, 80+w01, 50, 20);
		add(jl02);
		
		
		jcb1 = new JCheckBox("��������");
		jcb1.setBounds(20, 120+w01, 80, 20);
		if(Sound.b[0])
			jcb1.setSelected(true);
		add(jcb1);
		jcb1.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				Sound.b[0] = !Sound.b[0];
				if(!Sound.b[0])
				{
					Main.sound.stop();
				}
				else
					Main.sound.start();			
				}
		});
		
		jcb2 = new JCheckBox("������");
		jcb2.setBounds(20, 160+w01, 80, 20);
		if(Sound.b[1])
			jcb2.setSelected(true);
		add(jcb2);
		jcb2.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				Sound.b[1] = !Sound.b[1];
			}
		});
		
		jcb3 = new JCheckBox("�л���ը");
		jcb3.setBounds(120, 120+w01, 100, 20);
		if(Sound.b[2])
			jcb3.setSelected(true);
		add(jcb3);
		jcb3.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				Sound.b[2] = !Sound.b[2];
			}
		});
		
		jcb4 = new JCheckBox("��ҷɻ���boss��ը");
		jcb4.setBounds(120, 160+w01, 170, 20);
		if(Sound.b[3])
			jcb4.setSelected(true);
		add(jcb4);
		jcb4.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				Sound.b[3] = !Sound.b[3];
			}
		});
	}

	
	private void showSuccess(JFrame j) {
		setTitle("��ʾ");
		setBounds(j.getBounds().x+150, j.getBounds().y+150, 500, 300);
		
		
		jl01 = new JLabel("��ս�ɹ�");
		jl01.setFont(new Font("acefont-family", Font.BOLD, 50));
		jl01.setForeground(Color.blue);
		jl01.setBounds(65, 40, 400, 50);
		add(jl01);
		
		jl02 = new JLabel("����" + GamePanel.sum);
		jl02.setFont(new Font("acefont-family", Font.BOLD, 30));
		jl02.setForeground(Color.RED);
		jl02.setBounds(65, 120, 400, 50);
		add(jl02);
	}
	
}
