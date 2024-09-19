import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public abstract class Conta implements ITaxas,Serializable {

    private int numero;

    protected Cliente dono;

    private double saldo;

    protected double limite;

    private ArrayList<Operacao> operacoes;

    private int proximaOperacao;
    private String agencia;
                                        
    private static int totalContas = 0;

    public Conta(int numero, String agencia,Cliente dono, double saldo, double limite) {
        this.numero = numero;
        this.dono = dono;
        this.saldo = saldo;
        this.limite = limite;
        this.agencia = agencia;

        this.operacoes = new ArrayList<Operacao>();
        this.proximaOperacao = 0;

        Conta.totalContas++;
    }

    @Override
    public boolean equals(Object obj){
        Conta other = (Conta) obj;
        return this.numero == other.getNumero();
    }

    @Override
    public String toString(){
        String str_oprs = "";
        for (Operacao opr : this.operacoes){
            if (opr == null) continue;
            str_oprs += opr.toString()+"\n";
        }
        return this.numero+"\n"+this.dono+"\n"+this.limite+"\nOperacoes\n"+str_oprs;
    }

    public boolean sacar(double valor)  throws ValorNegativoException,SemLimiteException{
        if (valor < 0) throw new ValorNegativoException("Erro em sacar");
        if (valor <= this.limite) {
            this.saldo -= valor;

            this.operacoes.add(new OperacaoSaque(valor));
            this.proximaOperacao++;
            return true;
        }else throw new SemLimiteException("Chegou no Limite");
    }
                               
    public void depositar(double valor) throws ValorNegativoException{
        if (valor <0) throw new ValorNegativoException("Erro em depositar");
        this.saldo += valor;

        this.operacoes.add(new OperacaoDeposito(valor));
        this.proximaOperacao++;

        
    }

    String getAgencia(){
        return agencia;
    }

    public boolean transferir(Conta destino, double valor)  throws ValorNegativoException,SemLimiteException{
        boolean valorSacado = this.sacar(valor);
        if (valorSacado) {
            
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    public void imprimir() {
        System.out.println("===== Conta " + this.numero + " =====");
        this.dono.imprimir();
                                        
        System.out.println("Saldo: " + this.saldo);
        System.out.println("Limite: " + this.limite);
        System.out.println("====================");
    }

    public void imprimirExtrato(int flag) {
        System.out.println("======= Extrato Conta " + this.numero + "======");
        if (flag == 0)
            for(Operacao atual : this.operacoes) {
                if (atual != null) {
                    atual.imprimir();
                }
            }
        else if (flag == 1){
            ArrayList<Operacao> ordenada = new ArrayList<Operacao>();
            for(Operacao atual : this.operacoes) {
                ordenada.add(atual);
            }
            Collections.sort(ordenada);
            for(Operacao atual : ordenada) {
                if (atual != null) {
                    atual.imprimir();
                }
            }
        }
        System.out.println("====================");
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getDono() {
        return dono;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimite() {
        return limite;
    }

    public static int getTotalContas() {
        return Conta.totalContas;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    public void imprimirExtratoTaxas(){
        System.out.println("=== Extrato de Taxas ===\n");
        double soma = 0;
        System.out.println("Manutenção: "+this.calculaTaxas()+"\n");
        soma += this.calculaTaxas();
        if (this.operacoes.size() >= 1){
            System.out.println("Operações");
            for (Operacao opr : operacoes){
                if (opr == null) break;
                if (opr.tipo == 's'){
                    System.out.println("Saque: "+opr.calculaTaxas());
                    soma += opr.calculaTaxas();
                }else if (opr.tipo == 'd'){
                    System.out.println("Depósito: "+opr.calculaTaxas());
                    soma += opr.calculaTaxas();
                }
            }
        }else{
            System.out.println("Nenhuma operação feita");
        }
        System.out.println("\nTotal: "+((float) Math.round(soma*100))/100);
    }

    public abstract void setLimite(double limite);
    // @Override
    // public abstract double calculaTaxas();
}
