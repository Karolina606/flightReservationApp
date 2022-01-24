package com.client.views.admin;

//import com.controller.EmployeeController;
import com.client.EmployeeRestClient;
import com.model.Employee;
import com.security.SecurityService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("Pracownicy")
@Route(value = "employeesRestApi")
public class EmployeeView extends VerticalLayout {

    Grid<Employee> grid = new Grid<>(Employee.class);
    TextField filterText = new TextField();
    //EmployeeController controller = new EmployeeController();
    EmployeeForm form;

    // Navigation
    Button logoutBtn = new Button("Wyloguj");
    Button employeesNavigateBtn = new Button("Pracownicy");
    Button planesNavigateBtn = new Button("Samoloty");
    Button flightsNavigateBtn = new Button("Loty");
    Button personalDataBtn = new Button("Dane osobowe");

    public EmployeeView(){
        // Collection<Employee> Employee = controller.getEmployee();

        add(new H2("Pracownicy"));

        addClassName("personal-data-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(
                getToolbar(),
                getContent()
        );

        updateList();
    }

    public void updateList() {
        //grid.setItems(service.findAllData(filterText.getValue()));
        List<Employee> employees = EmployeeRestClient.callGetAllEmployeeApi();
        if (employees != null){
            grid.setItems(employees);
        }
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new EmployeeForm(this);
        form.setWidth("25em");
        form.setVisible(false);
    }

    private Component createNavigateButtonLayout() {
        employeesNavigateBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        planesNavigateBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        flightsNavigateBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        personalDataBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        logoutBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);

        employeesNavigateBtn.addClickListener(event -> UI.getCurrent().navigate("employeesRestApi"));
        planesNavigateBtn.addClickListener(event -> UI.getCurrent().navigate("planesRestApi"));
        flightsNavigateBtn.addClickListener(event -> UI.getCurrent().navigate("flightRestApi"));
        personalDataBtn.addClickListener(event -> UI.getCurrent().navigate("personalDataRestApi"));
        logoutBtn.addClickListener(event -> logout());
        return new HorizontalLayout(employeesNavigateBtn, planesNavigateBtn, flightsNavigateBtn, personalDataBtn, logoutBtn);
    }

    public void logout(){
        SecurityService ss = new SecurityService();
        ss.logout();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Nazwisko...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addEmployeeBtn = new Button("Zarządzaj pracownikami");
        addEmployeeBtn.addClickListener(event -> showHideEmployeeManager());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addEmployeeBtn, createNavigateButtonLayout());
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void showHideEmployeeManager() {
        form.setVisible(!form.isVisible());
    }


    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("id", "salary");
        grid.addColumn(Employee::getEmpolyeeRole).setHeader("EmployeeRole");
        grid.addColumn(Employee -> Employee.getPersonalData().getFirstName()).setHeader("FirstName");
        grid.addColumn(Employee -> Employee.getPersonalData().getLastName()).setHeader("LastName");
        grid.addColumn(Employee -> Employee.getPersonalData().getPhoneNumber()).setHeader("Phone");

        grid.addColumn(new ComponentRenderer<>(employee -> {
                    Button deleteBtn = new Button("Usuń");
                    deleteBtn.addClickListener(click -> {
                        EmployeeRestClient.callDeleteEmployeeApi(employee.getId());
                        updateList();
                    });
                    deleteBtn.setWidth("100%");

                    HorizontalLayout editLayout = new HorizontalLayout(deleteBtn);
                    editLayout.setWidth("100%");
                    return editLayout;
                }))
                .setHeader("Usuń");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
