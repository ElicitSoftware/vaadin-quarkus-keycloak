package com.elicitsoftware.example;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "basic", layout = MainLayout.class)
@RolesAllowed("user")
public class BasicView extends Div {
    public BasicView() {
        add(new H1("Basic View"));
        add(new H2("This is a basic view and only available to authenticated users"));
    }
}
