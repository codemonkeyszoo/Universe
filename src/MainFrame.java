/*
 * MainFrame.java
 * 
 * Copyright (C) 2013 CodeMonkeysZoo (code.monkeys.zoo@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Главное окно программы.
 *
 * @author pvp
 * 
 */
public class MainFrame extends JFrame implements WindowListener, MouseListener, UniverseListener {

	/**
	 * Конструктор по умолчанию.
	 * 
	 */
	public MainFrame() {

		// Создаем Вселенную
		universe = UniverseFactory.createUniverse();

		initialize();
	}


	/*
	 * Элементы управления...
	 *
	 */
	private UniversePanel panel_Universe = null;

	private JPanel panel_Buttons = null;
	
	private JButton button_TimeRun  = null;
	private JButton button_TimeStop = null;

	/**
	 * Этот метод выполняет инициализацию.
	 * 
	 */
	private void initialize() {
		
		setTitle("Gravity");
		setSize(1024, 768);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(this);
		addMouseListener(this);

		setLayout(new BorderLayout());

		// Добавляем панель отображающую состояние Вселенной
		add(panel_Universe = new UniversePanel(), BorderLayout.CENTER);

		// Добавляем панель кнопок
		add(panel_Buttons = new JPanel(new FlowLayout()), BorderLayout.SOUTH);

		// Добавляем кнопку запуска времени
		button_TimeRun = new JButton();

		button_TimeRun.setText("Run");

		button_TimeRun.setMinimumSize  (new Dimension(100, 45));
		button_TimeRun.setMaximumSize  (new Dimension(100, 45));
		button_TimeRun.setPreferredSize(new Dimension(100, 45));
		button_TimeRun.setSize         (new Dimension(100, 45));

		button_TimeRun.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("Starting Universe thread...");

				universeThread = new Thread(universe);
				universeThread.start();
			}
		});

		panel_Buttons.add(button_TimeRun);

		// Добавляем кнопку останова времени
		button_TimeStop = new JButton();

		button_TimeStop.setText("Stop");

		button_TimeStop.setMinimumSize  (new Dimension(100, 45));
		button_TimeStop.setMaximumSize  (new Dimension(100, 45));
		button_TimeStop.setPreferredSize(new Dimension(100, 45));
		button_TimeStop.setSize         (new Dimension(100, 45));

		button_TimeStop.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (universeThread != null) {
					
					System.out.println("Stop Universe thread.");

					universeThread.interrupt(); universeThread = null;
				}
			}
		});

		panel_Buttons.add(button_TimeStop);

		// Подписываемся на события Вселенной
		universe.addListener(this);
	}


	/**
	 * Вселенная.
	 * 
	 */
	public Universe universe = null;

	/**
	 * Поток Вселенной.
	 *
	 */
	private Thread universeThread = null;


	/**
	 * @see WindowListener
	 *
	 */
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {

		if (universeThread != null) {

			System.out.println("Stop Universe thread because window is closing.");

			universeThread.interrupt(); universeThread = null;
		}
	}

	public void windowClosed     (WindowEvent e) {}
	public void windowIconified  (WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated  (WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}


	/**
	 * @see MouseListener
	 *
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		Point P = panel_Universe.getMousePosition();

		if (P == null) return ;

		double x = P.getX() - panel_Universe.getWidth () / 2;
		double y = P.getY() - panel_Universe.getHeight() / 2;

		universe.add(new UPoint(x, -y, 1000.0));
	}

	public void mousePressed (MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited  (MouseEvent e) {}


	/**
	 * @see UniverseListener
	 *
	 */
	@Override
	public void onUniverseChange(Universe universe) {

		Runnable R = new Runnable() {

			@Override
			public void run() {

				repaint();
			}
		};

		SwingUtilities.invokeLater(R);
	}

	@Override
	public void onUniverseTimeStep(Universe universe) {

		Runnable R = new Runnable() {

			@Override
			public void run() {

				repaint();
			}
		};

		SwingUtilities.invokeLater(R);
	}


	/**
	 * Панель отображающая состояние Вселенной.
	 * 
	 */
	public class UniversePanel extends JPanel {

		/**
		 * Конструктор по умолчанию.
		 *
		 */
		public UniversePanel() {

			initialize();
		}

		/**
		 * Этот метод выполняет инициализацию.
		 *
		 */
		private void initialize() {

			setBackground(Color.WHITE);
		}
		

		@Override
		public void paint(Graphics G) {

			super.paint(G);

			// Расчитываем экранные координаты начала отсчета системы координат
			int x0 = getWidth () / 2;
			int y0 = getHeight() / 2;

			// Рисуем оси координат
			G.drawLine(x0, y0 - 1000, x0, y0 + 1000);
			G.drawLine(x0 - 1000, y0, x0 + 1000, y0);

			// Отображаем состояние Вселенной
			UniverseHelper.render(G, universe, x0, y0);
		}
		
	}
	
}
