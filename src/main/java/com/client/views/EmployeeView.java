package com.client.views;

//import com.controller.EmployeeController;
import com.client.EmployeeRestClient;
import com.model.Employee;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Pracownicy")
@Route(value = "employeesRestApi")
public class EmployeeView extends VerticalLayout {

    Grid<Employee> grid = new Grid<>(Employee.class);
    TextField filterText = new TextField();
    //EmployeeController controller = new EmployeeController();
    //EmployeeForm form;

    public EmployeeView(){
        // Collection<Employee> Employee = controller.getEmployee();

        add(new H2("Dane osobowe"));

        addClassName("personal-data-view");
        setSizeFull();

        configureGrid();
        //configureForm();

        add(
                getToolbar(),
                getContent()
        );

        updateList();
    }

    public void updateList() {
        //grid.setItems(service.findAllData(filterText.getValue()));
        grid.setItems(EmployeeRestClient.callGetAllEmployeeApi());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
//        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

//    private void configureForm() {
//        form = new EmployeeForm(service, this);
//        form.setWidth("25em");
//        form.setVisible(false);
//    }

    private Component getToolbar() {
        filterText.setPlaceholder("Nazwisko...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addEmployeeBtn = new Button("Manage employee");
        //addEmployeeBtn.addClickListener(event -> showHideEmployeeManager());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addEmployeeBtn);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

//    private void showHideEmployeeManager() {
//        form.setVisible(!form.isVisible());
//    }


    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("id", "salary");
        grid.addColumn(Employee::getEmpolyeeRole).setHeader("EmployeeRole");
        grid.addColumn(Employee -> Employee.getPersonalData().getFirstName()).setHeader("FirstName");
        grid.addColumn(Employee -> Employee.getPersonalData().getLastName()).setHeader("LastName");
        grid.addColumn(Employee -> Employee.getPersonalData().getPhoneNumber()).setHeader("Phone");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
