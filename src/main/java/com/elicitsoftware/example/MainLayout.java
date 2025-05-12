package com.elicitsoftware.example;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.quarkus.oidc.OidcSession;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

public class MainLayout extends AppLayout {

    @Inject
    OidcSession oidcSession;

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
        SideNav nav = getsideNav();
        Scroller navScroller = new Scroller(nav);
        navScroller.setClassName(LumoUtility.Padding.SMALL);
        addToDrawer(navScroller);
        addToDrawer(getAuthButton());
    }

    private SideNav getsideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Home @AnonymousAllowed", "/",
                        VaadinIcon.INFO.create()),
                new SideNavItem("User @RolesAllowed(\"user\")", "/basic", VaadinIcon.USER.create()),
                new SideNavItem("Admin @RolesAllowed(\"admin\")", "/admin", VaadinIcon.LOCK.create())

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

