package com.elicitsoftware.example;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "admin", layout = MainLayout.class)
@RolesAllowed("admin")
public class AdminView extends Div {
    public AdminView() {
        add(new H1("Admin View"));
        add(new H2("This is a basic view and only available to authenticated users with the role admin"));
    }
}
