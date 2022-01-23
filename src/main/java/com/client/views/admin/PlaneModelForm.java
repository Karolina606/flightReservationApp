package com.client.views.admin;

import com.client.PlaneModelRestClient;
import com.model.PlaneModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class PlaneModelForm extends FormLayout {

	PlaneView planeViewParent;

	TextField planeModelId = new TextField("Id modelu (do usuwania)");
	TextField brand = new TextField("Marka");
	TextField model = new TextField("Model");
	TextField numberOfPilots = new TextField("Liczba pilotów");
	TextField numberOfAttendants = new TextField("Liczba obsługi");
	TextField numberOfSeats = new TextField("Liczba miejsc");
	TextField tankCapacity = new TextField("Pojemnosc baku");

	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button cancle = new Button("Cancle");

	public PlaneModelForm(PlaneView planeViewParent){
		this.planeViewParent = planeViewParent;

		add(planeModelId, brand, model, numberOfPilots, numberOfAttendants, numberOfSeats, tankCapacity, createButtonLayout());
	}

	private Component createButtonLayout() {
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		cancle.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		save.addClickShortcut(Key.ENTER);
		cancle.addClickShortcut(Key.ESCAPE);

		save.addClickListener(event -> addPlaneModel());
		delete.addClickListener(event -> deletePlaneModel());
		return new HorizontalLayout(save, delete, cancle);
	}

	private void deletePlaneModel() {
		Long PlaneModelId = Long.parseLong(planeModelId.getValue());
		PlaneModelRestClient.callDeletePlaneModelApi(PlaneModelId);
		planeViewParent.updateList();
	}

	private void addPlaneModel() {

		String newBrand = brand.getValue();
		String newModelName = model.getValue();
		Integer newNumberOfPilots = Integer.parseInt(numberOfPilots.getValue());
		Integer newNumberOfAttendants = Integer.parseInt(numberOfAttendants.getValue());
		Integer newNumberOfSeats = Integer.parseInt(numberOfSeats.getValue());
		Float newTankCapacity = Float.parseFloat(tankCapacity.getValue());

		PlaneModel newPlaneModel = new PlaneModel(newBrand, newModelName, newNumberOfSeats, newNumberOfPilots, newNumberOfAttendants, newTankCapacity);
		PlaneModelRestClient.callCreatePlaneModelApi(newPlaneModel);

		planeViewParent.updateList();
	}
}
