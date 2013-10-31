/*
 * UVector.java
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
 * Вектор.
 *
 * @author pvp
 * 
 */
public class UVector {

	// Координаты вектора
	public double x = 0.0;
	public double y = 0.0;


	/**
	 * Конструктор по умолчанию.
	 * 
	 */
	public UVector() {
	}

	// Параметризованные конструкторы

	public UVector(double x, double y) {

		this.x = x;
		this.y = y;
	}

	public UVector(UVector source) {

		this.x = source.x;
		this.y = source.y;
	}


	// Операторы

	public UVector add(UVector v) {

		x += v.x;
		y += v.y;

		return this;
	}

	public UVector sub(UVector v) {

		x -= v.x;
		y -= v.y;

		return this;
	}

	public UVector set(UVector v) {

		x = v.x;
		y = v.y;

		return this;
	}

	/**
	 * Этот метод возвращает длину вектора.
	 *
	 * @return double
	 * 
	 */
	public double length() {

		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Этот метод нормализует вектор (приводит его к единичной окружности).
	 *
	 * @return this
	 * 
	 */
	public UVector normalize() {

		scale(1.0 / length());

		return this;
	}

	/**
	 * Этот метод масштабирует вектор.
	 *
	 * @param factor коэффициент масштабирования.
	 *
	 * @return this
	 * 
	 */
	public UVector scale(double factor) {

		x *= factor;
		y *= factor;

		return this;
	}

	/**
	 * Этот метод возвращает копию вектора.
	 *
	 * @return UVector
	 * 
	 */
	public UVector copy() {

		return new UVector(this);
	}

}
