package com.elicitsoftware.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;

/**
 * The main view contains a button and a click listener.
 */
@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class MainView extends VerticalLayout {

    @Inject
    GreetService greetService;

    public MainView(SecurityIdentity identity) {
        TextField textField = new TextField("Your name");
        if (identity != null && !identity.isAnonymous()) {
            if (identity.getRoles().contains("admin")) {
                add(new Paragraph(identity.getPrincipal().getName() + " you are an admin!"));
            }
            if (identity.getRoles().contains("user")) {
                add(new Paragraph(identity.getPrincipal().getName() + " you are also a user!"));
            }
            textField.setValue(identity.getPrincipal().getName());
        } else {
            add(new Paragraph("You are not authenticated."));
        }

        // Use TextField for standard text input
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
