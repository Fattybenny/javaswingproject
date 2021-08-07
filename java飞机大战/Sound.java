package main;


import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



public class Sound {		
	private Clip clip;
	static boolean[] b = new boolean[]{true, true, true, true};//������������
	                                 //������
	
	//�������ļ��ķ�����
	public Sound(String path){
		AudioInputStream audio;
		try {
			URL url = this.getClass().getResource(path);
			audio = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audio);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		/**
		 * ֹͣ����
		 */
		void stop() {
			clip.stop();//��ͣ��Ƶ����
		}
		
		/**
		 * ��ʼ����
		 */
		void start() {
			clip.start();//������Ƶ
		}
		
		/**
		 * �طű�����������
		 */
		void loop() {
			clip.loop(20);//�ط�
		}
	 
	 
	 
}
