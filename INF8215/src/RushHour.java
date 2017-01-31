import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class RushHour {

	/*
	 * representation du probleme : Les six lignes sont numerotees de haut en
	 * bas, de 0 a 5 et les 6 colonnes de gauche a droite, de 0 a 5.
	 *
	 * il y a nbcars voitures, num�rot�es de 0 a nbcars-1 pour chaque voiture i
	 * on a : - color[i] sa couleur - horiz[i] si la voiture est horizontale
	 * (vrai) ou verticale (faux) - len[i] sa longueur (2 ou 3) - moveon[i] la
	 * ligne (si horiz[i]) ou la colonne (sinon) o� se trouve la voiture
	 *
	 */

	public int nbcars;
	public String[] color;
	public boolean[] horiz;
	public int[] len;
	public int[] moveon;

	public int nbMoves;
	/*
	 * la matrice free permet de savoir si une case est libre
	 */
	public boolean[][] free = new boolean[6][6];

	void initFree(State s) {
		for(int i = 0; i < 6;i++){
			for(int j = 0; j<6;j++){
				free[i][j]= true;
			}
		}
		for(int i = 0; i < s.pos.length;i++){
			for(int j = 0; j<len[i];j++){
				if(!horiz[i]){
					free[s.pos[i]+j][moveon[i]]=false;
				}else{
					free[moveon[i]][s.pos[i]+j]=false;
				}
			}
		}
	}

	/*
	 * renvoie la liste des d�placements possibles
	 */

	public ArrayList<State> moves(State s) {
		initFree(s);
		ArrayList<State> l = new ArrayList<State>();
		
		
		for(int i = 0; i < s.pos.length;i++){
			if(!horiz[i]){
				int next = s.pos[i]+len[i];
				if(next<6)
					if(free[next][moveon[i]]){
						l.add(new State(s,i,1));
					}
				int prev = s.pos[i]-1;
				if(prev>=0){
					if(free[prev][moveon[i]]){
						l.add(new State(s,i,-1));
					}
				}
				
			}else{
				int next = s.pos[i]+len[i];
				if(next<6)
					if(free[moveon[i]][next]){
						l.add(new State(s,i,1));
					}
				int prev = s.pos[i]-1;
				if(prev>=0){
					if(free[moveon[i]][prev]){
						l.add(new State(s,i,-1));
					}
				}
			}
		}
		
		return l;
	}

	/*
	 * trouve une solution � partir de s
	 */
	public State solve(State s) {
		HashSet<State> visited = new HashSet<State>();
		visited.add(s);
		Queue<State> Q = new LinkedList<State>();

		int nbVisited = 0;
		State current = s; 
		while(current!=null && !current.success()){
			nbVisited ++;			
			ArrayList<State> states = moves(current);
			for(int i=0;i<states.size();i++){
				if(!visited.contains(states.get(i)))
				{
					Q.add(states.get(i));
					visited.add(states.get(i));
				}
				
			}
			current = Q.poll();
		}
		
		System.out.println("Visited = "+nbVisited );
		if(current!=null)
			return current;
		System.out.println("pas de solution");
		return null;
	}

	public State solveAstar(State s) {
		HashSet<State> visited = new HashSet<State>();
		visited.add(s);
		PriorityQueue<State> Q = new PriorityQueue<State>(10, new MyComparator());

		int nbVisited = 0;
		State current = s; 
		while(current!=null && !current.success()){
			nbVisited ++;
			ArrayList<State> states = moves(current);
			for(int i=0;i<states.size();i++){
				if(!visited.contains(states.get(i)))
				{
					Q.add(states.get(i));
					visited.add(states.get(i));
				}
				
			}
			current = Q.poll();
		}
		
		
		System.out.println("Visited = "+nbVisited );
		if(current!=null)
			return current;

		
		System.out.println("pas de solution");
		return null;
	}

	/*
	 * affiche la solution
	 */

	void printSolution(State s) {
		System.out.println(s.n+" mouvements");
		Stack<String> stack = new Stack<String>();
		for(State current = s; current.prev != null; current=current.prev){
			//System.out.println(colo);
			String tmp;
			if(!horiz[current.c]){
				if(current.d == 1)
					tmp = "le haut";
				else
					tmp = "le bas";
			}
			else{
				if(current.d == 1)
					tmp = "la droite";
				else
					tmp = "la gauche";
				
			}
			stack.push("voiture "+color[current.c]+" vers "+tmp);
		}
		
		for(String current = stack.pop(); !stack.isEmpty(); current=stack.pop()){
			System.out.println(current);
		}
			
	}
	
	private class MyComparator implements Comparator<State> {
		public int compare(State arg0, State arg1) {
			return arg0.f - arg1.f;
		}
	}
}
