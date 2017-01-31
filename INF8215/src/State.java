
public class State {
	/*
	 * pos donne la position de la voiture i dans sa ligne ou colonne (premi�re
	 * case occup�e par la voiture)
	 */
	public int[] pos;

	/*
	 * c,d et prev premettent de retracer l'�tat pr�c�dent et le dernier
	 * mouvement effectu�
	 */
	public int c;
	static private RushHour rh;
	public int d;
	public State prev;
	/*
	 * � utiliser dans la deuxi�me partie, 
	 * n indique la distance entre l'�tat
	 * actuel et l'�tat initial, f le co�t de l'�tat actuel.
	 */
	public int n;
	public int nbMoves;
	public int f = 0;

	/*
	 * Contructeur d'un �tat initial (& recebem qualquer valor = lixo)
	 */
	public State(int[] p, RushHour rh) {
		n = 0;
		nbMoves = n;
		int tam = p.length;
		pos = new int[tam];
		for (int i = 0; i < tam; i++)
			pos[i] = p[i];
		prev = null;
		State.rh = rh;
		
		f = nbMoves+estimee3();

	}

	/*
	 * constructeur d'un �tat � partir d'un �tat s et d'un mouvement (c,d)
	 */
	public State(State s, int c, int d) {
		n = s.n+1;
		nbMoves = n;
		int tam = s.pos.length;
		pos = new int[tam];
		for (int i = 0; i < tam; i++)
			pos[i] = s.pos[i];
		prev = s;
		this.c = c;
		this.d = d;
		prev = s;
		pos[c]+=d;
		State.rh = s.rh;

		
		f = nbMoves+estimee3();

	}


	// this est il final?
	public boolean success() {

		return pos[0]==4;

	}

	/*
	 * Estimation du nombre de coup restants
	 */
	public int estimee1() {
		return 4-pos[0];
	}

	public int estimee2() {
		int ret = 4-pos[0];
		
		boolean[][] free = new boolean[6][6];

		for(int i = 0; i < 6;i++){
			for(int j = 0; j<6;j++){
				free[i][j]= true;
			}
		}
		for(int i = 0; i < pos.length;i++){
			for(int j = 0; j<rh.len[i];j++){
				if(!rh.horiz[i]){
					free[pos[i]+j][rh.moveon[i]]=false;
				}else{
					free[rh.moveon[i]][pos[i]+j]=false;
				}
			}
		}

		for(int i = pos[0]+2;i<=5;i++){
			if(!free[rh.moveon[0]][i])
				ret++;
		}
		
		
		return ret;
	}
	
	public int estimee3() {
		int ret = 4-pos[0];
		
		int[][] free = new int[6][6];

		for(int i = 0; i < 6;i++){
			for(int j = 0; j<6;j++){
				free[i][j]= -1;
			}
		}
		for(int i = 0; i < pos.length;i++){
			for(int j = 0; j<rh.len[i];j++){
				if(!rh.horiz[i]){
					free[pos[i]+j][rh.moveon[i]]=i;
				}else{
					free[rh.moveon[i]][pos[i]+j]=i;
				}
			}
		}

		for(int i = pos[0]+2;i<=5;i++){
			int car = free[rh.moveon[0]][i];
			if(car != -1){
				if(rh.len[i] == 3){
					ret += pos[i];
					
					for(int j = pos[i]-1; j>=0; j--){
						if(free[j][i] != -1){
							ret++;
						}
					}
					
				}else{
					if(pos[i] == 3){
						ret+=1;
						
						if(free[5][i] != -1){
							ret++;
						}
						
					}
					else if(pos[i] == 2){
						ret+=1;
						if(free[1][i] != -1){
							ret++;
						}
					}
				}
			}
		}
		
		
		return ret;
	}
	

	@Override
	public boolean equals(Object o) {
		State s = (State) o;
		if (s.pos.length != pos.length) {
			System.out.println("les �tats n'ont pas le m�me nombre de voitures");
		}
		int tamanho = pos.length;

		for (int i = 0; i < tamanho; i++)
			if (pos[i] != s.pos[i])
				return false;
		return true;
	}

	@Override
	public int hashCode() {
		int h = 0;
		for (int i = 0; i < pos.length; i++)
			h = 37 * h + pos[i];
		return h;
	}

	
}
