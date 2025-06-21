
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analises;

import javax.swing.JOptionPane;

/**
 *
 * @author valter
 */
public class AnaliseSintatica {
    private Token token;
    private AnaliseLexica mt;
    
    public AnaliseSintatica(String codigo) {
        this.token = new Token();
        this.mt = new AnaliseLexica(codigo);
        PROGRAM();
    }
    
    public void PROGRAM() {
        token = mt.geraToken();
        
        if (token.getStr().toString().equals("program")) {
            token = mt.geraToken();
            
            if (token.getTipo() == TiposToken.ID) {
                mt.getTabelaIdentificadores().adiciona(token.getStr().toString()); //Adiciona o token na tabela de símbolos
                token = mt.geraToken();
                
                if (token.getStr().toString().equals(";")) {
                    LIB();
                    CONST();
                    VAR();
                    
                    if (token.getStr().toString().equals(";")) 
                        token = mt.geraToken();
                    
                    
                    if (token.getStr().toString().equals("begin")) {
                        token = mt.geraToken();
                        
                        LISTA_COM();
                        
                        if (token.getStr().toString().equals("end")) {
                            token = mt.geraToken();
                            
                            if (token.getStr().toString().equals(".")) {
                                JOptionPane.showMessageDialog(null, "Arquivo compilado com sucesso!!!");
                                return;
                            }
                            else ERRO(".", token.getStr().toString());
                        }
                        else ERRO("end", token.getStr().toString()); //Código para aqui, parte 3! Return of the Jedi
                    }
                    else ERRO("begin", token.getStr().toString());
                }
                else ERRO(";", token.getStr().toString());
            }
            else ERRO("identificador", token.getStr().toString());
        }
        else ERRO("program", token.getStr().toString());
    }
    
    public void LIB() {
        token = mt.geraToken();
        
        if (token.getStr().toString().equals("uses")){
            token = mt.geraToken();
            
            if (token.getTipo() == TiposToken.ID) {
                LISTA_ID();
                
                if (token.getStr().toString().equals(";"))
                    return;
                else ERRO(";", token.getStr().toString());
            }
            else ERRO("identificador", token.getStr().toString());
        }
        else return; //λ
        
    }
    
    public void LISTA_ID() {
        token = mt.geraToken();
        if (token.getStr().toString().equals(",")){
            token = mt.geraToken();
            if (token.getTipo() == TiposToken.ID){
                LISTA_ID();
            }
            else ERRO("identificador", token.getStr().toString());
        }
        else return; //λ
    }
    
    public void CONST() {
        if (token.getStr().toString().equals(";")){
            token = mt.geraToken();
            if (token.getStr().toString().equals("const")) {
                token = mt.geraToken();
                DEC_CONST();
            }
        }
        else return; //λ
    }
    
    public void DEC_CONST() {

        if (token.getTipo() == TiposToken.ID) {
            token = mt.geraToken();
            
            if (token.getStr().toString().equals("=")) {
                token = mt.geraToken();
                
                if (token.getTipo() == TiposToken.CTE) {
                    token = mt.geraToken();
                    
                    if (token.getStr().toString().equals(";")) {
                        token = mt.geraToken();
                        if (token.getStr().toString().equals("var")){
                            return;
                        }else{
                            DEC_CONST2();
                        }
                    }
                    else ERRO(";", token.getStr().toString());
                }
                else ERRO("constante", token.getStr().toString());
            }
            else ERRO("=", token.getStr().toString());
        }
        else ERRO("identificador", token.getStr().toString());
    }
    
    public void DEC_CONST2() {
        if (token.getTipo() == TiposToken.ID) {
            DEC_CONST();
        }
        return; //λ
    }
    
    public void VAR() {
    	 if (token.getStr().toString().equals("var")) {
    	        token = mt.geraToken();
    	        DEC_VAR();
    	    }
        return; //λ
    }
    
    public void DEC_VAR() {
        LISTA_ID(); 
        if (token.getStr().toString().equals(":")) {
            token = mt.geraToken();
            DEC_VAR2();
        } else {
            ERRO(":", token.getStr().toString()); //Código para aqui
        }
    }

    public void DEC_VAR2() {
        if (token.getStr().toString().equals("^")) {
            token = mt.geraToken();
            DEC_VAR3();
        } else {
            DEC_VAR3();
        }
    }
    
    public void DEC_VAR3() {
        if (TIPO(token.getStr().toString())) {
            token = mt.geraToken();
            if (token.getStr().toString().equals(";")) {
                token = mt.geraToken();
                if (token.getStr().toString().equals("begin")){
                    return;
                }else{
                    DEC_VAR(); 
                }
            } else {
                ERRO(";", token.getStr().toString());
            }
        } else if (token.getStr().toString().equals("array")) {
            token = mt.geraToken();
            if (token.getStr().toString().equals("[")) {
                token = mt.geraToken();
                INTERVALO();

                if (token.getStr().toString().equals("]")) {
                    token = mt.geraToken();

                    if (token.getStr().toString().equals("of")) {
                        token = mt.geraToken();

                        if (TIPO(token.getStr().toString())) {
                            token = mt.geraToken();
                            
                            if (token.getStr().toString().equals(";")) {
                                token = mt.geraToken();
                                if (token.getStr().toString().equals("begin")){
                                    return;
                                }else{
                                    DEC_VAR(); 
                                }
                            } else ERRO(";", token.getStr().toString());
                        } else ERRO("tipo", token.getStr().toString());
                    } else ERRO("of", token.getStr().toString());
                } else ERRO("]", token.getStr().toString());
            } else ERRO("[", token.getStr().toString());
        } else {
            ERRO("tipo ou array", token.getStr().toString());
        }
    }
    
    public void INTERVALO() {
        if (token.getTipo() == TiposToken.CTE) {
            System.out.println(token.getStr().toString());
            token = mt.geraToken();
            System.out.println(token.getStr().toString());

            if (token.getStr().toString().equals("..")) {
                System.out.println(token.getStr().toString());
                token = mt.geraToken();

                if (token.getTipo() == TiposToken.CTE) {
                    token = mt.geraToken();
                    INTERVALO2();
                } else ERRO("constante", token.getStr().toString());
            } else ERRO("..", token.getStr().toString());
        } else ERRO("constante", token.getStr().toString());
    }

    
    public void INTERVALO2() {
        if (token.getStr().toString().equals(",")) {
            token = mt.geraToken();

            if (token.getTipo() == TiposToken.CTE) {
                token = mt.geraToken();

                if (token.getStr().toString().equals("..")) {
                    token = mt.geraToken();

                    if (token.getTipo() == TiposToken.CTE) {
                        token = mt.geraToken();
                        INTERVALO2(); 
                    } else ERRO("constante", token.getStr().toString());
                } else ERRO("..", token.getStr().toString());
            } else ERRO("constante", token.getStr().toString());
        }
        
    }
    
    public boolean TIPO(String tipo) {
        return tipo.equals("integer") ||
               tipo.equals("real") ||
               tipo.equals("boolean") ||
               tipo.equals("char") ||
               tipo.equals("string");
    }
    public void LISTA_COM() {
        if (token.getTipo() == TiposToken.ID || 
            token.getStr().toString().equals("read") || 
            token.getStr().toString().equals("write")) {

            COMANDO();
            LISTA_COM();
        } 
        else {
            return; // λ
        }
    }

    public void COMANDO() {
        if (token.getStr().toString().equals("end")) {
            return;
        }
            else if (token.getTipo() == TiposToken.ID) {
                ATRIB();
            } else if (token.getStr().toString().equals("read")||token.getStr().toString().equals("readln")) {
                ENTRADA();
            } else if (token.getStr().toString().equals("write")||token.getStr().toString().equals("writeln")) {
                SAIDA();
            } else {
                ERRO("comando (atribuição, read ou write)", token.getStr().toString());
            }
    }

    public void ATRIB() {
        if (token.getTipo() == TiposToken.ID) {
            token = mt.geraToken();
            if (token.getStr().toString().equals(":=")) {
                token = mt.geraToken();
                TERMO();
                    if (token.getStr().toString().equals(";")) {
                        token = mt.geraToken();
                        COMANDO();
                    } else {
                        ERRO(";", token.getStr().toString());
                    }
            } else {
                ERRO(":=", token.getStr().toString()); //Código para aqui 2, electric boogaloo
            }
        } else {
            ERRO("identificador", token.getStr().toString());
        }
    }

    public void ENTRADA() {
        if (token.getStr().toString().equals("read")) {
            token = mt.geraToken();
            if (token.getStr().toString().equals("(")) {
                token = mt.geraToken();
                LISTA_ID();
                if (token.getStr().toString().equals(")")) {
                    token = mt.geraToken();
                    if (token.getStr().toString().equals(";")) {
                        token = mt.geraToken();
                        COMANDO();
                    } else {
                        ERRO(";", token.getStr().toString());
                    }
                } else {
                    ERRO(")", token.getStr().toString());
                }
            } else {
                ERRO("(", token.getStr().toString());
            }
        } else if (token.getStr().toString().equals("readln")) {
            token = mt.geraToken();
            READLN();
        }
    }
    
    public void READLN() {
        if (token.getStr().toString().equals("(")) {
            token = mt.geraToken();
            LISTA_ID();
            if (token.getStr().toString().equals(")")) {
                token = mt.geraToken();
                    if (token.getStr().toString().equals(";")) {
                        token = mt.geraToken();
                        COMANDO();
                    } else {
                        ERRO(";", token.getStr().toString());
                    }
            } else {
                ERRO(")", token.getStr().toString());
            }
        } else {
            return; // λ
        }
    }

    public void SAIDA() {
        if (token.getStr().toString().equals("write")) {
            token = mt.geraToken();
            if (token.getStr().toString().equals("(")) {
                token = mt.geraToken();
                TERMO();
                LISTA_TERMO();
                if (token.getStr().toString().equals(")")) {
                    token = mt.geraToken();
                    if (token.getStr().toString().equals(";")) {
                        token = mt.geraToken();
                        COMANDO();
                    } else {
                        ERRO(";", token.getStr().toString());
                    }
                } else {
                    ERRO(")", token.getStr().toString());
                }
            } else {
                ERRO("(", token.getStr().toString());
            }
        } else if (token.getStr().toString().equals("writeln")) {
            token = mt.geraToken();
            WRITELN();
        }
    }
    
    public void WRITELN() {
        if (token.getStr().toString().equals(";")) {
            token = mt.geraToken();
            COMANDO();
        }
        if (token.getStr().toString().equals("(")) {
            token = mt.geraToken();
            TERMO();
            LISTA_TERMO();
            if (token.getStr().toString().equals(")")) {
                token = mt.geraToken();
                if (token.getStr().toString().equals(")")) {
                    token = mt.geraToken();
                    if (token.getStr().toString().equals(";")) {
                        token = mt.geraToken();
                        COMANDO();
                    } else {
                        ERRO(";", token.getStr().toString());
                    }
                }
            } else {
                ERRO(")", token.getStr().toString());
            }
        } else {
            return; // λ
        }
    }


    public void TERMO() {
        if (token.getTipo() == TiposToken.ID || token.getTipo() == TiposToken.CTE) {
            token = mt.geraToken();
        } else {
            ERRO("identificador ou constante", token.getStr().toString());
        }
    }

    public void LISTA_TERMO() {
        if (token.getStr().toString().equals(",")) {
            token = mt.geraToken();
            TERMO();
            LISTA_TERMO(); 
        } else {
            return; // λ
        }
    }
    

    public void ERRO(String esperado, String obtido) {
        JOptionPane.showMessageDialog(null, "Ocorreu um erro na análise! Era esperado " + esperado + " e foi obtido " + obtido);
        return;
    }
}

