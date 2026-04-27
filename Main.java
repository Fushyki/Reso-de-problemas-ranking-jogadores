import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

public class Main extends JFrame {

    private BinarySearchTree tree;
    private TreePanel treePanel;

    public Main() {
        tree = new BinarySearchTree();
        CSVLoader.load(tree, "players.csv");

        setTitle("Ranking de Jogadores - ABB");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30, 30, 45));

        treePanel = new TreePanel(tree);
        treePanel.setPreferredSize(new Dimension(1100, 570));
        JScrollPane scrollPane = new JScrollPane(treePanel);
        scrollPane.setBackground(new Color(30, 30, 45));
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = buildControlPanel();
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel buildControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        panel.setBackground(new Color(20, 20, 35));

        Font font = new Font("SansSerif", Font.PLAIN, 13);
        Color fieldBg = new Color(45, 45, 65);
        Color fieldFg = Color.WHITE;

        JLabel nickLabel = new JLabel("Nickname:");
        nickLabel.setForeground(Color.LIGHT_GRAY);
        nickLabel.setFont(font);

        JTextField nickField = new JTextField(12);
        nickField.setBackground(fieldBg);
        nickField.setForeground(fieldFg);
        nickField.setCaretColor(Color.WHITE);
        nickField.setFont(font);

        JLabel rankLabel = new JLabel("Ranking:");
        rankLabel.setForeground(Color.LIGHT_GRAY);
        rankLabel.setFont(font);

        JTextField rankField = new JTextField(6);
        rankField.setBackground(fieldBg);
        rankField.setForeground(fieldFg);
        rankField.setCaretColor(Color.WHITE);
        rankField.setFont(font);

        JButton insertBtn = styledButton("Inserir", new Color(60, 140, 80));
        JButton searchBtn = styledButton("Buscar", new Color(60, 100, 180));
        JButton removeBtn = styledButton("Remover", new Color(180, 60, 60));

        insertBtn.addActionListener(e -> {
            String nick = nickField.getText().trim();
            String rankText = rankField.getText().trim();
            if (nick.isEmpty() || rankText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha nickname e ranking.");
                return;
            }
            try {
                int rank = Integer.parseInt(rankText);
                tree.insert(new Player(nick, rank));
                treePanel.repaint();
                nickField.setText("");
                rankField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ranking deve ser um numero inteiro.");
            }
        });

        searchBtn.addActionListener(e -> {
            String nick = nickField.getText().trim();
            if (nick.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o nickname para buscar.");
                return;
            }
            boolean found = tree.search(nick);
            if (found) {
                JOptionPane.showMessageDialog(this, "Jogador \"" + nick + "\" encontrado na arvore.");
            } else {
                JOptionPane.showMessageDialog(this, "Jogador \"" + nick + "\" nao encontrado.");
            }
        });

        removeBtn.addActionListener(e -> {
            String nick = nickField.getText().trim();
            if (nick.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o nickname para remover.");
                return;
            }
            Player removed = tree.remove(nick);
            if (removed != null) {
                treePanel.repaint();
                JOptionPane.showMessageDialog(this, "Jogador \"" + removed.getNickname() + "\" removido.");
                nickField.setText("");
                rankField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Jogador \"" + nick + "\" nao encontrado.");
            }
        });

        panel.add(nickLabel);
        panel.add(nickField);
        panel.add(rankLabel);
        panel.add(rankField);
        panel.add(insertBtn);
        panel.add(searchBtn);
        panel.add(removeBtn);

        return panel;
    }

    private JButton styledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}