package main;

public class Main {
	static GameFrame f1;
	static Sound sound;
	
      public static void main(String[] args) {
		f1=new GameFrame();
		f1.setDefaultCloseOperation(3);
		f1.setVisible(true);
		f1.setResizable(false);
		if(sound==null)
		{
			sound=new Sound("/sounds/mainback.wav");
			sound.start();
			sound.loop();
		}
	}
}
