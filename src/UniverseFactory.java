/*
 * UniverseFactory.java
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
 * Фабрика Вселенных.
 *
 * @author pvp
 * 
 */
public class UniverseFactory {

	/**
	 * Этот метод создает Вселенную.
	 *
	 * @return Universe
	 * 
	 */
	public static Universe createUniverse() {

		return new Universe();
	}

	/**
	 * Этот метод создает Солнечную систему.
	 *
	 * Солнце и планеты представлены материальными точками. Массы
	 * измеряются в 10E+24 кг. Расстояние от Солнца измеряется
	 * в астрономических единицах.
	 *
	 * Данные о массах с сайта http://ssd.jpl.nasa.gov/?planets
	 *
	 * Данные о расстоянии от Солнца и скоростях с сайта
	 * http://heavens-above.com
	 *
	 * http://www.stellarium.org/
	 *
	 * @return Universe
	 * 
	 */
	public static Universe createSolarSystem() {

		Universe U = new Universe();

		U.add(new UPoint("Sun", 0, 0, 300000));
		
		U.add(new UPoint("Mercury", 0.46, 0,    0.330104, new UVector(0, 48.00)));
		U.add(new UPoint("Venus",   0.72, 0,    4.86732 , new UVector(0, 35.13)));
		U.add(new UPoint("Earth",   1.02, 0,    5.97219 , new UVector(0, 29.29)));
		U.add(new UPoint("Mars",    1.46, 0,    0.641693, new UVector(0, 25.12)));
		U.add(new UPoint("Jupiter", 4.95, 0, 1898.13    , new UVector(0, 13.70)));
		U.add(new UPoint("Saturn",  9.64, 0,  568.319   , new UVector(0,  9.54)));
		U.add(new UPoint("Uranus", 20.08, 0,   86.8103  , new UVector(0,  6.49)));
		U.add(new UPoint("Neptun", 30.00, 0,  102.410   , new UVector(0,  5.44)));
		U.add(new UPoint("Pluto",  32.06, 0,    0.01309 , new UVector(0,  5.73)));

		return U;
	}

}
