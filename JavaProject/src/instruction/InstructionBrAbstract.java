package instruction;

public abstract class InstructionBrAbstract implements Instruction{
	
	protected int offSetAdr;
	
	public InstructionBrAbstract(int OffSetAdr){this.offSetAdr = offSetAdr;}

}
