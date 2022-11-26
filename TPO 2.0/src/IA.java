import java.util.Vector;

public class IA extends Jugador{
    public IA() {
        super('O');
    }
    private Vector<Integer> espaciosLibres(TaTeTi x){
        Vector<Integer> result = new Vector<Integer>();
        char[] listaTab = x.getTablero();
        for(int i = 0; i < 9; i++){
            if(listaTab[i] == ' ')
                result.add(i);
        }
        return result;
    }
    private TaTeTi variante(TaTeTi tab, int casillero, char jg){
        char[] original = new char[9];
        TaTeTi var;

        char[] tablero = tab.getTablero();
        

        for(int i = 0; i < 9; i++)
            original[i] = tablero[i];

        original[casillero] = jg;

        var = new TaTeTi(); var.inicializar();
        var.setTablero(original);

        return var;
    }
    private PosInd minmax(TaTeTi tab, char x){
        char maxJg = new Humano().getSimbolo();
        char Jg;

        if(x == maxJg)
            Jg = new IA().getSimbolo();
        else
            Jg = maxJg;

        if(tab.Gano()){
            PosInd r;
            if(Jg == maxJg){
                r = new PosInd(-1, espaciosLibres(tab).size() + 1); // -1 es un parametro imposible solo para remplazar
            }
               else{
                r = new PosInd(-1, -1 * espaciosLibres(tab).size() + 1);
            }
            return r;
        }
        else if(tab.lleno()){
            PosInd r =  new PosInd(-1, 0); // empate = 0
            return r;
        }

        PosInd resultado;

        if (x == maxJg)
            resultado = new PosInd(-1,-100000);
        else
            resultado = new PosInd(-1,1000000);

        Vector<Integer> espacios = espaciosLibres(tab);

        for(int i : espacios){
            TaTeTi variante = variante(tab, i, x);
            PosInd camino;

            if (x == maxJg)
                camino = minmax(variante, 'O');
            else
                camino = minmax(variante, 'X');

            camino.setPos(i);

            if(x == maxJg){
                if (camino.getScore() > resultado.getScore())
                    resultado = camino;
            }
            else{
                if (camino.getScore() < resultado.getScore())
                    resultado = camino;
            }
        }
        return resultado;
    }
    public int jugar(TaTeTi x){
        int pos;
        Jugador auxPC = new IA();

        pos = minmax(x, auxPC.getSimbolo()).getPos();
        return pos;
    }
}
