package com.lab5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tour {

	// Зберігання проходу міст
	private List<City> tour = new ArrayList<City>();
	private double fitness = 0;
	private int distance = 0;

	private TourManager tourManager = null;

	// Створює порожній тур
	public Tour(TourManager tourManager) {
		this.tourManager = tourManager;
		
		for (int i = 0; i < tourManager.size(); i++) {
			tour.add(null);
		}
	}

	// Створення рамдомного індивідууму
	public void generateIndividual() {

		// Перегляд всіх відстаней міст і додавання їх до проходу
		for (int cityIndex = 0; cityIndex < tourManager.size(); cityIndex++) {
			setCity(cityIndex, tourManager.get(cityIndex));
		}
		// Випадкова зміна проходу
		Collections.shuffle(tour);
	}

	// Отримання міста з проходу
	public City getCity(int tourPosition) {
		return (City) tour.get(tourPosition);
	}

	// Встановлює місто в певному положені проходу
	public void setCity(int tourPosition, City city) {
		tour.set(tourPosition, city);

		// Якщо проходи були замінені то потрібно занулити відстань і
		// пристосованість
		fitness = 0;
		distance = 0;
	}

	// Отримання пристосованості проходу
	public double getFitness() {
		if (fitness == 0) {
			fitness = 1 / (double) getDistance();
		}
		return fitness;
	}

	// Отримання загальної відстанні проходу
	public int getDistance() {
		if (distance == 0) {
			int tourDistance = 0;

			// Перебір міст проходу
			for (int cityIndex = 0; cityIndex < tourSize(); cityIndex++) {

				// Отримання міста з якого рухаємося
				City fromCity = getCity(cityIndex);

				// Місто до якого рухаємося
				City destinationCity;

				// Перевірка чи є останнє місто проходу
				if (cityIndex + 1 < tourSize()) {
					destinationCity = getCity(cityIndex + 1);
				} else {
					destinationCity = getCity(0);
				}

				// Отримання дистаннції між двома містами
				tourDistance += fromCity.distanceTo(destinationCity);
			}
			distance = tourDistance;
		}
		return distance;
	}

	// Отримання кількості міст в проході
	public int tourSize() {
		return tour.size();
	}

	//
	// Перевірка чи прохід містить місту
	public boolean containsCity(City city) {
		return tour.contains(city);
	}

	@Override
	public String toString() {
		String geneString = "|";
		for (int i = 0; i < tourSize(); i++) {
			geneString += getCity(i) + "|";
		}
		return geneString;
	}
}
