package iP;

public class SumIP implements IP {

	public int[] exec(int[] in) {
		int[] out = new int[1];
		out[0] = 0;
		for(int i=0; i<in.length; i++)
			out[0] += in[i];
		return out;
	}
	
	public String toString() {
		return "Somme";
	}


}
