/*
 * UPoint.java
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

/**
 * Материальная точка.
 *
 * @author pvp
 * 
 */
public class UPoint {

	// Имя точки
	public String name = "";

	// Положение точки в пространстве
	public UVector position = new UVector(0.0, 0.0);

	// Масса точки
	public double m = 0.0;

	// Скорость точки
	public UVector velocity = new UVector(0.0, 0.0);

	// Ускорение точки
	public UVector acceleration = new UVector(0.0, 0.0);

	// Сила гравитации в точке
	public UVector gravity = new UVector(0.0, 0.0);

	
	/**
	 * Конструктор по умолчанию.
	 *
	 */
	public UPoint() {
	}

	// Параметризованные конструкторы

	public UPoint(double x, double y, double m) {

		this.position.x = x;
		this.position.y = y;
		this.m = m;
	}

	public UPoint(String name, double x, double y, double m) {

		this.name = name;
		this.position.x = x;
		this.position.y = y;
		this.m = m;
	}

	public UPoint(String name, double x, double y, double m, UVector velocity) {

		this.name = name;
		this.position.x = x;
		this.position.y = y;
		this.m = m;
		this.velocity = velocity;
	}

}
