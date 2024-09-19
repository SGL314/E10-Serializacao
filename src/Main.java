import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Criando um cliente que é uma pessoa física
        Cliente pessoa = new PessoaFisica("João", "Av. Antonio Carlos, 6627",new Date(), "111.111.111-11", 36, 'm');
        
        // Criando uma conta corrente para esse cliente
        Conta cc = new ContaCorrente(1000,"100",pessoa,100,100);
        cc.setDono(pessoa);
        
        //Fazendo operações de saques e depósitos
        try{
            cc.depositar(1000);
            cc.depositar(2000);
            cc.sacar(100);
            // cc.depositar(-1);
            // cc.sacar(-1);
            //cc.setLimite(-101);
            cc.sacar(200);
            cc.depositar(3000);
            cc.sacar(10);
            cc.sacar(15);
            
            
            cc.imprimirExtrato(1);
            cc.imprimirExtrato(0);
            cc.imprimirExtratoTaxas();
        } catch (SemLimiteException e){
            System.out.println(e+": SLE");
        } catch (ValorNegativoException e){
            System.out.println(e+": VNE");
        } catch (IllegalArgumentException e){
            System.out.println(e+": IAE");
        }
        try{
            String nome = cc.getAgencia()+"-"+cc.getNumero();
            FileOutputStream Fout = new FileOutputStream(nome+".ser");
            ObjectOutputStream out = new ObjectOutputStream(Fout);
            out.writeObject(cc);
            out.close();
            Fout.close();
        } catch (Exception e){

        }


    }
}