package main;


import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



public class Sound {		
	private Clip clip;
	static boolean[] b = new boolean[]{true, true, true, true};//控制声音播放
	                                 //按键音
	
	//打开声音文件的方法。
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
		 * 停止播放
		 */
		void stop() {
			clip.stop();//暂停音频播放
		}
		
		/**
		 * 开始播放
		 */
		void start() {
			clip.start();//播放音频
		}
		
		/**
		 * 回放背景音乐设置
		 */
		void loop() {
			clip.loop(20);//回放
		}
	 
	 
	 
}
