
public class Runner {
	
	public static void main(String[] args) {
		String filePATH = "src/res/corncob_lowercase.txt";
		Solver BS = new Solver(filePATH);	
		Board b1 = new Board("hefodeilwvacstql"); //supposed best 4x4 from online post
		
		//analyze computation time
		double startTime = System.nanoTime();
		
		b1.printBoard();
		b1.setSolution(BS.solveBoard(b1));
		b1.printUniqueWords();
		System.out.println("\n");
		
		double endTime = System.nanoTime();
		double elapsedTime = endTime-startTime;
		double elapsedTimeMiliSeconds = elapsedTime / 1000000000;

		System.out.println("\nSolved in Miliseconds" + elapsedTimeMiliSeconds);
		 
	}
	
}
