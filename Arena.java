import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;

public class Arena {
    private List<Combatente> ladoA;
    private List<Combatente> ladoB;
    private Random random = new Random(); 

    public Arena() {
        //instancia.
        ladoA = new ArrayList<>();
        ladoB = new ArrayList<>();
    }
    public List<Combatente> getLadoA(){return ladoA;}
    public List<Combatente> getLadoB(){return ladoB;}

    public void adicionarCombatente(Combatente c, char lado){
        if (lado == 'A') {
            ladoA.add(c);
        } else {
            ladoB.add(c);
        }
    }
    public boolean equipeViva(List<Combatente> equipe){
        for (Combatente c : equipe) {
            if (c.estaVivo()){
                return true;
            }
         }
        return false;
    }
    public String processarTurnos(){
        StringBuilder log = new StringBuilder(); 
        List<Combatente> todos = new ArrayList<>();
        todos.addAll(ladoA);
        todos.addAll(ladoB);    
//embaralha pra ordem de ataque ser aleatoria
        Collections.shuffle(todos);
        for (Combatente atacante : todos){
            if (!atacante.estaVivo()) continue;

            List<Combatente> inimigos;
            if (ladoA.contains(atacante)){
                inimigos = ladoB;
            }else{
                inimigos = ladoA;
            }
            List<Combatente> alvosVivos = new ArrayList<>();
            for (Combatente vitima : inimigos){
                if(vitima.estaVivo()){
                    alvosVivos.add(vitima);
                }
            }
//se tiver alvo, escolhe um no random e aplica o dano
            if (alvosVivos.size() > 0) {
                Combatente alvo = alvosVivos.get(random.nextInt(alvosVivos.size()));
                int dano = atacante.calcularAtaque();
    
                log.append(atacante.getNome() + " ataca " + alvo.getNome() + " causando " + dano + ".\n");            
                alvo.receberDano(dano);
            }
        }
        //retorna o texto todo pro swing exibir
        return log.toString();
    }
//metodo pra mostrar o resultado
    public String getResultadoFinal() {
        if (equipeViva(ladoA)) {
            return "DOMINIO DA LUZ! O Lado A venceeeu!!!";
        } else if (equipeViva(ladoB)) {
            return "DOMINIO DA SOMBRA! O Lado B venceeeu!!!";
        } else {
            return "EMPATE! Acirrado demais :)";
        }
    }
}