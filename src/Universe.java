/*
 * Universe.java
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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Вселенная.
 *
 * @author pvp
 * 
 */
public class Universe implements Runnable {

	/**
	 * Конструктор по умолчанию.
	 * 
	 */
	public Universe() {
	}


	/**
	 * Массив точек вселенной.
	 * 
	 */
	private ArrayList <UPoint> points = new ArrayList <UPoint> ();

	public Collection <UPoint> getPoints() {

		return points;
	}

	public void add(UPoint point) {

		points.add(point); fireChange();
	}

	/**
	 * Гравитационная постоянная вселенной.
	 * 
	 * Определяет силу гравитационного взаимодействия.
	 *
	 */
	private double gravityConstant = 1.0;

	public double getGravityConstant() {

		return gravityConstant;
	}


	@Override
	public void run() {

		while (true) {

			doTimeStep(); fireTimeStep();

			try {

				Thread.sleep(100);

			} catch (InterruptedException E) {

				break;
			}
		}
	}


	/**
	 * Этот метод продвигает время.
	 *
	 */
	public void doTimeStep() {

		// Обновляем гравитационные силы в системе
		updateGravities();

		// Пробуем объединить близко расположенные точки
		tryJoinPoints(1.5);

		// Обновляем ускорения в системе
		updateAccelerations();

		// Обновляем скорости в системе
		updateVelocities(100.0);

		// Обновляем положения в системе
		updatePosition(100.0);
	}


	/**
	 * Этот метод обновляет гравитационные силы,
	 * действующие между точками Вселенной.
	 * 
	 */
	private void updateGravities() {

		//
		// Гравитационная сила в каждой точке является
		// суперпозицией сил, обусловленных остальными
		// точками системы
		//
		for (UPoint P : points) {

			UVector gravity = new UVector();

			int N = 0;
			
			for (UPoint X : points) {

				if (X != P) {

					UVector T = X.position.copy().sub(P.position);
					
					//
					// Сила притяжения прямо пропорциональна сумме
					// масс точек и обратно пропорциональна
					// квадрату расстояния между ними
					//
					double p = gravityConstant * (X.m * P.m) / Math.pow(T.length(), 2);

					// Направление вектора в сторону второй точки
					UVector d = T.normalize();

					// Вычисляем вектор гравитационной силы
					UVector g = d.scale(p);

					// Копим суперпозицию сил
					gravity.add(g);
				}
			}

			// Масштабируем на количество точек
			if (N > 0) gravity.scale(1.0 / (double)N);

			// Сохраняем
			P.gravity = gravity;
		}
	}

	/**
	 * Этот метод пробует объединить близко расположенные точки.
	 *
	 * @param threshold порог удаления точек.
	 * 
	 */
	private void tryJoinPoints(double threshold) {

		int pointIndex1 = 0;

		while (pointIndex1 < points.size()) {

			UPoint P1 = points.get(pointIndex1);

			//
			// Нет смысла проверять предыдущие точки: они уже проверены
			// (верно поскольку расстояние между точками скалярно)
			//
			int pointIndex2 = pointIndex1 + 1;

			while (pointIndex2 < points.size()) {

				UPoint P2 = points.get(pointIndex2);

				// Расчитываем удаление точек
				double L = P2.position.copy().sub(P1.position).length();

				// Проверяем порог
				if (L < threshold) {

					// Гравитация, ускорение и скорость как суперпозиция
					P1.gravity     .add(P2.gravity     ).scale(0.5);
					P1.acceleration.add(P2.acceleration).scale(0.5);
					P1.velocity    .add(P2.velocity    ).scale(0.5);

					// Положение усредняем
					P1.position.add(P2.position).scale(0.5);

					// Массы складываем
					P1.m += P2.m;

					points.remove(P2);

				} else {

					pointIndex2 ++ ;
				}
			}

			pointIndex1 ++ ;
		}
	}

	/**
	 * Этот метод обновляет ускорения точек Вселенной.
	 * 
	 */
	private void updateAccelerations() {

		for (UPoint P : points) {

			//
			// Ускорение из формулы F = m * a
			//
			P.acceleration.set(P.gravity.copy().scale(1.0 / P.m));
		}
	}

	/**
	 * Этот метод обновляет скорости точек Вселенной.
	 *
	 */
	private void updateVelocities(double dT) {

		for (UPoint P : points) {

			P.velocity.add(P.acceleration.copy().scale(dT));
		}
	}

	/**
	 * Этот метод обновляет положения точек Вселенной.
	 * 
	 */
	private void updatePosition(double dT) {
		
		for (UPoint P : points) {

			P.position.add(P.velocity.copy().scale(dT));
		}
	}


	/**
	 * Массив подписчиков.
	 * 
	 */
	private ArrayList <UniverseListener> listeners = new ArrayList <UniverseListener> ();

	public Collection <UniverseListener> getListeners() {

		return listeners;
	}


	/**
	 * Этот метод добавляет подписчика.
	 *
	 * @param L подписчик.
	 *
	 */
	public void addListener(UniverseListener L) {

		listeners.add(L);
	}

	/**
	 * Этот метод удаляет подписчика.
	 *
	 * @param L подписчик.
	 *
	 */
	public void removeListener(UniverseListener L) {

		listeners.remove(L);
	}


	/**
	 * Этот метод оповещает подписчиков об обновлении времени.
	 *
	 */
	public void fireChange() {

		for (UniverseListener L : listeners) L.onUniverseChange(this);
	}

	/**
	 * Этот метод оповещает подписчиков об обновлении времени.
	 * 
	 */
	public void fireTimeStep() {

		for (UniverseListener L : listeners) L.onUniverseTimeStep(this);
	}

}
