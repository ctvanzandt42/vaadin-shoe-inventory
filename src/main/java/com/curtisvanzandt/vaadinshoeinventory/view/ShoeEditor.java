package com.curtisvanzandt.vaadinshoeinventory.view;

import com.curtisvanzandt.vaadinshoeinventory.model.Shoe;
import com.curtisvanzandt.vaadinshoeinventory.repository.ShoeRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in MainView.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX.
 */
@SpringComponent
@UIScope
public class ShoeEditor extends VerticalLayout implements KeyNotifier {

    private final ShoeRepository repository;

    /**
     * The currently edited shoe
     */
    private Shoe shoe;

    /* Fields to edit properties in Customer entity */
    TextField brand = new TextField("Brand");
    IntegerField size = new IntegerField("Shoe size");

    /* Action buttons */
    // TODO why more code?
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Shoe> binder = new Binder<>(Shoe.class);
    private ChangeHandler changeHandler;

    @Autowired
    public ShoeEditor(ShoeRepository repository) {
        this.repository = repository;

        add(brand, size, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editShoe(shoe));
        setVisible(false);
    }

    void delete() {
        repository.delete(shoe);
        changeHandler.onChange();
    }

    void save() {
        repository.save(shoe);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editShoe(Shoe s) {
        if (s == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = s.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            shoe = repository.findById(s.getId()).get();
        }
        else {
            shoe = s;
        }
        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(shoe);

        setVisible(true);

        // Focus first name initially
        brand.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }

}
