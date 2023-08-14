import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends JFrame {
		private static final int BOARD_WIDTH = 20;
		private static final int BOARD_HEIGHT = 20;
		private static final int CELL_SIZE = 20;
		private static final int GAME_SPEED = 100;
		private static final int INITIAL_LENGTH = 3;

		private enum Direction { UP, DOWN, LEFT, RIGHT }

		private Timer gameTimer;
		private Direction direction;
		private LinkedList<Point> snake;
		private Point food;
		private int score;

		public SnakeGame() {
				setTitle("Snake Game");
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setSize(BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE);
				setLocationRelativeTo(null);
				setResizable(false);
				setFocusable(true);

				addKeyListener(new SnakeKeyListener());

				snake = new LinkedList<>();
				int startX = BOARD_WIDTH / 2;
				int startY = BOARD_HEIGHT / 2;
				for (int i = 0; i < INITIAL_LENGTH; i++) {
						snake.add(new Point(startX - i, startY));
				}
				direction = Direction.RIGHT;
				generateNewFood();
				score = 0;

				gameTimer = new Timer(GAME_SPEED, e -> gameLoop());
				gameTimer.start();
		}

		private void generateNewFood() {
				Random random = new Random();
				int x, y;
				do {
						x = random.nextInt(BOARD_WIDTH);
						y = random.nextInt(BOARD_HEIGHT);
				} while (snake.contains(new Point(x, y)));

				food = new Point(x, y);
		}

		private void gameLoop() {
				Point head = snake.getFirst();
				Point newHead;
				switch (direction) {
						case UP:
								newHead = new Point(head.x, head.y - 1);
								break;
						case DOWN:
								newHead = new Point(head.x, head.y + 1);
								break;
						case LEFT:
								newHead = new Point(head.x - 1, head.y);
								break;
						case RIGHT:
								newHead = new Point(head.x + 1, head.y);
								break;
						default:
								newHead = head;
				}

				if (newHead.equals(food)) {
						snake.addFirst(newHead);
						score++;
						generateNewFood();
				} else {
						snake.addFirst(newHead);
						snake.removeLast();
				}

				if (checkCollisions()) {
						gameTimer.stop();
						JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
						int playAgain = JOptionPane.showConfirmDialog(this, "Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
						if (playAgain == JOptionPane.YES_OPTION) {
								restartGame();
						} else {
								System.exit(0);
						}
				}

				repaint();
		}

		private boolean checkCollisions() {
				Point head = snake.getFirst();
				if (head.x < 0 || head.x >= BOARD_WIDTH || head.y < 0 || head.y >= BOARD_HEIGHT) {
						return true; // Collided with the boundary
				}
				for (int i = 1; i < snake.size(); i++) {
						if (head.equals(snake.get(i))) {
								return true; // Collided with the body
						}
				}
				return false;
		}

		private void restartGame() {
				snake.clear();
				int startX = BOARD_WIDTH / 2;
				int startY = BOARD_HEIGHT / 2;
				for (int i = 0; i < INITIAL_LENGTH; i++) {
						snake.add(new Point(startX - i, startY));
				}
				direction = Direction.RIGHT;
				generateNewFood();
				score = 0;
				gameTimer.start();
		}

		@Override
		public void paint(Graphics g) {
				g.clearRect(0, 0, getWidth(), getHeight());

				g.setColor(Color.RED);
				g.fillRect(food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

				g.setColor(Color.GREEN);
				for (Point p : snake) {
						g.fillRect(p.x * CELL_SIZE, p.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				}
		}

		private class SnakeKeyListener implements KeyListener {
				@Override
				public void keyPressed(KeyEvent e) {
						int key = e.getKeyCode();
						if (key == KeyEvent.VK_UP && direction != Direction.DOWN) {
								direction = Direction.UP;
						} else if (key == KeyEvent.VK_DOWN && direction != Direction.UP) {
								direction = Direction.DOWN;
						} else if (key == KeyEvent.VK_LEFT && direction != Direction.RIGHT) {
								direction = Direction.LEFT;
						} else if (key == KeyEvent.VK_RIGHT && direction != Direction.LEFT) {
								direction = Direction.RIGHT;
						}
				}

				@Override
				public void keyTyped(KeyEvent e) {}

				@Override
				public void keyReleased(KeyEvent e) {}
		}

		public static void main(String[] args) {
				SwingUtilities.invokeLater(() -> new SnakeGame().setVisible(true));
		}
}
