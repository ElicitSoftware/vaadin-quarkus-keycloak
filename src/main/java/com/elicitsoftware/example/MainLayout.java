package com.elicitsoftware.example;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.PostConstruct;

public class MainLayout extends AppLayout {

    VaadinSession session = VaadinSession.getCurrent();

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
    }

    private SideNav getsideNav() {
        SideNav sideNav = new SideNav();

        sideNav.addItem(
                new SideNavItem("Home -- @AnonymousAllowed", "/",
                        VaadinIcon.INFO.create()),
                new SideNavItem("User -- @RolesAllowed(\"user\")", "/basic", VaadinIcon.USER.create()),
                new SideNavItem("Admin @RolesAllowed(\"admin\")", "/admin", VaadinIcon.LOCK.create()),
                // TODO remove respondent form the session.
                //Change this to login if not logged in
                new SideNavItem("Logout", "",
                        VaadinIcon.UNLINK.create())
        );
        return sideNav;
    }
}

