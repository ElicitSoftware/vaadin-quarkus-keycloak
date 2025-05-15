package com.elicitsoftware.example;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.quarkus.oidc.OidcSession;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

public class MainLayout extends AppLayout {

    @Inject
    JsonWebToken accessToken;

    public MainLayout() {
    }

    @PostConstruct
    public void init() {
        createHeader();
        createNavBar();
    }

    private void createHeader() {
        DrawerToggle toggle = new DrawerToggle();
        Anchor title = new Anchor("/", "OIDC Test");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");
        addToNavbar(toggle, title);
    }

    private void createNavBar() {
        // Create a layout to hold the buttons
        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.setPadding(false);
        buttonLayout.setSpacing(true);

        // Home Button
        Button homeButton = new Button(
                "Home",
                VaadinIcon.INFO.create(),
                e -> com.vaadin.flow.component.UI.getCurrent().navigate(MainView.class)
        );
        homeButton.setWidthFull();
        buttonLayout.add(homeButton);

        // User Button
        Button userButton = new Button(
                "User",
                VaadinIcon.USER.create(),
                e -> com.vaadin.flow.component.UI.getCurrent().navigate(BasicView.class)
        );
        userButton.setWidthFull();
        buttonLayout.add(userButton);

        // Admin Button
        Button adminButton = new Button(
                "Admin",
                VaadinIcon.USER_STAR.create(),
                e -> com.vaadin.flow.component.UI.getCurrent().navigate(AdminView.class)
        );
        adminButton.setWidthFull();
        buttonLayout.add(adminButton);

        // Logout Button
        Button logoutButton = new Button(
                "Logout",
                VaadinIcon.UNLINK.create()
        );
        logoutButton.addClickListener(e -> {
            VaadinSession.getCurrent().close();
            com.vaadin.flow.component.UI.getCurrent().getPage().setLocation("/logout");
        });
        logoutButton.setWidthFull();
        buttonLayout.add(logoutButton);

        addToDrawer(buttonLayout);
    }

    private SideNav getsideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Home @AnonymousAllowed", "/",
                        VaadinIcon.INFO.create()),
                new SideNavItem("User @RolesAllowed(\"user\")", "/basic", VaadinIcon.USER.create()),
                new SideNavItem("Admin @RolesAllowed(\"admin\")", "/admin", VaadinIcon.LOCK.create()),
                new SideNavItem("logout", "/logout",
                        VaadinIcon.UNLINK.create())
        );
        return sideNav;
    }

    private Anchor getAuthButton() {

        Anchor anchor;
        if (accessToken.getName() != null) {
            anchor = new Anchor("/logout", "Logout");
            anchor.getElement().setAttribute("router-ignore", "");
        } else {
            anchor = new Anchor("/login", "Login");
            anchor.getElement().setAttribute("router-ignore", "");
        }
        return anchor;
    }

}

