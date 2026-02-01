import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class testeCacador2 {
    private Cacador cacador;

    @BeforeEach
    public void preparar() {
        cacador = new Cacador("Caçador A0", 5);
    }
    @Test
    public void testeCriacao() {
        assertEquals("Caçador A0", cacador.getNome());
        assertTrue(cacador.estaVivo());
        assertEquals(70, cacador.getPv());
    }
    @Test
    public void testAtaqueBasico() {
        int dano = cacador.calcularAtaque();
        assertTrue(dano == 20 || dano == 40, "Dano deve ser 20 ou 40");
    }
    @Test
    public void testeReceberDano() {
        cacador.receberDano(30);
        assertEquals(40, cacador.getPv());
        assertTrue(cacador.estaVivo());
    }
}