package de.haukeingmar.wicketrepeater.web;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import de.haukeingmar.wicketrepeater.dao.SampleDataCreator;

public class WicketApplication extends WebApplication implements ApplicationContextAware {

	private ApplicationContext context;

	public WicketApplication() {
	}

	@Override
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));

		SampleDataCreator dataCreator = context.getBean(SampleDataCreator.class);

		dataCreator.createSampleData(1000);
	}

	@Override
	public void setApplicationContext(final ApplicationContext ctx) throws BeansException {
		context = ctx;
	}

}
