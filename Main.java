import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Apenas inicia a interface
        SwingUtilities.invokeLater(() -> {
            new InterfaceSwing().setVisible(true);
        });
    }
}