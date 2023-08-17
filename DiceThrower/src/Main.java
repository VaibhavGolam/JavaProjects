import javax.swing.*;
import java.awt.BorderLayout;
import java.util.Random;

class Dice extends JFrame {

		public JButton Jbut;
		public JLabel Jres;

		public Dice() {
				setTitle("Dice thrower");
				setSize(260, 100);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setLocationRelativeTo(null);
				setVisible(true);

				Jbut = new JButton("Throw Dice!");
				Jres = new JLabel();

				Jbut.addActionListener(e -> showInput());

				JPanel panel = new JPanel(new BorderLayout());
				panel.add(Jres, BorderLayout.CENTER);
				panel.add(Jbut, BorderLayout.SOUTH);

				getContentPane().add(panel);
		}

		public void showInput() {
				Random random = new Random();
				int randomNumber = random.nextInt(6) + 1;
				Jres.setText("Dice number: " + randomNumber);
		}

		public static void main(String[] args) {
				SwingUtilities.invokeLater(() -> new Dice());
		}
}
