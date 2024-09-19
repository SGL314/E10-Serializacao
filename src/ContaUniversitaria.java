public class ContaUniversitaria extends Conta{
    public ContaUniversitaria(int numero, Cliente dono, double saldo, double limite){
        super(numero,dono,saldo,limite);
        this.setLimite(limite);
    }
    @Override
    public void setLimite(double limite) throws IllegalArgumentException {
        if (limite < 0) throw new IllegalArgumentException();
        if (limite > 500) throw new IllegalArgumentException();
        this.limite = limite;
    }
    @Override
    public double calculaTaxas(){
        return 0;
    }
}



