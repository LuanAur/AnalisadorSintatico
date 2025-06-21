/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analises;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author valter
 */
public class TabelaReservadas {
    Map<Integer, String> tabela = new HashMap<Integer, String>();
    
    public TabelaReservadas() {
        tabela.put(1, "program");
        tabela.put(2, "uses");
        tabela.put(3, "begin");
        tabela.put(4, "end");
        tabela.put(5,"write");
        tabela.put(6, "writeln");
        tabela.put(7, "read");
        tabela.put(8, "readln");
        tabela.put(9,"array");
        tabela.put(10,"const");
        tabela.put(11,"var");
        tabela.put(12,"integer");
        tabela.put(13,"real");
        tabela.put(14,"boolean");
        tabela.put(15,"char");
        tabela.put(16,"string");
        //Todo: Adicionar demais palavras reservadas
    }
    
    public Boolean estaContido(String valor) {
        return tabela.containsValue(valor);
    }
    
}
