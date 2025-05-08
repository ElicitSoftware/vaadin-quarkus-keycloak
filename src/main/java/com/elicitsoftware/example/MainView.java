package com.elicitsoftware.example;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a button and a click listener.
 */
@Route(value = "", layout = MainLayout.class)
@PermitAll // Allow all authenticated users
public class MainView extends VerticalLayout {

    @Inject
    GreetService greetService;

    @Inject
    SecurityIdentity identity;

    public MainView() {

        if (identity != null && !identity.isAnonymous()) {
            System.out.println("Authenticated user: " + identity.getPrincipal().getName());
            System.out.println("Roles: " + identity.getRoles());
        } else {
            System.out.println("User is not authenticated.");
        }

        if (identity != null && !identity.isAnonymous()) {
            if (identity.getRoles().contains("admin")) {
                add(new Paragraph("You are also an admin!"));
            } else if (identity.getRoles().contains("user")) {
                add(new Paragraph("You are also a user!"));
            }
        } else {
            add(new Paragraph("You are not authenticated."));
        }

        // Use TextField for standard text input
        TextField textField = new TextField("Your name");
        textField.addThemeName("bordered");

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello", e -> {
            add(new Paragraph(greetService.greet(textField.getValue())));
        });

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in
        // shared-styles.css.
        addClassName("centered-content");

        add(textField, button);
    }
}
