import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		String input1 = JOptionPane.showInputDialog(null, "Enter a number:");
		int n = Integer.parseInt(input1);

		if (n <= 0) {
			JOptionPane.showMessageDialog(null, "Number cannot be zero or negative. Please enter a positive number.");
			return;
		}

		String input2 = JOptionPane.showInputDialog(null, "Enter another number:");
		int m = Integer.parseInt(input2);

		StringBuilder table1 = new StringBuilder();
		for (int i = 1; i <= 10; i++) {
			int result = n * i;
			table1.append(n).append(" x ").append(i).append(" = ").append(result).append("\n");
		}

		StringBuilder table2 = new StringBuilder();
		for (int i = 1; i <= 10; i++) {
			int result = m * i;
			table2.append(m).append(" x ").append(i).append(" = ").append(result).append("\n");
		}

		String outputMessage = "Multiplication Table of " + n + ":\n" + table1 + "\n" +
				"Multiplication Table of " + m + ":\n" + table2;
		JOptionPane.showMessageDialog(null, outputMessage);
	}
}
