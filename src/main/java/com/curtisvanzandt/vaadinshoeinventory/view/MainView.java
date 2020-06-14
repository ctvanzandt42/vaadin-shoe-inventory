package com.curtisvanzandt.vaadinshoeinventory.view;

import com.curtisvanzandt.vaadinshoeinventory.model.Shoe;
import com.curtisvanzandt.vaadinshoeinventory.repository.ShoeRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

@Route
public class MainView extends VerticalLayout {

    private final ShoeRepository repository;
    private final ShoeEditor editor;
    final Grid<Shoe> grid;
    final TextField filter;
    private final Button addNewBtn;

    public MainView(ShoeRepository repository, ShoeEditor editor) {
        this.repository = repository;
        this.editor = editor;
        this.grid = new Grid<>(Shoe.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New shoe", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "brand", "size");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filter.setPlaceholder("Filter by brand name");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listShoes(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editShoe(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.editShoe(new Shoe("", null)));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listShoes(filter.getValue());
        });

        listShoes(null);
    }

    private void listShoes(String filterText) {
        if (StringUtils.isEmpty(filterText))
            grid.setItems(repository.findAll());
        else
            grid.setItems(repository.findByBrandStartsWithIgnoreCase(filterText));
    }
}
