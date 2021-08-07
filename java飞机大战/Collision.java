package main;



public class Collision {
	private Sound p;
	
	
	//��ҷɻ��ӵ���л���ײ
	void bullet_enemy(Bullet b, EnemyPlane e) {
		if(b.getX_Y().getX() >= e.getX_Y().getX()-Bullet.BULLET_WIDTH
				&& b.getX_Y().getX() <= e.getX_Y().getX()+EnemyPlane.ENEMY_SIZE
				&& b.getX_Y().getY() >= e.getX_Y().getY()-Bullet.BULLET_HEIGHT
				&& b.getX_Y().getY() <= e.getX_Y().getY()+EnemyPlane.ENEMY_SIZE) {
			b.stayed = false;
			e.stayed = false;
			if(Sound.b[2]) {
				//�л���ը����
				p = new Sound("/sounds/Break.wav");
				p.start();
			}			
			GamePanel.sum += 100;//���ӷ�����ÿ�ܷ�100
		}
	}
	
	//��ҷɻ���л���ײ
	void plane_enemy(MyPlane m, EnemyPlane e) {
		if(m.getX_Y().getX() >= e.getX_Y().getX()-MyPlane.PLANE_SIZE
				&& m.getX_Y().getX() <= e.getX_Y().getX()+EnemyPlane.ENEMY_SIZE
				&& m.getX_Y().getY() >= e.getX_Y().getY()-MyPlane.PLANE_SIZE
				&& m.getX_Y().getY() <= e.getX_Y().getY()+EnemyPlane.ENEMY_SIZE) {
			e.stayed = false;
			if(GamePanel.live <= 50) {
				m.stayed = false;
				if(Sound.b[3]) {
					//��ҷɻ���ը����
					p = new Sound("/sounds/HeroBrustSound.wav");
					p.start();
				
				}
				GamePanel.live = 0;
			} else
				GamePanel.live -= 50;
		}
	}
	
	//��ҷɻ��͵л��ӵ���
	void bullet_plane(Bullet b, MyPlane m) {
		if(b.getX_Y().getX() >= m.getX_Y().getX()-Bullet.BULLET_WIDTH
				&& b.getX_Y().getX() <= m.getX_Y().getX()+MyPlane.PLANE_SIZE-Bullet.BULLET_WIDTH
				&& b.getX_Y().getY() >= m.getX_Y().getY()-Bullet.BULLET_HEIGHT
				&& b.getX_Y().getY() <= m.getX_Y().getY()+MyPlane.PLANE_SIZE) {
			b.stayed = false;
			if(GamePanel.live <= 5) {
				m.stayed = false;
				if(Sound.b[3]) {
					//��ҷɻ���ը����
					p = new Sound("/sounds/HeroBrustSound.wav");					
						p.start();			
			    }
				GamePanel.live = 0;//�ɻ���������
			
			} else
				GamePanel.live -= 5;//�ɻ���������
		}
	}
	
	//boss����ҷɻ���
	void boss_plane(BossPlane b, MyPlane m) {
		if(b.getX_Y().getX() >= m.getX_Y().getX()-BossPlane.BOSS_WIDTH
				&& b.getX_Y().getX() <= m.getX_Y().getX()+MyPlane.PLANE_SIZE
				&& b.getX_Y().getY() >= m.getX_Y().getY()-BossPlane.BOSS_HEIGHT
				&& b.getX_Y().getY() <= m.getX_Y().getY()+MyPlane.PLANE_SIZE) {
			m.stayed = false;
			if(Sound.b[3]) {
				//��ҷɻ���ը����
				p = new Sound("/sounds/HeroBrustSound.wav");
				p.start();		
		    }
			GamePanel.live = 0;
		}
	}
	//��ҷɻ��ӵ���boss�ɻ���ײ
	void bullet_boss(Bullet b, BossPlane m) {
		if(b.getX_Y().getX() >= m.getX_Y().getX()
				&& b.getX_Y().getX() <= m.getX_Y().getX()+BossPlane.BOSS_WIDTH-Bullet.BULLET_WIDTH
				&& b.getX_Y().getY() >= m.getX_Y().getY()
				&& b.getX_Y().getY() <= m.getX_Y().getY()+BossPlane.BOSS_HEIGHT 
				&& m.getX_Y().getY() >= 0) {
			b.stayed = false;
			if(GamePanel.bosslive <= 1) {
				m.stayed = false;
				if(Sound.b[3]) {
					//��ҷɻ���ը����
					p = new Sound("/sounds/HeroBrustSound.wav");
						p.start();
			    }
				GamePanel.bosslive = 0;//boss�ɻ���������
				GamePanel.sum += 500;//����+500
			
			} else
				GamePanel.bosslive -= 1;//boss�ɻ���������
		}
	}
	
	
	
}
