import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurrencyConverter extends JFrame {
		public JLabel amountLabel, sourceLabel, targetLabel, resultLabel;
		public JTextField amountField;
		public JComboBox<String> sourceCurrencyComboBox, targetCurrencyComboBox;
		public JButton convertButton;

		public CurrencyConverter() {
				setTitle("Currency Converter");
				setSize(300, 200);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setLocationRelativeTo(null);

				amountLabel = new JLabel("Enter the amount to be converted:");
				sourceLabel = new JLabel("Enter the source currency:");
				targetLabel = new JLabel("Enter the target currency:");
				resultLabel = new JLabel();

				amountField = new JTextField(10);
				String[] currencies = {"USD", "EUR", "RUP"};
				sourceCurrencyComboBox = new JComboBox<>(currencies);
				targetCurrencyComboBox = new JComboBox<>(currencies);

				convertButton = new JButton("Convert");
				convertButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
								convertCurrency();
						}
				});

				amountLabel.setHorizontalAlignment(JLabel.CENTER);
				sourceLabel.setHorizontalAlignment(JLabel.CENTER);
				targetLabel.setHorizontalAlignment(JLabel.CENTER);
				resultLabel.setHorizontalAlignment(JLabel.CENTER);

				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				panel.add(amountLabel);
				panel.add(amountField);
				panel.add(sourceLabel);
				panel.add(sourceCurrencyComboBox);
				panel.add(targetLabel);
				panel.add(targetCurrencyComboBox);
				panel.add(convertButton);
				panel.add(resultLabel);

				add(panel);
		}

		private void convertCurrency() {
				double amount;
				try {
						amount = Double.parseDouble(amountField.getText());
				} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
				}

				String sourceCurrency = (String) sourceCurrencyComboBox.getSelectedItem();
				String targetCurrency = (String) targetCurrencyComboBox.getSelectedItem();

				double exchangeRate = getExchangeRate(sourceCurrency, targetCurrency);

				if (exchangeRate == 0) {
						JOptionPane.showMessageDialog(this, "Exchange rate not available for the specified currencies.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
				}

				double convertedAmount = amount * exchangeRate;
				resultLabel.setText(String.format("%.2f %s is equivalent to %.2f %s", amount, sourceCurrency, convertedAmount, targetCurrency));
		}

		private double getExchangeRate(String sourceCurrency, String targetCurrency) {
				if (sourceCurrency.equals("USD") && targetCurrency.equals("EUR")) {
						return 0.91;
				} else if (sourceCurrency.equals("USD") && targetCurrency.equals("RUP")) {
						return 82.88;
				} else if (sourceCurrency.equals("EUR") && targetCurrency.equals("USD")) {
						return 1.09;
				} else if(sourceCurrency.equals("EUR")&& targetCurrency.equals("RUP")){
						return 90.74;
				} else if (sourceCurrency.equals("RUP") && targetCurrency.equals("USD")){
						return 0.012;
				} else if (sourceCurrency.equals("RUP") && targetCurrency.equals("EUR")){
						return 0.011;
				}else {
						return 0;
				}
		}

		public static void main(String[] args) {
				SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
								new CurrencyConverter().setVisible(true);
						}
				});
		}
}
