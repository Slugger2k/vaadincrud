package net.homelinux.vaadincrud;

import javax.servlet.annotation.WebServlet;

import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4153548881674980614L;
	private PersonListener crudListener;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();
		Label label = new Label("Vaadin / Hibernate Crud App");
		label.addStyleName("h1");
		layout.addComponent(label);

		GridCrud<Person> crud = new GridCrud<>(Person.class);
		crud.getGrid().setColumns("id", "name", "age", "address", "birthday", "email");
		
		crud.getCrudFormFactory().setUseBeanValidation(true);
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "name", "address", "birthday", "email");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.UPDATE, "name", "address", "birthday", "email");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.READ, "id", "age", "name", "address", "birthday", "email");
		crud.getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, "id", "age", "name", "address", "birthday", "email");
		
		crudListener = new PersonListener();
		crud.setCrudListener(crudListener);

		layout.addComponent(crud);
		setContent(layout);
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8609613425007711957L;

	}
}
