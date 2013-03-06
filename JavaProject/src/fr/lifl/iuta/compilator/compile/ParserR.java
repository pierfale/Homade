package fr.lifl.iuta.compilator.compile;

import fr.lifl.iuta.compilator.exception.UnknownInstructionException;

public class ParserR {

	/**
	 * Convertie un code en assembleur en tableau instruction 64 bit
	 * @param source : Code source en assembleur
	 * @return tableau d'instruction binaire
	 * @throws UnknownInstructionException 
	 * @throws IncorrectFormatSource : Code assembleur incorrecte
	 */

	public static String BinToAsm(long[] source) throws UnknownInstructionException {

		String retour = "";
		long instrTmp;

//		for(int i = 0; i<source.length; i++){
//			System.out.println(String.format("%x",source[i])+"\n");
//		}


		for(int i = 0; i<source.length; i++){
			for(int j =0; j < 4; j++) {
				long mask = (long)0xFFFF << (16*(3-j));				
				instrTmp = source[i] & mask;
				short instr = (short) (instrTmp >> (16*(3-j)));
				if(instr==0)instr = (short)0xffff;
				int tmp = ((int)(instr>>10)&0x003F);
				
				switch(tmp){
				case 0 : 
					retour += "BR " + (instr&0x3FF) + "\n";
					break;
				case 1 :
					retour += "BZ " + (instr&0x3FF) + "\n";
					break;
				case 2 :
					retour += "BNZ " + (instr&0x3FF) + "\n";
					break;
				case 3 :
					retour += "BA " + + ((int)(source[i]>>16)&0xFFFF) + "\n";
					j=2;
					break;
				case 4 :	
					retour += "CALL " + ((int)(source[i]>>16)&0xFFFF) + "\n";
					j=2;
					break;
				case 5 :
					retour += "RET " + "\n";
					break;
				case 7 :
					retour += "HALT " + "\n";
					break;
				default :
					if(tmp >= 32){
						if((int)instr == -1){
							//retour += "NULL" + "\n";
							continue;
						}
						else{
							retour += "IP " + ((tmp&0x18)>>3) + " " + ((tmp&0x6)>>1) + " " + (tmp&0x1) + " " + (instr&0x3FF) + "\n";
						}
					}
					else if (tmp >= 24){
						retour += "WAIT " + "\n";
					}
					else if (tmp >= 16){
						retour += "SPMID " + (instr&0x1FFF) + "\n";
					}
					else if (tmp >= 12){
						retour += "WIM " + (instr&0x3FF) + "\n";
					}
					else if (tmp >= 8){
						retour += "LIT " + (instr&0x3FF) + "\n";
					}
					else {
						throw new UnknownInstructionException(retour);
					}
					break;
				}			
			}
		}
		//System.out.println(retour);
		return retour;
	}
}

