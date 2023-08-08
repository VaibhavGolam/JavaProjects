import javax.swing.*;
import java.awt.*;
import java.util.Random;

class coinFlip extends JFrame {
		public JButton Jbut;
		public JLabel Jres;

		public coinFlip() {
				setTitle("Coin Flipper");
				setSize(260, 100);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setLocationRelativeTo(null);
				setVisible(true);

				Jbut = new JButton("Flip Coin");
				Jres = new JLabel();
				Jres.setFont(new Font("italic",Font.ITALIC, 20));
				Jres.setHorizontalAlignment(SwingConstants.CENTER);

				Jbut.addActionListener(e -> showInput());

				JPanel panel = new JPanel(new BorderLayout());
				getContentPane().setBackground(Color.decode("#4682A9"));
				panel.add(Jres, BorderLayout.NORTH);
				panel.add(Jbut, BorderLayout.SOUTH);

				getContentPane().add(panel);
		}

		public void showInput() {
				Random random = new Random();
				int randomNumber = random.nextInt(2) + 1;
				if (randomNumber == 1) {
						Jres.setText("Head");
				} else {
						Jres.setText("Tail");
				}
		}

		public static void main(String[] args) {
				SwingUtilities.invokeLater(() -> new coinFlip());
		}
}
