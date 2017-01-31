
public class Test {

	public static void main(String[] args) {
		/*test1();
		test2();
		test3();
		test4();*/
		solve22();
		solve1();
		solve40();
		solveAstar();
	}

	static void test1() {
		int[] positioning = { 1, 0, 1, 4, 2, 4, 0, 1 };
		State s0 = new State(positioning, null);
		System.out.println(!s0.success());
		boolean b = !s0.success();
		State s = new State(s0, 1, 1);

		System.out.println(s.prev == s0);
		b = b && s.prev == s0;
		System.out.println(s0.pos[1] + " " + s.pos[1]);

		s = new State(s, 6, 1);
		s = new State(s, 1, -1);
		s = new State(s, 6, -1);

		System.out.println(s.equals(s0));
		b = b & s.equals(s0);
		s = new State(s0, 1, 1);
		s = new State(s, 2, -1);
		s = new State(s, 3, -1);
		s = new State(s, 4, -1);
		s = new State(s, 4, -1);
		s = new State(s, 5, -1);
		s = new State(s, 5, -1);
		s = new State(s, 5, -1);
		s = new State(s, 6, 1);
		s = new State(s, 6, 1);
		s = new State(s, 6, 1);
		s = new State(s, 7, 1);
		s = new State(s, 7, 1);
		s = new State(s, 0, 1);
		s = new State(s, 0, 1);
		s = new State(s, 0, 1);
		b = b & s.success();
		System.out.println(s.success());
		if (!b)
			System.err.println("mauvais r�sultat");
	}

	static void test2() {
		boolean[][] res = { { false, false, true, true, true, false }, { false, true, true, false, true, false },
				{ false, false, false, false, true, false }, { false, true, true, false, true, true },
				{ false, true, true, true, false, false }, { false, true, false, false, false, true } };
		RushHour rh = new RushHour();
		rh.nbcars = 8;
		rh.horiz = new boolean[] { true, true, false, false, true, true, false, false };
		rh.len = new int[] { 2, 2, 3, 2, 3, 2, 3, 3 };
		rh.moveon = new int[] { 2, 0, 0, 0, 5, 4, 5, 3 };
		State s = new State(new int[] { 1, 0, 1, 4, 2, 4, 0, 1 }, rh);
		rh.initFree(s);
		boolean b = true;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				System.out.print(rh.free[i][j] + "\t");
				b = b && (rh.free[i][j] == res[i][j]);
			}
			System.out.println();
		}
		if (b)
			System.out.println("r�sultat correct");
		else
			System.err.println("mauvais r�sultat");
	}

	static void test3() {
		RushHour rh = new RushHour();
		rh.nbcars = 12;
		rh.horiz = new boolean[] { true, false, true, false, false, true, false, true, false, true, false, true };
		rh.len = new int[] { 2, 2, 3, 2, 3, 2, 2, 2, 2, 2, 2, 3 };
		rh.moveon = new int[] { 2, 2, 0, 0, 3, 1, 1, 3, 0, 4, 5, 5 };
		State s = new State(new int[] { 1, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 }, rh);
		State s2 = new State(new int[] { 1, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 2 }, rh);
		System.out.println(rh.moves(s).size());
		System.out.println(rh.moves(s2).size());
	}

	static void test4() {
		RushHour rh = new RushHour();
		rh.nbcars = 12;
		rh.color = new String[] { "rouge", "vert clair", "jaune", "orange", "violet clair", "bleu ciel", "rose",
				"violet", "vert", "noir", "beige", "bleu" };
		rh.horiz = new boolean[] { true, false, true, false, false, true, false, true, false, true, false, true };
		rh.len = new int[] { 2, 2, 3, 2, 3, 2, 2, 2, 2, 2, 2, 3 };
		rh.moveon = new int[] { 2, 2, 0, 0, 3, 1, 1, 3, 0, 4, 5, 5 };
		State s = new State(new int[] { 1, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 }, rh);
		int n = 0;
		for (s = rh.solve(s); s.prev != null; s = s.prev)
			n++;
		System.out.println(n);
	}

	static void solve22() {
		RushHour rh = new RushHour();
		rh.nbcars = 12;
		rh.color = new String[] { "rouge", "vert clair", "jaune", "orange", "violet clair", "bleu ciel", "rose",
				"violet", "vert", "noir", "beige", "bleu" };
		rh.horiz = new boolean[] { true, false, true, false, false, true, false, true, false, true, false, true };
		rh.len = new int[] { 2, 2, 3, 2, 3, 2, 2, 2, 2, 2, 2, 3 };
		rh.moveon = new int[] { 2, 2, 0, 0, 3, 1, 1, 3, 0, 4, 5, 5 };
		State s = new State(new int[] { 1, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 }, rh);
		s = rh.solveAstar(s);
		rh.printSolution(s);
	}


	static void solve1() {
		RushHour rh = new RushHour();
		rh.nbcars = 8;
		rh.color = new String[] { "rouge", "vert clair", "violet", "orange", "vert", "bleu ciel", "jaune", "bleu" };
		rh.horiz = new boolean[] { true, true, false, false, true, true, false, false };
		rh.len = new int[] { 2, 2, 3, 2, 3, 2, 3, 3 };
		rh.moveon = new int[] { 2, 0, 0, 0, 5, 4, 5, 3 };
		State s = new State(new int[] { 1, 0, 1, 4, 2, 4, 0, 1 }, rh);
		s = rh.solveAstar(s);
		rh.printSolution(s);
	}

	static void solve40() {
		RushHour rh = new RushHour();
		rh.nbcars = 13;
		rh.color = new String[] { "rouge", "jaune", "vert clair", "orange", "bleu clair", "rose", "violet clair",
				"bleu", "violet", "vert", "noir", "beige", "jaune clair" };
		rh.horiz = new boolean[] { true, false, true, false, false, false, false, true, false, false, true, true,
				true };
		rh.len = new int[] { 2, 3, 2, 2, 2, 2, 3, 3, 2, 2, 2, 2, 2 };
		rh.moveon = new int[] { 2, 0, 0, 4, 1, 2, 5, 3, 3, 2, 4, 5, 5 };
		State s = new State(new int[] { 3, 0, 1, 0, 1, 1, 1, 0, 3, 4, 4, 0, 3 }, rh);
		s = rh.solveAstar(s);
		rh.printSolution(s);
	}

	static void solveAstar() {
		RushHour rh = new RushHour();
		rh.nbcars = 12;
		rh.color = new String[] { "rouge", "vert clair", "jaune", "orange", "violet clair", "bleu ciel", "rose",
				"violet", "vert", "noir", "beige", "bleu" };
		rh.horiz = new boolean[] { true, false, true, false, false, true, false, true, false, true, false, true };
		rh.len = new int[] { 2, 2, 3, 2, 3, 2, 2, 2, 2, 2, 2, 3 };
		rh.moveon = new int[] { 2, 2, 0, 0, 3, 1, 1, 3, 0, 4, 5, 5 };
		State s = new State(new int[] { 1, 0, 3, 1, 1, 4, 3, 4, 4, 2, 4, 1 }, rh);
		s = rh.solveAstar(s);
		rh.printSolution(s);
	}
}
