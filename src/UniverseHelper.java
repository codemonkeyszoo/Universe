/*
 * UniverseHelper.java
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

import java.awt.Color;
import java.awt.Graphics;

/**
 * Вспомогательный класс для работы во Вселенной.
 *
 * @author pvp
 * 
 */
public class UniverseHelper {

	/**
	 * Этот метод отображает состояние вселенной.
	 *
	 * @param G графический контекст для рисования,
	 * @param U Вселенная,
	 * @param x0 экранная координата X начала отсчета системы координат,
	 * @param y0 экранная координата Y начала отсчета системы координат.
	 *
	 */
	public static void render(Graphics G, Universe U, int x0, int y0) {

		// Рисуем точки вселенной
		
		for (UPoint P : U.getPoints()) {

			UVector p = P.position.copy();
			UVector g = P.position.copy().add(P.gravity .copy().scale( 100.0));
			UVector a = P.position.copy().add(P.velocity.copy().scale(1000.0));

			// Рисуем точку

			G.setColor(COLOR_POINTS);
	
			G.fillOval((int)p.x + x0 - 5, y0 - (int)p.y - 5, 10, 10);

			// На каждую точку действует гравитационная сила

			G.setColor(COLOR_GRAVITIES);

			G.drawLine((int)p.x + x0, y0 - (int)p.y, (int)g.x + x0, y0 - (int)g.y);

			// Суперпозиция сил обуславливает ускорение

			G.setColor(COLOR_VELOCITIES);

			G.drawLine((int)p.x + x0, y0 - (int)p.y, (int)a.x + x0, y0 - (int)a.y);
		}
	}


	/*
	 * Цвета...
	 * 
	 */
	private static final Color COLOR_POINTS     = Color.BLUE;
	private static final Color COLOR_GRAVITIES  = Color.RED;
	private static final Color COLOR_VELOCITIES = Color.GREEN;
	
}
