import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main extends JFrame {
		private JPanel outputPanel; // Use a JPanel to display tasks
		private ArrayList<JPanel> taskPanels; // Store the task panels
		private final int TASK_PANEL_HEIGHT = 40; // Fixed height for each task panel

		public Main() {
				setTitle("Task Tracker");
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setSize(400, 400); // Increased height to accommodate the task list

				setLayout(new BorderLayout());

				// Input
				JLabel inputLabel = new JLabel("Task: ");
				JTextField textField = new JTextField(15);
				JButton addButton = new JButton("Add Task");

				JPanel inputPanel = new JPanel();
				inputPanel.add(inputLabel);
				inputPanel.add(textField);
				inputPanel.add(addButton);

				add(inputPanel, BorderLayout.NORTH);

				// Output
				outputPanel = new JPanel();
				outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));

				JScrollPane scrollPane = new JScrollPane(outputPanel);
				add(scrollPane, BorderLayout.CENTER);

				addButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
								String task = textField.getText();
								if (!task.isEmpty()) {
										addTask(task);
										textField.setText("");
								}
						}
				});

				taskPanels = new ArrayList<>(); // Initialize the list of task panels

				setLocationRelativeTo(null);
				setVisible(true);
		}

		private void addTask(String task) {
				JPanel taskPanel = new JPanel();
				taskPanel.setPreferredSize(new Dimension(outputPanel.getWidth(), TASK_PANEL_HEIGHT)); // Set a fixed height
				taskPanel.setLayout(new BorderLayout()); // Use BorderLayout for the task panel

				JPanel textPanel = new JPanel(new BorderLayout()); // Panel for the task text and delete button alignment
				JLabel taskLabel = new JLabel(task);
				JButton deleteButton = new JButton("Delete");

				deleteButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
								outputPanel.remove(taskPanel);
								taskPanels.remove(taskPanel);
								outputPanel.revalidate();
								outputPanel.repaint();
						}
				});

				textPanel.add(taskLabel, BorderLayout.CENTER); // Task label in the center
				textPanel.add(deleteButton, BorderLayout.EAST); // Delete button on the right

				taskPanel.add(textPanel, BorderLayout.CENTER); // Add the textPanel to the center of the task panel

				outputPanel.add(taskPanel);
				taskPanels.add(taskPanel); // Add the task panel to the list
				outputPanel.revalidate(); // Refresh the panel layout
		}

		public static void main(String[] args) {
				SwingUtilities.invokeLater(() -> new Main());
		}
}
