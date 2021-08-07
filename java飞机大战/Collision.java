package main;



public class Collision {
	private Sound p;
	
	
	//玩家飞机子弹与敌机碰撞
	void bullet_enemy(Bullet b, EnemyPlane e) {
		if(b.getX_Y().getX() >= e.getX_Y().getX()-Bullet.BULLET_WIDTH
				&& b.getX_Y().getX() <= e.getX_Y().getX()+EnemyPlane.ENEMY_SIZE
				&& b.getX_Y().getY() >= e.getX_Y().getY()-Bullet.BULLET_HEIGHT
				&& b.getX_Y().getY() <= e.getX_Y().getY()+EnemyPlane.ENEMY_SIZE) {
			b.stayed = false;
			e.stayed = false;
			if(Sound.b[2]) {
				//敌机爆炸声音
				p = new Sound("/sounds/Break.wav");
				p.start();
			}			
			GamePanel.sum += 100;//增加分数，每架飞100
		}
	}
	
	//玩家飞机与敌机碰撞
	void plane_enemy(MyPlane m, EnemyPlane e) {
		if(m.getX_Y().getX() >= e.getX_Y().getX()-MyPlane.PLANE_SIZE
				&& m.getX_Y().getX() <= e.getX_Y().getX()+EnemyPlane.ENEMY_SIZE
				&& m.getX_Y().getY() >= e.getX_Y().getY()-MyPlane.PLANE_SIZE
				&& m.getX_Y().getY() <= e.getX_Y().getY()+EnemyPlane.ENEMY_SIZE) {
			e.stayed = false;
			if(GamePanel.live <= 50) {
				m.stayed = false;
				if(Sound.b[3]) {
					//玩家飞机爆炸声音
					p = new Sound("/sounds/HeroBrustSound.wav");
					p.start();
				
				}
				GamePanel.live = 0;
			} else
				GamePanel.live -= 50;
		}
	}
	
	//玩家飞机和敌机子弹碰
	void bullet_plane(Bullet b, MyPlane m) {
		if(b.getX_Y().getX() >= m.getX_Y().getX()-Bullet.BULLET_WIDTH
				&& b.getX_Y().getX() <= m.getX_Y().getX()+MyPlane.PLANE_SIZE-Bullet.BULLET_WIDTH
				&& b.getX_Y().getY() >= m.getX_Y().getY()-Bullet.BULLET_HEIGHT
				&& b.getX_Y().getY() <= m.getX_Y().getY()+MyPlane.PLANE_SIZE) {
			b.stayed = false;
			if(GamePanel.live <= 5) {
				m.stayed = false;
				if(Sound.b[3]) {
					//玩家飞机爆炸声音
					p = new Sound("/sounds/HeroBrustSound.wav");					
						p.start();			
			    }
				GamePanel.live = 0;//飞机生命减少
			
			} else
				GamePanel.live -= 5;//飞机生命减少
		}
	}
	
	//boss与玩家飞机碰
	void boss_plane(BossPlane b, MyPlane m) {
		if(b.getX_Y().getX() >= m.getX_Y().getX()-BossPlane.BOSS_WIDTH
				&& b.getX_Y().getX() <= m.getX_Y().getX()+MyPlane.PLANE_SIZE
				&& b.getX_Y().getY() >= m.getX_Y().getY()-BossPlane.BOSS_HEIGHT
				&& b.getX_Y().getY() <= m.getX_Y().getY()+MyPlane.PLANE_SIZE) {
			m.stayed = false;
			if(Sound.b[3]) {
				//玩家飞机爆炸声音
				p = new Sound("/sounds/HeroBrustSound.wav");
				p.start();		
		    }
			GamePanel.live = 0;
		}
	}
	//玩家飞机子弹与boss飞机碰撞
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
					//玩家飞机爆炸声音
					p = new Sound("/sounds/HeroBrustSound.wav");
						p.start();
			    }
				GamePanel.bosslive = 0;//boss飞机生命减少
				GamePanel.sum += 500;//分数+500
			
			} else
				GamePanel.bosslive -= 1;//boss飞机生命减少
		}
	}
	
	
	
}
