import javax.swing.*;
import java.awt.*;


public class InterfaceSwing extends JFrame {
    private Arena arena;
    private JTextArea logArea;
    private JPanel painelLadoA, painelLadoB;
    private JLabel statusLabel;


    private int gA = 1, aA = 1, cA = 1;
    private int gB = 1, aB = 1, cB = 1;

    private int nvA = 5, nvB = 5;

    public InterfaceSwing() {
        this.arena = new Arena();
        setTitle("Batalha RPG - Trabalho");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
       
        mostrarConfiguracao();
    }
    private void mostrarConfiguracao() {
        getContentPane().removeAll();
        setLayout(new BorderLayout(10, 10));


        JPanel painelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
       
        // Lado A
        JPanel pA = new JPanel(new GridLayout(5, 1)); 
        pA.setBorder(BorderFactory.createTitledBorder("Equipe Aliança"));
        pA.add(criarControle("Guardiões: ", 1, true));
        pA.add(criarControle("Arcanistas: ", 2, true));
        pA.add(criarControle("Caçadores: ", 3, true));
        pA.add(new JSeparator());
        pA.add(criarControleNivel("Nível da Equipe: ", true));
        // Lado B
        JPanel pB = new JPanel(new GridLayout(5, 1)); 
        pB.setBorder(BorderFactory.createTitledBorder("Equipe Horda"));
        pB.add(criarControle("Guardiões: ", 1, false));
        pB.add(criarControle("Arcanistas: ", 2, false));
        pB.add(criarControle("Caçadores: ", 3, false));
        pB.add(new JSeparator());
        pB.add(criarControleNivel("Nível da Equipe: ", false));

        painelCentral.add(pA);
        painelCentral.add(pB);

        JButton btnLutar = new JButton("COMEÇAR LUTA");
        btnLutar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLutar.addActionListener(e -> iniciarBatalha());

        add(painelCentral, BorderLayout.CENTER);
        add(btnLutar, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
    private JPanel criarControleNivel(String nome, boolean ehLadoA) {
        JPanel painel = new JPanel(new FlowLayout());
        JLabel lblValor = new JLabel(String.valueOf(ehLadoA ? nvA : nvB));
        JButton btnMais = new JButton("+");
        JButton btnMenos = new JButton("-");

        btnMais.addActionListener(e -> {
            if (ehLadoA) nvA++; else nvB++;
            lblValor.setText(String.valueOf(ehLadoA ? nvA : nvB));
        });

        btnMenos.addActionListener(e -> {
            if (ehLadoA) nvA = Math.max(1, nvA - 1); else nvB = Math.max(1, nvB - 1);
            lblValor.setText(String.valueOf(ehLadoA ? nvA : nvB));
        });

        painel.add(new JLabel(nome));
        painel.add(btnMenos);
        painel.add(lblValor);
        painel.add(btnMais);
        return painel;
    }

    private JPanel criarControle(String nome, int tipo, boolean ehLadoA) {
        JPanel painel = new JPanel(new FlowLayout());
        JLabel lblValor = new JLabel("1");
        JButton btnMais = new JButton("+");
        JButton btnMenos = new JButton("-");


        btnMais.addActionListener(e -> {
            alterarValores(tipo, ehLadoA, 1);
            lblValor.setText(String.valueOf(getValor(tipo, ehLadoA)));
        });

        btnMenos.addActionListener(e -> {
            alterarValores(tipo, ehLadoA, -1);
            lblValor.setText(String.valueOf(getValor(tipo, ehLadoA)));
        });

        painel.add(new JLabel(nome));
        painel.add(btnMenos);
        painel.add(lblValor);
        painel.add(btnMais);
        return painel;
    }

    private void alterarValores(int tipo, boolean ehLadoA, int qtd) {
        if (ehLadoA) {
            if (tipo == 1) gA = Math.max(0, gA + qtd);
            if (tipo == 2) aA = Math.max(0, aA + qtd);
            if (tipo == 3) cA = Math.max(0, cA + qtd);
        } else {
            if (tipo == 1) gB = Math.max(0, gB + qtd);
            if (tipo == 2) aB = Math.max(0, aB + qtd);
            if (tipo == 3) cB = Math.max(0, cB + qtd);
        }
    }

    private int getValor(int tipo, boolean ehLadoA) {
        if (ehLadoA) return (tipo == 1) ? gA : (tipo == 2) ? aA : cA;
        return (tipo == 1) ? gB : (tipo == 2) ? aB : cB;
    }

    private void iniciarBatalha() {
        this.arena = new Arena(); 
        for(int i=0; i<gA; i++) arena.adicionarCombatente(new Guardiao("Guardião A"+i, nvA), 'A');
        for(int i=0; i<aA; i++) arena.adicionarCombatente(new Arcanista("Mago A"+i, nvA), 'A');
        for(int i=0; i<cA; i++) arena.adicionarCombatente(new Cacador("Caçador A"+i, nvA), 'A');

        for(int i=0; i<gB; i++) arena.adicionarCombatente(new Guardiao("Guardião B"+i, nvB), 'B');
        for(int i=0; i<aB; i++) arena.adicionarCombatente(new Arcanista("Mago B"+i, nvB), 'B');
        for(int i=0; i<cB; i++) arena.adicionarCombatente(new Cacador("Caçador B"+i, nvB), 'B');

        getContentPane().removeAll();
        configurarTelaCombate();
        revalidate();
    }

    private void configurarTelaCombate() {
        setLayout(new BorderLayout(10, 10));

        statusLabel = new JLabel("Batalha em Andamento", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(statusLabel, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        painelLadoA = new JPanel();
        painelLadoA.setLayout(new BoxLayout(painelLadoA, BoxLayout.Y_AXIS));
        painelLadoB = new JPanel();
        painelLadoB.setLayout(new BoxLayout(painelLadoB, BoxLayout.Y_AXIS));

        JScrollPane scrollA = new JScrollPane(painelLadoA);
        scrollA.setPreferredSize(new Dimension(180, 0));
        scrollA.setBorder(BorderFactory.createTitledBorder("Aliança (Nível " + nvA + ")"));

        JScrollPane scrollB = new JScrollPane(painelLadoB);
        scrollB.setPreferredSize(new Dimension(180, 0));
        scrollB.setBorder(BorderFactory.createTitledBorder("Horda (Nível " + nvB + ")"));

        add(scrollA, BorderLayout.WEST);
        add(scrollB, BorderLayout.EAST);

        JButton btnTurno = new JButton("PRÓXIMO TURNO");
        btnTurno.addActionListener(e -> {
            if (arena.equipeViva(arena.getLadoA()) && arena.equipeViva(arena.getLadoB())) {
                logArea.append(arena.processarTurnos() + "\n");
                atualizarListas();
            } else {
                statusLabel.setText(arena.getResultadoFinal());
                btnTurno.setEnabled(false);
            }
        });
        add(btnTurno, BorderLayout.SOUTH);


        atualizarListas();
    }
    private void atualizarListas() {
        painelLadoA.removeAll();
        for (Combatente c : arena.getLadoA()) {
            JLabel l = new JLabel(c.getNome() + " (" + c.getPv() + " HP)");
            l.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
            if (!c.estaVivo()) l.setForeground(Color.RED);
            painelLadoA.add(l);
        }

        painelLadoB.removeAll();
        for (Combatente c : arena.getLadoB()) {
            JLabel l = new JLabel(c.getNome() + " (" + c.getPv() + " HP)");
            l.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
            if (!c.estaVivo()) l.setForeground(Color.RED);
            painelLadoB.add(l);
        }
        painelLadoA.revalidate();
        painelLadoB.revalidate();
        repaint();
    }
}

