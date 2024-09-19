public class OperacaoDeposito extends Operacao implements ITaxas{

    public OperacaoDeposito(double valor) {
        super('d', valor);
    }
    @Override
    public String toString(){
        return this.getTipo()+"\n"+this.getData()+"\n"+this.getValor();
    }
    @Override
    public double calculaTaxas(){
        return 0;
    }
}
